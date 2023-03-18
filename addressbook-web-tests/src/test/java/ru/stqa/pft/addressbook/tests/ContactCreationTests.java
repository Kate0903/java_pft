package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactCreationTests extends TestBase {


  @DataProvider
  public Iterator<Object[]> validContactsFromCsv() throws IOException {
    List<Object[]> list = new ArrayList<Object[]>();
    try(BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.csv")))){
      String line = reader.readLine();
      while (line != null){
        String[] split = line.split(";");
        list.add(new Object[] {new ContactData().withFirstname(split[0]).withLastname(split[1])
                .withMobile(split[2]).withAddress(split[3]).withEmail(split[4])});
        line = reader.readLine();
      }
      return list.iterator();
    }
  }

  @DataProvider
  public Iterator<Object[]> validContactsFromJson() throws IOException {
    try(BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")))){
      String json = "";
      String line = reader.readLine();
      while (line != null){
        json += line;
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<ContactData> contact = gson.fromJson(json, new TypeToken<List<ContactData>>() {}.getType()); //List<ContactData>.class
      return contact.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
    }

  }
  @Test (dataProvider = "validContactsFromJson")
  public void testContactCreation(ContactData contact) throws Exception {
    app.goTo().homePage();
    Contacts before = app.contact().all();
    File photo = new File("src/test/resources/3787591.png");
    contact.withPhoto(photo);
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
