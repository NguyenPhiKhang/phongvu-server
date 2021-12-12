package com.kltn.phongvuserver.services.ipml;

import com.kltn.phongvuserver.models.DataImage;
import com.kltn.phongvuserver.models.Rating;
import com.kltn.phongvuserver.repositories.DataImageRepository;
import com.kltn.phongvuserver.services.IImageDataService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public List<DataImage> storesImageData(List<MultipartFile> multipartFiles) {
        List<DataImage> images = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            String fileName = StringUtils.cleanPath((Objects.requireNonNull(file.getOriginalFilename())));
            DataImage FileDB = null;
            try {
                FileDB = new DataImage(fileName, fileName, file.getContentType(), file.getBytes());
//                FileDB.addRating(rating);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(fileName);

            if (FileDB != null) {
                images.add(FileDB);
            }
        }
        return images;
    }

    @Override
    public DataImage storeImageData(MultipartFile multipartFile) {
        String fileName = StringUtils.cleanPath((Objects.requireNonNull(multipartFile.getOriginalFilename())));
        DataImage FileDB = null;
        try {
            FileDB = new DataImage(fileName, fileName, multipartFile.getContentType(), multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(fileName);

        return FileDB;
    }

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

