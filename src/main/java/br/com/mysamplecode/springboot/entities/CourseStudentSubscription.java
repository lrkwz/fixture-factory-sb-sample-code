package br.com.mysamplecode.springboot.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

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
