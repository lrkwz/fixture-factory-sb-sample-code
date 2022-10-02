package br.com.mysamplecode.springboot.entities;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Table(name = "course_student_subscription")
@Entity
public class CourseStudentSubscription {
    @EmbeddedId
    private CourseStudentSubscriptionPK courseStudentSubscriptionPK;
    private CourseStudentSubscriptionStatus status;

    @ManyToOne
    @JoinColumn(name = "id_course", insertable = false, updatable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "id_student", insertable = false, updatable = false)
    private Student student;
}
