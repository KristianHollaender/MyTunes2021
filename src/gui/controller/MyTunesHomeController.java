package gui.controller;

import be.Playlist;
import be.Song;
import dal.SongsDAO;
import bll.SongManager;
import gui.model.PlaylistModel;
import gui.model.SongModel;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;


import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.ResourceBundle;

public class MyTunesHomeController implements Initializable {

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
    @FXML
    private TableView<PlaylistModel> tvPlaylist;
    @FXML
    private TableView<Song> tvSongsOnPlaylist;
    @FXML
    private TableView<Song> tvSongs;
    @FXML
    private TableColumn<Song, String> tcTitle;
    @FXML
    private TableColumn<Song, String> tcArtist;
    @FXML
    private TableColumn<Song, String> tcCategory;
    @FXML
    private TableColumn<Song, Double> tcTimeSongs;
    @FXML
    private TableColumn<Playlist, String> tcPlaylistName;
    @FXML
    private TableColumn<Playlist, Integer> tcPlaylistSongs;
    @FXML
    private TextField searchField;
    @FXML
    private TextField tfSearchBar;
    @FXML
    private Label LabelPlayerSong;


    private ObservableList<Song> allSongs = FXCollections.observableArrayList();
    private ObservableList<Playlist> allPlaylist = FXCollections.observableArrayList();


    private SongsDAO songsDAO = new SongsDAO();
    private SongManager songManager = new SongManager();



    private static boolean isPlaying = false;
    String bip = "data/Emotions.mp3";
    Media hit = new Media(new File(bip).toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(hit);

    public MyTunesHomeController() throws Exception {
    }


    public void createNewSong(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/EditSongs.fxml"));
        Stage stage = new Stage();
        stage.setTitle("New/Edit Song");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void editSong(ActionEvent actionEvent) throws IOException {
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

    public void editPlaylist(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/EditPlaylist.fxml"));
        Stage stage = new Stage();
        stage.setTitle("New/Edit Playlist");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void close(ActionEvent actionEvent) throws IOException{
        Platform.exit();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tcTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        tcArtist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        tcCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        tcTimeSongs.setCellValueFactory(new PropertyValueFactory<>("songLength"));

        try {
            allSongs = FXCollections.observableList(songsDAO.getSongs());
            tableViewLoad(allSongs);
        } catch (Exception e) {
            e.printStackTrace();
        }

        changeVolume();
        
    }

    public void tableViewLoad(ObservableList<Song> allSongs) {
        tvSongs.setItems(getSongData());
    }

    public ObservableList<Song> getSongData() {
        return allSongs;
    }
    public void selectedSong(){
        LabelPlayerSong.setText("SongPlaying");
    }
    public void start(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnSongPlayer && isPlaying == false) {
            mediaPlayer.play();
            isPlaying = true;
            selectedSong();
        } else if (isPlaying == true) {
            mediaPlayer.pause();
            isPlaying = false;
            LabelPlayerSong.setText("nonePlaying");
        }
    }


    public void changeVolume(){
        sliderSound.setValue(mediaPlayer.getVolume() * 100);
        sliderSound.valueProperty().addListener(new InvalidationListener() {

            @Override
            public void invalidated(Observable observable) {
                mediaPlayer.setVolume(sliderSound.getValue() / 100);
            }
        });
    }


    /**
     * Changes songsTable, whenever the searchField changes.
     */
    public void search() {
        try {
            this.tvSongs.setItems(FXCollections.observableList(songManager.searchSongs(tfSearchBar.getText())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
