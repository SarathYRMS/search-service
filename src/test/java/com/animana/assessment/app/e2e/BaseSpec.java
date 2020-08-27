package com.animana.assessment.app.e2e;

import com.animana.assessment.app.config.SearchConfig;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseSpec {
    @Autowired
    protected WebTestClient webTestClient;

    @LocalServerPort
    protected int port;

    @Autowired
    SearchConfig searchConfig;
}
