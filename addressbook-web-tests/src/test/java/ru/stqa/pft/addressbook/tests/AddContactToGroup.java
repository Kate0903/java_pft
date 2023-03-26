package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.appmanager.ContactHelper;
import ru.stqa.pft.addressbook.appmanager.GroupHelper;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class AddContactToGroup extends TestBase {

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
  public void testAddContactToGroup() {
    app.goTo().homePage();
    Contacts before = app.contact().all();
    ContactData modifiedContact = before.iterator().next();
    WebElement group = app.group().groupsOnAddressbookPage().iterator().next();
    int groupId = Integer.parseInt(group.getAttribute("value"));
    group.click();
    app.contact().addContactIntoGroup();
    Groups groups = app.db().groupsForContacts(modifiedContact.getId());
    List<Integer> groupsIds = groups.stream().mapToInt(g->g.getId()).boxed().collect(Collectors.toList());
    Assert.assertTrue(groupsIds.contains(groupId));

  }

}
