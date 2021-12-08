package com.kltn.phongvuserver.services.ipml;

import com.kltn.phongvuserver.models.Order;
import com.kltn.phongvuserver.models.OrderItem;
import com.kltn.phongvuserver.models.Product;
import com.kltn.phongvuserver.models.dto.InputOrderItemDTO;
import com.kltn.phongvuserver.repositories.OrderItemRepository;
import com.kltn.phongvuserver.repositories.ProductRepository;
import com.kltn.phongvuserver.services.IOrderItemService;
import com.kltn.phongvuserver.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Throwable.class)
public class OrderItemService implements IOrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private IProductService productService;

    @Autowired
    private ProductRepository productRepository;
//
//    @Autowired
//    private IFlashSaleProductService flashSaleProductService;

    @Override
    public boolean checkExistsOrderItem(int id) {
//        return orderItemRepository.existsById(id);
        return true;
    }

    @Override
    public void save(InputOrderItemDTO orderItemDTO, Order order) {
//        OrderItem orderItem = new OrderItem();
//        Random rd = new Random();
//        int idOrderItem;
//        do {
//            idOrderItem = 100 + rd.nextInt(6000001);
//        } while (orderItemRepository.existsById(idOrderItem));
//
//        orderItem.setId(idOrderItem);
//        orderItem.setPrice(orderItemDTO.getPrice());
//        orderItem.setImageUrl(orderItemDTO.getImageUrl());
//        orderItem.setQuantity(orderItemDTO.getQuantity());
//        orderItem.setCreatedAt(new Timestamp(System.currentTimeMillis()));
//        orderItem.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
//        orderItem.setOrder(order);
////        orderItem.setProduct(productService.findProductById(orderItemDTO.getProductId()));
//
//        Product product = productRepository.findById(orderItemDTO.getProductId()).orElse(null);
//        if (product.getTypeId().equals("configurable")) {
//            Product productOption = productService.findProductById(orderItemDTO.getProductOptionId());
//            OptionProductInteger optionProductInteger = optionProductIntegerService.getOptionProductIntegerByProductId(orderItemDTO.getProductOptionId());
//            optionProductInteger.setValue(optionProductInteger.getValue() - orderItemDTO.getQuantity());
//            optionProductIntegerService.save(optionProductInteger);
////            productOption.getOptionProductIntegers().forEach(v-> v.setValue(v.getValue()-orderItemDTO.getQuantity()));
//
//            orderItem.setProductOption(productOption);
//        } else {
//            OptionProductInteger optionProductInteger = optionProductIntegerService.getOptionProductIntegerByProductId(orderItemDTO.getProductId());
//            optionProductInteger.setValue(optionProductInteger.getValue() - orderItemDTO.getQuantity());
//            optionProductIntegerService.save(optionProductInteger);
//        }
//
//        product.setOrderCount(product.getOrderCount() + 1);
//        orderItem.setProduct(productService.save(product));
//
//        FlashSaleProduct flashSaleProduct = flashSaleProductService.getProductFlashSaleInProgress(orderItemDTO.getProductId());
//        if(flashSaleProduct!=null){
//            flashSaleProduct.setSaleAmount(orderItemDTO.getQuantity());
//            flashSaleProductService.saveFlashSaveProduct(flashSaleProduct);
//        }
//
//        orderItemRepository.save(orderItem);
    }

    @Override
    public void save(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }

    @Override
    public OrderItem getOrderItem(int id) {
        return orderItemRepository.findById(id).orElse(null);
    }

    @Override
    public void updateIsReview(int id, int reviewStatus) {
        OrderItem orderItem = orderItemRepository.findById(id).orElse(null);
        if (orderItem != null) {
            orderItem.setReviewStatus(reviewStatus == 1);
            orderItem.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            orderItemRepository.save(orderItem);
        }
    }

    @Override
    public Set<OrderItem> mapListInputOrderItemDTOToOrderItem(List<InputOrderItemDTO> list, Order order) {
        return list.stream().map(inputOrderItemDTO -> mapInputOrderItemDTOToOrderItem(inputOrderItemDTO, order)).collect(Collectors.toSet());
    }

    @Override
    public OrderItem mapInputOrderItemDTOToOrderItem(InputOrderItemDTO inputOrderItemDTO, Order order) {
        OrderItem orderItem = new OrderItem();
        Random rd = new Random();
        int idOrderItem;
        do {
            idOrderItem = 100 + rd.nextInt(6000001);
        } while (orderItemRepository.existsById(idOrderItem));

        orderItem.setId(idOrderItem);
        orderItem.setPrice(inputOrderItemDTO.getPrice());
        orderItem.setOriginalPrice(inputOrderItemDTO.getOriginalPrice());
        orderItem.setImageUrl(inputOrderItemDTO.getImageUrl());
        orderItem.setQuantity(inputOrderItemDTO.getQuantity());
        orderItem.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        orderItem.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        orderItem.setOrder(order);

        Product product = productService.findProductByIdVisibleTrue(inputOrderItemDTO.getProductId());
        product.setOrderCount(product.getOrderCount() + 1);
        product.setQuantity(product.getQuantity() - inputOrderItemDTO.getQuantity());
        orderItem.setProduct(productRepository.save(product));

        return orderItem;
    }
}
