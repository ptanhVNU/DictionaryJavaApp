import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class MainGraphics extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
           primaryStage.setTitle("Từ điển");
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root, 400, 400);
            primaryStage.setScene(scene);
            primaryStage.getIcons().add(new Image("graphics/image/icon_app.png"));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
