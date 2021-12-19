package com.kltn.phongvuserver.controllers.impl;

import com.kltn.phongvuserver.controllers.IProductController;
import com.kltn.phongvuserver.mappers.impl.ProductItemDTOMapper;
import com.kltn.phongvuserver.mappers.impl.RatingRSDTOMapper;
import com.kltn.phongvuserver.models.CosineSimilarity;
import com.kltn.phongvuserver.models.RecommendRating;
import com.kltn.phongvuserver.models.dto.InputReviewProductDTO;
import com.kltn.phongvuserver.models.dto.ProductDetailDTO;
import com.kltn.phongvuserver.models.dto.ProductItemDTO;
import com.kltn.phongvuserver.models.dto.SearchProductDTO;
import com.kltn.phongvuserver.models.recommendsystem.AVGRatedProductDTO;
import com.kltn.phongvuserver.models.recommendsystem.RatingRSDTO;
import com.kltn.phongvuserver.repositories.CosineSimilarityRepository;
import com.kltn.phongvuserver.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1")
@Transactional(rollbackFor = Throwable.class)
public class ProductController implements IProductController {
    @Autowired
    private IProductService productService;

    @Autowired
    private IRatingService ratingService;

    @Autowired
    private IRecommendRatingService recommendRatingService;

    @Autowired
    private ICosineSimilarityService cosineSimilarityService;

    @Autowired
    private CosineSimilarityRepository cosineSimilarityRepository;

    @Autowired
    private IHistorySearchService historySearchService;

    @Autowired
    private RatingRSDTOMapper ratingRSDTOMapper;

    @Autowired
    private ProductItemDTOMapper productItemDTOMapper;

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<ProductItemDTO> getProductsByCategory(int idCategory, int demand, int brand, int page, int pageSize) {
        return productService.getProductsByCategory(idCategory, demand, brand, page, pageSize);
    }

    @Override
    public ProductDetailDTO getProductById(int id, int userId) {
        return productService.getProductDetailById(id, userId);
    }

    @Override
    public ResponseEntity<List<ProductItemDTO>> getProductTopRating(int userId, int page, int pageSize) {
        long startTime = new Date().getTime();
        List<ProductItemDTO> list;

        if (userId == 0 || !(ratingService.checkUserIsRated(userId) > 0)) {
            list = productService.productTopRating(page, pageSize).stream().map(value -> productItemDTOMapper.mapRow(value)).collect(Collectors.toList());
        } else {
            if (!recommendRatingService.checkExistUser(userId)) {
                recommend_product_for_user(userId);
            }
            list = productService.productRecommendForUser(userId).stream().map(value -> productItemDTOMapper.mapRow(value)).collect(Collectors.toList());
        }

        long endTime = new Date().getTime();
        long difference = endTime - startTime;
        System.out.println("Elapsed time in milliseconds: " + difference);
        return ResponseEntity.ok().body(list);
    }

    @Override
    public ResponseEntity<String> createCosineSimilarity() {
        long startTime = new Date().getTime();

        List<Integer> list_product_rated = ratingService.getProductsRated();

        List<RatingRSDTO> listRatingRS = ratingService.getAll().stream().map(value -> ratingRSDTOMapper.mapRow(value)).collect(Collectors.toList());

        List<RatingRSDTO> listRatingNormalized = listRatingRS.stream().map(rt -> new RatingRSDTO(rt.getUserId(), rt.getProductId(), rt.getValue())).collect(Collectors.toList());

        List<AVGRatedProductDTO> listAVG = ratingService.calcAVGRatedProduct();

        normalizedRating(list_product_rated, listRatingNormalized, listAVG);

        List<CosineSimilarity> cosSimilarities = new ArrayList<>();

        // calc cosine similarity
        calcCosineSimilarity(list_product_rated, listRatingNormalized, cosSimilarities);

        cosineSimilarityService.removeAll();
        cosSimilarities.forEach(c -> em.persist(c));
        recommendRatingService.removeAll();

        long endTime = new Date().getTime();
        long difference = endTime - startTime;
        System.out.println("Elapsed time in milliseconds: " + difference);

        return ResponseEntity.ok().body("done");
    }

