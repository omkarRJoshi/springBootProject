package com.springproject.springboot.learning.controller;

import com.springproject.springboot.learning.entity.Department;
import com.springproject.springboot.learning.error.DepartmentNotFoundException;
import com.springproject.springboot.learning.service.DepartmentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;
    private final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);
    @PostMapping("/departments")
    public Department saveDepartment(@Valid @RequestBody Department department){
        LOGGER.info("inside saveDepartment of DepartmentController");
        return departmentService.saveDepartment(department);
    }

    @GetMapping("/departments")
    public List<Department> fetchDepartments(){
        return departmentService.getDepartments();
    }

    @GetMapping("/departments/{id}")
    public Department fetchDepartment(@PathVariable("id") Long id) throws DepartmentNotFoundException {
        LOGGER.info("inside fetchDepartment of DepartmentController");
        return departmentService.fetchDepartment(id);
    }

    @DeleteMapping("departments/{id}")
    public String deleteDepartment(@PathVariable("id") Long id) throws DepartmentNotFoundException {
        return departmentService.deleteDepartment(id);
    }

    @PutMapping("departments/{id}")
    public Department updatedepartment(@PathVariable("id") Long id, @RequestBody Department department) throws DepartmentNotFoundException {
        return departmentService.updatedepartment(id, department);
    }

    @GetMapping("departments/name/{name}")
    public Department fetchDepartmentByName(@PathVariable("name") String departmentName){
        return departmentService.fetchDepartmentByName(departmentName);
    }

}
