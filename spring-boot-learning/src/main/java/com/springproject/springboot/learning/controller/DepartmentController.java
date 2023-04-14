package com.springproject.springboot.learning.controller;

import com.springproject.springboot.learning.entity.Department;
import com.springproject.springboot.learning.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/departments")
    public Department saveDepartment(@RequestBody Department department){
        return departmentService.saveDepartment(department);
    }

    @GetMapping("/departments")
    public List<Department> fetchDepartments(){
        return departmentService.getDepartments();
    }

    @GetMapping("/departments/{id}")
    public Department fetchDepartment(@PathVariable("id") Long id){
        return departmentService.fetchDepartment(id);
    }

    @DeleteMapping("departments/{id}")
    public String deleteDepartment(@PathVariable("id") Long id){
        return departmentService.deleteDepartment(id);
    }

    @PutMapping("departments/{id}")
    public Department updatedepartment(@PathVariable("id") Long id, @RequestBody Department department){
        return departmentService.updatedepartment(id, department);
    }

}
