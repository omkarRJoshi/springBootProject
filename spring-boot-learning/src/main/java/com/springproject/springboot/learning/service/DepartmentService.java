package com.springproject.springboot.learning.service;

import com.springproject.springboot.learning.entity.Department;
import com.springproject.springboot.learning.error.DepartmentNotFoundException;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    public Department fetchDepartmentByName(String departmentName);

    public Department saveDepartment(Department department);

    public List<Department> getDepartments();

    public Department fetchDepartment(Long id) throws DepartmentNotFoundException;

    public String deleteDepartment(Long id) throws DepartmentNotFoundException;

    Department updatedepartment(Long id, Department department) throws DepartmentNotFoundException;
}
