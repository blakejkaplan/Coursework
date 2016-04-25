//This entire file is part of my masterpiece.
//Blake Kaplan (bjk20)

/*
 * I believe that this class implements good design techniques. As with other classes, it extends the ImageView to utilize
 * it's bounds methods. The classes that I added are very simple and are not very large. The code is quite succinct but was
 * very useful in implementing shot motion.
 */

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Shot extends ImageView {

	private double myAngle;
	private double myVelocity;
	private boolean enemy;
	
	public Shot(double x, double y, double angle, double velocity, boolean enemyShot) {	
		this.setX(x);
		this.setY(y);
		myAngle = angle;
		myVelocity = velocity;
		if (enemyShot) {
			this.setImage(new Image(getClass().getClassLoader().getResourceAsStream("EnemyShot.png")));
		}
		else{
			this.setImage(new Image(getClass().getClassLoader().getResourceAsStream("Shot.png")));
		}
		enemy = enemyShot;	
	}
	
	public void updatePos() {
		this.setX(this.getX() + myVelocity * Math.cos(myAngle * (Math.PI / 180)));
		this.setY(this.getY() - myVelocity * Math.sin(myAngle * (Math.PI / 180)));	
	}
	
	public boolean enemy() {
		return enemy;
	}
}
