package uiFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ResourceBundle rb = ResourceBundle.getBundle("textosFX", Locale.getDefault());
        FXMLLoader loaderMenu = new FXMLLoader(getClass().getResource("/fxml/Screen1.fxml"),rb);
        BorderPane root = loaderMenu.load();
        Scene scene = new Scene(root);


        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icon.png")));
        primaryStage.getIcons().add(image);
        primaryStage.setTitle(rb.getString("title"));
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(true);
    }
}