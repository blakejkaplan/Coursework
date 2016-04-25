import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class EndConditions {

	private Group root;
	private Stage myStage;
	private boolean myDidWin;
	
	public Scene init(boolean didWin, Stage stage, int score) {
		
		myDidWin = didWin;
		myStage = stage;
		root = new Group();
		Label text;
		Scene winScene = new Scene(root, Main.HORIZONTAL_SIZE, Main.VERTICAL_SIZE);
		if (myDidWin) text = new Label("You Win!\nYou Survived the Invasion!\nFinal Score: " + score + "\nPress Escape to Return to Menu");
		else{
			text = new Label("You Lose!\nThe Enemies Have Taken Over!\nFinal Score: " + score + "\nPress Escape to Return to Menu");
		}
		text.setFont(Font.font("Roboto", 50));
		text.setLayoutX(myStage.getWidth()/2-400);
		text.setLayoutY(myStage.getHeight()/2 - 100);
		text.setTextAlignment(TextAlignment.CENTER);
		winScene.setFill(Color.YELLOW);
		root.getChildren().add(text);
		winScene.setOnKeyPressed(event -> returnToMenu(event.getCode()));
		return winScene;
	}
	
	public void returnToMenu(KeyCode code) {
		switch(code){
			case ESCAPE:
				SplashScreen splash = new SplashScreen();
				myStage.setScene(splash.init(myStage, Main.HORIZONTAL_SIZE, Main.VERTICAL_SIZE));
		default:
			break;
		}
	}
}
