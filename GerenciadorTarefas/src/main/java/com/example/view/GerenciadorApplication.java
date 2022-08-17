package com.example.view;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class GerenciadorApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GerenciadorApplication.class.getResource("/view/TelaGerenciador.fxml"));
        Scene scene = new Scene((Parent) fxmlLoader.load(), 734, 466);
        stage.setTitle("Gerenciador de Tarefas");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}