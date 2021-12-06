package com.kltn.phongvuserver.controllers.impl;

import com.kltn.phongvuserver.controllers.IOrderItemController;
import com.kltn.phongvuserver.services.IOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Transactional(rollbackFor = Throwable.class)
@RequestMapping("/api/v1")
public class OrderItemController implements IOrderItemController {
    @Autowired
    private IOrderItemService orderItemService;

    @Override
    public String updateReviewStatus(int id, int status) {
        orderItemService.updateIsReview(id, status);
        return "Cập nhật thành công!";
    }
}
