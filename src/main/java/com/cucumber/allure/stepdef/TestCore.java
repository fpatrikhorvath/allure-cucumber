package com.cucumber.allure.stepdef;

import com.cucumber.allure.context.ScenarioContext;
import com.cucumber.allure.service.BookService;
import com.cucumber.allure.service.UserService;
import com.cucumber.allure.stores.UserLayerContextStore;
import io.cucumber.spring.CucumberContextConfiguration;
import io.cucumber.spring.ScenarioScope;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration(loader = SpringBootContextLoader.class, value = {"classpath:spring.xml"})
@SpringBootTest(classes = TestCore.class)
@Primary
@ScenarioScope
public class TestCore {
    protected final ScenarioContext scenarioContext;
    private final UserLayerContextStore userLayerContextStore;

    public TestCore(final UserLayerContextStore userLayerContextStore,
                    final ScenarioContext scenarioContext) {
        this.userLayerContextStore = userLayerContextStore;
        this.scenarioContext = scenarioContext;
    }

    protected UserService getUserService() {
        return userLayerContextStore.getUserService();
    }

    protected BookService getBookService() {
        return userLayerContextStore.getBookService();
    }
}