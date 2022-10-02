package br.com.mysamplecode.springboot.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum CourseStudentSubscriptionStatus {
    PENDING("Pending"),
    PAYMENT_PENDING("Waiting Payment"),
    APPROVED("Aproved"),
    REJECTED("Rejected");

    private String label;

}
