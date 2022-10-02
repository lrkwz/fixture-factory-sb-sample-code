package br.com.mysamples.templates;

import br.com.mysamplecode.springboot.payloads.RegisterCourseRequest;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class RegisterCourseRequestTemplate implements TemplateLoader {
    public final static String VALID_REGISTER_COURSE = "VALID_REGISTER_COURSE";
    public static final String INVALID_REGISTER_COURSE_COURSE_INVALID = "INVALID_REGISTER_COURSE_COURSE_INVALID";

    public static final String INVALID_REGISTER_COURSE_STUDENT_INVALID = "INVALID_REGISTER_COURSE_STUDENT_INVALID";


    @Override
    public void load() {
        Fixture.of(RegisterCourseRequest.class).addTemplate(VALID_REGISTER_COURSE, new Rule(){{
            add("courseId", "f32d21d8-0a00-11ed-861d-0242ac120002");
            add("studentId", "f32d244e-0a00-11ed-861d-0242ac120002");
        }});

        Fixture.of(RegisterCourseRequest.class).addTemplate(INVALID_REGISTER_COURSE_COURSE_INVALID)
                .inherits(VALID_REGISTER_COURSE, new Rule(){{
                    add("courseId", "3e4531f4-0c42-11ed-861d-0242ac120002");
        }});

        Fixture.of(RegisterCourseRequest.class).addTemplate(INVALID_REGISTER_COURSE_STUDENT_INVALID)
                .inherits(VALID_REGISTER_COURSE, new Rule(){{
                    add("studentId", "6f342f4a-0c42-11ed-861d-0242ac120002");
                }});
    }
}
