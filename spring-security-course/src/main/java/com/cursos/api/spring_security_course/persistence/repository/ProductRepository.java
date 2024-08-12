package com.cursos.api.spring_security_course.persistence.repository;

import com.cursos.api.spring_security_course.persistence.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}
