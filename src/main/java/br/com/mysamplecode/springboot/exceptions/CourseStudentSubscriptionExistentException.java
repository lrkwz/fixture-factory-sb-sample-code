package br.com.mysamplecode.springboot.exceptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@NoArgsConstructor
public class CourseStudentSubscriptionExistentException extends RuntimeException {

    public CourseStudentSubscriptionExistentException(String studentName, String studentCourse){
        super(String.format("There is already a request for the student %s for the course %s"
                ,studentName,studentCourse));
    }

}
