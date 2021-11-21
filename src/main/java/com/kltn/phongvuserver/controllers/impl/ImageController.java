package com.kltn.phongvuserver.controllers.impl;

import com.kltn.phongvuserver.controllers.IImageController;
import com.kltn.phongvuserver.exceptions.ResourceNotFoundException;
import com.kltn.phongvuserver.models.DataImage;
import com.kltn.phongvuserver.services.IImageDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Transactional(rollbackFor = Throwable.class)
public class ImageController implements IImageController {
    @Autowired
    private IImageDataService imageDataService;

    @Override
    public ResponseEntity<byte[]> getFile(String id) {
        DataImage image = imageDataService.getFile(id).orElseThrow(() -> new ResourceNotFoundException("Not found image id: " + id));

        return ResponseEntity.ok().contentType(MediaType.valueOf(image.getType())).body(image.getData());
    }
}
