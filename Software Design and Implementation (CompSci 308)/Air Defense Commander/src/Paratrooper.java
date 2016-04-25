import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Random;

public class Paratrooper extends ImageView {
	
	private static final int MAX_INTERVAL_VARIATION = 80;
	private static final int MINIMUM_SHOT_INTERVAL = 80;
	private double myVelocity;
	private double myAcceleration;
	private boolean reachedGround;
	private boolean counted;
	private int shotInterval;
	private Random rand;
	private int stepOnCreation;
	
	public Paratrooper(double x, double y, double velocity, double acceleration) {
		this.setX(x);
		this.setY(y);
		this.setImage(new Image(getClass().getClassLoader().getResourceAsStream("Paratrooper.png")));
		myVelocity = velocity;
		myAcceleration = acceleration;
		reachedGround = false;
		counted = false;
		rand = new Random();
	}
	
	public Paratrooper(double x, double y, double velocity, double acceleration, int creationStep) {
		this.setX(x);
		this.setY(y);
		this.setImage(new Image(getClass().getClassLoader().getResourceAsStream("Paratrooper.png")));
		myVelocity = velocity;
		myAcceleration = acceleration;
		reachedGround = false;
		counted = false;
		rand = new Random();
		shotInterval = rand.nextInt(MAX_INTERVAL_VARIATION) + MINIMUM_SHOT_INTERVAL;
		stepOnCreation = creationStep;	
	}
	
	public void updatedVelocity() {
		myVelocity += myAcceleration;
	}
	
	public void updatePosition() {
		if (this.getY() > 680){
			reachedGround = true;
		}
		
		else{
			this.setY(this.getY() + myVelocity);
		}	
	}
	
	public boolean isGrounded() {
		return reachedGround;
	}
	
	public boolean isCounted() {
		return counted;
	}
	
	public void count() {
		counted = true;	
	}
	
	public boolean fire(int currentStep) {
		return ((currentStep - stepOnCreation) % shotInterval == 0);
	}
}
