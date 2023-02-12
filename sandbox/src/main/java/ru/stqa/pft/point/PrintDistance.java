package ru.stqa.pft.point;

public class PrintDistance {
  public static void main(String[] args) {
    Point p1 = new Point(10, -2);
    Point p2 = new Point(20, 10);
    System.out.println("Расстояние между точкой с координатами " + p1.x + "," + p1.y + " и точкой с координатами " + p2.x +
            "," + p2.y + " составляет: " + Point.distance(p1, p2));

    System.out.println("Расстояние между точкой с координатами " + p1.x + "," + p1.y + " и точкой с координатами " + p2.x +
            "," + p2.y + " составляет: " + p1.distance(p2));


  }
}
