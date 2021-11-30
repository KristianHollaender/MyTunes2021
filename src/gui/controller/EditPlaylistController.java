package gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



public class EditPlaylistController {

    public TextField txtFieldPlaylist;
    public Button btnCreate;
    public Button btnUndo;

    public void cancelPlaylist(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCreate.getScene().getWindow();
        stage.close();
    }

    public void createPlaylist(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCreate.getScene().getWindow();
        stage.close();
    }
}
