package ru.stqa.pft.mantis.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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
    app.changePassword().listUsers();
    WebElement user = app.changePassword().listUsers().iterator().next();
    String email =  app.changePassword().getEmail(user);
    String username =  app.changePassword().getUserName(user);
    app.changePassword().clickUser(user);
    app.changePassword().button();
    List<MailMessage> mailMessages = app.mail().waitForMAil(1,30000);
    String confirmationLink = findConfirmationLink(mailMessages, email);
    app.changePassword().finish(confirmationLink, newPassword, username);
    assertTrue(app.newSession().login(username,newPassword));
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

