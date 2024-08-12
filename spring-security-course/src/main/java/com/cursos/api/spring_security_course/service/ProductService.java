package com.cursos.api.spring_security_course.service;

import com.cursos.api.spring_security_course.dto.SaveProductDto;
import com.cursos.api.spring_security_course.persistence.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ProductService {

    Page<Product> findAll( Pageable pageable );

    Optional<Product> findOneById( Long id );

    Product createOne( SaveProductDto saveProductDto );

    Product updateOneById(Long id, SaveProductDto saveProductDto);

    Product disableOneById(Long id);
}
