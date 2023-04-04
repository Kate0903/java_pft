package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.AssertJUnit.assertTrue;

public class ChangePassword extends TestBase{
  @BeforeMethod
  public void startMailServer(){
    app.mail().start();
  }
  @Test(enabled = true)
  public void testChangePassword() throws MessagingException, IOException {
    long now = System.currentTimeMillis();
    String newPassword = String.format("password%s", now);
    app.changePassword().entering();
    app.changePassword().navigation();
    //List<MailMessage> mailMessages = app.mail().waitForMAil(2,10000);
    app.changePassword().listUsers();
    //String confirmationLink = findConfirmationLink(mailMessages,email);
    //app.registration().finish(confirmationLink, password,user);
    //assertTrue(app.newSession().login(user,password));
  }

  private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m)->m.to.equals(email)).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }


  @AfterMethod(alwaysRun = true)
  public void stopMailServer(){
    app.mail().stop();
  }
}

