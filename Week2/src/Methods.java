import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Methods {
    // incomplete. Potentially many bugs and errors. But the idea should work

     void parse(ArrayList<Point2D.Float> room, ArrayList<ArrayList<Point2D.Float>> objectsArray, String str) {

        String[] firstSplit = str.split("#");
        // firstSplit[0] = room, firstSplit [1] = objectsArray
        String[] secondSplit = firstSplit[1].split(";");
        // now secondSplit contains all the individual objects
        for (String temp : secondSplit) {
            StringBuilder sb = new StringBuilder(temp);
            sb.deleteCharAt(0);
            sb.deleteCharAt(1);
            temp = sb.toString();
        }
        // now every string has no unit cost, easier to deal with

        for (String temp : secondSplit) {
            //(0, 0), (3, 0), (3, 3), (0, 3)
            ArrayList<Point2D.Float> tempArray = new ArrayList<Point2D.Float>();
            tempArray = getPointArray(temp);
            objectsArray.add(tempArray);
        }
        StringBuilder sb = new StringBuilder(firstSplit[0]);
        sb.deleteCharAt(0);
        sb.deleteCharAt(1);
        room = getPointArray(sb.toString());
        return;
    }

     ArrayList<Point2D.Float> getPointArray(String str) {
        // turning (0, 0), (3, 0), (3, 3), (0, 3) into ArrayList<Point2D.Float>
        ArrayList<Point2D.Float> tempArray = new ArrayList<Point2D.Float>();
        char[] array = str.toCharArray();
        String temp = "";
        for (char i : array) {
            float x = -1;
            float y = -1;
            if (x != -1 && y != -1) {
                Point2D.Float tempPoint = new Point2D.Float(x, y);
                tempArray.add(tempPoint);
                x = -1;
                y = -1;
            }
            if (Character.isDigit(array[i]) && Character.isDigit(array[i + 1])) {
                temp = temp + i;
            } else if (Character.isDigit(array[i]) && !Character.isDigit(array[i + 1])) {
                temp = temp + 1;

                if (x != -1) {
                    y = Float.parseFloat(temp);
                } else {
                    x = Float.parseFloat(temp);
                }
            }

        }
        return tempArray;
    }

    float findArea(ArrayList<Point2D.Float> points) {

        ArrayList<Point2D.Float> tempArray = points;
        tempArray.add(points.get(0));

        float sum = 0;
        for (int i = 0; i < tempArray.size() - 1; ++i) {
            sum += (tempArray.get(i).getX() * tempArray.get(i + 1).getY()) - (tempArray.get(i + 1).getX() * tempArray.get(i).getY());
        }
        return Math.abs(sum / 2);
    }

    ArrayList<Point2D.Float> findBiggest(ArrayList<ArrayList<Point2D.Float>> objectsArray) {
        ArrayList<Point2D.Float> result;

        result = objectsArray.get(0);
        for (int i = 1; i < objectsArray.size() - 1; i++) {
            if (findArea(result) > findArea(objectsArray.get(i))) {
                result = objectsArray.get(i);
            }
        }
        return result;
    }

    // Still trying to implement this method
    ArrayList<Point2D.Float> ifHaveRoom(ArrayList<Point2D.Float> room, ArrayList<ArrayList<Point2D.Float>> results,
                                        ArrayList<Point2D.Float> newElement) {

         // This is the hardest one. Still not done yet



    }


}






