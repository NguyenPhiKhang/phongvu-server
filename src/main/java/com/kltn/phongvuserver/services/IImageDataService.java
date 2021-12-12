package com.kltn.phongvuserver.services;

import com.kltn.phongvuserver.models.DataImage;
import com.kltn.phongvuserver.models.Rating;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IImageDataService {
    DataImage findImageById(String id);
    List<DataImage> findListImageDataByIds(List<String> ids);
    List<DataImage> getAllImages();
    boolean checkExistsId(String id);
    List<DataImage> storesImageData(List<MultipartFile> multipartFiles);
    DataImage storeImageData(MultipartFile multipartFile);
    //    ImageData storeImageData(MultipartFile multipartFile, String id);
    Optional<DataImage> getFile(String id);
    void saveImage(DataImage imageData);
    void removeImageById(String id);
}
