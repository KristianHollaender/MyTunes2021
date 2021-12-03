package gui.controller;

import be.Song;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class EditSongsController implements Initializable {

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

    private Song songToAdd;



    public void seeAllGenre(ActionEvent actionEvent) {
    }

    /**
     * Add the new song to database.
     */
    public void chooseMP3() {

    }


    public void cancelSong(ActionEvent actionEvent) {
        Stage stage = (Stage) btnAdd.getScene().getWindow();
        stage.close();
    }

    public void createOrEditSong(ActionEvent actionEvent) {
        Stage stage = (Stage) btnAdd.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
