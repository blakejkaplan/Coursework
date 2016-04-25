//This entire file is part of my masterpiece
//Blake Kaplan (bjk20)

/*
 * I believe that this class is well designed because it is very succinct with its coding.
 * There is no unnecessary code and each method is at most 6 lines. I think that I did a good 
 * job adding the necessary functionality on top of the ImageView class.
 */

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Soldier extends ImageView {
	
	private int myHealth;
	private int mySpeed;
	private boolean dead;
	
	public Soldier(int speed, double x, double y){
		
		myHealth = 100;
		this.setImage(new Image(getClass().getClassLoader().getResourceAsStream("Soldier.png")));
		this.setX(x);
		this.setY(y);
		mySpeed = speed;
		dead = false;
		
	}

	public int getHealth() {
		return myHealth;
	}
	
	public void updatePos(String direction) {
		if (direction.equals("left")){ 
			this.setX(this.getX()-mySpeed);
		}
		else{
			this.setX(this.getX()+mySpeed);
		}
		
	}
	
	public double getHeight() {
		return this.getImage().getHeight();
	}
	
	public double getWidth() {
		return this.getImage().getWidth();
	}
	
	public void hit() {
		myHealth -= 20;
		if (myHealth == 0) dead = true;
	}
	
	public boolean isDead() {
		return dead;
	}	

}
