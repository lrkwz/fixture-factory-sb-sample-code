package br.com.mysamplecode.springboot.repositories;

import br.com.mysamplecode.springboot.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
}
