package CryptarithmeticSolver;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CryptarithmeticSolverModel extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Cryptarithmetic Solver by Jithin Chacko");
        scene.getStylesheets();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
