package com.cursos.api.spring_security_course.service;

import com.cursos.api.spring_security_course.dto.SaveCategoryDto;
import com.cursos.api.spring_security_course.persistence.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CategoryService {

    Page<Category> findAll(Pageable pageable );

    Optional<Category> findOneById( Long id );

    Category createOne( SaveCategoryDto saveCategoryDto );

    Category updateOneById(Long id, SaveCategoryDto saveCategoryDto);

    Category disableOneById(Long id);
}
