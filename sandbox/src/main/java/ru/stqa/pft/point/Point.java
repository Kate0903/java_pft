package ru.stqa.pft.point;

public class Point {
  double x;
  double y;

  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public static double distance(Point p1, Point p2) {
    double dx = p1.x - p2.x;
    double dy = p1.y - p2.y;
    double sx = Math.pow(dx, 2);
    double sy = Math.pow(dy, 2);
    return Math.sqrt(sx + sy);

  }

  public double distance(Point p) {
    double dx = p.x - this.x;
    double dy = p.y - this.y;
    double sx = Math.pow(dx, 2);
    double sy = Math.pow(dy, 2);
    return Math.sqrt(sx + sy);
  }


}
