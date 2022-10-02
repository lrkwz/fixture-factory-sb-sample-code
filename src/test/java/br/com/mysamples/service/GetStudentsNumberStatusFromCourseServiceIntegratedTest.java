package br.com.mysamples.service;

import br.com.mysamplecode.springboot.SampleApplication;
import br.com.mysamplecode.springboot.controllers.SampleController;
import br.com.mysamplecode.springboot.entities.CourseStudentSubscription;
import br.com.mysamplecode.springboot.payloads.StatusNumberResponse;
import br.com.mysamples.processor.EntityManagerProcessor;
import br.com.mysamples.templates.CourseStudentSubscriptionTemplate;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(org.springframework.test.context.junit.jupiter.SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
        , classes = SampleApplication.class)
@AutoConfigureMockMvc
@Import(EntityManagerProcessor.class)
 class GetStudentsNumberStatusFromCourseServiceIntegratedTest {

    private static final String GET_SUBSCRIPTION_COUNT = "/sample/subscriptions/count/{course-id}";
    private static final String COURSE_ID= "f32d21d8-0a00-11ed-861d-0242ac120002";

    @Autowired
    private SampleController sampleController;

    @Autowired
    private EntityManagerProcessor entityManagerProcessor;

    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        FixtureFactoryLoader.loadTemplates("br.com.mysamples.templates");
        Fixture.from(CourseStudentSubscription.class).uses(entityManagerProcessor).gimme(CourseStudentSubscriptionTemplate.VALID_COURSE_SUBSCRIPTION);
    }

    @Test
    void shouldAssertTrue() throws Exception {

        String result = mockMvc.perform(get(GET_SUBSCRIPTION_COUNT,COURSE_ID)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                        .andReturn()
                                .getResponse()
                                        .getContentAsString();
        StatusNumberResponse[] statusNumberResponseList = objectMapper.readValue(result, StatusNumberResponse[].class);
        Assertions.assertThat(statusNumberResponseList).hasSize(1)
                .allMatch(statusNumberResponse -> statusNumberResponse.getCount().equals(1L));;
    }

}
