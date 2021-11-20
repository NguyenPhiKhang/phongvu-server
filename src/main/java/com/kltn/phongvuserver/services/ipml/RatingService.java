package com.kltn.phongvuserver.services.ipml;


import com.kltn.phongvuserver.models.Product;
import com.kltn.phongvuserver.models.Rating;
import com.kltn.phongvuserver.models.RatingStar;
import com.kltn.phongvuserver.models.User;
import com.kltn.phongvuserver.repositories.ProductRepository;
import com.kltn.phongvuserver.repositories.RatingRepository;
import com.kltn.phongvuserver.repositories.RatingStarRepository;
import com.kltn.phongvuserver.repositories.UserRepository;
import com.kltn.phongvuserver.services.IRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

@Service
@Transactional(rollbackFor = Throwable.class)
public class RatingService implements IRatingService {

    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private RatingStarRepository ratingStarRepository;

    @Override
    public void autoRating() {
        Random rd = new Random();
        List<User> users = userRepository.findAll();
        List<Product> products = productRepository.findProductsByVisibilityTrue();

        int size_product = products.size() - 100;

        System.out.println("size product: " + size_product);

        int q = 100;

        for (int j = 0; j < size_product; j++) {
            Set<Rating> listRatings = new HashSet<>();
            Product pd = products.get(j);
            System.out.println("-----------------product thu: " + j + "--- id: " + pd.getId() + " ------------------------");
            int rating_rd = rd.nextInt(401);
            rating_rd = q > 0 ? rating_rd : rating_rd + q;
            q *= -1;
            int total_rating = rating_rd < 0 ? 0 : rating_rd == 0 ? 500 + rd.nextInt(611) : Math.min(rating_rd, 400);
            System.out.println("tong rating: " + total_rating);
            int star1 = 0;
            int star2 = 0;
            int star3 = 0;
            int star4 = 0;
            int star5 = 0;
            for (int i = 0; i < total_rating; i++) {
//                int idRating;
//                do {
//                    idRating = 10000000 + rd.nextInt(6000001);
//                } while (ratingRepository.existsById(idRating));

                int star = 1 + rd.nextInt(11);
                switch (star) {
                    case 1:
                        star1++;
                        break;
                    case 2:
                        star2++;
                        break;
                    case 3:
                        star3++;
                        break;
                    case 4:
                        star4++;
                        break;
                    default:
                        star5++;
                        break;

                }

                int userIndex;
                User user;
//                do {
                    userIndex = rd.nextInt(users.size());
                    user = users.get(userIndex);
//                } while (ratingRepository.existsByUserIdAndProductId(user.getId(), pd.getId()));


                Rating rating = new Rating();
//                rating.setId(idRating);
                rating.setStar(Math.min(star, 5));
                rating.setUser(user);
                rating.setProduct(pd);
                rating.setTimeCreated(new Timestamp(System.currentTimeMillis()));
                rating.setTimeUpdated(new Timestamp(System.currentTimeMillis()));

                listRatings.add(rating);
            }

//            int idRatingStar;
//            do {
//                idRatingStar = 100 + rd.nextInt(6000001);
//            } while (ratingRepository.existsById(idRatingStar));

            RatingStar ratingStar = new RatingStar(star1, star2, star3, star4, star5);

            pd.setRatingStar(ratingStar);
            pd.setRatings(listRatings);

            productRepository.save(pd);
        }
    }
}