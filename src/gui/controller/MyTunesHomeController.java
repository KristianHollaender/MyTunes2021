package gui.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.stage.Stage;


import java.io.IOException;

public class MyTunesHomeController{

    @FXML
    private Button btnSongBack;
    @FXML
    private Button btnSongPlayer;
    @FXML
    private Button btnSongForward;
    @FXML
    private Slider sliderSound;
    @FXML
    private Button btnSearchbar;
    @FXML
    private Button btnNewPlaylist;
    @FXML
    private Button btnEditPlaylist;
    @FXML
    private Button btnDeletePlaylist;
    @FXML
    private Button btnLeftArrow;
    @FXML
    private Button btnUp;
    @FXML
    private Button btnDown;
    @FXML
    private Button btnDeleteSongsOnPlaylist;
    @FXML
    private Button btnNewSong;
    @FXML
    private Button btnEditSong;
    @FXML
    private Button btnDeleteSong;
    @FXML
    private Button btnClose; 


    public void createNewSong(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/EditSongs.fxml"));
        Stage stage = new Stage();
        stage.setTitle("New/Edit Song");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void createPlaylist(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/EditPlaylist.fxml"));
        Stage stage = new Stage();
        stage.setTitle("New/Edit Playlist");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void close(ActionEvent actionEvent) throws IOException{
        Platform.exit();
    }
}
