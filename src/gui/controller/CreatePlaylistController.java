package gui.controller;

import be.Playlist;
import bll.PlaylistManager;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.PlaylistDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class CreatePlaylistController {

    @FXML
    private TextField txtFieldPlaylist;
    @FXML
    private Button btnCreate;
    @FXML
    private Button btnUndo;

    private PlaylistManager playlistManager = new PlaylistManager();


    public CreatePlaylistController() throws Exception {
    }

    public void cancelNewPlaylistButton() throws SQLServerException {
        Stage stage = (Stage) btnUndo.getScene().getWindow();
        stage.close();
    }

    public void createPlaylist() throws SQLException {
        Stage stage = (Stage) btnCreate.getScene().getWindow();
        if (txtFieldPlaylist != null && !txtFieldPlaylist.getText().isEmpty()) {
            playlistManager.createPlaylist(txtFieldPlaylist.getText());
        } else
            System.out.println("You didn't give your new playlist a name");
        stage.close();
    }

    public void editPlaylist() throws SQLException {
        Stage stage = (Stage) btnCreate.getScene().getWindow();
        if (txtFieldPlaylist != null && !txtFieldPlaylist.getText().isEmpty()) {
            //todo implement this somehow
        } else
            System.out.println("You must type something in the field to change the name of the playlist");
        stage.close();
    }

}
