package org.FIS2021.controllers;
import org.FIS2021.exceptions.PlantNotFoundException;
import org.FIS2021.models.Plant;
import org.FIS2021.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import org.FIS2021.services.Comandaservice;
import org.FIS2021.services.PlantService;

import java.io.IOException;
import java.util.ArrayList;

public class EditPlantStoreController {
    @FXML
    private Button butonLogOut;
    @FXML
    private ListView ListaProduse;
    @FXML
    private TextField idPlant;
    @FXML
    private TextField cantitate;
    @FXML
    private User user;

    public void setUser(User user) {
        this.user = user;
    }


    @FXML
    private void handleLoginAction() {
        try {
            Stage stage = (Stage) butonLogOut.getScene().getWindow();
            Parent loginRoot = FXMLLoader.load(getClass().getResource("/FXML/Login.fxml"));
            Scene scene = new Scene(loginRoot, 640, 480);
            stage.setTitle("Plant Store -login");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void initialize() {
        ArrayList<Plant> orders = PlantService.getAllPlantsShop();
        for (Plant o : orders) {
            ListaProduse.getItems().add(o.toString());
        }

    }

    @FXML
    private void removePlantStore() {

        try {
            if (PlantService.getPlantProvider(idPlant.getText()) != null) {
                Plant p = (PlantService.getPlantProvider(idPlant.getText()));

                PlantService.managerAddPlant(new Plant(p.getProvider(), p.getNume(), p.getPret(), Integer.parseInt(cantitate.getText())));

            }

        } catch (PlantNotFoundException e) {
            System.out.println("ma ta");
            return;
        }
    }


}

