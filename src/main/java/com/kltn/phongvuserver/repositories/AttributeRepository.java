package com.kltn.phongvuserver.repositories;

import com.kltn.phongvuserver.models.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Integer> {
    Attribute findAttributeByCode(String code);
}
