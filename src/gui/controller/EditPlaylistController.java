package gui.controller;

import be.Playlist;
import be.Song;
import bll.PlaylistManager;
import com.microsoft.sqlserver.jdbc.SQLServerException;
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
    private Button btnSave;
    @FXML
    private Button btnCancel;

    private PlaylistManager playlistManager = new PlaylistManager();
    private MyTunesHomeController myTunesHomeController = new MyTunesHomeController();


    public EditPlaylistController() throws Exception {
    }

    @FXML
    public void initialize(){
    }

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

    public void editPlaylistButton() throws IOException {
        Stage stage = (Stage) btnSave.getScene().getWindow();
        if (txtFieldPlaylist != null && !txtFieldPlaylist.getText().isEmpty()) {
            myTunesHomeController.editPlaylist(txtFieldPlaylist.getText());
        } else
            System.out.println("You must type something in the field to change the name of the playlist");
        stage.close();
    }

    public void setSelectedPlaylist(Playlist playlist) {
        txtFieldPlaylist.setText(playlist.getTitle());
    }
}
