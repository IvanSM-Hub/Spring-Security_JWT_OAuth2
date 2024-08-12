package com.cursos.api.spring_security_course.service.impl;

import com.cursos.api.spring_security_course.dto.SaveCategoryDto;
import com.cursos.api.spring_security_course.exception.ObjectNotFoundException;
import com.cursos.api.spring_security_course.persistence.entity.Category;
import com.cursos.api.spring_security_course.persistence.repository.CategoryRepository;
import com.cursos.api.spring_security_course.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll( pageable );
    }

    @Override
    public Optional<Category> findOneById(Long id) {
        return categoryRepository.findById( id );
    }

    @Override
    public Category createOne(SaveCategoryDto saveCategoryDto) {

        Category category = new Category();
        category.setName(saveCategoryDto.getName());
        category.setStatus( Category.CategoryStatus.ENABLED );

        return categoryRepository.save( category );

    }

    @Override
    public Category updateOneById(Long id, SaveCategoryDto saveCategoryDto) {

        Category categoryFromDB = categoryRepository.findById( id ).orElseThrow(
                () -> new ObjectNotFoundException("Category not found with id: " + id)
        );
        categoryFromDB.setName(saveCategoryDto.getName());

        return categoryRepository.save( categoryFromDB );

    }

    @Override
    public Category disableOneById(Long id) {

        Category categoryFromDB = categoryRepository.findById( id ).orElseThrow(
                () -> new ObjectNotFoundException("Category not found with id: " + id)
        );

        categoryFromDB.setStatus(Category.CategoryStatus.ENABLED);

        return categoryRepository.save(categoryFromDB);

    }

}
