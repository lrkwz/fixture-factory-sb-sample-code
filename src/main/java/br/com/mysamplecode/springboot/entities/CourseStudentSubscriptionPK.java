package br.com.mysamplecode.springboot.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;

@Data
@RequiredArgsConstructor
public class CourseStudentSubscriptionPK implements Serializable {
    @Column(name = "id_student")
    private final String studentId;
    @Column(name = "id_course")
    private final String courseId;
}
