package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactCreationTests extends TestBase {


  @Test
  public void testContactCreation() throws Exception {
    app.goTo().homePage();
    Contacts before = app.contact().all();
    File photo = new File("src/test/resources/3787591.png");
    app.goTo().addContactPage();
    ContactData contact = new ContactData()
            .withFirstname("kate")
            .withLastname("kap")
            .withMobile("89562")
            .withAddress("Tokorevskaya")
            .withEmail("ghj@mail.ru")
            .withPhoto(photo);
    app.contact().create(contact);
    //TimeUnit.SECONDS.sleep(5);
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.contact().all();


    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));

  }
  @Test
  public void testBadContactCreation() throws Exception {
    app.goTo().homePage();
    Contacts before = app.contact().all();
    app.goTo().addContactPage();
    ContactData contact = new ContactData()
            .withFirstname("kate'");
    app.contact().create(contact);
    //TimeUnit.SECONDS.sleep(5);
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.contact().all();


    assertThat(after, equalTo(before));

  }
}
