package br.com.mysamples.templates;

import br.com.mysamplecode.springboot.entities.Course;
import br.com.mysamplecode.springboot.payloads.RegisterCourseRequest;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import java.time.LocalDate;

public class CourseTemplate implements TemplateLoader {
    public final static String VALID_COURSE = "VALID_COURSE";

    @Override
    public void load() {
        Fixture.of(Course.class).addTemplate(VALID_COURSE, new Rule(){{
            add("id", "f32d21d8-0a00-11ed-861d-0242ac120002");
            add("name", "course sample");
            add("initDate", LocalDate.now());
            add("endDate", LocalDate.now().plusMonths(3L));
            add("description", "sample course to learn how use template with a springboot project");

        }});
    }


}
