package main;
import javafx.application.Application;
import javafx.stage.Stage;
import main.graphicalInterface.MainWindow;

public class Root extends Application {
	
	public static void main ( String[] args ) {
		launch( args );
	}

	@Override
	public void start( Stage primaryStage ) throws Exception {
		
		//System.setProperty("javafx.animation.fullspeed", "true");
		//System.setProperty("prism.vsync", "false");
		
		MainWindow mainWindow = new MainWindow();
		mainWindow.show();
		
	}
	
}