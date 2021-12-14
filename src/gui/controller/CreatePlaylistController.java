package gui.controller;

import be.Playlist;
import bll.PlaylistManager;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.PlaylistDAO;
import gui.model.PlaylistModel;
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

    private PlaylistModel playlistModel = new PlaylistModel();

    /**
     * Constructor for CreatePlaylistController.
     */
    public CreatePlaylistController() throws Exception {
    }

    /**
     * Closes the create playlist window.
     */
    public void cancelNewPlaylistButton() throws SQLServerException {
        Stage stage = (Stage) btnUndo.getScene().getWindow();
        stage.close();
    }

    /**
     * Tries to create the new playlist with the given name, cannot be null.
     */
    public void createPlaylist() throws SQLException {
        Stage stage = (Stage) btnCreate.getScene().getWindow();
        if (txtFieldPlaylist != null && !txtFieldPlaylist.getText().isEmpty()) {
            playlistModel.createPlaylist(txtFieldPlaylist.getText());
        } else
            System.out.println("You didn't give your new playlist a name");
        stage.close();
    }

}
