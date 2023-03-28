package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;

public class AddContactIntoGroup extends TestBase {

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
  public void testAddContactIntoGroup() {
    app.goTo().homePage();
    Contacts before = app.contact().all();
    ContactData modifiedContact = before.iterator().next();
    app.contact().selectContactById(modifiedContact.getId());
    List<WebElement> groupsList = app.group().groupsOnAddressbookPage();
    Groups groupsContact = app.db().groupsForContacts(modifiedContact.getId());
    List<Integer> groupsIdList = groupsList.stream().mapToInt(g-> Integer.parseInt(g.getAttribute("value")))
            .boxed().collect(Collectors.toList());
    List<Integer> groupsIdContact = groupsContact.stream().mapToInt(g-> g.getId())
            .boxed().collect(Collectors.toList());
    List<Integer> others = groupsIdList.stream()
            .filter(id->!groupsIdContact.contains(id))
            .collect(Collectors.toList());
    int otherIdGroup = others.iterator().next();
    app.group().chooseGroup(otherIdGroup);
    app.contact().addContactIntoGroup();
    Groups groups = app.db().groupsForContacts(modifiedContact.getId());
    List<Integer> groupsIds = groups.stream().mapToInt(g->g.getId()).boxed().collect(Collectors.toList());
    Assert.assertTrue(groupsIds.contains(otherIdGroup));

  }
}
