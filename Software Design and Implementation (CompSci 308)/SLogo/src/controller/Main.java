package controller;

import guipackage.GUI;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
	private static final int SCREEN_WIDTH = 1300;
	private static final int SCREEN_HEIGHT = 800;
	
	private Stage s;
	GUI myGui;
    @Override
    public void start(Stage myStage) throws Exception {
    	s = new Stage();
    	myGui = new GUI(SCREEN_HEIGHT, SCREEN_WIDTH, s);
    	s.setScene(myGui.createScene());
    	
    	s.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
