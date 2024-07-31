package com.cursos.api.spring_security_course.persistence.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated( EnumType.STRING )
    private CategoryStatus status;

    public static enum CategoryStatus { ENABLED, DISABLED }

}
