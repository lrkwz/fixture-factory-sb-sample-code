package br.com.mysamplecode.springboot.payloads;

import lombok.Data;

@Data
public class RegisterCourseRequest {
    private String courseId;
    private String studentId;
}
