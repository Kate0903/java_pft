package generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {
  @Parameter (names = "-c", description = "Contact count")
  public int count;
  @Parameter (names = "-f", description = "Target file")
  public String file;
  @Parameter(names = "-d", description = "Data format")
  public String format;
  public static void main(String[] args) throws IOException {
    ContactDataGenerator generator = new ContactDataGenerator();
    JCommander jCommander = new JCommander(generator);
    try{
      jCommander.parse(args);
    } catch(ParameterException ex){
      jCommander.usage();
      return;
    }
    generator.run();
  }

  private void run() throws IOException {
    List<ContactData> groups = generateContacts(count);
    if (format.equals("csv")) {
      saveAsCsv(new File(file), groups);
    }  else if ((format.equals("json"))) {
      saveAsJson(new File(file), groups);
    }
    else {
      System.out.println("Unrecognized format " + format);
    }
  }

  private void saveAsCsv(File file, List<ContactData> contacts) throws IOException {
    Writer writer = new FileWriter(file);
    for (ContactData contact : contacts){
      writer.write(String.format("%s;%s;%s;%s;%s\n", contact.getFirstname(),
              contact.getLastname(), contact.getMobilePhone(), contact.getAddress(), contact.getEmail()));
    }
    writer.close();
  }

  private void saveAsJson(File file, List<ContactData> contacts) throws IOException {
    Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    String json = gson.toJson(contacts);
    Writer writer = new FileWriter(file);
    writer.write(json);
    writer.close();
  }

  private List<ContactData> generateContacts(int count) {
    List<ContactData> contacts = new ArrayList<ContactData>();
    for (int i = 0; i<count; i++){
      contacts.add(new ContactData().withFirstname(String.format("kate %s",i))
              .withLastname(String.format("kap %s",i)).withMobile(String.format("89562 %s",i))
              .withAddress(String.format("Tokorevskaya %s",i)).withEmail(String.format("ghj@mail.ru %s",i)));
    }
    return contacts;
  }


}
