package br.com.mysamplecode.springboot.controllers;

import br.com.mysamplecode.springboot.payloads.HelloResponse;
import br.com.mysamplecode.springboot.payloads.RegisterCourseRequest;
import br.com.mysamplecode.springboot.payloads.StatusNumberResponse;
import br.com.mysamplecode.springboot.service.GetStudentsNumberStatusFromCourseService;
import br.com.mysamplecode.springboot.service.RegisterCourseService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sample")
@RequiredArgsConstructor
public class SampleController {
    private final GetStudentsNumberStatusFromCourseService getStudentsNumberStatusFromCourseService;
    private final RegisterCourseService registerCourseService;

    @GetMapping(path = "/hello", produces = "application/json")
    @ApiResponse(description = "Say Hello")
    public HelloResponse getHelloResponse(){
        return HelloResponse.builder().text("Hello").build();
    }


    @GetMapping(path = "/subscriptions/count/{course-id}", produces = "application/json")
    @ApiResponse(description = "subscription number by status")
    public List<StatusNumberResponse> getSubscriptionsNumberFromCourse(@PathVariable("course-id") String id){
        return getStudentsNumberStatusFromCourseService.execute(id);
    }

    @PostMapping(path = "/subscriptions")
    @ResponseStatus(HttpStatus.CREATED)
    public void insertSubscription(@RequestBody RegisterCourseRequest registerCourseRequest){
        registerCourseService.execute(registerCourseRequest);
    }
}
