package com.springproject.springboot.learning.service;

import com.springproject.springboot.learning.entity.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    public Department saveDepartment(Department department);

    public List<Department> getDepartments();

    public Department fetchDepartment(Long id);

    public String deleteDepartment(Long id);

    Department updatedepartment(Long id, Department department);
}
