import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static final int VERTICAL_SIZE = 1000;
    public static final int HORIZONTAL_SIZE = 1000;
    
    private SplashScreen splash;

    public void start (Stage myStage) {
        splash = new SplashScreen();
        myStage.setTitle("Air Defense Commander");
        Scene scene = splash.init(myStage, HORIZONTAL_SIZE, VERTICAL_SIZE);
        myStage.setScene(scene);
        myStage.show();
        
    }

    public static void main (String[] args) {
        launch(args);
    }
}
