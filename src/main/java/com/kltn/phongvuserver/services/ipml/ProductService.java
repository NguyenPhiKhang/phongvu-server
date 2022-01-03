package com.kltn.phongvuserver.services.ipml;

import com.kltn.phongvuserver.mappers.impl.ProductDetailDTOMapper;
import com.kltn.phongvuserver.mappers.impl.ProductItemDTOMapper;
import com.kltn.phongvuserver.mappers.impl.SearchProductDTOMapper;
import com.kltn.phongvuserver.models.*;
import com.kltn.phongvuserver.models.dto.InputReviewProductDTO;
import com.kltn.phongvuserver.models.dto.ProductDetailDTO;
import com.kltn.phongvuserver.models.dto.ProductItemDTO;
import com.kltn.phongvuserver.models.dto.SearchProductDTO;
import com.kltn.phongvuserver.models.recommendsystem.DescriptionCountDTO;
import com.kltn.phongvuserver.models.recommendsystem.DocumentProperties;
import com.kltn.phongvuserver.models.recommendsystem.TfidfCalculation;
import com.kltn.phongvuserver.repositories.CategoryRepository;
import com.kltn.phongvuserver.repositories.ProductRepository;
import com.kltn.phongvuserver.repositories.RatingRepository;
import com.kltn.phongvuserver.services.*;
import com.kltn.phongvuserver.utils.CategoryUtil;
import com.kltn.phongvuserver.utils.CommonUtil;
import com.kltn.phongvuserver.utils.ImageUtil;
import com.kltn.phongvuserver.utils.RecommendSystemUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Throwable.class)
public class ProductService implements IProductService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ISeenProductService seenProductService;

    @Autowired
    private IFavoriteService favoriteService;

    @Autowired
    private IRatingService ratingService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IOrderItemService orderItemService;

    @Autowired
    private IImageDataService imageDataService;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private IRecommendRatingService recommendRatingService;

    @Autowired
    private ProductItemDTOMapper productItemDTOMapper;

    @Autowired
    private ProductDetailDTOMapper productDetailDTOMapper;

    @Autowired
    private SearchProductDTOMapper searchProductDTOMapper;

    @Override
    public List<ProductItemDTO> getProductsByCategory(int idCategory, int demand, int brand, int page, int pageSize) {
        Category category = categoryRepository.findCategoryFetchSubCategories(idCategory);
        Set<Integer> listId = new HashSet<>();

        Pageable pageable = CommonUtil.getPageForNativeQueryIsFalse(page, pageSize);
        int pageNew = CommonUtil.getPageForNativeQueryIsTrue(page, pageSize);

        List<Product> products;
        if (category.getName().contains("nhu cầu") || category.getName().contains("thương hiệu")) {
            listId.add(category.getParentCategory().getId());
            CategoryUtil.getListIdCategory(category.getParentCategory(), listId);

            products = productRepository.findProductsByCategoryAndBrandOrDemand(listId, demand, brand, pageNew);
        } else {
            listId.add(category.getId());
            CategoryUtil.getListIdCategory(category, listId);

            products = productRepository.findProductsByCategoryAndBrand(listId, brand, pageable);
        }

        return products.stream().map(p -> productItemDTOMapper.mapRow(p)).collect(Collectors.toList());
    }

    @Override
    public ProductDetailDTO getProductDetailById(int id, int userId) {
        Product product = productRepository.findProductDetailById(id);

        if (userId != 0) {
            seenProductService.createOrUpdateSeenProduct(userId, product);
            if (favoriteService.checkUserLikedProduct(userId, id)) {
                product.setLiked(true);
            }
        }

        return productDetailDTOMapper.mapRow(product);
    }

    @Override
    public Product findProductByIdVisibleTrue(int id) {
        return productRepository.findByIdAndVisibilityTrue(id);
    }

    @Override
    public List<Product> getAllProductVisibility() {
        return productRepository.getProductsVisibilityTrue();
    }

    @Override
    public void reviewProduct(int userId, InputReviewProductDTO inputReview) {
        Rating rating = new Rating();
        rating.setStar(inputReview.getStar());
        rating.setComment(inputReview.getComment());
        rating.setUser(userService.getUserById(userId));
        OrderItem orderItem = orderItemService.getOrderItem(inputReview.getOrderItem());
        orderItem.setReviewStatus(true);
        orderItemService.save(orderItem);
        rating.setProduct(orderItem.getProduct());
        rating.setTimeCreated(new Timestamp(System.currentTimeMillis()));
        rating.setTimeUpdated(new Timestamp(System.currentTimeMillis()));
        rating.setIncognito(inputReview.isIncognito());

        try {
            saveImage(rating, inputReview.getListFiles());
        } catch (RuntimeException ex) {
            ratingRepository.save(rating);
        }

    }

    @Override
    public List<Product> productTopRating(int page, int pageSize) {
        int pageNew = CommonUtil.getPageForNativeQueryIsTrue(page, pageSize);

        float C = productRepository.meanOfVoteAverage();
        float m = productRepository.calculateQuantile();

        return productRepository.topRatingProducts(m, C, pageNew, pageSize);
    }

    @Override
    public List<Product> productRecommendForUser(int userId, int page, int pageSize) {
        RecommendRating recommendRating = recommendRatingService.findRecommendRatingByUserId(userId);
        int start = (page - 1) * pageSize;
        int end = start + pageSize;
        List<Integer> listProducts = Arrays.stream(getSliceOfArray(recommendRating.getProducts().split("-"), start, end)).map(Integer::parseInt).collect(Collectors.toList());
        List<Product> products = productRepository.findProductByListIdProduct(listProducts);
        List<Product> productsReturn = new ArrayList<>();

        listProducts.forEach(l -> {
            products.stream().filter(p -> p.getId() == l).findFirst().ifPresent(productsReturn::add);
        });

        return productsReturn;
    }

    @Override
    public List<HashMap<String, Double>> calcContentBasedTest(String textTest) {

        List<Product> list = productRepository.findProductsByVisibilityTrue();

        HashMap<Product, Double> listProductSearch = RecommendSystemUtil.calcCosineSimilarityText(textTest, list, "all");

        System.out.println(listProductSearch.size());

        HashMap<Product, Double> testSearch = listProductSearch.entrySet().stream().sorted(Map.Entry.<Product, Double>comparingByValue().reversed())
                .limit(10)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        testSearch.forEach((k, v) -> {
            System.out.println(k.getName());
            System.out.println(v);
            System.out.println("____---------------------------_____");
        });

        return null;
    }

    @Override
    public List<SearchProductDTO> getProductSearch(String search, int page, int pageSize) {
        List<Product> list = productRepository.findProductsForSearch();

        System.out.println(page);

        return RecommendSystemUtil.calcCosineSimilarityText(search, list, "name").entrySet().stream().sorted(Map.Entry.<Product, Double>comparingByValue().reversed())
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .map(entry -> searchProductDTOMapper.mapRow(entry))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductItemDTO> getProductsSimilarity(int id, int page, int pageSize) {
        String shortDescOrName = productRepository.getShortDescriptionOrName(id);
        String categoryName = productRepository.findNameCategoryByProduct(id);
//        String productName = productRepository.getNameProduct(id);

        List<Product> list = productRepository.getProductAndShortDescriptionExceptProduct(id);
        List<Product> listProduct = new ArrayList<>(RecommendSystemUtil.calcCosineSimilarityText(shortDescOrName, list, "all").keySet());

        return RecommendSystemUtil.calcCosineSimilarityText(categoryName, listProduct, "category").entrySet().stream()
                .sorted(Map.Entry.<Product, Double>comparingByValue().reversed())
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .map(v -> productItemDTOMapper.mapRow(v.getKey()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductItemDTO> getProductsAlsoLike(int userId, int page, int pageSize, String sameFor) {
        List<Integer> listIdProduct = new ArrayList<>();
        Pageable pageable = CommonUtil.getPageForNativeQueryIsFalse(1, 10);
        Page<Object[]> pageSeen = productRepository.getShortDescriptionOrNameByUser(userId, pageable);
        List<DescriptionCountDTO> listSeen = pageSeen.getContent().stream().map(v -> {
            listIdProduct.add((Integer) v[2]);
            return new DescriptionCountDTO((String) v[0], (int) v[1]);
        }).collect(Collectors.toList());

        List<Product> list = productRepository.getProductAndShortDescriptionExceptListProduct(listIdProduct);
        int noOfDocs = list.size();

        TfidfCalculation TfidfObj = new TfidfCalculation();

        //containers for documents and their properties required to calculate final score
        DocumentProperties[] docProperties = new DocumentProperties[noOfDocs];
        SortedSet<String> wordList = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < noOfDocs; i++) {
            docProperties[i] = TfidfObj.calculateTF(list.get(i), wordList, sameFor);
        }

        //calculating InverseDocument frequency
        HashMap<String, Double> inverseDocFreqMap = TfidfObj.calculateInverseDocFrequency(docProperties, wordList);

        //Calculating tf-idf
        HashMap<Product, HashMap<String, Double>> listTFIDF = new HashMap<>();
        for (int i = 0; i < noOfDocs; i++) {
            listTFIDF.put(list.get(i), TfidfObj.calculateTFIDF(docProperties[i], inverseDocFreqMap));
        }

        SortedSet<String> wordListSearch = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        DocumentProperties documentProperty = TfidfObj.calculateTF(listSeen, wordListSearch);

        HashMap<String, Double> tfidfSearch = TfidfObj.calculateTFIDF(documentProperty, inverseDocFreqMap);

        HashMap<Product, Double> listProductAlsoLike = new HashMap<>();

        for (Map.Entry<Product, HashMap<String, Double>> pd : listTFIDF.entrySet()) {
            Iterator<Map.Entry<String, Double>> it = tfidfSearch.entrySet().iterator();
            double dot_pd = 0.0;
            double norm_search = 0.0;
            while (it.hasNext()) {
                Map.Entry<String, Double> pair = it.next();
                if (pd.getValue().containsKey((String) pair.getKey())) {
                    dot_pd += pd.getValue().get(pair.getKey()) * (double) pair.getValue();
                }
                norm_search += (double) pair.getValue() * (double) pair.getValue();
            }
            double norm_pd = 0.0;
            for (double v : pd.getValue().values()) {
                norm_pd += v * v;
            }

            double cosine = dot_pd / (Math.sqrt(norm_pd) * Math.sqrt(norm_search));
            if (cosine > 0.0)
                listProductAlsoLike.put(pd.getKey(), cosine);
        }

        System.out.println(listProductAlsoLike.size());

        return listProductAlsoLike.entrySet().stream()
                .sorted(Map.Entry.<Product, Double>comparingByValue().reversed())
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .map(v -> productItemDTOMapper.mapRow(v.getKey()))
                .collect(Collectors.toList());
    }

    private void saveImage(Rating rating, List<MultipartFile> files) {
        List<MultipartFile> multipartFiles = new ArrayList<>();
        files.forEach(file -> {
            try {
                String fileName = ImageUtil.fileName(imageDataService, file);
                MultipartFile multipartFile = new MockMultipartFile(fileName, fileName, file.getContentType(), file.getInputStream());
                multipartFiles.add(multipartFile);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        rating.addAllDataImages(imageDataService.storesImageData(multipartFiles));
        ratingRepository.save(rating);
    }

    private String[] getSliceOfArray(String[] arr,
                                     int start, int end) {

        // Get the slice of the Array
        int size = Math.min(end, arr.length) - start;
        String[] slice = new String[Math.max(size, 0)];

        // Copy elements of arr to slice
        for (int i = 0; i < slice.length; i++) {
            slice[i] = arr[start + i];
        }

        // return the slice
        return slice;
    }
}
