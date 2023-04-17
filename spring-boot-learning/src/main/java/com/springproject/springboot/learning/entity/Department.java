package com.springproject.springboot.learning.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Data // equivalent to @Getter @Setter @RequiredArgsConstructor @ToString @EqualAndHashCode
@NoArgsConstructor // includes default constructor
@AllArgsConstructor // includes constructor with all arguments
@Builder // Builder Pattern will be implemented for DepartmentService
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long departmentId;
    @NotBlank(message = "please add the department name")
//    @Length(max = 5, min = 1)
//    @Size(max = 10, min = 0)
//    @Email
//    @Positive
//    @Negative
//    @PositiveOrZero
//    @NegativeOrZero
//    @Future //for date type
//    @FutureOrPresent //for date type
//    @Past //for date type
//    @PastOrPresent //for date type
    private String departmentName;
    private String departmentAddress;
    private String departmentCode;
}
