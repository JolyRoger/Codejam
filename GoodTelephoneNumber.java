package org.monakhov.codejam;

import java.io.*;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by daniil.monakhov on 8/21/2015.
 */
public class GoodTelephoneNumber {

    enum Direction {
        Up, Down, Left, Right,
        UpRight, UpLeft, DownRight, DownLeft,
        None
    }

    TreeMap<Integer, String> numberSum = new TreeMap<>();

    private Direction adjacent(int a, int b) {
        switch (a) {
            case 0 :
                switch(b) {
                    case 8 : return Direction.Up;
                    case 7 : return Direction.UpLeft;
                    case 9 : return Direction.UpRight;
                    default: return Direction.None;
                }
            case 1 :
                switch(b) {
                    case 2 : return Direction.Right;
                    case 4 : return Direction.Down;
                    case 5 : return Direction.DownRight;
                    default: return Direction.None;
                }
            case 2 :
                switch(b) {
                    case 1 : return Direction.Left;
                    case 3 : return Direction.Right;
                    case 4 : return Direction.DownLeft;
                    case 5 : return Direction.Down;
                    case 6 : return Direction.DownRight;
                    default: return Direction.None;
                }
            case 3 :
                switch(b) {
                    case 2 : return Direction.Left;
                    case 5 : return Direction.DownLeft;
                    case 6 : return Direction.Down;
                    default: return Direction.None;
                }
            case 4 :
                switch(b) {
                    case 1 : return Direction.Up;
                    case 2 : return Direction.UpRight;
                    case 5 : return Direction.Right;
                    case 7 : return Direction.Down;
                    case 8 : return Direction.DownRight;
                    default: return Direction.None;
                }
            case 5 :
                switch(b) {
                    case 1 : return Direction.UpLeft;
                    case 2 : return Direction.Up;
                    case 3 : return Direction.UpRight;
                    case 4 : return Direction.Left;
                    case 6 : return Direction.Right;
                    case 7 : return Direction.DownLeft;
                    case 8 : return Direction.Down;
                    case 9 : return Direction.DownRight;
                    default: return Direction.None;
                }
            case 6 :
                switch(b) {
                    case 2 : return Direction.UpLeft;
                    case 3 : return Direction.Up;
                    case 5 : return Direction.Left;
                    case 8 : return Direction.DownLeft;
                    case 9 : return Direction.Down;
                    default: return Direction.None;
                }
            case 7 :
                switch(b) {
                    case 4 : return Direction.Up;
                    case 5 : return Direction.UpRight;
                    case 8 : return Direction.Right;
                    case 0 : return Direction.DownRight;
                    default: return Direction.None;
                }
            case 8 :
                switch(b) {
                    case 4 : return Direction.UpLeft;
                    case 5 : return Direction.Up;
                    case 6 : return Direction.UpRight;
                    case 7 : return Direction.Left;
                    case 9 : return Direction.Right;
                    case 0 : return Direction.Down;
                    default: return Direction.None;
                }
            case 9 :
                switch(b) {
                    case 5 : return Direction.UpLeft;
                    case 6 : return Direction.Up;
                    case 8 : return Direction.Left;
                    case 0 : return Direction.DownLeft;
                    default: return Direction.None;
                }
        }
        return Direction.None;
    }

    private int sum(String phoneNumber) {
        char[] chArr = phoneNumber.toCharArray();
        int s = 0;
        int prevDigit = -1;
        Direction prevDirection = Direction.None;
        Direction currentDirection = Direction.None;

        for (int i = 0; i < chArr.length; i++) {
            int currentDigit = Character.getNumericValue(chArr[i]);
            if (i == 0) {
                s = 0;
            }
            else if (i == 1) {
                if (prevDigit == currentDigit) {
                    s += 0;
                } else {
                    currentDirection = adjacent(prevDigit, currentDigit);
                    if (currentDirection != Direction.None) s+= 1;
                    else s += 2;
                }
            } else {
                currentDirection = adjacent(prevDigit, currentDigit);
                if (prevDigit == currentDigit) {
                    s += 0;
                } else if (currentDirection != Direction.None) {
                    if (prevDirection == currentDirection) {
                        s += 1;
                    } else s += 2;
                } else s += 3;
            }
            prevDigit = currentDigit;
            prevDirection = currentDirection;
        }
        return s;
    }

    public void process(ArrayList<String[]> testCasesArr) {
        for (String[] strings : testCasesArr) {
            for (String phoneNumber : strings) {
                numberSum.putIfAbsent(sum(phoneNumber), phoneNumber);
            }
            System.out.println(numberSum.firstEntry().getValue());
            numberSum.clear();
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("No input file");
            System.exit(1);
        }

        File f  = new File(args[0]);
        if (!f.exists()) {
            System.err.println("No input file");
            System.exit(1);
        }
        int count = 0;
        int testCases = 0;
        int testDataLength = 0;
        boolean newData = true;
        ArrayList<String[]> testCasesArr = new ArrayList<>();
        String[] dataArr = null;
        int arrIndex = 0;

        try(LineNumberReader lnr = new LineNumberReader(new FileReader(f));) {
            while(true) {
                String dataStr = lnr.readLine();
                if (dataStr == null) break;
                if (count == 0) {
                    int data = Integer.parseInt(dataStr);
                    testCases = data;
                    System.out.println("testCases: " + testCases);
                } else if (count > 0) {
                    if (newData) {
                        int data = Integer.parseInt(dataStr);
                        testDataLength = data;
                        dataArr = new String[testDataLength];
                        newData = false;
                        arrIndex = 0;
                    } else {
                        dataArr[arrIndex++] = dataStr;
                        if (arrIndex >= dataArr.length) {
                            testCasesArr.add(dataArr);
                            newData = true;
                        }
                    }
                }
                count++;
            }
            GoodTelephoneNumber gtn = new GoodTelephoneNumber();
            gtn.process(testCasesArr);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
