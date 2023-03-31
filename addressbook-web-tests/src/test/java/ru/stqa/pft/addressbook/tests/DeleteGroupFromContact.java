package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class DeleteGroupFromContact extends TestBase{
  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.contact().all().size() == 0) {
      app.goTo().addContactPage();
      File photo = new File("src/test/resources/3787591.png");
      app.contact().createBeforeMethod(
              new ContactData()
                      .withFirstname("kate")
                      .withLastname("kap")
                      .withMobile("89562")
                      .withAddress("Tokorevskaya")
                      .withEmail("ghj@mail.ru")
                      .withPhoto(photo));
    }
    app.goTo().homePage();
  }

  @Test
  public void testAddContactToGroup() {
    app.goTo().homePage();
    Contacts before = app.contact().all();
    ContactData modifiedContact = before.iterator().next();
    Groups groupsBefore = app.db().groupsForContacts(modifiedContact.getId());
    if (groupsBefore.size() == 0){
      app.goTo().groupPage();
      if (app.db().groups().size() == 0) {
        app.group().create(new GroupData().withName("test1").withHeader("test2").withFooter("test3"));
      }
      app.goTo().homePage();
      app.contact().selectContactById(modifiedContact.getId());
      WebElement group = app.group().groupsOnAddressbookPage().iterator().next();
      int groupId = Integer.parseInt(group.getAttribute("value"));
      app.group().chooseGroup(groupId);
      app.contact().addContactIntoGroup();
    }

    Groups groupsAfterAdd = app.db().groupsForContacts(modifiedContact.getId());
    GroupData deletedGroup = groupsAfterAdd.iterator().next();
    app.goTo().homePage();
    app.group().groupsOnAddressbookPageFirst(deletedGroup.getId());
    app.contact().selectContactById(modifiedContact.getId());
    app.group().removeContactFromGroup();
    Groups groupsAfter = app.db().groupsForContacts(modifiedContact.getId());
    List<Integer> groupsIds = groupsAfter.stream().mapToInt(g->g.getId()).boxed().collect(Collectors.toList());
    Assert.assertFalse(groupsIds.contains(deletedGroup.getId()));
  }
}
