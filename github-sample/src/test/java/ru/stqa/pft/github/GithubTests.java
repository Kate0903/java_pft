package ru.stqa.pft.github;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import com.jcabi.github.*;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.IdentityHashMap;

public class GithubTests {

  @Test
  public void testCommits() throws IOException {
    Github github = new RtGithub("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");
    RepoCommits commits = github.repos().get(new Coordinates.Simple("Kate0903", "java_pft")).commits();
    for (RepoCommit commit : commits.iterate(new ImmutableMap.Builder<String,String>().build())){
      System.out.println(new RepoCommit.Smart(commit).message());
    }
  }
}
