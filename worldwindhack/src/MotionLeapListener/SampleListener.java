package MotionLeapListener;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import static com.leapmotion.leap.Gesture.State.STATE_START;
import static com.leapmotion.leap.Gesture.State.STATE_STOP;
import com.leapmotion.leap.GestureList;
import com.leapmotion.leap.KeyTapGesture;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.SwipeGesture;
import com.leapmotion.leap.Vector;
import gov.nasa.worldwind.layers.ViewControlsSelectListener;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

enum Direction
{
    UP, DOWN, LEFT, RIGHT, ZOOM_IN, ZOOM_OUT, NONE
}

public class SampleListener extends Listener
{

    HashMap<Integer, gestureCircle[]> circles = new HashMap<>();
    gestureSwipe[] gestureSwipes = new gestureSwipe[10];
    int currentCircleIndex = 0;
    int currentSwipeIndex = 0;

    public void onInit(Controller controller)
    {
        for (int i = 0; i < gestureSwipes.length; i++)
        {
            gestureSwipes[i] = new gestureSwipe();
        }
        
        System.out.println("Initialised!");
    }

    public void onConnect(Controller controller)
    {
        System.out.println("Connected to motion sensor");
        controller.enableGesture(Gesture.Type.TYPE_SWIPE);
        //controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
        controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
        controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
    }

    public void onDisconnect(Controller controller)
    {
        System.out.println("Motion sensor disconnected");
    }

    public void onExit(Controller controller)
    {
        System.out.println("Exited");
    }

    public Direction getSwipeDirection(SwipeGesture swipe)
    {
        return Direction.NONE;
    }

    public void onFrame(Controller controller)
    {
        //System.out.println("Frame available");
        Frame frame = controller.frame();
        /*
        System.out.println("Frame id: " + frame.id()
                + ", Timestamp: " + frame.timestamp() 
                + ", Fingers: " + frame.fingers().count()
                + ", Hands: " + frame.hands().count()
                + ", Gestures: " + frame.gestures().count());
         */

        GestureList gestures = frame.gestures();
        for (int i = 0; i < gestures.count(); i++)
        {
            Gesture gesture = gestures.get(i);
            System.out.println("Gesture get " + gesture.type());
            switch (gesture.type())
            {

                case TYPE_SWIPE:
                    SwipeGesture swipe = new SwipeGesture(gesture);
                    /*
                    System.out.println("Swipe ID: " + swipe.id()    
                    + ", State: " + swipe.state()
                    //+ ", Position: " + swipe.position()
                    + ", Direction: " + swipe.direction()
                            
                    + " Speed: " + swipe.speed());
                     */
                    if (swipe.state() == STATE_START)

                    // System.out.println("Gesture_1 start: " + gesturesArray[1].start + ", stop: " +  gesturesArray[1].stop );
                    // switch ((String.valueOf(swipe.state()).trim())) {
                    {
                        
                        if (swipe.speed() > 200)
                        {
                            gestureSwipe gestureSwipe = new gestureSwipe();
                            gestureSwipe.start = swipe.startPosition();
                            gestureSwipe.stop = swipe.position();
                            gestureSwipe.id = swipe.id();

                            moveMouse(gestureSwipe.returnDirectionAndSpeed(), 0);
                        }
                        

                        /*
                        switch (swipe.state())
                        {
                            case STATE_START:
                                int id = swipe.id();

                                gestureSwipes[currentSwipeIndex].start = swipe.position();
                                break;
                            case STATE_STOP:
                                int stop_id = swipe.id();
                                for (int y = 0; y < gestureSwipes.length; y++)
                                {
                                    if (stop_id == gestureSwipes[y].id)
                                    {
                                        currentSwipeIndex = y;
                                        break;
                                    }
                                }
                                if (swipe.speed() > 0)
                                {
                                   
                                    gestureSwipes[currentSwipeIndex].stop = swipe.position();
                                    gestureSwipes[currentSwipeIndex].speed = swipe.speed();
                                }
                                // Check if we now have a valid start and stop value for this ID
                                if ((gestureSwipes[currentSwipeIndex].stop.getX() != 0.0)
                                        && (gestureSwipes[currentSwipeIndex].start.getX() != 0.0))
                                {
                                    // If it's valid we will return a direction
                                    //System.out.println("Calling returnDirection!");
                                    Direction thingy = gestureSwipes[currentSwipeIndex].returnDirectionAndSpeed();
                                    //System.out.println("Result: " + thingy);
                                    // Incredment index by 1: if we're at the end of the array, start again at 0. 

                                    moveMouse(thingy, 0);
                                }
                                if (currentSwipeIndex < 10)
                                {
                                    currentSwipeIndex++;
                                }
                                else
                                {
                                    currentSwipeIndex = 0;
                                }
                                break;*/
                        }
                    
                    break;
                /*case TYPE_CIRCLE:
                    //System.out.println("Found a circle!");
                    CircleGesture circleGesture = new CircleGesture(gesture);
                    String clockwise = "";
                    if (circleGesture.pointable().direction().angleTo(circleGesture.normal()) <= Math.PI / 4)
                    {
                        clockwise = "clockwise";
                    }
                    else
                    {
                        clockwise = "anticlockwise";
                    }
                    double sweptAngle = 0;
                    if (circleGesture.state() != State.STATE_START)
                    {
                        // If the circle is just starting, don't record an angle yet
                        CircleGesture previous = new CircleGesture(controller.frame(1).gesture(circleGesture.id()));
                        // How far between the previous circle point and this point?
                        sweptAngle = (circleGesture.progress() - previous.progress()) * 2 * Math.PI;
                    }
                    System.out.println("Circle id: " + String.valueOf(circleGesture.id())
                            + ", State: " + circleGesture.state()
                            + ", Progress: " + circleGesture.progress()
                            + ", Radius: " + circleGesture.radius()
                            + ", SweptAngle: " + Math.toDegrees(sweptAngle)
                            + ", Direction: " + clockwise);
                    switch (circleGesture.state())
                    {
                        case STATE_START:
                            int id = circleGesture.id();
                            if (!circles.containsKey(id))
                            {
                                circles.put(id, new gestureCircle[2]);
                            }
                            gestureCircle circle = new gestureCircle();
                            circle.id = id;
                            circle.radius = circleGesture.radius();
                            circles.get(id)[0] = circle;
                        case STATE_STOP:
                            int stop_id = circleGesture.id();
                            if (circles.containsKey(stop_id))
                            {
                                gestureCircle stop_circle = new gestureCircle();
                                stop_circle.id = stop_id;
                                stop_circle.radius = circleGesture.radius();
                                if (clockwise.equals("clockwise"))
                                {
                                    stop_circle.direction = Direction.ZOOM_IN;
                                }
                                else
                                {
                                    stop_circle.direction = Direction.ZOOM_OUT;
                                }
                                circles.get(stop_id)[1] = stop_circle;

                                if (circles.get(stop_id)[0].id == stop_id)
                                {
                                    System.out.println("Zoom in progress");
                                    moveMouse(stop_circle.direction, stop_circle.radius);
                                }
                            }
                            break;
                    }
                    break;*/
                    case TYPE_KEY_TAP:
                        KeyTapGesture keytapGesture = new KeyTapGesture(gesture);
                        if (keytapGesture.direction() != new Vector())
                            moveMouse(Direction.ZOOM_OUT, 100);
                        break;
                    case TYPE_SCREEN_TAP:
                        moveMouse(Direction.ZOOM_IN, 100);
                        break;
            }
        }
    }

