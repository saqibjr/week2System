import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Methods {
    // parse is done. It is working.

     void parse(ArrayList<Point2D.Double> room, ArrayList<ArrayList<Point2D.Double>> objectsArray, String str) {
     // 1: (0, 0), (10, 0), (10, 10), (0, 10) # 9:(0, 0), (3, 0), (3, 3), (0, 3);4:(0, 0), (2, 0), (2, 2), (0, 2)
        String[] firstSplit = str.split("#");
        // firstSplit[0] = room, firstSplit [1] = objectsArray
        String[] secondSplit = firstSplit[1].split(";");
        // now secondSplit contains all the individual objects
        ArrayList<String> storing = new ArrayList<String>();
         for ( String tempStr : secondSplit) {
             tempStr = tempStr.replaceAll("[0-9]+:","");
             storing.add(tempStr);
         }

       for (String temp : storing) {
            //(0, 0), (3, 0), (3, 3), (0, 3)
            ArrayList<Point2D.Double> tempArray = new ArrayList<Point2D.Double>();
            tempArray = getPointArray(temp);
            objectsArray.add(tempArray);
        }

        firstSplit[0] = firstSplit[0].replaceAll("[0-9]+:","");

        room.addAll(getPointArray(firstSplit[0]));

        return;
    }

     ArrayList<Point2D.Double> getPointArray(String str) {
        // turning (0, 0), (3, 0), (3, 3), (0, 3) into ArrayList<Point2D.Double>
        ArrayList<Point2D.Double> tempArray = new ArrayList<Point2D.Double>();
        char[] array = str.toCharArray();
        String temp = "";
        Double x = -1.0;
        Double y = -1.0;
        for ( int i = 0; i < array.length; i++) {
            //int length = 0;
            //if (length == array.length) {return tempArray;}
            if (x != -1.0 && y != -1.0) {
                Point2D.Double tempPoint = new Point2D.Double(x, y);
                tempArray.add(tempPoint);
                x = -1.0;
                y = -1.0;
                temp = "";
            }
            if(array[i]=='-') { temp = temp + array[i];}
            if (Character.isDigit(array[i]) && Character.isDigit(array[i + 1])) {
                temp = temp + array[i];
            } else if(Character.isDigit(array[i]) && array[i + 1]== '.' ){
                temp = temp + array[i] + '.';
            }
            else if (Character.isDigit(array[i]) && ((array[i + 1])== ')'|| array[i + 1]== ',') ) {
                temp = temp + array[i];
                if (x == -1.0) {
                    x = Double.parseDouble(temp);
                    temp = "";
                } else {
                    y = Double.parseDouble(temp);
                    temp = "";
                }
            }

        }
     return tempArray;
     }



    ArrayList<ArrayList<Point2D.Double>> ifHaveRoom(ArrayList<Point2D.Double> room, ArrayList<ArrayList<Point2D.Double>> newElement) {
        ArrayList<ArrayList<Point2D.Double>> tempNewElement = newElement;
        ArrayList<ArrayList<Point2D.Double>> results = new ArrayList<ArrayList<Point2D.Double>>();
        Path2D.Double roomPolygon = buildPolygon(room);
        Rectangle2D Outerbound = roomPolygon.getBounds2D();
        int xLow = Outerbound.OUT_LEFT;
        int xHigh = Outerbound.OUT_RIGHT;
        int yLow = Outerbound.OUT_BOTTOM;
        int yHigh = Outerbound.OUT_TOP;
        ArrayList<Point2D.Double> guesses = new ArrayList<Point2D.Double>();
        int i = 0;

        while (i < 10) {
            // 1000 points inside the room
            Random r = new Random();
            double xVlaue = xLow + (xHigh - xLow) * r.nextDouble();
            double yValue = yLow + (yHigh - yLow) * r.nextDouble();
            Point2D.Double temp = new Point2D.Double(xVlaue,yValue);
            if(roomPolygon.contains(temp) && !guesses.contains(temp)) {
                guesses.add(temp);
                i++;
        }}

        for (ArrayList<Point2D.Double> y : tempNewElement) {
            // for each piece of furniture
            for (Point2D.Double temp : guesses) {
                // for each guess
                ArrayList<Point2D.Double> newTransition = trasition(y,temp.getX(),temp.getY());
                // we will make a new transition based on the guess -- moving this piece of furniture from 0,0 to guess
                boolean q = true;
                outer:
                for (Point2D.Double transitionValue: newTransition) {
                    // for each vertices in the new transition values
                    if (roomPolygon.contains(transitionValue)) {
                        // if it is in the room
                        for (ArrayList<Point2D.Double> result : results) {
                            // and if overlaps with other furniture
                            if(buildPolygon(result).contains(transitionValue)) {
                                q = false;
                                break outer;
                                // then we know it is not valid and we know this guessing point is not going to work
                                // so we move on to another guessing point
                            }
                        }
                    }
                }
                if (q==true) {
                    results.add(newTransition);
                }
            }
        }
        return results;
    }

    ArrayList<Point2D.Double> trasition (ArrayList<Point2D.Double> previous, double x, double y) {
         int length = previous.size();
        ArrayList<Point2D.Double> newResult = new ArrayList<Point2D.Double>();
         for (int i = 0; i < length ; i++) {
             Point2D.Double temp = new Point2D.Double(previous.get(i).getX() + x, previous.get(i).getY() + y );
             newResult.add(temp);
         }
        return newResult;
    }

    Path2D.Double buildPolygon (ArrayList<Point2D.Double> room) {
        Path2D.Double path = new Path2D.Double();
        boolean isFirst = true;
        double firstTimeX = 0.0;
        double firstTimeY = 0.0;
        for (Point2D.Double i : room) {
             if (isFirst) {
                 path.moveTo(i.getX(), i.getY());
                 firstTimeX = i.getX();
                 firstTimeY = i.getY();
                 isFirst = false;
             } else {
                 path.lineTo(i.getX(), i.getY());
             }
         }
         //path.lineTo(firstTimeX,firstTimeY);
         path.closePath();
         return path;
    }


  /*  ArrayList<Point2D.Double> findBiggest(ArrayList<ArrayList<Point2D.Double>> objectsArray) {
        ArrayList<Point2D.Double> result;

        result = objectsArray.get(0);
        for (int i = 1; i < objectsArray.size() - 1; i++) {
            if (findArea(result) > findArea(objectsArray.get(i))) {
                result = objectsArray.get(i);
            }
        }
        return result;
} */
  /* float findArea(ArrayList<Point2D.Double> points) {

        ArrayList<Point2D.Double> tempArray = points;
        tempArray.add(points.get(0));

        float sum = 0;
        for (int i = 0; i < tempArray.size() - 1; ++i) {
            sum += (tempArray.get(i).getX() * tempArray.get(i + 1).getY()) - (tempArray.get(i + 1).getX() * tempArray.get(i).getY());
        }
        return Math.abs(sum / 2);}
   */
}






