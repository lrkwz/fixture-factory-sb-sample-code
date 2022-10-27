package br.com.mysamplecode.springboot.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseStudentSubscriptionPK implements Serializable {
    @Column(name = "id_student")
    private String studentId;
    @Column(name = "id_course")
    private String courseId;

}
