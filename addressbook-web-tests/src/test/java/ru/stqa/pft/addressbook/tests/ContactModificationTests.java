package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class ContactModificationTests extends TestBase{
  @Test
  public void testContactModification(){

    if (!app.getContactHelper().isThereAContact()){
      app.getNavigationHelper().gotoGroupPage();
      if (!app.getGroupHelper().isThereAGroup()){
       app.getGroupHelper().createGroup(new GroupData("test1", "test2", "test3"));
      }
      app.getNavigationHelper().gotoAddContactPage();
      app.getContactHelper().createContact(new ContactData("kate", "kap", "89562", "Tokorevskaya","ghj@mail.ru"));
    }
    app.getNavigationHelper().gotoHomePage();
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().editSelectedContact(before.size() - 1);
    app.getContactHelper().fillContactForm(new ContactData("kate", "kap", "89562", "Tokorevskaya","ghj@mail.ru"), false);
    app.getContactHelper().submitContactModification();
    app.getContactHelper().returnHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size());
  }

}
