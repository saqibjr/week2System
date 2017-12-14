import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        String input = Input.INPUT;
        ArrayList<Point2D.Double> room = new ArrayList<>();
        ArrayList<ArrayList<Point2D.Double>> objectsArray = new ArrayList<ArrayList<Point2D.Double>>();
        Methods helper = new Methods();
        helper.parse(room, objectsArray, input);
        System.out.print(objectsArray);
        ArrayList<ArrayList<Point2D.Double>> results = helper.ifHaveRoom(room,objectsArray);
        String finalResults = "";
        for (ArrayList<Point2D.Double> temp : results) {

            for (int i = 0; i < temp.size();i++) {
                if (i==temp.size()-1) {finalResults = finalResults + "("+ temp.get(i).getX()+", " + temp.get(i).getY()+")";}
                else {finalResults = finalResults + "("+ temp.get(i).getX()+", " + temp.get(i).getY()+"), ";}
            }
            finalResults = finalResults + "; ";
        }
        //System.out.print(finalResults);
    }
}

/**
 ArrayList<Point2D.Double> temp1 = new ArrayList<Point2D.Double>();
 temp1.add(new Point2D.Double(0.0,0.0));
 temp1.add(new Point2D.Double(1.0,1.0));
 temp1.add(new Point2D.Double(2.0,2.0));
 System.out.println(temp1);
 ArrayList<Point2D.Double> newTransition = helper.trasition(temp1,1,1);
 System.out.println(newTransition);

 roomPolygon.contains(transitionValue)

 **/