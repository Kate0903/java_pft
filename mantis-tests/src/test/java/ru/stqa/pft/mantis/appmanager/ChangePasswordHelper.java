package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class ChangePasswordHelper extends HelperBase{
  public ChangePasswordHelper(ApplicationManager app) {
    super(app);

  }
  public void entering() {
    wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
    type (By.name("username"),app.getProperty("web.adminLogin"));
    click(By.cssSelector("input[value = '¬ход']"));
    type (By.name("password"), app.getProperty("web.adminPassword"));
    click(By.cssSelector("input[value = '¬ход']"));


  }

  public void navigation() {
    click(By.xpath("//i[@class='fa fa-gears menu-icon']"));
    click(By.xpath("//*[text()[contains(.,'”правление пользовател€ми')]]"));
  }

  public List<org.openqa.selenium.WebElement> listUsers() {
    return   wd.findElements(By.xpath("//table[@class='table table-striped table-bordered table-condensed table-hover']/tbody/tr"))
            .stream()
            .filter(e->!((org.openqa.selenium.WebElement)e.findElements(By.xpath(".//td")).toArray()[3])
                    .getText().equalsIgnoreCase("администратор"))
            .collect(Collectors.toList());
  }
  public void clickUser(WebElement user) {
    user.findElement(By.xpath(".//a")).click();
  }
  public String getEmail(WebElement user) {
    return user.findElement(By.xpath(".//td[3]")).getText();
  }
  public String getUserName(WebElement user) {
    return user.findElement(By.xpath(".//td/a")).getText();
  }
    public void button() {
    click(By.cssSelector("input[value = '—бросить пароль']"));
  }

  public void finish(String confirmationLink, String newPassword, String username) {
    wd.get(confirmationLink);
    type(By.name("realname"), username);
    type(By.name("password"), newPassword);
    type(By.name("password_confirm"), newPassword);
    click(By.xpath("//button[@type='submit']"));

  }



}
