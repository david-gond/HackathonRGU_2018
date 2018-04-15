/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MotionLeapListener;

//import java.util.Vector;
import com.leapmotion.leap.Vector;
/**
 *
 * @author gondd
 */
public class fullGestureDetails {
    Vector start = new Vector();
    Vector stop = new Vector();
    //int id;
    double speed;
    /*
    public fullGestureDetails(double start[], double stop[], double speed)
    {
        this.stop = stop;
        this.speed = speed;
    }
    */
    public String returnDirectionAndSpeed() {
    
        // Which direction are we going? Let's compare x and y.
        double startX = start.getX();
        double endX = stop.getX();
        double startY = start.getY();
        double endY = stop.getY();
        double differenceX = startX - endX;
        double differenceY = startY - endY;
        // Reset gesture values to 0
        for(int i = 0; i < 3; i++) {
            start.setX(0);
            start.setY(0);
            stop.setX(0);
            stop.setY(0);
        }
        // First of all: are we moving in the X axis or the Y?
        if (Math.abs(differenceX) >= Math.abs(differenceY))
        {
            // We are moving in the X axis. Are we moving left or right?
            if (differenceX > 0)
            {
                return "left";
            }
            else
            {
                return "right";
            }
        }
        else
        {
            // We are moving in the Y axis. Are we moving left or right?
            if (differenceY > 0)
            {
                return "up";
            }
            else
            {
                return "down";
            }
        }
    }
}
