package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase{
  @Test
  public void testContactModification(){
    app.getNavigationHelper().gotoHomePage();
    app.getContactHelper().editSelectedContact();
    app.getContactHelper().fillContactForm(new ContactData("kate", "kap", "89562", "Tokorevskaya","ghj@mail.ru",null), false);
    app.getContactHelper().submitContactModification();
    app.getContactHelper().returnHomePage();

  }

}
