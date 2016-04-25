import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Survival implements GameMode {

	private static final int SOLDIER_SEGMENTS = 7;
	private static final double TROOP_ACCELERATION = .1;
	private static final int TROOP_VELOCITY = 2;
	private static final int MAX_VIABLE_WIDTH = 950;
	private static final int SOLDIER_VERTICAL_OFFSET = 120;
	private static final int SOLDIER_SPEED = 30;
	private Scene gameScene;
	private Group myRoot;
	private Soldier mySoldier;
	private List<Shot> myShots;
	private double shotAngle;
	private Group childrenShots;
	private List<Paratrooper> myTroops;
	private Group childrenTroops;
	private int mod;
	private int numSteps;
	private Label gameText;
	private int score;
	private boolean invincible;
	private Stage myStage;
	private Timeline myAnimation;

	public Scene init(Stage stage, int width, int height, Timeline animation) {
		myStage = stage;
		myAnimation = animation;
		gameScene = new Scene(buildUI(), width, height);
		gameScene.setFill(Color.SKYBLUE);
		myAnimation = animation;
		return gameScene;
	}

	public Group buildUI() {
		myRoot = new Group();
		gameText = new Label("");
		gameText.setFont(Font.font("Roboto", 16));
		mySoldier = new Soldier(SOLDIER_SPEED, myStage.getWidth() / 2, myStage.getHeight() - SOLDIER_VERTICAL_OFFSET);
		myRoot.getChildren().addAll(mySoldier, gameText);
		return myRoot;
	}

	public void gameStart() {
		myShots = new ArrayList<Shot>();
		myTroops = new ArrayList<Paratrooper>();
		childrenShots = new Group();
		childrenTroops = new Group();
		numSteps = 0;
		mod = 80;
		score = 0;
		myRoot.getChildren().addAll(childrenShots, childrenTroops);
		setupEvents();
	}

	public void setupEvents() {
		gameScene.setOnKeyPressed(event -> keyPressed(event.getCode()));
		gameScene.setOnMouseMoved(event -> mouseMoved(event));
		gameScene.setOnMouseClicked(event -> fireShot(event));
	}

	public void step(double num) {
		Random rand = new Random();
		if (numSteps % mod == 0) {
			for (int i = 0; i < rand.nextInt(2) + 1; i++) {
				myTroops.add(new Paratrooper(rand.nextDouble() * MAX_VIABLE_WIDTH, 0, TROOP_VELOCITY, TROOP_ACCELERATION, numSteps+1));
			}
		}
		advanceShots();
		advanceTroops();
		checkMovingPieces();
		checkGameStatus();
		numSteps++;
		gameText.setText("SCORE: " + score + "\nTIME REMAINING: " + (30 - (numSteps/ 30)) + "\nSOLDIER HEALTH: " + mySoldier.getHealth() );
	}

	public void keyPressed(KeyCode myCode) {
		switch (myCode) {
		case LEFT:
			mySoldier.updatePos("left");
			break;
		case RIGHT:
			mySoldier.updatePos("right");
			break;
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
		default:
			break;
		}
	}

	public void mouseMoved(MouseEvent myEvent) {
		double firingPointX = mySoldier.getX() + mySoldier.getWidth();
		double firingPointY = mySoldier.getY() + mySoldier.getHeight() / SOLDIER_SEGMENTS;
		if (myEvent.getX() > firingPointX) {
			shotAngle = Math.atan((firingPointY - myEvent.getY()) / (myEvent.getX() - firingPointX)) * (180 / Math.PI);
		} else if (myEvent.getX() < firingPointX && myEvent.getY() <= firingPointY) {
			shotAngle = 90
					+ (Math.atan((firingPointX - myEvent.getX()) / (firingPointY - myEvent.getY())) * (180 / Math.PI));
		} else if (myEvent.getX() < firingPointX && myEvent.getY() > firingPointY) {
			shotAngle = 180
					+ (Math.atan((myEvent.getY() - firingPointY) / (firingPointX - myEvent.getX())) * (180 / Math.PI));
		} else {
			shotAngle = 90;
		}
	}
	
	public void fireShot(MouseEvent myEvent) {
		double firingPointX = mySoldier.getX() + mySoldier.getWidth();
		double firingPointY = mySoldier.getY() + mySoldier.getHeight() / 7;
		myShots.add(new Shot(firingPointX, firingPointY, shotAngle, 20, false));
		score -= 10;
	}
	
	public void advanceShots() {
		childrenShots.getChildren().clear();
		for (Shot myShot : myShots){
			myShot.updatePos();
			childrenShots.getChildren().add(myShot);
		}
	}
	
	public void advanceTroops() {
		childrenTroops.getChildren().clear();
		for (Paratrooper myTroop : myTroops){
			myTroop.updatedVelocity();
			myTroop.updatePosition();
			if (myTroop.fire(numSteps)){
				myShots.add(new Shot(myTroop.getX(), myTroop.getY(), calcEnemyShotAngle(myTroop.getX(), myTroop.getY()), 10, true));
			}
			childrenTroops.getChildren().add(myTroop);
		}
	}
	
	public double calcEnemyShotAngle(double x, double y) {
		double shotAngle;
		if (x > mySoldier.getX()) {
			shotAngle = 180 + (Math.atan((mySoldier.getY() - y) / (x - mySoldier.getX())) * (180 / Math.PI));
		}
		else if (x < mySoldier.getX()){
			shotAngle = 270 + (Math.atan((mySoldier.getX() - x) / (mySoldier.getY() - y)) * (180 / Math.PI));
		}
		else{
			shotAngle = 90;
		}
		return shotAngle;
	}

	public void checkMovingPieces() {
		Iterator<Shot> shotIter = myShots.iterator();
		while (shotIter.hasNext()){
			Shot myShot = shotIter.next();
			if (myShot.getX() < 0 || myShot.getX() > myStage.getWidth() || myShot.getY() < 0 || myShot.getY() > myStage.getHeight()){
				shotIter.remove();
			}
			Iterator<Paratrooper> troopIter = myTroops.iterator();
			while (troopIter.hasNext()){
				Paratrooper myTroop = troopIter.next();
				if (myTroop.intersects(myShot.getBoundsInLocal()) && !myShot.enemy()){
					score += 110;
					troopIter.remove();
					if (myShots.contains(myShot)) shotIter.remove();
				}
			}
			if (myShot.intersects(mySoldier.getBoundsInLocal()) && myShot.enemy() && !invincible){
				shotIter.remove();
				mySoldier.hit();
			}
		}
	}
	
	public void checkGameStatus() {
		if (mySoldier.getHealth() == 0){
			EndConditions result = new EndConditions();
			myStage.setScene(result.init(false, myStage, score));
			myAnimation.stop();
		}
		if ((numSteps + 1) / 30 == 30){
			EndConditions result = new EndConditions();
			myStage.setScene(result.init(true, myStage, score));
		}
	}
}
