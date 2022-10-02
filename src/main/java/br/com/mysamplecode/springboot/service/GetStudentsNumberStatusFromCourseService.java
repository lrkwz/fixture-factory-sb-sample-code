package br.com.mysamplecode.springboot.service;

import br.com.mysamplecode.springboot.payloads.StatusNumberResponse;
import br.com.mysamplecode.springboot.repositories.CourseSubscriptionRepository;
import br.com.mysamplecode.springboot.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetStudentsNumberStatusFromCourseService {

    private final CourseSubscriptionRepository courseSubscriptionRepository;
    private final StudentRepository studentRepository;

    public List<StatusNumberResponse> execute(String courseID){
        return courseSubscriptionRepository.listSubscriptionNumberByStatus(courseID);
    }
}
