package com.cucumber.allure.stepdef;

import com.cucumber.allure.context.ScenarioContext;
import com.cucumber.allure.rest.ResponseErrorEnum;
import com.cucumber.allure.stores.UserLayerContextStore;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.testng.AssertJUnit.assertEquals;

public class GeneralSteps extends TestCore {
    private static final Logger LOG = LogManager.getLogger(GeneralSteps.class);

    public GeneralSteps(final UserLayerContextStore userLayerContextStore,
                        final ScenarioContext scenarioContext) {
        super(userLayerContextStore, scenarioContext);
    }

    @Then("the response has {} error")
    public void theResponseHasError(final ResponseErrorEnum expectedResponseMessage) {
        final ResponseErrorEnum actualResponseMessage = scenarioContext.getResponse();
        LOG.info("Error response: {}", actualResponseMessage);
        assertEquals(expectedResponseMessage, actualResponseMessage);
    }
}
