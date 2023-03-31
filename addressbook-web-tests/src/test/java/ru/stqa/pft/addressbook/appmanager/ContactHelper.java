package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import org.openqa.selenium.support.ui.Select;
import ru.stqa.pft.addressbook.model.Contacts;

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
   type(By.name("mobile"), contactData.getMobilePhone());
   type(By.name("address"), contactData.getAddress());
   type (By.name("email"), contactData.getEmail());
   attach((By.name("photo")), contactData.getPhoto());


   if (creation){
     new Select(wd.findElement(By.name("new_group"))).selectByIndex(1);
   } else {
     Assert.assertFalse(isElementPresent(By.name("new_group")));
   }
  }
  public void fillContactFormBeforeMethod(ContactData contactData){
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("mobile"), contactData.getMobilePhone());
    type(By.name("address"), contactData.getAddress());
    type(By.name("email"), contactData.getEmail());
    attach((By.name("photo")), contactData.getPhoto());
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
  public void createBeforeMethod(ContactData contact) {
    fillContactFormBeforeMethod(contact);
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
  public void modifyGroup(ContactData contact) {
    editSelectedContact(contact.getId());

    returnHomePage();
  }
  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteSelectedContacts();
    contactCache = null;
    closeDeleteAlert();
  }
  public int count() {
    return wd.findElements(By.xpath("//input[@name='selected[]']")).size();
  }
  public void addContactIntoGroup() {
    click(By.name("add"));
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
      String allPhones = cells.get(5).getText();
      String allEmails = cells.get(4).getText();
      String address = cells.get(3).getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      ContactData contact = new ContactData()
              .withId(id)
              .withFirstname(firstName)
              .withLastname(lastName)
              .withAllPhones(allPhones)
              .withAllEmails(allEmails)
              .withAddress(address);

      contactCache.add(contact);
    }

    return new Contacts(contactCache);
  }

  public void closeDeleteAlert() {
    closeAlert();
    WebDriverWait wait = new WebDriverWait(wd, 10);
    wait.until(webDriver -> webDriver.getCurrentUrl().contains("/addressbook/delete.php"));
  }

  public ContactData infoFromEditForm(ContactData contact) {
    initContactModificationById(contact.getId());
    String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    String email = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getText();

    wd.navigate().back();
    return new ContactData()
            .withId(contact.getId())
            .withFirstname(firstname)
            .withLastname(lastname)
            .withHomePhone(home)
            .withMobile(mobile)
            .withWorkPhone(work)
            .withEmail(email)
            .withEmail2(email2)
            .withEmail3(email3)
            .withAddress(address);


  }
  private void initContactModificationById(int id) {
    WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value='%s']", id)));
    WebElement row = checkbox.findElement(By.xpath("./../.."));
    List<WebElement> cells = row.findElements(By.tagName("td"));
    cells.get(7).findElement(By.tagName("a")).click();
  }
}

