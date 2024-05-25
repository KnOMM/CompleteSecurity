//package org.backend.oauth2withjwt;
//
//import com.jayway.jsonpath.DocumentContext;
//import com.jayway.jsonpath.JsonPath;
//import org.junit.Before;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.test.context.support.WithUserDetails;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.jdbc.SqlConfig;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
//
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
//import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//
////@ContextConfiguration
////@WebAppConfiguration
//class CashCardApplicationTests {
//    @Autowired
//    TestRestTemplate restTemplate; // requests to the locally running application
//
//    @Autowired
//    private WebApplicationContext context;
//    private MockMvc mvc;
//
////    @Before
////    public void setup() {
////        mvc = MockMvcBuilders
////                .webAppContextSetup(context)
////                .apply(springSecurity())
////                .build();
////    }
//
//    @Test
//    public void shouldReturnTrue() throws Exception {
//        mvc.perform(post("/cashcards/99").with(user("user")));
//    }
//
//
//    @Test
//    @Sql(value = "/data.sql",
//            config = @SqlConfig(transactionMode = ISOLATED))
//    @Sql(
//            scripts = "/delete-test-data.sql",
//            config = @SqlConfig(transactionMode = ISOLATED),
//            executionPhase = AFTER_TEST_METHOD
//    )
//    @WithUserDetails("sarah1")
//    void shouldReturnACashCardWhenDataIsSaved() {
//
//        ResponseEntity<String> response = restTemplate
////                .withBasicAuth("sarah1", "abc123")
//                .getForEntity("/cashcards/99", String.class);
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        DocumentContext documentContext = JsonPath.parse(response.getBody()); // converts JSON string into JSON-aware object
//
//        Number id = documentContext.read("$.id");
//        assertThat(id).isEqualTo(99);
//
//        Double amount = documentContext.read("$.amount");
//        assertThat(amount).isEqualTo(123.45);
//    }
//
////    @Test
////    void shouldNotReturnACashCardWithAnUnknownId() {
////        ResponseEntity<String> response = restTemplate.withBasicAuth("sarah1", "abc123").getForEntity("/cashcards/1000", String.class);
////
////        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
////        assertThat(response.getBody()).isBlank();
////    }
////
////    @Test
////    @DirtiesContext
////    void shouldCreateANewCashCard() {
////        CashCard newCashCard = new CashCard(null, 250.00, null);
////        ResponseEntity<Void> createResponse = restTemplate.withBasicAuth("sarah1", "abc123").postForEntity("/cashcards", newCashCard, Void.class);
////        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
////
////        URI locationOfNewCashCard = createResponse.getHeaders().getLocation();
////        ResponseEntity<String> getResponse = restTemplate.withBasicAuth("sarah1", "abc123").getForEntity(locationOfNewCashCard, String.class);
////        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
////
////        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
////        Number id = documentContext.read("$.id");
////        Double amount = documentContext.read("$.amount");
////
////        assertThat(id).isNotNull();
////        assertThat(amount).isEqualTo(250.00);
////    }
////
//    @Test
//    @Sql(value = "/data.sql",
//            config = @SqlConfig(transactionMode = ISOLATED))
//    @Sql(
//            scripts = "/delete-test-data.sql",
//            config = @SqlConfig(transactionMode = ISOLATED),
//            executionPhase = AFTER_TEST_METHOD
//    )
//    void shouldReturnAllCashCardsWhenListIsRequested() {
//        ResponseEntity<String> response = restTemplate.withBasicAuth("sarah1", "abc123").getForEntity("/cashcards", String.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        DocumentContext documentContext = JsonPath.parse(response.getBody()); // converts JSON string into JSON-aware object
//
//        int cashCardCount = documentContext.read("$.length()");
//        assertThat(cashCardCount).isEqualTo(3);
//
//        JSONArray ids = documentContext.read("$..id");
//        assertThat(ids).containsExactlyInAnyOrder(99, 100, 101);
//
//        JSONArray amounts = documentContext.read("$..amount");
//        assertThat(amounts).containsExactlyInAnyOrder(123.45, 150.00, 1.0);
//    }
//
//
//    @Test
//    @Sql(value = "/data.sql",
//            config = @SqlConfig(transactionMode = ISOLATED))
//    @Sql(
//            scripts = "/delete-test-data.sql",
//            config = @SqlConfig(transactionMode = ISOLATED),
//            executionPhase = AFTER_TEST_METHOD
//    )
//    void shouldReturnAPageOfCashCards() {
//        ResponseEntity<String> response = restTemplate.withBasicAuth("sarah1", "abc123").getForEntity("/cashcards?page=0&size=1", String.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        DocumentContext documentContext = JsonPath.parse(response.getBody());
//        JSONArray page = documentContext.read("$[*]");
//        assertThat(page.size()).isEqualTo(1);
//    }
//
//    @Test
//    @Sql(value = "/data.sql",
//            config = @SqlConfig(transactionMode = ISOLATED))
//    @Sql(
//            scripts = "/delete-test-data.sql",
//            config = @SqlConfig(transactionMode = ISOLATED),
//            executionPhase = AFTER_TEST_METHOD
//    )
//    void shouldReturnASortedPageOfCashCards() {
//        ResponseEntity<String> response = restTemplate.withBasicAuth("sarah1", "abc123").getForEntity("/cashcards?page=0&size=1&sort=amount,desc", String.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        DocumentContext documentContext = JsonPath.parse(response.getBody());
//        JSONArray read = documentContext.read("$[*]");
//        assertThat(read.size()).isEqualTo(1);
//
//        double amount = documentContext.read("$[0].amount");
//        assertThat(amount).isEqualTo(150.00);
//    }
//
//    @Test
//    @Sql(value = "/data.sql",
//            config = @SqlConfig(transactionMode = ISOLATED))
//    @Sql(
//            scripts = "/delete-test-data.sql",
//            config = @SqlConfig(transactionMode = ISOLATED),
//            executionPhase = AFTER_TEST_METHOD
//    )
//    void shouldReturnASortedPageOfCashCardsWithNoParametersAndUseDefaultValues() {
//        ResponseEntity<String> response = restTemplate.withBasicAuth("sarah1", "abc123").getForEntity("/cashcards", String.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        DocumentContext documentContext = JsonPath.parse(response.getBody());
//        JSONArray page = documentContext.read("$[*]");
//        assertThat(page.size()).isEqualTo(3);
//
//        JSONArray amounts = documentContext.read("$..amount");
//        assertThat(amounts).containsExactly(1.0, 123.45, 150.0);
//    }
//
//    @Test
//    void shouldNotReturnACashCardWhenUsingBadCredentials() {
//        ResponseEntity<String> response = restTemplate
//                .withBasicAuth("BAD-USER", "abc123")
//                .getForEntity("/cashcards/99", String.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
//
//        response = restTemplate
//                .withBasicAuth("sarah1", "BAD-PASSWORD")
//                .getForEntity("/cashcards/99", String.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
//    }
//
//
//    @Test
//    @Sql(value = "/data.sql",
//            config = @SqlConfig(transactionMode = ISOLATED))
//    @Sql(
//            scripts = "/delete-test-data.sql",
//            config = @SqlConfig(transactionMode = ISOLATED),
//            executionPhase = AFTER_TEST_METHOD
//    )
//    void shouldRejectUsersWhoAreNotCardOwners() {
//        ResponseEntity<String> response = restTemplate
//                .withBasicAuth("hank-owns-no-cards", "qrs456")
//                .getForEntity("/cashcards/99", String.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
//    }
//
//    @Test
//    @Sql(value = "/data.sql",
//            config = @SqlConfig(transactionMode = ISOLATED))
//    @Sql(
//            scripts = "/delete-test-data.sql",
//            config = @SqlConfig(transactionMode = ISOLATED),
//            executionPhase = AFTER_TEST_METHOD
//    )
//    void shouldNotAllowAccessToCashCardsTheyDoNotOwn() {
//        ResponseEntity<String> response = restTemplate
//                .withBasicAuth("sarah1", "abc123")
//                .getForEntity("/cashcards/102", String.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//    }
//
//    @Test
//    @Sql(value = "/data.sql",
//            config = @SqlConfig(transactionMode = ISOLATED))
//    @Sql(
//            scripts = "/delete-test-data.sql",
//            config = @SqlConfig(transactionMode = ISOLATED),
//            executionPhase = AFTER_TEST_METHOD
//    )
//    void shouldUpdateAnExistingCashCard() {
//        CashCard cashCardUpdate = new CashCard(null, 19.99, null);
//        HttpEntity<CashCard> request = new HttpEntity<>(cashCardUpdate);
//        ResponseEntity<Void> response = restTemplate
//                .withBasicAuth("sarah1", "abc123")
//                .exchange("/cashcards/99", HttpMethod.PUT, request, Void.class);
////        .exchange("/cashcards/99", HttpMethod.GET, new HttpEntity(null), String.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
//
//        ResponseEntity<String> getResponse = restTemplate
//                .withBasicAuth("sarah1", "abc123")
//                .getForEntity("/cashcards/99", String.class);
//        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
//        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
//        Number id = documentContext.read("$.id");
//        Double amount = documentContext.read("$.amount");
//        assertThat(id).isEqualTo(99);
//        assertThat(amount).isEqualTo(19.99);
//    }
//
//    @Test
//    @Sql(value = "/data.sql",
//            config = @SqlConfig(transactionMode = ISOLATED))
//    @Sql(
//            scripts = "/delete-test-data.sql",
//            config = @SqlConfig(transactionMode = ISOLATED),
//            executionPhase = AFTER_TEST_METHOD
//    )
//    void shouldNotUpdateACashCardThatDoesNotExist() {
//        CashCard unknownCard = new CashCard(null, 19.99, null);
//        HttpEntity<CashCard> request = new HttpEntity<>(unknownCard);
//        ResponseEntity<Void> response = restTemplate
//                .withBasicAuth("sarah1", "abc123")
//                .exchange("/cashcards/99999", HttpMethod.PUT, request, Void.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//    }
//
//    @Test
//    @Sql(value = "/data.sql",
//            config = @SqlConfig(transactionMode = ISOLATED))
//    @Sql(
//            scripts = "/delete-test-data.sql",
//            config = @SqlConfig(transactionMode = ISOLATED),
//            executionPhase = AFTER_TEST_METHOD
//    )
//    void shouldNotUpdateACashCardThatIsOwnedBySomeoneElse() {
//        CashCard kumarsCard = new CashCard(null, 333.33, null);
//        HttpEntity<CashCard> request = new HttpEntity<>(kumarsCard);
//        ResponseEntity<Void> response = restTemplate
//                .withBasicAuth("sarah1", "abc123")
//                .exchange("/cashcards/102", HttpMethod.PUT, request, Void.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//    }
//
//    @Test
//    @Sql(value = "/data.sql",
//            config = @SqlConfig(transactionMode = ISOLATED))
//    @Sql(
//            scripts = "/delete-test-data.sql",
//            config = @SqlConfig(transactionMode = ISOLATED),
//            executionPhase = AFTER_TEST_METHOD
//    )
//    @DirtiesContext
//    void shouldDeleteAnExistingCashCard() {
//        ResponseEntity<Void> response = restTemplate
//                .withBasicAuth("sarah1", "abc123")
//                .exchange("/cashcards/99", HttpMethod.DELETE, null, Void.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
//
//        ResponseEntity<String> getResponse = restTemplate
//                .withBasicAuth("sarah1", "abc123")
//                .getForEntity("/cashcards/99", String.class);
//        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//    }
//
//    @Sql(value = "/data.sql",
//            config = @SqlConfig(transactionMode = ISOLATED))
//    @Sql(
//            scripts = "/delete-test-data.sql",
//            config = @SqlConfig(transactionMode = ISOLATED),
//            executionPhase = AFTER_TEST_METHOD
//    )
//    @Test
//    void shouldNotDeleteACashCardThatDoesNotExist() {
//        ResponseEntity<Void> deleteResponse = restTemplate
//                .withBasicAuth("sarah1", "abc123")
//                .exchange("/cashcards/99999", HttpMethod.DELETE, null, Void.class);
//        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//    }
//
//    @Sql(value = "/data.sql",
//            config = @SqlConfig(transactionMode = ISOLATED))
//    @Sql(
//            scripts = "/delete-test-data.sql",
//            config = @SqlConfig(transactionMode = ISOLATED),
//            executionPhase = AFTER_TEST_METHOD
//    )
//    @Test
//    void shouldNotAllowDeletionOfCashCardsTheyDoNotOwn() {
//        ResponseEntity<Void> deleteResponse = restTemplate
//                .withBasicAuth("sarah1", "abc123")
//                .exchange("/cashcards/102", HttpMethod.DELETE, null, Void.class);
//        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//
//        ResponseEntity<String> getResponse = restTemplate
//                .withBasicAuth("kumar2", "xyz789")
//                .getForEntity("/cashcards/102", String.class);
//        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
//    }
//}