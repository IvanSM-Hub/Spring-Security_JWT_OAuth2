package com.cursos.api.spring_security_course.service.impl;

import com.cursos.api.spring_security_course.dto.SaveProductDto;
import com.cursos.api.spring_security_course.exception.ObjectNotFoundException;
import com.cursos.api.spring_security_course.persistence.entity.Category;
import com.cursos.api.spring_security_course.persistence.entity.Product;
import com.cursos.api.spring_security_course.persistence.repository.ProductRepository;
import com.cursos.api.spring_security_course.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Optional<Product> findOneById(Long id) {
        return productRepository.findById( id );
    }

    @Override
    public Product createOne(SaveProductDto saveProductDto) {

        Product newProduct = new Product();
        newProduct.setName(saveProductDto.getName());
        newProduct.setPrice(saveProductDto.getPrice());
        newProduct.setStatus(Product.ProductStatus.ENABLED);

        Category newCategory = new Category();

        newCategory.setId(saveProductDto.getCategory());

        newProduct.setCatengory(newCategory);

        return productRepository.save(newProduct);
    }

    @Override
    public Product updateOneById(Long id, SaveProductDto saveProductDto) {

        Product productFromDB = productRepository.findById( id ).orElseThrow(
                () -> new ObjectNotFoundException( "Product not found with id: " + id )
        );
        productFromDB.setName(saveProductDto.getName());
        productFromDB.setPrice(saveProductDto.getPrice());
        productFromDB.setStatus(Product.ProductStatus.ENABLED);

        Category newCategory = new Category();

        newCategory.setId(saveProductDto.getCategory());

        productFromDB.setCatengory(newCategory);

        return productRepository.save(productFromDB);

    }

    @Override
    public Product disableOneById(Long id) {
        Product productFromDB = productRepository.findById( id ).orElseThrow(
                () -> new ObjectNotFoundException( "Product not found with id: " + id )
        );
        productFromDB.setStatus(Product.ProductStatus.DISABLED);
        return productRepository.save(productFromDB);
    }
}
