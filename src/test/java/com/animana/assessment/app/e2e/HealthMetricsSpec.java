package com.animana.assessment.app.e2e;

import com.animana.assessment.app.dto.SearchResponse;
import org.junit.Test;

public class HealthMetricsSpec extends BaseSpec {

    @Test
    public void test_actuator_end_point() {
        webTestClient.get()
                .uri("http://localhost:" + port + "/actuator")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void test_actuator_health_end_point() {
        webTestClient.get()
                .uri("http://localhost:" + port + "/actuator/health")
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.status").isEqualTo("UP");
    }

    @Test
    public void test_actuator_metrics_end_point() {

        // invoke search before
        webTestClient.get()
                .uri("http://localhost:" + port + "/search?q=hello")
                .exchange()
                .expectBodyList(SearchResponse.class)
                .hasSize(10);


        webTestClient.get()
                .uri("http://localhost:" + port + "/actuator/metrics/reactor.flow.duration")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.measurements[0].statistic").isEqualTo("COUNT")
                .jsonPath("$.measurements[0].value").value(value -> {
            assert Double.parseDouble(value.toString()) > 0;
        })
                .jsonPath("$.measurements[1].statistic").isEqualTo("TOTAL_TIME")
                .jsonPath("$.measurements[1].value").value(value -> {
            assert Double.parseDouble(value.toString()) > 0;
        });
    }
}
