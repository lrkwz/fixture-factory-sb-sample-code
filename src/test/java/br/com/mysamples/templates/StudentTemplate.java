package br.com.mysamples.templates;

import br.com.mysamplecode.springboot.entities.Student;
import br.com.mysamplecode.springboot.payloads.RegisterCourseRequest;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class StudentTemplate implements TemplateLoader {
    public final static String VALID_STUDENT = "VALID_STUDENT";
    @Override
    public void load() {
        Fixture.of(Student.class).addTemplate(VALID_STUDENT, new Rule(){{
            add("id", "f32d244e-0a00-11ed-861d-0242ac120002");
            add("name", "sampleStudentName");
        }});
    }
}
