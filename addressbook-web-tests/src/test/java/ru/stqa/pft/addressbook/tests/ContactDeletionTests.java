package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactDeletionTests extends TestBase{
  public void ensurePreconditions(){
    app.goTo().homePage();
    if (app.contact().all().size() == 0){
      app.goTo().groupPage();
      if (app.group().all().size() == 0){
        app.group().create(new GroupData()
                .withName("test1"));
      }
      app.goTo().addContactPage();
      app.contact().create(new ContactData()
              .withFirstname("kate")
              .withLastname("kap")
              .withMobile("89562")
              .withAddress("Tokorevskaya")
              .withEmail("ghj@mail.ru"));
    }
    app.goTo().homePage();
  }
  @Test
  public void testContactDeletion() throws InterruptedException {
    Contacts before = app.contact().all();
    ContactData deletedContact = before.iterator().next();
    app.contact().delete(deletedContact);
    app.goTo().homePage();
    assertThat(app.contact().count(), equalTo(before.size() - 1));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before.without(deletedContact)));


  }



}
