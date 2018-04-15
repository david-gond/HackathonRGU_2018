package MotionLeapListener;
        
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.GestureList;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.SwipeGesture;


    
  
public class SampleListener extends Listener {
    
    fullGestureDetails[] gesturesArray = new fullGestureDetails[20]; 

    public void onInit(Controller controller) {
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
    
    public String getSwipeDirection(SwipeGesture swipe) {
        
        
        return "none";
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
                    System.out.println("Gesture_1 start: " + gesturesArray[1].start + ", stop: " +  gesturesArray[1].stop );
                    switch (swipe.state().toString()) {
                        case "STATE_START":
                            gesturesArray[swipe.id()].start = swipe.position();
                            break;
                        case "STATE_STOP":
                            gesturesArray[swipe.id()].stop = swipe.position();
                            gesturesArray[swipe.id()].speed = swipe.speed();
                            // Check if we now have a valid start and stop value for this ID
                            if((gesturesArray[swipe.id()].stop.getX() != 0.0) &&
                               (gesturesArray[swipe.id()].start.getX() != 0.0)) {
                                // If it's valid we will return a direction
                                    System.out.println("Calling returnDirection!");
                                    String thingy = gesturesArray[swipe.id()].returnDirectionAndSpeed();
                                    System.out.println("Result: " + thingy);
                            }
                            break;
                    }
                    
                        
                    
                    break;
            }
        }
        
    }
}
