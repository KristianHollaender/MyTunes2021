package gui.controller;

import be.Playlist;
import bll.PlaylistManager;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class EditPlaylistController {

    @FXML
    private TextField txtFieldPlaylist;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

    private PlaylistManager playlistManager = new PlaylistManager();

    public EditPlaylistController() throws Exception {
    }

    @FXML
    public void initialize(){
    }

    public void cancelEditPlaylistButton() throws SQLServerException {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    public void editPlaylistButton() throws SQLException {
        Stage stage = (Stage) btnSave.getScene().getWindow();
        if (txtFieldPlaylist != null && !txtFieldPlaylist.getText().isEmpty()) {
            //todo make this work somehow
            System.out.println("Editing a playlist name doesnt work yet :(");
        } else
            System.out.println("You must type something in the field to change the name of the playlist");
        stage.close();
    }

}
