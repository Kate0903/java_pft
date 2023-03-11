package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
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

  public void create(ContactData contact) {
    fillContactForm(contact, true);
    addContactCreation();
    returnHomePage();
  }
  public void modify(int index, ContactData contact) {
    editSelectedContact(index);
    fillContactForm(contact, false);
    submitContactModification();
    returnHomePage();
  }
  public void delete(int index) {
   selectContact(index);
   deleteSelectedContacts();
   closeDeleteAlert();

  }

  public int getContactCount() {
    return wd.findElements(By.xpath("//input[@name='selected[]']")).size();
  }

  public List<ContactData> list() {
    List<ContactData> contacts = new ArrayList<ContactData>();
    List<WebElement> elements = wd.findElements(By.xpath("//table[@id='maintable']/tbody/tr[@name='entry']"));
    for (WebElement element : elements){
      List<WebElement>  cells = element.findElements(By.cssSelector("td"));
      String firstName =cells.get(2).getText();
      String lastName = cells.get(1).getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      ContactData contact = new ContactData().withId(id).withFirstname(firstName).withLastname(lastName);
      contacts.add(contact);
    }

    return contacts;
  }

  public void closeDeleteAlert() {
    closeAlert();
    WebDriverWait wait = new WebDriverWait(wd, 10);
    wait.until(webDriver -> webDriver.getCurrentUrl().contains("/addressbook/delete.php"));
  }
}
