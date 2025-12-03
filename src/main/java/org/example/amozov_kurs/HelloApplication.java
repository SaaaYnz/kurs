package org.example.amozov_kurs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/amozov_kurs/image/icon.png")));
        stage.getIcons().add(icon);
        Scene scene = new Scene(fxmlLoader.load(), 500, 350);
        stage.setTitle("Authorization!");
        stage.setScene(scene);
        stage.show();
    }

    static void main(String[] args) {
        Application.launch(HelloApplication.class, args);
    }
}
