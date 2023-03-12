package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.stqa.pft.addressbook.tests.TestBase.app;

public class ContactMailTests extends TestBase{
  @Test
  public void testContactMails(){
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
    assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));

  }
  private String mergeEmails(ContactData contact) {
    return Arrays.asList(contact.getEmail(),contact.getEmail2(), contact.getEmail3())
            .stream().filter((s) -> !s.equals(""))
            .collect(Collectors.joining("\n"));
  }

}
