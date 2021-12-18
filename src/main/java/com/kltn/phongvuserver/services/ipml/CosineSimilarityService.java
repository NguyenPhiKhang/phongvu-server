package com.kltn.phongvuserver.services.ipml;

import com.kltn.phongvuserver.models.CosineSimilarity;
import com.kltn.phongvuserver.repositories.CosineSimilarityRepository;
import com.kltn.phongvuserver.services.ICosineSimilarityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CosineSimilarityService implements ICosineSimilarityService {
    @Autowired
    private CosineSimilarityRepository cosineSimilarityRepository;

    @Override
    public void saveAll(List<CosineSimilarity> list){
        cosineSimilarityRepository.saveAll(list);
    }

    @Override
    public CosineSimilarity save(CosineSimilarity cosineSimilarityDTO) {return cosineSimilarityRepository.save(cosineSimilarityDTO);}

    @Override
    public CosineSimilarity getByColumnAndRow(int row, int column){
        return cosineSimilarityRepository.findByColumnAndRow(column, row);
    }

    @Override
    public List<CosineSimilarity> getAll(){
        return cosineSimilarityRepository.findAll();
    }

    @Override
    public void removeAll(){
        cosineSimilarityRepository.deleteAllCosine();
    }
}
