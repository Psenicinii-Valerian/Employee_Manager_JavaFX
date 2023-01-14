package it.step;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Forma de initializare pe lung
        //  FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/manager.fxml"));
        //  AnchorPane pane = loader.load();
        //  loader.getController();


        AnchorPane root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Employee manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}