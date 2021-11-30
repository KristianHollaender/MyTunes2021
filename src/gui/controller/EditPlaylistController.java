package gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



public class EditPlaylistController {

    @FXML
    private TextField txtFieldPlaylist;
    @FXML
    private Button btnCreate;
    @FXML
    private Button btnUndo;


    public void cancelPlaylist(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCreate.getScene().getWindow();
        stage.close();
    }

    public void createPlaylist(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCreate.getScene().getWindow();
        stage.close();
    }
}
