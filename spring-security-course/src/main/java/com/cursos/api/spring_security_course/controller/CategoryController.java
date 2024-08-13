package com.cursos.api.spring_security_course.controller;

import com.cursos.api.spring_security_course.dto.SaveCategoryDto;
import com.cursos.api.spring_security_course.persistence.entity.Category;
import com.cursos.api.spring_security_course.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    @PreAuthorize("hasAuthority('READ_ALL_CATEGORIES')")
    public ResponseEntity<Page<Category>> findAll(Pageable pageable) {

        Page<Category> categoryPage = categoryService.findAll(pageable);

        if(categoryPage.hasContent()) return ResponseEntity.ok(categoryPage);

        return ResponseEntity.notFound().build();

    }

    @GetMapping("/{categoryId}")
    @PreAuthorize("hasAuthority('READ_ONE_CATEGORY')")
    public ResponseEntity<Category> findOneById( @PathVariable("categoryId") Long id ) {

        Optional<Category> category = categoryService.findOneById( id );

        if(category.isPresent()) return ResponseEntity.ok( category.get() );

        return ResponseEntity.notFound().build();

    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ONE_CATEGORY')")
    public ResponseEntity<Category> createOne( @RequestBody @Valid SaveCategoryDto saveCategoryDto ) {

        Category category = categoryService.createOne( saveCategoryDto );

        return ResponseEntity.status(HttpStatus.CREATED).body(category);

    }

    @PutMapping("/{categoryId}")
    @PreAuthorize("hasAuthority('UPDATE_ONE_CATEGORY')")
    public ResponseEntity<Category> updateOneById( @PathVariable("categoryId") Long id, @RequestBody @Valid SaveCategoryDto saveCategoryDto ) {

        Category category = categoryService.updateOneById( id, saveCategoryDto );

        return ResponseEntity.ok(category);

    }

    @PutMapping("/{categoryId}/disabled")
    @PreAuthorize("hasAuthority('DISABLE_ONE_CATEGORY')")
    public ResponseEntity<Category> disableOneById( @PathVariable("categoryId") Long id ) {

        Category category = categoryService.disableOneById( id );

        return ResponseEntity.ok( category );

    }

}
