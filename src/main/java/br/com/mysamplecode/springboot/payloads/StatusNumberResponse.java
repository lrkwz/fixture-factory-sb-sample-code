package br.com.mysamplecode.springboot.payloads;

import br.com.mysamplecode.springboot.entities.CourseStudentSubscriptionStatus;
import lombok.Data;

@Data
public class StatusNumberResponse {

    private CourseStudentSubscriptionStatus status;
    private Long count;

    public StatusNumberResponse(CourseStudentSubscriptionStatus status, Long count){
        this.status = status;
        this.count = count;
    }
}
