package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChangePasswordHelper extends HelperBase{
  public ChangePasswordHelper(ApplicationManager app) {
    super(app);

  }
  public void entering() {
    wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
    type (By.name("username"),app.getProperty("web.adminLogin"));
    click(By.cssSelector("input[value = 'Вход']"));
    type (By.name("password"), app.getProperty("web.adminPassword"));
    click(By.cssSelector("input[value = 'Вход']"));


  }

  public void navigation() {
    click(By.xpath("//i[@class='fa fa-gears menu-icon']"));
    click(By.xpath("//*[text()[contains(.,'Управление пользователями')]]"));
  }

  public List<org.openqa.selenium.WebElement> listUsers() {
    boolean c = "администратор" == "администратор";
    List<org.openqa.selenium.WebElement> x =  wd.findElements(By.xpath("//table[@class='table table-striped table-bordered table-condensed table-hover']/tbody/tr"))
            .stream()
            .filter(e->!((org.openqa.selenium.WebElement)e.findElements(By.xpath("//td")).toArray()[3])
                    .getText().equals("администратор"))
            .collect(Collectors.toList());
    boolean w =!((org.openqa.selenium.WebElement) wd.findElements(By.xpath("//table[@class='table table-striped table-bordered table-condensed table-hover']/tbody/tr")).stream().findFirst().get()
            .findElements(By.xpath("//td")).toArray()[3])
            .getText().equals("администратор");
    return wd.findElements(By.xpath("//table[@class='table table-striped table-bordered table-condensed table-hover']/tbody/tr"));
  }

    public void chooseUser() {
    click(By.cssSelector("input[value = 'Сбросить пароль']"));
  }



}
