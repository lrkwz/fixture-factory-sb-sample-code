package br.com.mysamples.service;

import br.com.mysamplecode.springboot.entities.Course;
import br.com.mysamplecode.springboot.entities.CourseStudentSubscription;
import br.com.mysamplecode.springboot.entities.CourseStudentSubscriptionPK;
import br.com.mysamplecode.springboot.entities.Student;
import br.com.mysamplecode.springboot.exceptions.CourseStudentSubscriptionExistentException;
import br.com.mysamplecode.springboot.exceptions.EntityNotFound;
import br.com.mysamplecode.springboot.payloads.RegisterCourseRequest;
import br.com.mysamplecode.springboot.repositories.CourseRepository;
import br.com.mysamplecode.springboot.repositories.CourseSubscriptionRepository;
import br.com.mysamplecode.springboot.repositories.StudentRepository;
import br.com.mysamplecode.springboot.service.RegisterCourseService;
import br.com.mysamples.templates.CourseTemplate;
import br.com.mysamples.templates.RegisterCourseRequestTemplate;
import br.com.mysamples.templates.StudentTemplate;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegisterCourseServiceTest {

    private final static String STUDENT_ID = "f32d244e-0a00-11ed-861d-0242ac120002";
    private final static String COURSE_ID = "f32d21d8-0a00-11ed-861d-0242ac120002";
    @InjectMocks
    private RegisterCourseService registerCourseService;
    @Mock
    private  CourseRepository courseRepository;
    @Mock
    private  StudentRepository studentRepository;
    @Mock
    private  CourseSubscriptionRepository courseStudentSubscriptionRepository;

    @Captor
    private ArgumentCaptor<CourseStudentSubscription> courseStudentSubscriptionArgumentCaptor;

    @BeforeEach
    public void setup(){

        FixtureFactoryLoader.loadTemplates("br.com.mysamples.templates");

    }

    @Test
    public void shouldInsertANewRequestForTheCourse(){
        when(courseRepository.findById(eq(COURSE_ID)))
                .thenReturn(Optional.of(Fixture.from(Course.class).gimme(CourseTemplate.VALID_COURSE)));
        when(studentRepository.findById(eq(STUDENT_ID)))
                .thenReturn(Optional.of(Fixture.from(Student.class).gimme(StudentTemplate.VALID_STUDENT)));

        RegisterCourseRequest request = Fixture.from(RegisterCourseRequest.class)
                .gimme(RegisterCourseRequestTemplate.VALID_REGISTER_COURSE);

        registerCourseService.execute(request);

        verify(courseRepository,times(1)).findById(anyString());
        verify(courseStudentSubscriptionRepository, times(1)).existsById(any());
        verify(studentRepository,times(1)).findById(anyString());
        verify(courseStudentSubscriptionRepository,times(1))
                .save(courseStudentSubscriptionArgumentCaptor.capture());

        CourseStudentSubscription savedCourseStudentSubscription = courseStudentSubscriptionArgumentCaptor.getValue();

        assertEquals(savedCourseStudentSubscription.getCourseStudentSubscriptionPK().getCourseId(),
                COURSE_ID);
        assertEquals(savedCourseStudentSubscription.getCourseStudentSubscriptionPK().getStudentId(),
                STUDENT_ID);
    }

    @Test
    public void shouldThrowAnExceptionWhenAWrongCourseIDIsInformed(){

        RegisterCourseRequest request = Fixture.from(RegisterCourseRequest.class)
                .gimme(RegisterCourseRequestTemplate.INVALID_REGISTER_COURSE_COURSE_INVALID);

               assertThrows(EntityNotFound.class, ()->{


                   registerCourseService.execute(request);
               });
    }

    @Test
    public void shouldThrowAnExceptionWhenAWrongStudentIDIsInformed(){

        RegisterCourseRequest request = Fixture.from(RegisterCourseRequest.class)
                .gimme(RegisterCourseRequestTemplate.INVALID_REGISTER_COURSE_STUDENT_INVALID);

        assertThrows(EntityNotFound.class, ()->{


            registerCourseService.execute(request);
        });
    }

    @Test
    public void shouldThrowAnExceptionWhenTryToRegisterToAAlreadyRegisteredCourse(){
        when(courseRepository.findById(eq(COURSE_ID)))
                .thenReturn(Optional.of(Fixture.from(Course.class).gimme(CourseTemplate.VALID_COURSE)));
        when(studentRepository.findById(eq(STUDENT_ID)))
                .thenReturn(Optional.of(Fixture.from(Student.class).gimme(StudentTemplate.VALID_STUDENT)));

        RegisterCourseRequest request = Fixture.from(RegisterCourseRequest.class)
                .gimme(RegisterCourseRequestTemplate.VALID_REGISTER_COURSE);

        CourseStudentSubscriptionPK pk = new CourseStudentSubscriptionPK(STUDENT_ID,COURSE_ID);
        when(courseStudentSubscriptionRepository.existsById(eq(pk))).thenReturn(true);

        assertThrows(CourseStudentSubscriptionExistentException.class, ()->
                registerCourseService.execute(request)
                );
    }
}
