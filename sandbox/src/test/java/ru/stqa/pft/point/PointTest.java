package ru.stqa.pft.point;

import org.testng.Assert;
import org.testng.annotations.Test;


public class PointTest {
  @Test
  public void testPoint(){
    Point p1 = new Point(10,-2);
    Point p2 = new Point(20, 10);
    Assert.assertEquals(p1.distance(p2), 15.620499351813308);
  }
}
