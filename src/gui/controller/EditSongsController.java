package gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javafx.scene.control.Button;

public class EditSongsController {

    @FXML
    private TextField txtFieldTitle;
    @FXML
    private TextField txtFieldArtist;
    @FXML
    private TextField txtFieldTime;
    @FXML
    private TextField txtFieldFile;
    @FXML
    private Button btnAdd;
    @FXML
    private Button chooseFile;
    @FXML
    private Button btnUndo;
    @FXML
    private MenuButton chooseGenre;



    public void seeAllGenre(ActionEvent actionEvent) {
    }

    public void chooseMP3(ActionEvent actionEvent) {
    }

    public void cancelSong(ActionEvent actionEvent) {
        Stage stage = (Stage) btnAdd.getScene().getWindow();
        stage.close();
    }

    public void createOrEditSong(ActionEvent actionEvent) {
        Stage stage = (Stage) btnAdd.getScene().getWindow();
        stage.close();
    }
}
