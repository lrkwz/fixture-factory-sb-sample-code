package br.com.mysamplecode.springboot.service;

import br.com.mysamplecode.springboot.entities.Course;
import br.com.mysamplecode.springboot.entities.CourseStudentSubscription;
import br.com.mysamplecode.springboot.entities.CourseStudentSubscriptionPK;
import br.com.mysamplecode.springboot.entities.CourseStudentSubscriptionStatus;
import br.com.mysamplecode.springboot.entities.Student;
import br.com.mysamplecode.springboot.exceptions.CourseStudentSubscriptionExistentException;
import br.com.mysamplecode.springboot.exceptions.EntityNotFound;
import br.com.mysamplecode.springboot.payloads.RegisterCourseRequest;
import br.com.mysamplecode.springboot.repositories.CourseRepository;
import br.com.mysamplecode.springboot.repositories.CourseSubscriptionRepository;
import br.com.mysamplecode.springboot.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterCourseService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    private final CourseSubscriptionRepository courseStudentSubscriptionRepository;

    public void execute(RegisterCourseRequest registerCourseRequest){
        Course course = courseRepository.findById(registerCourseRequest.getCourseId())
                .orElseThrow(EntityNotFound::new);
        Student student = studentRepository.findById(registerCourseRequest.getStudentId())
                .orElseThrow(EntityNotFound::new);

        CourseStudentSubscriptionPK pk = new CourseStudentSubscriptionPK(registerCourseRequest.getStudentId()
                , registerCourseRequest.getCourseId());
        if(courseStudentSubscriptionRepository.existsById(pk)){
           throw new CourseStudentSubscriptionExistentException();
        }
        CourseStudentSubscription courseStudentSubscription = new CourseStudentSubscription();
        courseStudentSubscription.setCourseStudentSubscriptionPK(pk);
        courseStudentSubscription.setStatus(CourseStudentSubscriptionStatus.PENDING);
        courseStudentSubscription.setCourse(course);
        courseStudentSubscription.setStudent(student);
        courseStudentSubscriptionRepository.save(courseStudentSubscription);
    }
}
