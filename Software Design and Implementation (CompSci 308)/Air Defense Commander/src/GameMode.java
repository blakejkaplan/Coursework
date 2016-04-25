//This entire file is part of my masterpiece
//Blake Kaplan (bjk20)

/*
 * I think that this interface contributes toward good project design. Both of my game modes implement this interface,
 * which means that they both share common features. This also allowed me to create more generalized methods that accepted 
 * any class that implemented this interface. An example of such a method is the initialize method in the SplashScreen class.
 * I think that following this kind of model demonstrates good design.
 */

import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public interface GameMode {

	public Scene init(Stage stage, int width, int height, Timeline animation);
	
	public void gameStart();
	
	public Group buildUI();
	
	public void step(double num);
	
	public void checkMovingPieces();
	
}
