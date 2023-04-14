package com.springproject.springboot.learning.repository;

import com.springproject.springboot.learning.entity.Department;
import com.springproject.springboot.learning.service.DepartmentService;
import com.springproject.springboot.learning.service.DepartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
