/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package W14B_G4_Assignment2;

import W14B_G4_Assignment2.view.UIwindow;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start (Stage primaryStage) throws Exception{
        UIwindow window = new UIwindow(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
