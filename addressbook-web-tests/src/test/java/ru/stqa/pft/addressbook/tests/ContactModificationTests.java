package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {
  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.contact().all().size() == 0) {
      app.goTo().groupPage();
      if (app.group().all().size() == 0) {
        app.group().create(new GroupData().withName("test1").withHeader("test2").withFooter("test3"));
      }
      app.goTo().addContactPage();
      app.contact().create(
              new ContactData()
                      .withFirstname("kate")
                      .withLastname("kap")
                      .withMobile("89562")
                      .withAddress("Tokorevskaya")
                      .withEmail("ghj@mail.ru"));
    }
    app.goTo().homePage();
  }

  @Test
  public void testContactModification() {
    Contacts before = app.db().contacts();
    ContactData modifiedContact = before.iterator().next();
    File photo = new File("src/test/resources/3787591.png");
    modifiedContact.withPhoto(photo);
    ContactData contact = new ContactData()
            .withId(modifiedContact.getId())
            .withFirstname("kate")
            .withLastname("kap")
            .withMobile("89562")
            .withAddress("Tokorevskaya")
            .withEmail("ghj@mail.ru")
            .withPhoto(photo);
    app.contact().modify(contact);
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.db().contacts();
    MatcherAssert.assertThat(after, CoreMatchers.equalTo(before.without(modifiedContact).withAdded(contact)));
  }


}
