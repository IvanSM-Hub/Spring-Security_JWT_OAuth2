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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    @PreAuthorize("hasAuthority('READ_ALL_PRODUCTS')")
    public ResponseEntity<Page<Product>> findAll(Pageable pageable) {

        Page<Product> productsPage = productService.findAll(pageable);

        if(productsPage.hasContent()) return ResponseEntity.ok(productsPage);

        return ResponseEntity.notFound().build();

    }

    @GetMapping("/{productId}")
    @PreAuthorize("hasAuthority('READ_ONE_PRODUCT')")
    public ResponseEntity<Product> findOneById( @PathVariable("productId") Long id ) {

        Optional<Product> product = productService.findOneById( id );

        if(product.isPresent()) return ResponseEntity.ok( product.get() );

        return ResponseEntity.notFound().build();

    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ONE_PRODUCT')")
    public ResponseEntity<Product> createOne( @RequestBody @Valid SaveProductDto saveProductDto ) {

        Product product = productService.createOne( saveProductDto );

        return ResponseEntity.status(HttpStatus.CREATED).body(product);

    }

    @PutMapping("/{productId}")
    @PreAuthorize("hasAuthority('UPDATE_ONE_PRODUCT')")
    public ResponseEntity<Product> updateOneById( @PathVariable("productId") Long id, @RequestBody @Valid SaveProductDto saveProductDto ) {

        Product product = productService.updateOneById( id, saveProductDto );

        return ResponseEntity.ok(product);

    }

    @PutMapping("/{productId}/disabled")
    @PreAuthorize("hasAuthority('DISABLE_ONE_PRODUCT')")
    public ResponseEntity<Product> disableOneById( @PathVariable("productId") Long id ) {

        Product product = productService.disableOneById( id );

        return ResponseEntity.ok( product );

    }

}
