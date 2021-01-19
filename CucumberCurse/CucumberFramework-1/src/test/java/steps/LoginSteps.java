package steps;


import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;


public class LoginSteps {

    WebDriver driver;

    @Before()
    public void setup() {
        System.setProperty("webdriver.gecko.driver", System.getProperty("user.home") +
                "/Documents/repos/udemy_java/CucumberCurse/CucumberFramework/src/test/resources/geckodriver");
        ///Users/alexandrerodrigues/Documents/repos/udemy_java/CucumberCurse/CucumberFramework
        this.driver = new FirefoxDriver();
        this.driver.manage().window().maximize();
        this.driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

    }

    @Given("^User navigates to stackoverflow website$")
    public void user_navigates_to_stackoverflow_website() throws Throwable {
        driver.get("https://stackoverflow.com/");
    }

    @Given("^User clicks on the login button$")
    public void user_clicks_on_the_login_button() throws Throwable {
        driver.findElement(By.xpath("//a[@class='login-link s-btn s-btn__filled py8 js-gps-track']")).click();

    }

    @Given("^User enters a valid username$")
    public void user_enters_a_valid_username() throws Throwable {
       // Thread.sleep(10000);
        //driver.findElement(By.xpath("//input[@id='email']")).sendKeys("marodrigues20@gmail.com");

    }

    @Given("^User enters a valid password$")
    public void user_enters_a_valid_password() throws Throwable {
        //Thread.sleep(3000);
        //driver.findElement(By.xpath("//input[@id='password']")).sendKeys("NeverTrust@2021");

    }

    @Given("^User clicks on the login button on homepage$")
    public void user_clicks_on_the_login_button_on_homepage() throws Throwable {
        //driver.findElement(By.xpath("//button[@id='submit-button']")).click();
    }

    @Then("^User should be taken to the successful login page$")
    public void user_should_be_taken_to_the_successful_login_page() throws Throwable {
        //Thread.sleep(5000);
        //WebElement askQuestionButton = driver.findElement(By.xpath(("//a[@class='ws-nowrap s-btn s-btn__primary']")));
        //Assert.assertEquals(true, askQuestionButton.isDisplayed());
    }
}