    @Override
    public ResponseEntity<RecommendRating> recommend_product_for_user(int user_id) {
        long startTime = new Date().getTime();

        if (!(ratingService.checkUserIsRated(user_id) > 0)) {
            return ResponseEntity.ok().body(new RecommendRating(user_id, ""));
        }

        List<RatingRSDTO> listRatingRS = ratingService.getAll().stream().map(value -> ratingRSDTOMapper.mapRow(value)).collect(Collectors.toList());
        List<Integer> list_product = ratingService.getProductsRated();

        List<RatingRSDTO> listRatingNormalized = listRatingRS.stream().map(rt -> new RatingRSDTO(rt.getUserId(), rt.getProductId(), rt.getValue())).collect(Collectors.toList());

        List<AVGRatedProductDTO> listAVG = ratingService.calcAVGRatedProduct();

        // normalized utility matrix
        normalizedRating(list_product, listRatingNormalized, listAVG);

        List<CosineSimilarity> cosSimilarities = cosineSimilarityService.getAll();
        Map<Integer, Double> mapPredictionProduct = new HashMap<>();

        // Rating Prediction
        List<Integer> list_pd_user_rated = new ArrayList<>();
        List<Integer> list_pd_user_unrated = new ArrayList<>();
        divide_rated_unrated(listRatingNormalized, list_product, user_id, list_pd_user_rated, list_pd_user_unrated);
        StringBuilder listProductRS = new StringBuilder();
        StringBuilder listProductRS_Show = new StringBuilder();

        list_pd_user_unrated.parallelStream().forEach(value -> {
            List<CosineSimilarity> list_cos_of_user = new ArrayList<>();
            List<RatingRSDTO> list_normalize_of_user = new ArrayList<>();
            list_cos_of_user = top_k_cosine_similarity_of_user(2, cosSimilarities, value, list_pd_user_rated);

            list_normalize_of_user = top_k_normalized_corresponding_top_k_cosine_similarity(
                    user_id, list_cos_of_user, listRatingNormalized, value);

            double a = list_cos_of_user.get(0).getRow() == list_normalize_of_user.get(0).getProductId() || list_cos_of_user.get(0).getColumn() == list_normalize_of_user.get(0).getProductId() ?
                    list_cos_of_user.get(0).getCosSimilarity() * list_normalize_of_user.get(0).getValue() +
                            list_cos_of_user.get(1).getCosSimilarity() * list_normalize_of_user.get(1).getValue() :
                    list_cos_of_user.get(0).getCosSimilarity() * list_normalize_of_user.get(1).getValue() +
                            list_cos_of_user.get(1).getCosSimilarity() * list_normalize_of_user.get(0).getValue();
            double b = Math.abs(list_cos_of_user.get(0).getCosSimilarity()) + Math.abs(list_cos_of_user.get(1).getCosSimilarity());

            if (a / b > 0) {
                mapPredictionProduct.put(value, a / b);
            }
        });

        mapPredictionProduct.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).limit(10).forEach(value -> {
            listProductRS.append(value.getKey()).append("-");
            listProductRS_Show.append(value.getKey()).append(" - ").append(value.getValue()).append(";");
        });
        if (!recommendRatingService.checkExistUser(user_id))
            recommendRatingService.save(new RecommendRating(user_id, listProductRS.deleteCharAt(listProductRS.length() - 1).toString()));
        else {
            RecommendRating recommendRating = recommendRatingService.getById(user_id);
            recommendRating.setProducts(listProductRS.deleteCharAt(listProductRS.length() - 1).toString());
            recommendRatingService.save(recommendRating);
        }

        long endTime = new Date().getTime();
        long difference = endTime - startTime;
        System.out.println("Elapsed time in milliseconds: " + difference);
        System.out.println("done");

