package MotionLeapListener;
        
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.GestureList;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.SwipeGesture;


/**
 *
 * @author steph
 */
public class SampleListener extends Listener {
    
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
                    SwipeGesture swipe = new SwipeGesture(gesture);
                    System.out.println("Swipe ID: " + swipe.id()
                    + ", State: " + swipe.state()
                    //+ ", Position: " + swipe.position()
                    + ", Direction: " + swipe.direction()
                    + " Speed: " + swipe.speed());
                    break;
            }
        }
        
    }
}
