package stepDefinitions;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import utils.DriverFactory;

import org.junit.Assert;

public class ContactUsSteps extends DriverFactory {
	@Given("^I access webdriveruniversity contact us form$")
	public void i_access_webdriveruniversity_contact_us_form() {
		getDriver().get("http://www.webdriveruniversity.com/Contact-Us/contactus.html");
	}

	@When("^I enter a valid firstname$")
	public void i_enter_a_valid_firstname() throws InterruptedException {
		Thread.sleep(3000);
		getDriver().findElement(By.cssSelector("input[name='first_name']")).sendKeys("tom");
	}

	@When("^I enter a valid last name$")
	public void i_enter_a_valid_last_name(DataTable dataTable){
		List<List<String>> data = dataTable.raw();
		
		getDriver().findElement(By.cssSelector("input[name='last_name']")).sendKeys(data.get(0).get(1));
	}

	@And("^i enter a valid email address$")
	public void i_enter_a_valid_email_address()  {
		getDriver().findElement(By.cssSelector("input[name='email']")).sendKeys("webdriveruniversity@outlook.com");
	}

	@And("^i enter comments$")
	public void i_enter_comments(DataTable dataTable) {
		List<List<String>> data = dataTable.raw();
		
		getDriver().findElement(By.cssSelector("textarea[name='message']")).sendKeys(data.get(0).get(0));
		getDriver().findElement(By.cssSelector("textarea[name='message']")).sendKeys(data.get(0).get(1));
	}

	@When("^i click on the submit button$")
	public void i_click_on_the_submit_button() {
		getDriver().findElement(By.xpath("//input[@value='SUBMIT']")).click();
	}

	@Then("^the information should successfully be submitted via the contact us form$")
	public void the_information_should_successfully_be_submitted_via_the_contact_us_form() throws InterruptedException  {
		Thread.sleep(3000);
		
		WebElement thanksContactUsPage = getDriver().findElement(By.xpath(".//*[@id='contact_reply']/h1"));
		
		//expected actual
		Assert.assertEquals("thankyouforyourmessage!", thanksContactUsPage.getText().toLowerCase().replaceAll("[ ()0-9]", ""));
	}
}
