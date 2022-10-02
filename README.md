# SpringBoot sample code to validate Fixture Factory plugin

## Objective

This project aims to create an example to validate the fixture Factory
plugin on unit tests and component tests.
There is  documentation about Fixture Factory on
it's the GitHub page.
The project's goal is to make available an example of a SpringBoot scenario with the fixture factory test code.
If you want to know more about the Fixture Factory framework, the documentation about the Fixture Factory and how to implement and use the templates can be found
here: [GitHub - FixtureFactory](https://github.com/six2six/fixture-factory)

## Scenario Description

### About the project
The SpringBoot project regarding a Technical School Backend application, where it's possible
for a student candidate can register for an offering course. It's also
possible to report the registered students on an offering course.

### What kind of test will be developed?
There are two types of tests: The first is a unit test to validate if a student
candidate could register for a lecture. The second test evaluates a custom Spring 
Data JPA query that brings the number of students registered in a course.

### The functional requirements
  The project has to allow a user to candidate itself to a Technical course.
#### Tests scenarios
  - **The candidate has to be registered on the School earlier 
to candidate to a course.**
  - **The candidate can't be registred in other active course at the school.**
  - **After be registred the student candidate status changes to pendent until the 
financial school departament allows it.** 


## Let's start the solution
Our project is using the 2.6 Spring Boot version with Maven. You can find the build properties and
project dependencies on the pom.xml file. You can also find other project properties on the application.yml file.

 ### Adding the Fixture Factory Dependency
 The First step is to add a Fixture Factory maven dependency.
 ``` XML
    <dependency>
        <groupId>br.com.six2six</groupId>
        <artifactId>fixture-factory</artifactId>
        <version>3.1.0</version>
    </dependency>
 ``` 
 If it's a gradle project, Add on the gradle build file dependency section.
 ``` Properties
    testImplementation group: 'br.com.six2six', name: 'fixture-factory', version: '3.1.0'
 ```

### Creating a unit test class using the Fixture Factory Template.

For the unit test scenario, we have to create a Register Course request template that's related to
the body request on the method
``SampleController.insertSubscription``.


Let's create the Register Course request template.
We have to create a class that implements the ``TemplateLoder``
interface. There will be necessary to implement the load method.
This method is responsible for make the templates available.
Each template is a TemplateHolder of a class. Let's create a valid 
register course request template holder.



```Java
public class RegisterCourseRequestTemplate implements TemplateLoader {
    public final static String VALID_REGISTER_COURSE = "VALID_REGISTER_COURSE";

    @Override
    public void load() {
        Fixture.of(RegisterCourseRequest.class).addTemplate(VALID_REGISTER_COURSE, new Rule(){{
            add("courseId", "f32d21d8-0a00-11ed-861d-0242ac120002");
            add("studentId", "f32d244e-0a00-11ed-861d-0242ac120002");
        }});
    }
}
```

The final static string attribute is a template label that's been using
to create a template holder which will be retrieved later.

We are creating a Template holder from RegisterCourseRequest class by
calling the method ``of`` from the Fixture class and adding values to
RegisterCourseRequest attributes by calling an add method from Rule.class.

## Call a template in unit test.

Now that we built a template class, we can use it in one or several unit tests.
In our example, with the label `` VALID_REGISTER_COURSE `` is going to be possible
to create a RegisterCourseRequest object with the attribute values:
   - "courseId": "f32d21d8-0a00-11ed-861d-0242ac120002"; 
   - "studentId": "f32d244e-0a00-11ed-861d-0242ac120002";

To make this possible we have to do some steps:
  - Load the templates package: It's necessary to inform to our test class where is
the template classes. We can make this possible unsing the statement:

``` Java
 FixtureFactoryLoader.loadTemplates("br.com.mysamples.templates");
```
The ``br.com.mysamples.templates`` represents where's our template classes.

It's possible to add this statement on the constructor test class, or if you are using
the Junit 4 or 5 you can add in the @Before(Each) methods instead.

  - Now it's possible to use our template to bring a valid RegisterCourseRequest object.

``` Java
RegisterCourseRequest request = Fixture.from(RegisterCourseRequest.class)
                .gimme(RegisterCourseRequestTemplate.VALID_REGISTER_COURSE);
```
Where the `` Fixture.from(<Class>)`` returns a ObjectFactory object and the
``ObjectFactory.gimme(<label>)`` returns an expected object value. 

This object will be used to represent the body request, we also need 
to create templates that represent the database course and student retrieved
object to other scenarios on our unit test class.

For our success insert subscription request scenario we will have a unit test
class like:

```Java
@ExtendWith(MockitoExtension.class)
public class RegisterCourseServiceTest {

    private final static String STUDENT_ID = "f32d244e-0a00-11ed-861d-0242ac120002";
    private final static String COURSE_ID = "f32d21d8-0a00-11ed-861d-0242ac120002";
    @InjectMocks
    private RegisterCourseService registerCourseService;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private CourseSubscriptionRepository courseStudentSubscriptionRepository;

    @Captor
    private ArgumentCaptor<CourseStudentSubscription> courseStudentSubscriptionArgumentCaptor;

    @BeforeEach
    public void setup() {

        FixtureFactoryLoader.loadTemplates("br.com.mysamples.templates");

    }

    @Test
    public void shouldInsertANewRequestForTheCourse() {
        when(courseRepository.findById(eq(COURSE_ID)))
                .thenReturn(Optional.of(Fixture.from(Course.class).gimme(CourseTemplate.VALID_COURSE)));
        when(studentRepository.findById(eq(STUDENT_ID)))
                .thenReturn(Optional.of(Fixture.from(Student.class).gimme(StudentTemplate.VALID_STUDENT)));

        RegisterCourseRequest request = Fixture.from(RegisterCourseRequest.class)
                .gimme(RegisterCourseRequestTemplate.VALID_REGISTER_COURSE);

        registerCourseService.execute(request);

        verify(courseRepository, times(1)).findById(anyString());
        verify(courseStudentSubscriptionRepository, times(1)).existsById(any());
        verify(studentRepository, times(1)).findById(anyString());
        verify(courseStudentSubscriptionRepository, times(1))
                .save(courseStudentSubscriptionArgumentCaptor.capture());

        CourseStudentSubscription savedCourseStudentSubscription = courseStudentSubscriptionArgumentCaptor.getValue();

        assertEquals(savedCourseStudentSubscription.getCourseStudentSubscriptionPK().getCourseId(),
                COURSE_ID);
        assertEquals(savedCourseStudentSubscription.getCourseStudentSubscriptionPK().getStudentId(),
                STUDENT_ID);
    }
}
```



## Call the template for component test

For a test component, we will use the same template used for unit tests.

The goal of this test is to evaluate features that report the number of
students and the situation related to a course.
For this feature, it was needed to customize the database query, and it's required to
create a test to evaluate it.

The difference between this test and the unit test is the necessity to insert
values in a database to test the database query result.
We can use the Fixture Factory to insert the data on a scope test database. To make this possible we can build a class that implements a Processor interface
or use a ``HibernateProcessor`` class already provided by Fixture Factory dependency.

In this project, we customized a Processor called ``entityManagerProcessor`` to
insert an object from a template class on the database.

Our component test is a Junit test that uses a SpringExtension instead the Mockito
extension used on the unit test.

To add the template object in the database using the ``entityManagerProcessor``
in our component test, we have to create an object based on a Fixture Factory
CourseStudentSubscription object. This template object, using the custom processor,
, will persist on the database the CourseStudentSubscription and the related
objects (Student and Course).


``` Java
Fixture.from(CourseStudentSubscription.class)
  .uses(entityManagerProcessor)
    .gimme(CourseStudentSubscriptionTemplate.VALID_COURSE_SUBSCRIPTION);
```
>> Note that we use a Template with a label ``VALID_COURSE_SUBSCRIPTION``. This
> Template references other two templates from Student and Course used on our 
> unit test scenario.

```Java
public class CourseStudentSubscriptionTemplate implements TemplateLoader {

    public final static String VALID_COURSE_SUBSCRIPTION = "VALID_COURSE_SUBSCRIPTION";


    @Override
    public void load() {
        Fixture.of(CourseStudentSubscription.class).addTemplate(VALID_COURSE_SUBSCRIPTION, new Rule() {{
           add("courseStudentSubscriptionPK",
                   new CourseStudentSubscriptionPK("f32d244e-0a00-11ed-861d-0242ac120002",
                           "f32d21d8-0a00-11ed-861d-0242ac120002"));
            add("status", CourseStudentSubscriptionStatus.APPROVED);
            add("course", one(Course.class, CourseTemplate.VALID_COURSE));
            add("student", one(Student.class, StudentTemplate.VALID_STUDENT));
        }});
    }
}

```

>> Once inserted on the database this values will be available until the component
> tests has finished.

Now we can create our test scenario to evaluate the query on the database.

``` Java
    @Test
    void shouldAssertTrue() throws Exception {

        String result = mockMvc.perform(get(GET_SUBSCRIPTION_COUNT,COURSE_ID)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                        .andReturn()
                                .getResponse()
                                        .getContentAsString();
        StatusNumberResponse[] statusNumberResponseList = objectMapper.readValue(result, StatusNumberResponse[].class);
        Assertions.assertThat(statusNumberResponseList).hasSize(1)
                .allMatch(statusNumberResponse -> statusNumberResponse.getCount().equals(1L));;
    }
```