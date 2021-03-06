package org.FIS2021;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.FIS2021.models.User;
import org.FIS2021.services.Comandaservice;
import org.FIS2021.services.FileSystemService;
import org.FIS2021.services.PlantService;
import org.FIS2021.services.UserService;

import java.nio.file.Files;
import java.nio.file.Path;

public class App extends Application {

    private static User user;

    @Override


    public void start(Stage stage) throws Exception {
        initDirectory();
        UserService.initDatabase();
        Comandaservice.initDatabase();
        PlantService.initDatabase();
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Register.fxml"));
        stage.setTitle("Plant Store - Registration");
        stage.setScene(new Scene(root, 640, 480));
        stage.show();
    }

    public static User getUser(){
        return user;
    }
    public static void setUser(User user){
        App.user=user;
    }


    private void initDirectory() {
        Path applicationHomePath = FileSystemService.APPLICATION_HOME_PATH;
        if (!Files.exists(applicationHomePath))
            applicationHomePath.toFile().mkdirs();
    }



    public static void main(String[] args) {
        launch();
    }

}