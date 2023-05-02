package com.wagawin.myhousehold.application.child;

import org.apache.http.entity.ContentType;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import us.abstracta.jmeter.javadsl.JmeterDsl;
import us.abstracta.jmeter.javadsl.core.TestPlanStats;
import us.abstracta.jmeter.javadsl.http.DslHttpSampler;

import java.io.IOException;
import java.time.Duration;

import static com.wagawin.myhousehold.application.child.ChildController.V1_CHILDREN_URL;

@ActiveProfiles("performance")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ChildControllerLoadTest {

    private static final String URL = "http://localhost:8080" + V1_CHILDREN_URL;
    private static final String V1_CHILD_1_INFO_URL = URL + "/1/info";
    private static final String V1_CHILD_1_COLOR_URL = URL + "/1/color";

    @Test
    void getInfo() throws IOException {
        DslHttpSampler sampler = JmeterDsl.httpSampler(V1_CHILD_1_INFO_URL).method("GET")
                .contentType(ContentType.APPLICATION_JSON);
        TestPlanStats stats = JmeterDsl.testPlan(JmeterDsl.threadGroup(2, 100000, sampler)).run();

        MatcherAssert.assertThat(stats.overall().errorsCount(), Matchers.is(0L));
        MatcherAssert.assertThat(stats.overall().sampleTimePercentile99(), Matchers.lessThan(Duration.ofSeconds(1)));
    }

    @Test
    void getColor() throws IOException {
        DslHttpSampler sampler = JmeterDsl.httpSampler(V1_CHILD_1_COLOR_URL).method("GET")
                .contentType(ContentType.APPLICATION_JSON);
        TestPlanStats stats = JmeterDsl.testPlan(JmeterDsl.threadGroup(2, 100000, sampler)).run();

        MatcherAssert.assertThat(stats.overall().errorsCount(), Matchers.is(0L));
        MatcherAssert.assertThat(stats.overall().sampleTimePercentile99(), Matchers.lessThan(Duration.ofSeconds(1)));
    }
}
