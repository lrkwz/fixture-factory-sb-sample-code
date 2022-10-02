package br.com.mysamplecode.springboot.repositories;

import br.com.mysamplecode.springboot.entities.CourseStudentSubscription;
import br.com.mysamplecode.springboot.entities.CourseStudentSubscriptionPK;
import br.com.mysamplecode.springboot.payloads.StatusNumberResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseSubscriptionRepository extends JpaRepository<CourseStudentSubscription, CourseStudentSubscriptionPK> {

    @Query("Select new br.com.mysamplecode.springboot.payloads.StatusNumberResponse(a.status, count(a)) FROM" +
            " CourseStudentSubscription a WHERE a.course.id=:id GROUP BY a.status ")
    List<StatusNumberResponse> listSubscriptionNumberByStatus(@Param("id") String id);
}
