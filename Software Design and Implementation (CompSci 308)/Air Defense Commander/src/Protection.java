import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javafx.scene.paint.Color;

public class Protection implements GameMode {

	private static final int SHOT_VELOCITY = 25;
	private static final int SHOT_START_Y = 752;
	private static final int SHOT_START_X = 495;
	private static final int GUN_HEIGHT = 35;
	private static final int GUN_WIDTH = 7;
	private static final int GUN_VERTICAL_OFFSET = 30;
	private static final double GUN_HORIZONTAL_OFFSET = 3.5;
	private static final int CANON_VERTICAL_OFFSET = 76;
	private static final int CANON_Y = 755;
	private static final int CANON_X = 500;
	private static final double TROOP_ACCELERATION = .2;
	private static final int TROOP_VELOCITY = 2;
	private static final int MAX_VIABLE_WIDTH = 950;
	private static final int GAME_RUNTIME = 30;
	private Scene gameScene;
	private Rectangle gun;
	private double lastAngle;
	private int numSteps;
	private int mod;
	private List<Paratrooper> troops;
	private Group myRoot;
	private Group childrenTroops;
	private Group childrenShots;
	private List<Shot> shots;
	private int numOnGround;
	private int score;
	private Label gameText;
	private boolean invincible;
	private boolean printAngle;
	private Stage myStage;
	private Timeline myAnimation;
	
	public Scene init(Stage stage, int width, int height, Timeline animation) {
		myStage = stage;
		gameScene = new Scene(buildUI(), width, height);
		gameScene.setRoot(buildUI());
		gameScene.setFill(Color.SKYBLUE);
		myAnimation = animation;
		return gameScene;
	}

	public void gameStart() {
		numSteps = 0;
		lastAngle = 0;
		mod = 70;
		score = 0;
		gameText = new Label("");
		gameText.setFont(Font.font("Roboto", 16));
		troops = new ArrayList<Paratrooper>();
		shots = new ArrayList<Shot>();
		childrenTroops = new Group();
		childrenShots = new Group();
		myRoot.getChildren().addAll(childrenTroops, childrenShots, gameText);
		setupEvents();
	}

	public Group buildUI() {
		myRoot = new Group();
		ImageView myImageView = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("Cannon.png")));
		myImageView.setTranslateX(myStage.getWidth() / 2 - myImageView.getImage().getWidth() / 2);
		myImageView.setTranslateY(myStage.getHeight() - CANON_VERTICAL_OFFSET);
		gun = new Rectangle(myStage.getWidth() / 2 - GUN_HORIZONTAL_OFFSET,
				myStage.getHeight() - myImageView.getImage().getHeight() - GUN_VERTICAL_OFFSET, GUN_WIDTH, GUN_HEIGHT);
		gun.getTransforms().add(new Rotate(90, 500, 755));
		myRoot.getChildren().addAll(myImageView, gun);
		return myRoot;
	}

	public void setupEvents() {
		gameScene.setOnMouseMoved(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				double newAngle;
				if (event.getX() > CANON_X) {
					newAngle = Math.atan((CANON_Y - event.getY()) / (event.getX() - CANON_X)) * (180 / Math.PI);
				} else if (event.getX() < CANON_X) {
					newAngle = 90 + Math.atan((CANON_X - event.getX()) / (CANON_Y - event.getY())) * (180 / Math.PI);

				} else {
					newAngle = 90;
				}
				gun.getTransforms().add(new Rotate(lastAngle - newAngle, CANON_X, CANON_Y));
				lastAngle = newAngle;
			}
		});
		gameScene.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				shots.add(new Shot(SHOT_START_X, SHOT_START_Y, lastAngle, SHOT_VELOCITY, false));
				score -= 10;
			}
		});
		gameScene.setOnKeyPressed(keyEvent -> cheatCode(keyEvent.getCode()));
	}
	
	public void cheatCode(KeyCode keyCode){
		switch(keyCode){
			case I:
				if (invincible){
					invincible = false;
					System.out.println("Invincibility deactivated");
					break;
				}
				else{
					System.out.println("Invincibility activated");
					invincible = true;	
					break;
				}
			case S:
				if (printAngle){
					printAngle = false;
					break;
				}
				else{
					printAngle = true;
					break;
				}
			case L:
				numOnGround = 4;
			default:
				break;
		}
	}

	public void step(double num) {
		childrenTroops.getChildren().clear();
		childrenShots.getChildren().clear();
		Random rand = new Random();
		if (numSteps % mod == 0) {
			for (int i = 0; i < rand.nextInt(2) + 1; i++) {
				troops.add(new Paratrooper(rand.nextDouble() * MAX_VIABLE_WIDTH, 0, TROOP_VELOCITY, TROOP_ACCELERATION));
			}
		}
		advanceTroops();
		advanceShots();
		checkMovingPieces();
		checkGameStatus();
		if (printAngle) printAngle();
		numSteps++;
		gameText.setText("SCORE: "+ score + "\nTIME REMAINING: " + (30 - (numSteps/ 30)) + "\nLIVES REMAINING: " + (4 - numOnGround));
	}

	public void advanceTroops() {
		for (Paratrooper troop : troops) {
			troop.updatedVelocity();
			troop.updatePosition();
			childrenTroops.getChildren().add(troop);
		}
	}

	public void advanceShots() {
		for (Shot myShot : shots) {
			myShot.updatePos();
			childrenShots.getChildren().add(myShot);
		}
	}

	public void checkMovingPieces() {
		Iterator<Shot> shotIter = shots.iterator();
		while (shotIter.hasNext()) {
			Shot myShot = shotIter.next();
			if (myShot.getX() > myStage.getWidth() || myShot.getY() > myStage.getHeight() || myShot.getX() < 0) {
				shotIter.remove();
			}
			Iterator<Paratrooper> troopIter = troops.iterator();
			while (troopIter.hasNext()) {
				Paratrooper troop = troopIter.next();
				if (myShot.intersects(troop.getBoundsInLocal()) && !troop.isGrounded()) {
					troopIter.remove();
					if (shots.contains(myShot)) shotIter.remove();
					score += 110;
				}
			}
			
		}
	}

	public void checkGameStatus() {
		for (Paratrooper troop : troops) {
			if (troop.isGrounded() && !troop.isCounted()) {
				troop.count();
				if (!invincible) numOnGround++;
			}
		}
		if (numOnGround > 3 && !invincible) {
			EndConditions result = new EndConditions();
			myStage.setScene(result.init(false, myStage, score));
			myAnimation.stop();
		}
		if ((numSteps+1)/30 == GAME_RUNTIME){ 
			EndConditions result = new EndConditions();
			myStage.setScene(result.init(true, myStage, score));
		}
	}
	
	public void printAngle(){
		System.out.printf("Shooting Angle: %f degrees\n", lastAngle);
	}
}
