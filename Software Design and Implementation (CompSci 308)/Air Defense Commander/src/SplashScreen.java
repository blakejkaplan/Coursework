import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;

public class SplashScreen {
	
    private static final int FRAMES_PER_SECOND = 30;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	
	private Scene splash;
	private Label title;
	private Button protection;
	private Button survival;
	private Timeline myAnimation;
	private Stage myStage;
	
	public Scene init(Stage stage, int width, int height) {
		myStage = stage;
		splash = new Scene(buildUI(), width, height);
		splash.setFill(Color.GREY);
		return splash;	
	}
	
	public Group buildUI(){
		Group root = new Group();
		title = new Label("Air Defense Commander");
		title.setFont(Font.font("Roboto", FontWeight.BOLD, 70));
		protection = makeButton("Protection");
		survival = makeButton("Survival");
		HBox myHBox = new HBox();
		myHBox.getChildren().addAll(protection, survival);
		myHBox.setLayoutX(350);
		myHBox.setSpacing(100);
		myHBox.setLayoutY(400);
		title.setLayoutX(80);
		title.setLayoutY(200);
		title.textFillProperty().set(Color.GREENYELLOW);
		root.getChildren().addAll(title, myHBox);
		return root;
	}

	public Button makeButton(String buttonText) {
		Button newButton = new Button(buttonText);
		newButton.setOnMouseClicked(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent event){
				ChooseScene(newButton.getText());
			}
		});
		return newButton;
	}
	
	public void ChooseScene(String sceneName) {
		Scene newScene;
		if (sceneName.equals("Protection")){
			Protection protection = new Protection();
			initialize(protection);
			newScene = protection.init(myStage, Main.HORIZONTAL_SIZE, Main.VERTICAL_SIZE, myAnimation);
			myAnimation.play();
			protection.gameStart();
			myStage.setScene(newScene);
		}
		else{
			Survival survival = new Survival();
			initialize(survival);
			newScene = survival.init(myStage, Main.HORIZONTAL_SIZE, Main.VERTICAL_SIZE, myAnimation);
			myAnimation.play();
			survival.gameStart();
			myStage.setScene(newScene);

		}
	}
	
	public void initialize(GameMode mode) {
		KeyFrame step = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> mode.step(SECOND_DELAY));
		myAnimation = new Timeline();
		myAnimation.setCycleCount(FRAMES_PER_SECOND * 30);
		myAnimation.getKeyFrames().add(step);
	}
	
}
