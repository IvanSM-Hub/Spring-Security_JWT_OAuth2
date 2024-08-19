package com.cursos.api.spring_security_course.persistence.repository;

import com.cursos.api.spring_security_course.persistence.entity.security.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation,Long> {

    @Query("SELECT o FROM Operation as o Where o.permitAll = true")
    List<Operation> findByPublicAccess();

}
