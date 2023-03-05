package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ContactDeletionTests extends TestBase{
  @Test
  public void testContactDeletion(){
    app.getNavigationHelper().gotoHomePage();
    if (!app.getContactHelper().isThereAContact()){
      app.getNavigationHelper().gotoGroupPage();
      if (!app.getGroupHelper().isThereAGroup()){
        app.getGroupHelper().createGroup(new GroupData("test1", null, null));
      }
      app.getNavigationHelper().gotoAddContactPage();
      app.getContactHelper().createContact(new ContactData("kate", "kap", "89562", "Tokorevskaya","ghj@mail.ru"));
    }
    app.getNavigationHelper().gotoHomePage();
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().selectContact(before.size() - 1);
    app.getContactHelper().deleteSelectedContacts();
    app.getContactHelper().closeAlert();
    app.getNavigationHelper().gotoHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size() - 1);

    before.remove(before.size() - 1);
      Assert.assertEquals(before, after);

  }

}
