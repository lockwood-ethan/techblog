package com.spring.techblog;

import com.spring.techblog.dtos.RegistrationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TechblogApplicationTests {

//    TestRestTemplate restTemplate = new TestRestTemplate(TestRestTemplate.HttpClientOption.ENABLE_COOKIES);
//
//	@Test
//    @DirtiesContext
//	public void canAddUserToDatabase() {
//        RegistrationRequest registrationRequest = new RegistrationRequest();
//        registrationRequest.setUsername("testUsername");
//        registrationRequest.setPassword("dfg345");
//        ResponseEntity<String> response = restTemplate.postForEntity("/register", registrationRequest, String.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//	}

    @Test
    void contextLoads() {
    }

}
