package pack;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/main.fxml"));
            Pane root = loader.load();
            Controller controller = loader.getController();
            controller.setStage(primaryStage);
            Scene scene = new Scene(root);
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("/resources/style.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("UDPChat");
            primaryStage.setOnCloseRequest(event -> {
                event.consume();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                if(alert.showAndWait().orElse(ButtonType.CANCEL).equals(ButtonType.OK)){
                    System.exit(0);
                }
            });
            primaryStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
