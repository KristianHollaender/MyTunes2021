package gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javafx.scene.control.Button;

public class EditSongsController {

    public TextField txtFieldTitle;
    public TextField txtFieldArtist;
    public TextField txtFieldTime;
    public TextField txtFieldFile;
    public Button btnAdd;
    public Button chooseFile;
    public Button btnUndo;
    public MenuButton chooseGenre;



    public void seeAllGenre(ActionEvent actionEvent) {
    }

    public void chooseMP3(ActionEvent actionEvent) {
    }

    public void cancelSong(ActionEvent actionEvent) {
    }

    public void createOrEditSong(ActionEvent actionEvent) {
        Stage stage = (Stage) btnAdd.getScene().getWindow();
        stage.close();
    }
}
