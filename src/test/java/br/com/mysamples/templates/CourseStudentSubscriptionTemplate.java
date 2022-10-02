package br.com.mysamples.templates;

import br.com.mysamplecode.springboot.entities.Course;
import br.com.mysamplecode.springboot.entities.CourseStudentSubscription;
import br.com.mysamplecode.springboot.entities.CourseStudentSubscriptionPK;
import br.com.mysamplecode.springboot.entities.CourseStudentSubscriptionStatus;
import br.com.mysamplecode.springboot.entities.Student;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class CourseStudentSubscriptionTemplate implements TemplateLoader {

    public final static String VALID_COURSE_SUBSCRIPTION = "VALID_COURSE_SUBSCRIPTION";


    @Override
    public void load() {
        Fixture.of(CourseStudentSubscription.class).addTemplate(VALID_COURSE_SUBSCRIPTION, new Rule() {{
           add("courseStudentSubscriptionPK",
                   new CourseStudentSubscriptionPK("f32d244e-0a00-11ed-861d-0242ac120002",
                           "f32d21d8-0a00-11ed-861d-0242ac120002"));
            add("status", CourseStudentSubscriptionStatus.APPROVED);
            add("course", one(Course.class, CourseTemplate.VALID_COURSE));
            add("student", one(Student.class, StudentTemplate.VALID_STUDENT));
        }});
    }
}
