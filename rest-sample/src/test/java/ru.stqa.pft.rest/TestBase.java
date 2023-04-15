package ru.stqa.pft.rest;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Executor;
import org.testng.SkipException;


import java.io.IOException;


public class TestBase {
  public boolean isIssueResolved(int issueId) throws IOException {
    String json = getExecutor().execute(Request.Get("http://bugify.stqa.ru/api/issues/" + issueId + ".json"))
            .returnContent().asString();
    JsonElement parsed = new JsonParser().parse(json);
    JsonElement issue = parsed.getAsJsonObject().get("issues");
    JsonArray array = issue.getAsJsonArray();
    JsonElement item = array.get(0);
    String status = item.getAsJsonObject().get("state_name").getAsString();
    if(status.equals("Resolved")||status.equals("Closed")){
    return true;
    } else return false;
  }

  public void skipIfNotFixed(int issueId) throws IOException {
    if (!isIssueResolved(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }

  private Executor getExecutor() {
    return Executor.newInstance().auth("b31e382ca8445202e66b03aaf31508a3", "");
  }

}

