package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {


  @Test
  public void testContactCreation() throws Exception {
    app.gotoAddContactPage();
    app.fillContactForm(new ContactData("kate", "kap", "89562", "Tokorevskaya","ghj@mail.ru"));
    app.addContactCreation();
    app.returnHomePage();
  }

}
