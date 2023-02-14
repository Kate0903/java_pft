package ru.stqa.pft.point;

import org.testng.Assert;
import org.testng.annotations.Test;


public class PointTest {
  @Test
  public void testPoint1(){
    Point p1 = new Point(10,-2);
    Point p2 = new Point(20, 10);
    Assert.assertEquals(p1.distance(p2), 15.620499351813308);
  }
  @Test
  public void testPoint2(){
    Point p1 = new Point(-15,2);
    Point p2 = new Point(-15, 2);
    Assert.assertEquals(p1.distance(p2), 0);
  }
  @Test
  public void testPoint3(){
    Point p1 = new Point(0,0);
    Point p2 = new Point(0, 0);
    Assert.assertEquals(p1.distance(p2), 0);
  }
}
