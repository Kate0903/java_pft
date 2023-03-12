package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import org.openqa.selenium.support.ui.Select;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
  public void selectContactById(int id) {
    wd.findElement(By.cssSelector("input[value ='" + id +"']")).click();

  }

  public void deleteSelectedContacts() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void editSelectedContact(int id) {
    wd.findElement(By.xpath("//a[@href='edit.php?id=" + id + "']")).click();

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
    contactCache = null;
    returnHomePage();
  }
  public void modify(ContactData contact) {
    editSelectedContact(contact.getId());
    fillContactForm(contact, false);
    submitContactModification();
    contactCache = null;
    returnHomePage();
  }
  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteSelectedContacts();
    contactCache = null;
    closeDeleteAlert();
  }
  public int getContactCount() {
    return wd.findElements(By.xpath("//input[@name='selected[]']")).size();
  }

  private Contacts contactCache = null;

  public Contacts all() {
    if (contactCache != null){
      return new Contacts(contactCache);
    }
    contactCache = new Contacts();
    List<WebElement> elements = wd.findElements(By.xpath("//table[@id='maintable']/tbody/tr[@name='entry']"));
    for (WebElement element : elements){
      List<WebElement>  cells = element.findElements(By.cssSelector("td"));
      String firstName =cells.get(2).getText();
      String lastName = cells.get(1).getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      ContactData contact = new ContactData().withId(id).withFirstname(firstName).withLastname(lastName);
      contactCache.add(contact);
    }

    return new Contacts(contactCache);
  }

  public void closeDeleteAlert() {
    closeAlert();
    WebDriverWait wait = new WebDriverWait(wd, 10);
    wait.until(webDriver -> webDriver.getCurrentUrl().contains("/addressbook/delete.php"));
  }
}

