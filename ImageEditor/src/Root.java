import graphicalInterface.MainWindow;
import javafx.application.Application;
import javafx.stage.Stage;

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