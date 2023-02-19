package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactHelper extends HelperBase{
  private WebDriver wd;

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void returnHomePage() {
   click(By.linkText("home page"));
  }

  public void addContactCreation() {
  click(By.xpath("//div[@id='content']/form/input[21]"));
  }

  public void fillContactForm(ContactData contactData) {
   type(By.name("firstname"), contactData.getFirstname());
   type(By.name("lastname"), contactData.getLastname());
   type(By.name("mobile"), contactData.getMobile());
   type(By.name("address"), contactData.getAddress());
   type (By.name("email"), contactData.getEmail());

  }
}
