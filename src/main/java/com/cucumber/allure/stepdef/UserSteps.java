package com.cucumber.allure.stepdef;

import com.cucumber.allure.context.ScenarioContext;
import com.cucumber.allure.rest.model.CreateUser201ResponseDTO;
import com.cucumber.allure.rest.model.UserDTO;
import com.cucumber.allure.stores.UserLayerContextStore;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static org.testng.AssertJUnit.*;

public class UserSteps extends TestCore {
    private static final Logger LOG = LogManager.getLogger(UserSteps.class);

    public UserSteps(final UserLayerContextStore userLayerContextStore,
                     final ScenarioContext scenarioContext) {
        super(userLayerContextStore, scenarioContext);
    }

    @Given("(create )a new user of status {word} and store it as {word} -> {}")
    public void createANewUserOfTypeAndStoreItAs
            (final String statusString, final String contextId, final HttpStatus httpStatus) {
        final UserDTO                                  user     = getUserService().initContextUser(statusString);
        final ResponseEntity<CreateUser201ResponseDTO> response = getUserService().registerUser(user);
        assertTrue(response.getStatusCode().isSameCodeAs(httpStatus));

        if (response.getStatusCode().isSameCodeAs(HttpStatus.CREATED)) {
            user.setId(Objects.requireNonNull(response.getBody()).getId());
        }

        scenarioContext.storeContextObject(contextId, user);
    }

    @Then("verify that user {word} exists")
    public void verifyThatUserExists(final String contextId) {
        final UserDTO                       expUser  = (UserDTO) scenarioContext.getContextObject(contextId);
        final ResponseEntity<List<UserDTO>> response = getUserService().getUsers();
        assertTrue(response.getStatusCode().isSameCodeAs(HttpStatus.OK));

        final UserDTO actUser = Objects.requireNonNull(response.getBody())
                .stream()
                .filter(u -> Objects.equals(u.getName(), expUser.getName()))
                .findFirst()
                .orElse(null);

        assertEquals(actUser, expUser);
    }

    @When("delete user {word} -> {}")
    public void deleteUser(final String contextId, final HttpStatus httpStatus) {
        UserDTO              user     = (UserDTO) scenarioContext.getContextObject(contextId);
        ResponseEntity<Void> response = getUserService().deleteUser(user.getId());
        assertTrue(response.getStatusCode().isSameCodeAs(httpStatus));
    }

    @Then("verify that user {word} does not exist")
    public void verifyThatUserDoesNotExist(final String contextId) {
        final UserDTO                       user     = (UserDTO) scenarioContext.getContextObject(contextId);
        final ResponseEntity<List<UserDTO>> response = getUserService().getUsers();
        assertTrue(response.getStatusCode().isSameCodeAs(HttpStatus.OK));

        final UserDTO actUser = Objects.requireNonNull(response.getBody())
                .stream()
                .filter(u -> Objects.equals(u.getName(), user.getName()))
                .findFirst()
                .orElse(null);

        assertNull(actUser);
    }
}