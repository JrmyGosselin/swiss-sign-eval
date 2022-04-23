package com.jgo.swisssigneval;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "features",
        plugin = {"pretty", "summary"})
public class RunCucumberTest {
}
