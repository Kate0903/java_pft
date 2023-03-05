package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

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
     new Select(wd.findElement(By.name("new_group"))).selectByIndex(1);
   } else {
     Assert.assertFalse(isElementPresent(By.name("new_group")));
   }
  }



  public void selectContact(int index) {
    wd.findElements(By.xpath("//input[@name='selected[]']")).get(index).click();

  }

  public void deleteSelectedContacts() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void editSelectedContact(int index) {
    wd.findElements(By.xpath("//img[@alt='Edit']")).get(index).click();

  }

  public void submitContactModification() {
    click(By.xpath("//div[@id='content']/form/input[22]"));
  }

  public boolean isThereAContact() {
    return isElementPresent(By.xpath("//input[@name='selected[]']"));
  }

  public void createContact(ContactData contact) {
    fillContactForm(contact, true);
    addContactCreation();
    returnHomePage();
  }

  public int getContactCount() {
    return wd.findElements(By.xpath("//input[@name='selected[]']")).size();
  }
}
