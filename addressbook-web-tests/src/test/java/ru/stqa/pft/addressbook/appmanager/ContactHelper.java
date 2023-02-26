package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import org.openqa.selenium.support.ui.Select;

public class ContactHelper extends HelperBase{

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void returnHomePage() {
   click(By.linkText("home page"));
  }

  public void addContactCreation() {
  click(By.xpath("//div[@id='content']/form/input[21]"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
   type(By.name("firstname"), contactData.getFirstname());
   type(By.name("lastname"), contactData.getLastname());
   type(By.name("mobile"), contactData.getMobile());
   type(By.name("address"), contactData.getAddress());
   type (By.name("email"), contactData.getEmail());
   if (creation){
     new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
   } else {
     Assert.assertFalse(isElementPresent(By.name("new_group")));
   }
  }



  public void selectContact() {
    click(By.xpath("//input[@name='selected[]']"));
  }

  public void deleteSelectedContacts() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void editSelectedContact() {
    click(By.xpath("//img[@alt='Edit']"));
  }

  public void submitContactModification() {
    click(By.xpath("//div[@id='content']/form/input[22]"));
  }
}