    public int getMousePower(double power)
    {
        if (power < 30)
        {
            return 1;
        }
        else if (power < 60)
        {
            return 2;
        }
        else if (power < 90)
        {
            return 3;
        }
        else if (power < 120)
        {
            return 4;
        }
        else
        {
            return 5;
        }
    }

    public void moveMouse(Direction direction, double power)
    {
        Robot robot = null;
        try
        {
            robot = new Robot();
            Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
            Point mousePoint = MouseInfo.getPointerInfo().getLocation();
            switch (direction)
            {
                case UP:
                    robot.mouseMove(screenSize.width / 2, 50);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseMove(screenSize.width / 2, screenSize.height / 2);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    break;
                case DOWN:
                    robot.mouseMove(screenSize.width / 2, screenSize.height / 2);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseMove(screenSize.width / 2, 50);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    break;
                case LEFT:
                    robot.mouseMove(screenSize.width / 2, screenSize.height / 2);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseMove(screenSize.width /4, screenSize.height / 2);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    break;
                case RIGHT:
                    robot.mouseMove(screenSize.width /4, screenSize.height / 2);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseMove(screenSize.width / 2, screenSize.height / 2);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    break;
                case ZOOM_OUT:
                    robot.mouseMove(screenSize.width / 2, screenSize.height / 2);
                    // power corresponds to mousewheel value
                    robot.mouseWheel(getMousePower(power));
                    break;
                case ZOOM_IN:
                    robot.mouseMove(screenSize.width / 2, screenSize.height / 2);
                    robot.mouseWheel(getMousePower(power) * -1);
                    break;
            }
            robot.mouseMove(mousePoint.x, mousePoint.y);
        }
        catch (AWTException ex)
        {
            Logger.getLogger(ViewControlsSelectListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
