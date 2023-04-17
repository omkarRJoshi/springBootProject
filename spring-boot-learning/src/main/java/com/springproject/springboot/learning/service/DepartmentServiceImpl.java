package com.springproject.springboot.learning.service;

import com.springproject.springboot.learning.entity.Department;
import com.springproject.springboot.learning.error.DepartmentNotFoundException;
import com.springproject.springboot.learning.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService{

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public Department fetchDepartmentByName(String departmentName) {
        return departmentRepository.findByDepartmentNameIgnoreCase(departmentName);
    }

    @Override
    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Department fetchDepartment(Long id) throws DepartmentNotFoundException {
        Optional<Department> department = departmentRepository.findById(id);
        if(!department.isPresent()){
            throw new DepartmentNotFoundException("Department with the id " + id + " is not available");
        }
        return department.get();
    }

    @Override
    public String deleteDepartment(Long id) throws DepartmentNotFoundException {
        Optional<Department> department = departmentRepository.findById(id);
        if(!department.isPresent()){
            throw new DepartmentNotFoundException("Department with the id "+  id + " is not present");
        }
        String dept = department.get().toString();
        departmentRepository.deleteById(id);
        return "deleted " + dept;
    }

    @Override
    public Department updatedepartment(Long id, Department department) throws DepartmentNotFoundException {
        Optional<Department> dept = departmentRepository.findById(id);
        if(!dept.isPresent()){
            throw new DepartmentNotFoundException("Department with the id "+  id + " is not present");
        }
        Department deptDB = dept.get();

        if(Objects.nonNull(department.getDepartmentName()) && !"".equalsIgnoreCase(department.getDepartmentName())){
            deptDB.setDepartmentName(department.getDepartmentName());
        }

        if(Objects.nonNull(department.getDepartmentAddress()) && !"".equalsIgnoreCase(department.getDepartmentAddress())){
            deptDB.setDepartmentAddress(department.getDepartmentAddress());
        }

        if(Objects.nonNull(department.getDepartmentCode()) && !"".equalsIgnoreCase(department.getDepartmentCode())){
            deptDB.setDepartmentCode(department.getDepartmentCode());
        }
        return departmentRepository.save(deptDB);
    }


}
