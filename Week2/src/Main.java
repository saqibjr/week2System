import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        String input = "";
        ArrayList<Point2D.Float> room = null;
        ArrayList<ArrayList<Point2D.Float>> objectsArray = null;
        float current = 0;
        float aim;
        ArrayList<ArrayList<Point>> results;
        Methods helper = new Methods();
        helper.parse(room, objectsArray, input);
        aim = helper.findArea(room)/3 ;
        while (current < aim) {
            ArrayList<Point2D.Float> temp = helper.findBiggest(objectsArray);
            //Point result = ifHaveRoom(element)
            // if result != .... error value, add result to results
            // if result == error value, get rid of result in the Objectarray
            // current = area(result) + current;
        }
        // Find a way to print all the information from results

    }
}