package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactCreationTests extends TestBase {


  @DataProvider
  public Iterator<Object[]> validContact(){
    List<Object[]> list = new ArrayList<Object[]>();
    list.add(new Object[] {new ContactData().withFirstname("kate 1").withLastname("kap 1")
            .withMobile("89562 1").withAddress("Tokorevskaya 1").withEmail("ghj@mail.ru 1")});
    list.add(new Object[] {new ContactData().withFirstname("kate 2").withLastname("kap 2")
            .withMobile("89562 2").withAddress("Tokorevskaya 2").withEmail("ghj@mail.ru 2")});
    list.add(new Object[] {new ContactData().withFirstname("kate 3").withLastname("kap 3")
            .withMobile("89562 3").withAddress("Tokorevskaya 3").withEmail("ghj@mail.ru 3")});
    return list.iterator();
  }
  @Test (dataProvider = "validContact")
  public void testContactCreation(ContactData contact) throws Exception {
    app.goTo().homePage();
    Contacts before = app.contact().all();
    File photo = new File("src/test/resources/3787591.png");
    app.goTo().addContactPage();
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
