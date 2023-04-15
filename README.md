# Spring Boot Project
## _demo spring boot project_

description -------------

## initial setup in start.spring.io
- initializer setup 
    - select maven project
    - spring boot version : 3.0.5
    - packaging: jar
    - java version: 11
- Add dependencies
    - Spring Web: needed because we'll be creating rest API's
    - H2 Database: in memory database
    - spring-boot starter: default dependecy
    - JPA: Persist data in SQL stores with Java Persistence API using Spring Data and Hibernate.
    - spring-boot-starter-validation: Bean Validation with Hibernate validator.
- [Above configuration](https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.0.5&packaging=jar&jvmVersion=11&groupId=com.springproject&artifactId=spring-boot-learning&name=spring-boot-learning&description=Demo%20project%20for%20Spring%20Boot&packageName=com.springproject.springboot.learning&dependencies=web,h2)

## REST Api's in controller

```
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
```

## Different keywords that we can use in repository layer : [click-here](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods)
- example
```
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    public Department findByDepartmentName(String departmentName);
    public Department findByDepartmentNameIgnoreCase(String departmentName);
}
```
- get the value using native query - (Example is not from this project)
```
public interface UserRepository extends JpaRepository<User, Long> {

  @Query(value = "SELECT * FROM USERS WHERE LASTNAME = ?1",
    countQuery = "SELECT count(*) FROM USERS WHERE LASTNAME = ?1",
    nativeQuery = true)
  Page<User> findByLastname(String lastname, Pageable pageable);
}
```

## Handeling validations
- suppose we have 3-4 fileds that is coming as the input parameter, we can add the validations there like (my department should always be available. if it is not there then is should considered as a bad request)
- we can add different validations using hibernate validation
- to do this we need to add the dependecy and we need to annotate our entities to validate all our request
- we need to add annotations above the field, for which we need the validation
- example
```
    @Id // annotation for primary key
    @GeneratedValue(strategy = GenerationType.AUTO) // auto generate
    private Long departmentId;
    
    //validation annotation start
    
    @NotBlank(message = "please add the department name")
    @Length(max = 5, min = 1)
    @Size(max = 10, min = 0)
    @Email
    @Positive
    @Negative
    @PositiveOrZero
    @NegativeOrZero
    @Future //for date type
    @FutureOrPresent //for date type
    @Past //for date type
    @PastOrPresent //for date type
    private String departmentName;
    
    //validation annotation end
    
    private String departmentAddress;
    private String departmentCode;
```