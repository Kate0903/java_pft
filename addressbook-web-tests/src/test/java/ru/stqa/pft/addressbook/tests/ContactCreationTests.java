package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {


  @Test
  public void testContactCreation() throws Exception {
    app.goTo().homePage();
    List<ContactData> before = app.contact().list();
    app.goTo().addContactPage();
    ContactData contact = new ContactData().withFirstname("kate").withLastname("kap").withMobile("89562").withAddress("Tokorevskaya").withEmail("ghj@mail.ru");
    app.contact().create(contact);
    //TimeUnit.SECONDS.sleep(5);
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size() + 1);


    before.add(contact);
    Comparator<? super ContactData> byId = (c1, c2 )-> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals (before, after);

  }
}