        return ResponseEntity.ok().body(new RecommendRating(user_id, listProductRS_Show.toString()));
    }

    @Override
    public List<HashMap<String, Double>> calculationContentBasedTest(String search) {
        return productService.calcContentBasedTest(search);
    }

    @Override
    public List<SearchProductDTO> searchProduct(String search, int page, int pageSize) {
        return productService.getProductSearch(search, page, pageSize);
    }

    @Override
    public List<SearchProductDTO> searchProduct(int userId, String search, int page, int pageSize) {
        historySearchService.createOrUpdateHistorySearch(userId, search);
        return productService.getProductSearch(search, page, pageSize);
    }

    @Override
    public List<ProductItemDTO> productsSimilarity(int productId, int page, int pageSize) {
        return productService.getProductsSimilarity(productId, page, pageSize);
    }

    @Override
    public List<ProductItemDTO> productsMightAlsoLike(int userId, int page, int pageSize) {
        return productService.getProductsAlsoLike(userId, page, pageSize, "all");
    }

    private void normalizedRating(List<Integer> list_product, List<RatingRSDTO> listRatingNormalized, List<AVGRatedProductDTO> listAVG) {
        int size_list_avg = listAVG.size();
        for (int i = 0; i < size_list_avg; i++) {
            int pd_id = list_product.get(i);
            double avg_pd = listAVG.get(i).getAvgRated();
            listRatingNormalized.parallelStream().
                    filter(value -> value.getProductId() == pd_id).
                    forEach(value -> {
                        value.setValue(value.getValue() - avg_pd);
                    });
        }
    }

    private void divide_rated_unrated(List<RatingRSDTO> list_normalized, List<Integer> list_product, int user_id, List<Integer> list_rated, List<Integer> list_unrated) {
        int size_list_product = list_product.size();
        list_rated.clear();
        list_unrated.clear();
        for (int i = 0; i < size_list_product; i++) {
            int pd = list_product.get(i);
//            if (list_normalized.contains(new RatingRSDTO(user_id, pd)))
            if (list_normalized.stream().anyMatch(value -> value.getUserId() == user_id && value.getProductId() == pd))
                list_rated.add(pd);
            else list_unrated.add(pd);
        }
    }

    private List<CosineSimilarity> top_k_cosine_similarity_of_user(int k, List<CosineSimilarity> list, int pd_unrated_id, List<Integer> list_pd_user_rated) {
        return list.stream().filter(cos -> (cos.getRow() == pd_unrated_id && list_pd_user_rated.contains(cos.getColumn())) ||
                (list_pd_user_rated.contains(cos.getRow()) && cos.getColumn() == pd_unrated_id)).limit(k).collect(Collectors.toList());
    }

    private List<RatingRSDTO> top_k_normalized_corresponding_top_k_cosine_similarity(int user_id, List<CosineSimilarity> list_cos_of_user, List<RatingRSDTO> listRatingNormalized, int pd_unrated_id) {
        return listRatingNormalized.stream().filter(nm -> nm.getUserId() == user_id && ((list_cos_of_user.stream().filter(value -> value.getRow() == pd_unrated_id && value.getColumn() == nm.getProductId()).findAny().orElse(null) != null) ||
                list_cos_of_user.stream().filter(value -> value.getRow() == nm.getProductId() && value.getColumn() == pd_unrated_id).findAny().orElse(null) != null)).limit(2).collect(Collectors.toList());
    }

    private void calcCosineSimilarity(List<Integer> list_product_rated, List<RatingRSDTO> listRatingNormalized, List<CosineSimilarity> cosSimilarities) {
//        int size_list = list_product_rated.size();
//        for (int i = size_list - 1; i > 0; i--) {
//            int product_id1 = list_product_rated.get(i);
//            List<RatingRSDTO> user_rated_product1 = calcUserRatedProduct(listRatingNormalized, product_id1);
//            for (int j = i - 1; j >= 0; j--) {
//                int product_id2 = list_product_rated.get(j);
//                List<RatingRSDTO> user_rated_product2 = calcUserRatedProduct(listRatingNormalized, product_id2);
//
//                double pd1_dot_pd2 = p1_dot_p2(user_rated_product1, user_rated_product2);
//                double norm_pd1 = calc_norm(user_rated_product1);
//                double norm_pd2 = calc_norm(user_rated_product2);
//                double norm_pd1_mul_norm_pd2 = norm_pd1 * norm_pd2;
//                double cosineSimilarity = norm_pd1_mul_norm_pd2 != 0.0 ? pd1_dot_pd2 / norm_pd1_mul_norm_pd2 : -1;
//
//                CosineSimilarity a = new CosineSimilarity(product_id1, product_id2, cosineSimilarity);
//                cosSimilarities.add(a);
//                System.out.println("i: " + i + " - j: " + j);
//            }
//        }

        List<Integer> listExisted = new ArrayList<>();

        cosSimilarities.addAll(list_product_rated.stream().flatMap(l -> {
            List<RatingRSDTO> user_rated_product1 = calcUserRatedProduct(listRatingNormalized, l);
            System.out.println("product: "+ l);
            Stream<CosineSimilarity> listCosine = list_product_rated.stream().filter(i -> !listExisted.contains(i))
                    .map(k-> {
                        List<RatingRSDTO> user_rated_product2 = calcUserRatedProduct(listRatingNormalized, k);

                        double pd1_dot_pd2 = p1_dot_p2(user_rated_product1, user_rated_product2);
                        double norm_pd1 = calc_norm(user_rated_product1);
                        double norm_pd2 = calc_norm(user_rated_product2);
                        double norm_pd1_mul_norm_pd2 = norm_pd1 * norm_pd2;
                        double cosineSimilarity = norm_pd1_mul_norm_pd2 != 0.0 ? pd1_dot_pd2 / norm_pd1_mul_norm_pd2 : -1;

                        return new CosineSimilarity(l, k, cosineSimilarity);
                    });

            listExisted.add(l);
            System.out.println("size: "+ listExisted.size()+ "/ "+list_product_rated.size());
            return listCosine;
        }).collect(Collectors.toList()));
    }

    private List<RatingRSDTO> calcUserRatedProduct(List<RatingRSDTO> list, int pd) {
        return list.stream().filter(ratingRSDTO -> ratingRSDTO.getProductId() == pd).collect(Collectors.toList());
    }

    private double p1_dot_p2(List<RatingRSDTO> list1, List<RatingRSDTO> list2) {
//        int size_list1 = list1.size();
//        int size_list2 = list2.size();
//        double p1_dot_p2 = 0.0;
//        for (int i = 0; i < size_list1; i++) {
//            for (int j = 0; j < size_list2; j++) {
//                RatingRSDTO rt1 = list1.get(i);
//                RatingRSDTO rt2 = list2.get(j);
//                if (rt1.getUserId() == rt2.getUserId()) {
//                    p1_dot_p2 += (rt1.getValue() * rt2.getValue());
//                    break;
//                }
//            }
//        }

//        return IntStream.range(0, list1.size())
//                .filter(v -> list1.get(v).getUserId() == list2.get(v).getUserId())
//                .mapToDouble(v -> list1.get(v).getValue() * list2.get(v).getValue())
//                .sum();

        return list1.stream()
                .mapToDouble(v-> list2.stream().filter(v1->v1.getUserId()==v.getUserId()).mapToDouble(v1->v1.getValue()*v.getValue()).sum())
                .sum();


//        return p1_dot_p2;
    }

    private double calc_norm(List<RatingRSDTO> list) {
//        int size_list = list.size();
//        double norm = 0.0;
//        for (int i = 0; i < size_list; i++) {
//            RatingRSDTO rt = list.get(i);
//            if (rt.getValue() != 0.0) {
//                norm += Math.pow(rt.getValue(), 2);
//            }
//        }
//        return Math.sqrt(norm);

        double norm = list.stream().filter(rt -> rt.getValue() != 0.0).mapToDouble(v -> Math.pow(v.getValue(), 2)).sum();
        return Math.sqrt(norm);
    }


}