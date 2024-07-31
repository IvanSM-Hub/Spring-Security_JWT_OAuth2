package com.cursos.api.spring_security_course.controller;

import com.cursos.api.spring_security_course.dto.SaveProductDto;
import com.cursos.api.spring_security_course.persistence.entity.Product;
import com.cursos.api.spring_security_course.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/categories")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<Page<Product>> findAll(Pageable pageable) {

        Page<Product> productsPage = productService.findAll(pageable);

        if(productsPage.hasContent()) return ResponseEntity.ok(productsPage);

        return ResponseEntity.notFound().build();

    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> findOneById( @PathVariable("productId") Long id ) {

        Optional<Product> product = productService.findOneById( id );

        if(product.isPresent()) return ResponseEntity.ok( product.get() );

        return ResponseEntity.notFound().build();

    }

    @PostMapping
    public ResponseEntity<Product> createOne( @RequestBody @Valid SaveProductDto saveProductDto ) {

        Product product = productService.createOne( saveProductDto );

        return ResponseEntity.status(HttpStatus.CREATED).body(product);

    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateOneById( @PathVariable("productId") Long id, @RequestBody @Valid SaveProductDto saveProductDto ) {

        Product product = productService.updateOneById( id, saveProductDto );

        return ResponseEntity.ok(product);

    }

    @PutMapping("/{productId}/disabled")
    public ResponseEntity<Product> disableOneById( @PathVariable("productId") Long id, @RequestBody @Valid SaveProductDto saveProductDto ) {

        Product product = productService.updateOneById( id, saveProductDto );

        return ResponseEntity.ok( product );

    }

}
