package com.example.myplant;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import lombok.Data;

@Data
public class Plant {
//    private final SimpleIntegerProperty id;
    private String path;
    private String firstPlant;
    private String secondPlant;
    private String thirdPlant;
    private HBox operate;
    Plant(String path, String firstPlant, String secondPlant, String thirdPlant, HBox hbox) {
//        this.id = new SimpleIntegerProperty(id);
        this.path = path;
        this.firstPlant = firstPlant;
        this.secondPlant = secondPlant;
        this.thirdPlant = thirdPlant;
        this.operate = hbox;
    }

    public void setPath(String string) {
        path = string;
    }

    
}
