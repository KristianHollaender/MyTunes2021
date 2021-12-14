package gui.controller;

import be.Playlist;
import be.Song;
import bll.PlaylistManager;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import gui.model.PlaylistModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class EditPlaylistController {

    @FXML
    private TextField txtFieldPlaylist;
    @FXML
    private TextField txtFieldId;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

    PlaylistModel playlistModel;
    private MyTunesHomeController myTunesHomeController = new MyTunesHomeController();

    /**
     * Constructor for the EditPlaylistController.
     */
    public EditPlaylistController() throws Exception {
        playlistModel = new PlaylistModel();
    }

    /**
     * Cancels the editPlaylist window and takes us back to the main window.
     */
    public void cancelEditPlaylistButton(ActionEvent actionEvent) throws SQLServerException {
        FXMLLoader parent = new FXMLLoader(getClass().getResource("/gui/view/MyTunesHome.fxml"));
        Scene mainWindowScene = null;
        try{
            mainWindowScene = new Scene(parent.load());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        Stage editSongStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        editSongStage.setScene(mainWindowScene);
    }

    /**
     * Edits the name of the selected playlist when pressed.
     */
    public void editPlaylistButton(ActionEvent actionEvent) throws SQLException {
        String title = txtFieldPlaylist.getText();
        int id = Integer.parseInt(txtFieldId.getText());
        Playlist playlist = new Playlist(id, title);
        playlistModel.editPlaylist(playlist);
        cancelEditPlaylistButton(actionEvent);
    }

    /**
     * Sets the selected Playlist and defines what text fields should be changed.
     */
    public void setSelectedPlaylist(Playlist playlist) {
        txtFieldPlaylist.setText(playlist.getTitle());
        txtFieldId.setText(String.valueOf(playlist.getId()));
    }
}
