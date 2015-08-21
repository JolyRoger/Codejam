package org.monakhov.codejam;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by daniil.monakhov on 8/21/2015.
 */

class Point {
    int x, y;
    Direction[] directions;
    boolean valid = false;

    public Point(int x, int y) { this.x = x; this.y = y; }

    public void setDirection(Direction[] directions) {
        this.directions = directions;
    }

    @Override
    public String toString() {
        return x + ":" + y + "-" + directions + "-" + valid;
    }
}

enum Direction {
    Up, Down, Left, Right
}

public class BabylonianWall {

    boolean[] states;

    private boolean isRuin(LinkedList<Point> points, Direction direction) {
        states[0] = true;
        if (points.size() == 1) return true;
        return false;
    }

    private Direction[] getResult(Point p) {
        return new Direction[] { Direction.Down, Direction.Up, Direction.Left, Direction.Right };
    }

    public boolean process(ArrayList<Point> points) {
        for (int i = 0; i < points.size(); i++) {
            Point point = points.get(i);
            if (i == 0) {
                point.setDirection(new Direction[] { Direction.Down, Direction.Up, Direction.Left, Direction.Right });
                point.valid = true;
            } else {

            }
        }
        return points.get(points.size()-1).valid = true;
    }

    public void processAllData(ArrayList<ArrayList<Point>> data) {
//        for (LinkedList<Point> points : data) {
            process(/*points*/ data.get(0));
//        }
//        for (LinkedList<Point> points : data) {
            for (Point point : /*points*/ data.get(0)) {
                System.out.println(point);
            }
//        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("No input file");
            System.exit(1);
        }
        File f = new File(args[0]);
        if (!f.exists()) {
            System.err.println("Input file does not exist");
            System.exit(1);
        }

        int count = 0;
        int testCases = 0;
        ArrayList<ArrayList<Point>> allCases = new ArrayList<>();
        ArrayList<Point> points = new ArrayList<>();
        String[] dataArr = null;

        int n = 0;
        int k = 0;
        int w = 0;

        try (LineNumberReader lnr = new LineNumberReader(new FileReader(f));) {
            while (true) {
                String dataStr = lnr.readLine();
                if (dataStr == null) break;
                if (count == 0) {
                    int data = Integer.parseInt(dataStr);
                    testCases = data;
                    System.out.println("testCases: " + testCases);
                } else if (count > 0) {
                    dataArr = dataStr.split(" ");
                    if (dataArr.length == 3) {
                        if (!points.isEmpty()) {
                            allCases.add(points);
                            points.clear();
                        }
                        n = Integer.parseInt(dataArr[0]);
                        k = Integer.parseInt(dataArr[1]);
                        w = Integer.parseInt(dataArr[2]);
                    } else {
                        Point p = new Point(Integer.parseInt(dataArr[0]), Integer.parseInt(dataArr[1]));
                        points.add(p);
                    }
                }
                count++;
            }
            if (!points.isEmpty()) allCases.add(points);
            BabylonianWall bw = new BabylonianWall();
            bw.processAllData(allCases);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
