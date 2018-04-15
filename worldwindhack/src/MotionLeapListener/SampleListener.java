package MotionLeapListener;
        
import com.leapmotion.leap.CircleGesture;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.Gesture.State;
import static com.leapmotion.leap.Gesture.State.STATE_START;
import static com.leapmotion.leap.Gesture.State.STATE_STOP;
import com.leapmotion.leap.GestureList;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.SwipeGesture;
import gov.nasa.worldwind.layers.ViewControlsSelectListener;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

enum Direction
{
    UP, DOWN, LEFT, RIGHT, NONE
}
  
public class SampleListener extends Listener {
    
    fullGestureDetails[] gesturesArray = new fullGestureDetails[20]; 
    

    public void onInit(Controller controller) {
        for (int i = 0 ; i < gesturesArray.length; i++)
            gesturesArray[i] = new fullGestureDetails();
        System.out.println("Initialised!");
    }
    public void onConnect(Controller controller) {
        System.out.println("Connected to motion sensor");
        controller.enableGesture(Gesture.Type.TYPE_SWIPE);
        controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
        controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
        controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);  
    }
    
    public void onDisconnect(Controller controller) {
        System.out.println("Motion sensor disconnected");
    }
    
    public void onExit(Controller controller) {
        System.out.println("Exited");
    }
    
    public Direction getSwipeDirection(SwipeGesture swipe) {
        return Direction.NONE;
    }
    
    public void onFrame(Controller controller) {
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
        for (int i = 0; i < gestures.count(); i++) {
            Gesture gesture = gestures.get(i);
            
            switch (gesture.type()) { 
                case TYPE_CIRCLE:
                    System.out.println("Found a circle!");
                    CircleGesture circleGesture = new CircleGesture(gesture);
                    String clockwise = "";
                    if(circleGesture.pointable().direction().angleTo(circleGesture.normal()) <= Math.PI/4) {
                        clockwise = "clockwise";
                    }
                    else {
                        clockwise = "anticlockwise";
                    }
                    double sweptAngle = 0;
                    if(circleGesture.state() != State.STATE_START) {
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
                    break;
                case TYPE_SWIPE:
                    //System.out.println("found a type_swipe!");
                    SwipeGesture swipe = new SwipeGesture(gesture);
                    /*
                    System.out.println("Swipe ID: " + swipe.id()    
                    + ", State: " + swipe.state()
                    //+ ", Position: " + swipe.position()
                    + ", Direction: " + swipe.direction()
                            
                    + " Speed: " + swipe.speed());
                    */
                    if (swipe.state() == STATE_START || swipe.state() == STATE_STOP)
                        System.out.println("String value: " + String.valueOf(swipe.state()));
                   // System.out.println("Gesture_1 start: " + gesturesArray[1].start + ", stop: " +  gesturesArray[1].stop );
                   // switch ((String.valueOf(swipe.state()).trim())) {
                    switch (swipe.state()) {
                        case STATE_START:
                            int id = swipe.id();
                            
                            //System.out.println("Triggered start state");
                            /*
                            System.out.println("got a start state at " 
                                    + String.valueOf(id) + ", value: " 
                                    + String.valueOf(swipe.position()) 
                                    + " speed : " + swipe.speed());
                            */
                            gesturesArray[id].start = swipe.position();
                            break;
                        case STATE_STOP:
                            
                            if (swipe.speed() > 200)
                            {
                                /*
                                System.out.println("got a stop state at " 
                                    + String.valueOf(swipe.id())
                                    + " speed : " + swipe.speed());
*/
                                gesturesArray[swipe.id()].stop = swipe.position();
                                gesturesArray[swipe.id()].speed = swipe.speed();
                            }
                            // Check if we now have a valid start and stop value for this ID
                            if((gesturesArray[swipe.id()].stop.getX() != 0.0) &&
                               (gesturesArray[swipe.id()].start.getX() != 0.0)) {
                                // If it's valid we will return a direction
                                    //System.out.println("Calling returnDirection!");
                                    Direction thingy = gesturesArray[swipe.id()].returnDirectionAndSpeed();
                                    //System.out.println("Result: " + thingy);
                                    //moveMouse(thingy);
                            }
                            break;
                    }
                    break;
            }
        }
    }
    
    public void moveMouse(Direction direction)
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
                    robot.mouseMove(screenSize.width - 50, screenSize.height / 2);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    break;
                case RIGHT:
                    robot.mouseMove(screenSize.width - 50, screenSize.height / 2);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseMove(screenSize.width / 2, screenSize.height / 2);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
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
