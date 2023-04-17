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
    - Lombok: Java annotation library which helps to reduce boilerplate code
    - MySQL Driver 
- [Above configuration](https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.0.5&packaging=jar&jvmVersion=11&groupId=com.springproject&artifactId=spring-boot-learning&name=spring-boot-learning&description=Demo%20project%20for%20Spring%20Boot&packageName=com.springproject.springboot.learning&dependencies=web,h2,devtools,data-jpa,validation,lombok,mysql)

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
## Adding Loggers
- spring boot comes with the slf4j logging library within the system.
- if we want to use another loggers such as log4j, then we can use it in spring boot, we need to add that in the pom.xml file
- declaration
`private final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);`
- use
`LOGGER.info("inside fetchDepartment of DepartmentController");`
- Loggers are helpful for logging and debugging our application

## Project Lombok (Removing Boiler plate code)
- add dependecy in the pom.xml and modify the plugin
- below are some annotations used to remove the boiler plate code
```
@Data // equivalent to @Getter @Setter @RequiredArgsConstructor @ToString @EqualAndHashCode
@NoArgsConstructor // includes default constructor
@AllArgsConstructor // includes constructor with all arguments
@Builder // Builder Pattern will be implemented for DepartmentService
public class Department {
    ....// class implementation
}
```

## Exception Handling
- we need to create custom ExceptionClass in the error package
```
public class DepartmentNotFoundException extends Exception{
   // override all constructors from Exception class 
}
```
- throw that exception in the entire heirarchy of service, and in the controller
`... method() throws DepartmentNotFoundException {....}`
- by doing above process we get entire error stack with the status code as 500 (Internal server error)
- Below is implementation to get proper status code and meaningful response
- Create a class, that class will be responsible for sending all the response back based on the exception that is being thrown
```
@ControllerAdvice // whatever class that we create to handle exceptions, that should be annotated with @ControllerAdvice
@ResponseStatus // as it sends the response status
// this is the class that will handle the all the particular exceptions that we want to send back as a response entity
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DepartmentNotFoundException.class) // what type of exception this particular method is handling
    // method which handles "DepartmentNotFoundException", and send the response as the request with the message
    public ResponseEntity<ErrorMessage> departmentNotFoundException(DepartmentNotFoundException exception, WebRequest request){
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
}
```

## set properties for MySql
```
spring.jpa.hibernate.ddl-auto = update
spring.datasource.url = jdbc:mysql://localhost:3306/dcbapp
spring.datasource.username = root
spring.datasource.password = omkar126
spring.datasource.driver-class-name = com.mysql.jdbc.Driver
spring.jpa.show-sql = true
```

## Unit Testing
- `spring-boot-starter-test` libary used for the testing
- `spring-boot-starter-test` contains `junit-jupiter`, `mockito-core`, and other libraries

#### Annotations req. for Unit testing
- `@SpringBootTest` - need to add for class
- `@Test` - need to add for method, and that method will act as a testcase
- `@DisplayName("name for the testcase")` - name given here will come in the documentation for the respective testcase
- `@BeforeEach` - method which has `@BeforeEach` annotation that will get execute before every testcase
- `@MockBean` - The tagged bin will be mocked

Example
```
@SpringBootTest
class DepartmentServiceTest {

    @Autowired
    private DepartmentService departmentService;

    @MockBean
    private DepartmentRepository departmentRepository;

    @BeforeEach
    void setup(){
        Department department = Department.builder()
                .departmentName("IT")
                .departmentAddress("Address 1")
                .departmentCode("05")
                .departmentId(1L)
                .build();
        // mocks the findByDepartmentNameIgnoreCase method with the input parameter as "IT"
        Mockito.when(departmentRepository.findByDepartmentNameIgnoreCase("IT"))
                .thenReturn(department);
    }

    @Test
    @DisplayName("Get Data based on valid department name")
    public void whenValidDepartmentName_thenDepartmentShouldFound(){
        String departmentName = "IT";
        Department found = departmentService.fetchDepartmentByName(departmentName);
        assertEquals(departmentName, found.getDepartmentName());
    }
}
```
- `@DataJpaTest` used for the repository layer testing, it persisit the data inside table while execution of the testcase, after that it flushesh the data
- `entityManager.persist(department);` used to store the data in TestEntityManager

Example

```
@DataJpaTest
class DepartmentRepositoryTest {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        Department department = Department.builder()
                .departmentName("Mechanical Engineering")
                .departmentAddress("Address-2")
                .departmentCode("01")
                .build();
        entityManager.persist(department);
    }

    @Test
    public void whenFindByDepartmentId_thenReturnDepartment(){
        Department department = departmentRepository.findById(1L).get();
        assertEquals(department.getDepartmentName(), "Mechanical Engineering");
    }
}
```
