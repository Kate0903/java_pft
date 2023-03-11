package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class ContactDeletionTests extends TestBase{
  public void ensurePreconditions(){
    app.goTo().homePage();
    if (app.contact().list().size() == 0){
      app.goTo().groupPage();
      if (app.group().list().size() == 0){
        app.group().create(new GroupData().withName("test1"));
      }
      app.goTo().addContactPage();
      app.contact().create(new ContactData().withFirstname("kate").withLastname("kap").withMobile("89562").withAddress("Tokorevskaya").withEmail("ghj@mail.ru"));
    }
    app.goTo().homePage();
  }
  @Test
  public void testContactDeletion() throws InterruptedException {
    List<ContactData> before = app.contact().list();
    int index = before.size() - 1;
    app.contact().delete(index);
    app.goTo().homePage();
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), index);

    before.remove(index);
      Assert.assertEquals(before, after);

  }



}
