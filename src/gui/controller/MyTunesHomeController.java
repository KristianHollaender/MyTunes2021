package gui.controller;

import be.MusicPlayer;
import be.Playlist;
import be.Song;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Optional;
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
    private TableView<Playlist> tvPlaylist;
    @FXML
    private TableColumn<Playlist, String> tcName;
    @FXML
    private TableColumn<Playlist, Integer> tcSongs;
    @FXML
    private TableColumn<Playlist, Double> tcTime;
    @FXML
    private TableView<Song> tvSongsOnPlaylist;
    @FXML
    private TableColumn<Song, String> tcSongsOnPlaylist;
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

    private Song selectedSong;
    private Song selectedSongOnPlaylist;
    private Playlist selectedPlaylist;
    private Song songPlaying;
    private Stage stage = new Stage();

    private ObservableList<Song> allSongs = FXCollections.observableArrayList();
    private ObservableList<Playlist> allPlaylist = FXCollections.observableArrayList();
    private ObservableList<Song> songsOnPlaylist = FXCollections.observableArrayList();

    private MusicPlayer musicPlayer = new MusicPlayer();
    private SongModel songModel = new SongModel();
    private PlaylistModel playlistModel = new PlaylistModel();

    private boolean isPlaying = false;

    public MyTunesHomeController() throws Exception {
    }

    /**
     * Initialize the main view methods
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTables();
        changeVolume();
        selectedSong();
        selectedPlaylist();
        selectedSongOnPlaylist();
    }

    /**
     * Initialize the 3 different tables used
     */
    public void initializeTables() {
        tcTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        tcArtist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        tcCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        tcTimeSongs.setCellValueFactory(new PropertyValueFactory<>("songLength"));

        try {
            allSongs = FXCollections.observableList(songModel.getSongs());
            tableViewLoad(allSongs);
        } catch (Exception e) {
            e.printStackTrace();
        }

        tcName.setCellValueFactory(new PropertyValueFactory<>("title"));
        tcSongs.setCellValueFactory(new PropertyValueFactory<>("songs"));
        tcTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        try {
            allPlaylist = FXCollections.observableList(playlistModel.getPlaylist());
            tableViewLoadPlaylist(allPlaylist);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Opens the create new song window
     */
    public void createNewSongButton() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/CreateSongs.fxml"));
        stage.setTitle("New Song");
        stage.setScene(new Scene(root));
        stage.show();
        stage.setOnHiding( event ->
        {try {
            allSongs = FXCollections.observableList(songModel.getSongs());
            tableViewLoad(allSongs);
        } catch (Exception e) {
            e.printStackTrace();
        } });
    }

    /**
     * Opens the edit song window
     */
    public void editSongButton(ActionEvent actionEvent) throws IOException {
        if(selectedSong != null) {
            Song selectedSong = tvSongs.getSelectionModel().getSelectedItem();
            FXMLLoader parent = new FXMLLoader(getClass().getResource("/gui/view/EditSongs.fxml"));
            Scene mainWindowScene = null;
            try {
                mainWindowScene = new Scene(parent.load());
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            Stage editSongStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            editSongStage.setScene(mainWindowScene);
            EditSongsController editSongsController = parent.getController();
            editSongsController.setSelectedSong(selectedSong);
            editSongStage.show();
        }else{
            System.out.println("No songs are selected");
        }
    }

    /**
     * Opens the create a new playlist window
     */
    public void createPlaylistButton() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/CreatePlaylist.fxml"));
        stage.setTitle("New Playlist");
        stage.setScene(new Scene(root));
        stage.show();
        stage.setOnHiding( event ->
        { try {
            allPlaylist = FXCollections.observableList(playlistModel.getPlaylist());
            tableViewLoadPlaylist(allPlaylist);
        } catch (Exception e){
            e.printStackTrace();
        } });
    }

    /**
     * Opens the edit playlist window
     */
    public void editPlaylistButton(ActionEvent actionEvent) throws IOException {
        if(selectedPlaylist != null) {
            Playlist selectedPlaylist = tvPlaylist.getSelectionModel().getSelectedItem();
            FXMLLoader parent = new FXMLLoader(getClass().getResource("/gui/view/EditPlaylist.fxml"));
            Scene mainWindowScene = null;
            try {
                mainWindowScene = new Scene(parent.load());
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            Stage editPlaylistStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            editPlaylistStage.setScene(mainWindowScene);
            EditPlaylistController editPlaylistController = parent.getController();
            editPlaylistController.setSelectedPlaylist(selectedPlaylist);
            editPlaylistStage.show();
        }else{
            System.out.println("No playlist selected");
        }
    }

    /**
     * Makes the close button close the entire application
     */
    public void closeTheAppButton() {
        Platform.exit();
    }

    /**
     * Makes the tcSongsOnPlaylist show all the songs on the playlist
     */
    public void seeSongsOnPlaylist(){
        tcSongsOnPlaylist.setCellValueFactory(new PropertyValueFactory<>("title"));
        try {
            songsOnPlaylist = FXCollections.observableList(playlistModel.getSongsOnPlaylist(selectedPlaylist.getId()));
            tableViewLoadSongsOnPlaylist(songsOnPlaylist);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void tableViewLoadPlaylist(ObservableList<Playlist> allPlaylist){
        tvPlaylist.setItems(getPlaylistData());
    }

    public void tableViewLoad(ObservableList<Song> allSongs) {
        tvSongs.setItems(getSongData());
    }

    public void tableViewLoadSongsOnPlaylist(ObservableList<Song> songsOnPlaylist){
        tvSongsOnPlaylist.setItems(getSongsOnPlaylistData());
    }

    public ObservableList<Playlist> getPlaylistData() {
        return allPlaylist;
    }

    public ObservableList<Song> getSongData() {
        return allSongs;
    }

    public ObservableList<Song> getSongsOnPlaylistData() {
        return songsOnPlaylist;
    }

    /**
     * Label used for setting the current playing song
     */
    public void currentPlayingSongLabel(){
        LabelPlayerSong.setText("");
    }

    /**
     * Makes the playButton play depending on if the user has selected a song from one of the tables
     * and if the player isn't playing already.
     * Uses a boolean value to check if the media player is playing or not.
     * If the player reaches the end of a song, it uses the songForward method for automatically playing the next song.
     */
    public void playButton(){
        if (selectedSongOnPlaylist != null && selectedSongOnPlaylist.getUrl() != null && !isPlaying) {
            songPlaying = selectedSongOnPlaylist;
            musicPlayer.setSong(songPlaying);
            musicPlayer.play();
            LabelPlayerSong.setText(songPlaying.getTitle() + " is Playing");
            btnSongPlayer.setText("=");
            musicPlayer.getMediaPlayer().setOnEndOfMedia(() -> {
                songForward();
            });
            isPlaying = !isPlaying;
        } else if (selectedSong != null && !isPlaying) {
            songPlaying = selectedSong;
            musicPlayer.setSong(songPlaying);
            musicPlayer.play();
            LabelPlayerSong.setText(selectedSong.getTitle() + " is Playing");
            btnSongPlayer.setText("=");
            musicPlayer.getMediaPlayer().setOnEndOfMedia(() -> {
                songForward();
            });
            isPlaying = !isPlaying;
        } else if (songPlaying != null){
            String songnamePlaying = musicPlayer.getSong().getTitle();
            musicPlayer.pause();
            btnSongPlayer.setText("▼");
            isPlaying = !isPlaying;
            LabelPlayerSong.setText(songnamePlaying + " is paused");
        }
    }

    /**
     * Makes the volume slider start at a 100 and makes it adjustable
     */
    public void changeVolume(){
        sliderSound.setValue(musicPlayer.getVolume() * 100);
        sliderSound.valueProperty().addListener(new InvalidationListener() {

            @Override
            public void invalidated(Observable observable) {
                musicPlayer.setVolume(sliderSound.getValue() / 100);
            }
        });
    }

    /**
     * Changes the songsTable whenever you press the search button.
     */
    public void search() {
        try {
            this.tvSongs.setItems(FXCollections.observableList(songModel.searchSong(tfSearchBar.getText())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Changes selected song to the song clicked in the tvSongs
     */
    private void selectedSong() {
        this.tvSongs.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            this.selectedSong = (Song) newValue;
            if (selectedSong != null) {
                LabelPlayerSong.setText(selectedSong.getTitle());
                songPlaying = selectedSong;
            }
        }));
    }

    /**
     * Makes you able to select a playlist from the table
     */
    private void selectedPlaylist(){
        this.tvPlaylist.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if ((Playlist) newValue != null) {
                this.selectedPlaylist = (Playlist) newValue;
                seeSongsOnPlaylist();
            }
        }));
    }

    /**
     * Makes you able to select a song on the songs on playlist table
     */
    private void selectedSongOnPlaylist() {
        this.tvSongsOnPlaylist.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            this.selectedSongOnPlaylist = (Song) newValue;
            if (selectedSongOnPlaylist != null) {
                LabelPlayerSong.setText(selectedSongOnPlaylist.getTitle());
                songPlaying = selectedSongOnPlaylist;
                this.tvSongs.getSelectionModel().clearSelection();
                if (isPlaying) {
                    updateSongPlaying();
                }
            }
        }));
    }

    /**
     * Deletes the selected song from the songs table
     */
    public void btnDeleteSong() throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("WARNING MESSAGE");
        alert.setHeaderText("Warning before you delete song");
        alert.setContentText("Are you sure you want to delete this song!?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            songModel.deleteSong(selectedSong.getId());
            reloadSongTable();
        }else {
            return;
        }
    }


    /**
     * Reloads the song table
     */
    public void reloadSongTable() throws Exception {
        try {
            int index = tvSongs.getSelectionModel().getFocusedIndex();
            this.tvSongs.setItems(FXCollections.observableList(songModel.getSongs()));
            tvSongs.getSelectionModel().select(index);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Reloads the songs on playlist table
     */
    private void reloadSongsOnPlaylist() throws Exception {
        try {
            int index = tvSongsOnPlaylist.getSelectionModel().getFocusedIndex();
            this.tvSongsOnPlaylist.setItems(FXCollections.observableList(playlistModel.getSongsOnPlaylist(selectedPlaylist.getId())));
            tvSongsOnPlaylist.getSelectionModel().select(index);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Reloads the playlist table
     */
    private void reloadPlaylistTable() {
        try {
            int index = tvPlaylist.getSelectionModel().getFocusedIndex();
            this.tvPlaylist.setItems(FXCollections.observableList(playlistModel.getPlaylist()));
            tvPlaylist.getSelectionModel().select(index);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Deletes the selected playlist if it doesn't contain any songs.
     */
    public void deletePlaylist() throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("WARNING MESSAGE");
        alert.setHeaderText("Warning before you delete playlist");
        alert.setContentText("Are you sure you want to delete this playlist!?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            selectedPlaylist();
            playlistModel.deletePlaylist(selectedPlaylist.getId());
        }else {
            return;
        }
        try{
            allPlaylist = FXCollections.observableList(playlistModel.getPlaylist());
            tableViewLoadPlaylist(allPlaylist);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Makes the mediaplayer play the next song in the songs table or the playlist
     */
    public void songForward() {
        if (selectedSongOnPlaylist != null) {
            int index = songsOnPlaylist.indexOf(songPlaying) + 1;
            try {
                songPlaying = songsOnPlaylist.get(index);
            } catch (Exception e) {
                songPlaying = songsOnPlaylist.get(0);
            }
        } else if (selectedSong != null) {
            int index = allSongs.indexOf(songPlaying) + 1;
            try {
                songPlaying = allSongs.get(index);
            } catch (Exception e) {
                songPlaying = allSongs.get(0);
            }
        }
        musicPlayer.pause();
        musicPlayer.setSong(songPlaying);
        musicPlayer.play();
        LabelPlayerSong.setText(songPlaying.getTitle() + " is Playing");
        btnSongPlayer.setText("=");
        musicPlayer.getMediaPlayer().setOnEndOfMedia(() -> {
            songForward();
        });
        isPlaying = true;
    }

    /**
     * Makes the mediaplayer play the previous song
     */
    public void songBack() throws IOException {
        int index = allSongs.indexOf(songPlaying) - 1;
        try {
            songPlaying = allSongs.get(index);
        } catch (Exception e) {
            songPlaying = allSongs.get(allSongs.size()-1);
        }
        musicPlayer.pause();
        musicPlayer.setSong(songPlaying);
        musicPlayer.play();
        LabelPlayerSong.setText(songPlaying.getTitle() + " is Playing");
        btnSongPlayer.setText("=");
        isPlaying = true;
    }

    /**
     * Presses the play button twice for a more fluent swap between songs
     */
    public void updateSongPlaying() {
        playButton();
        playButton();
    }

    /**
     * Removes the selected song from the current playlist.
     */
    public void deleteSongsFromPlaylistButton() throws SQLException {
        if (selectedPlaylist != null && selectedSongOnPlaylist != null) {
            try {
                int index = tvSongsOnPlaylist.getSelectionModel().getFocusedIndex();
                playlistModel.deleteFromPlaylist(selectedPlaylist.getId(), selectedSongOnPlaylist.getId());
                reloadSongsOnPlaylist();
                reloadPlaylistTable();
                tvSongsOnPlaylist.getSelectionModel().select(index > 0 ? index - 1 : index);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Adds the selected song to the selected playlist
     */
    public void addSongToPlaylistButton() throws SQLException {
        if (selectedSong != null)
            try {
                playlistModel.addSongToPlaylist(selectedPlaylist.getId(), selectedSong.getId());
                reloadSongsOnPlaylist();
                reloadPlaylistTable();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    /**
     * Takes the selected song on the playlist and moves its position 1 up
     */
    public void btnUp(){
        System.out.println("testUp");
        if (selectedSongOnPlaylist != null){
            System.out.println("Works");
            try {
                int index = tvSongsOnPlaylist.getSelectionModel().getFocusedIndex() -1;
                int index1 = tvSongsOnPlaylist.getSelectionModel().getFocusedIndex() -0;
                tvSongsOnPlaylist.getSelectionModel().select(index);
                Collections.swap(songsOnPlaylist, index, index1);
                tvSongsOnPlaylist.getSelectionModel().select(index);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * Takes the selected song on the playlist and moves its position 1 down
     */
    public void btnDown() {
        if (selectedSongOnPlaylist != null) {
            try {
                int index = tvSongsOnPlaylist.getSelectionModel().getFocusedIndex() + 1;
                int index1 = tvSongsOnPlaylist.getSelectionModel().getFocusedIndex() - 0;
                tvSongsOnPlaylist.getSelectionModel().select(index);
                Collections.swap(songsOnPlaylist, index, index1);
                tvSongsOnPlaylist.getSelectionModel().select(index);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public void editPlaylist(String newTitle) {
        try {
            selectedPlaylist.setTitle(newTitle);
            playlistModel.editPlaylist(selectedPlaylist);
            reloadPlaylistTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

