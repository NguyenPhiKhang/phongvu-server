package com.kltn.phongvuserver.services.ipml;

import com.kltn.phongvuserver.models.DataImage;
import com.kltn.phongvuserver.repositories.DataImageRepository;
import com.kltn.phongvuserver.services.IImageDataService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Throwable.class)
public class ImageDataService implements IImageDataService {
    @Autowired
    private DataImageRepository imageDataRepository;

    @Override
    public DataImage findImageById(String id) {
        return imageDataRepository.findDataImageById(id);
    }

    @Override
    public List<DataImage> findListImageDataByIds(List<String> ids) {
        return imageDataRepository.findByIdIn(ids);
    }

    @Override
    public List<DataImage> getAllImages() {
        return imageDataRepository.findAll();
    }

    @Override
    public boolean checkExistsId(String id) {
        return imageDataRepository.existsById(id);
    }

    @Override
    @SneakyThrows
    public List<DataImage> storesImageData(List<MultipartFile> multipartFiles) {
        List<DataImage> images = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            String fileName = StringUtils.cleanPath((Objects.requireNonNull(file.getOriginalFilename())));
            DataImage FileDB = new DataImage(fileName, fileName, file.getContentType(), file.getBytes());

            System.out.println(fileName);

            images.add(FileDB);
        }
        return imageDataRepository.saveAll(images);
    }

    @SneakyThrows
    @Override
    public DataImage storeImageData(MultipartFile multipartFile) {
        String fileName = StringUtils.cleanPath((Objects.requireNonNull(multipartFile.getOriginalFilename())));
        DataImage FileDB = new DataImage(fileName, fileName, multipartFile.getContentType(), multipartFile.getBytes());

        System.out.println(fileName);

        return imageDataRepository.save(FileDB);
    }

//    @SneakyThrows
//    @Override
//    public ImageData storeImageData(MultipartFile multipartFile, String id) {
//
//        return new ImageData(id, id, multipartFile.getContentType(), multipartFile.getBytes());
//    }


    @Override
    public void saveImage(DataImage imageData) {
        imageDataRepository.save(imageData);
    }

    @Override
    public void removeImageById(String id) {
        imageDataRepository.deleteById(id);
    }

    @Override
    public Optional<DataImage> getFile(String id) {
        return imageDataRepository.findById(id);
    }
}

