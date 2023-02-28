package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

public class ContactDeletionTests extends TestBase{
  @Test
  public void testContactDeletion(){
    app.getNavigationHelper().gotoHomePage();
    if (!app.getContactHelper().isThereAContact()){
      if (!app.getGroupHelper().isThereAGroup()){
        app.getGroupHelper().createGroup(new GroupData("test1", null, null));
      }
      app.getNavigationHelper().gotoAddContactPage();
      app.getContactHelper().createContact(new ContactData("kate", "kap", "89562", "Tokorevskaya","ghj@mail.ru","test1"));
    }
    app.getContactHelper().selectContact();
    app.getContactHelper().deleteSelectedContacts();
    app.getContactHelper().closeAlert();
    app.getNavigationHelper().gotoHomePage();
  }

}
