package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase{
  @BeforeMethod
  public void ensurePreconditions(){
    app.goTo().homePage();
    if (app.contact().list().size() == 0){
      app.goTo().groupPage();
      if (app.group().list().size() == 0){
        app.group().create(new GroupData("test1", "test2", "test3"));
      }
      app.goTo().addContactPage();
      app.contact().create(new ContactData("kate", "kap", "89562", "Tokorevskaya","ghj@mail.ru"));
    }
    app.goTo().homePage();
  }
  @Test
  public void testContactModification(){
    List<ContactData> before = app.contact().list();
    int index = before.size() - 1;
    ContactData contact = new ContactData(before.get(index).getId(),"kate", "kap", "89562", "Tokorevskaya","ghj@mail.ru");
    app.contact().modify(index, contact);
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size());

    before.remove(index);

    before.add(contact);
    Comparator<? super ContactData> byId = (c1, c2 )-> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals (before, after);
  }



}
