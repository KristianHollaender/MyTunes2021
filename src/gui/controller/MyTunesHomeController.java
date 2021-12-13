package gui.controller;

import be.MusicPlayer;
import be.Playlist;
import be.Song;
import bll.PlaylistManager;
import bll.SongManager;
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

    private SongManager songManager = new SongManager();
    private MusicPlayer musicPlayer = new MusicPlayer();
    private PlaylistManager playlistManager = new PlaylistManager();

    private boolean isPlaying = false;

    public MyTunesHomeController() throws Exception {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTables();
        changeVolume();
        selectedSong();
        selectedPlaylist();
        selectedSongOnPlaylist();
    }

    public void initializeTables() {
        tcTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        tcArtist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        tcCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        tcTimeSongs.setCellValueFactory(new PropertyValueFactory<>("songLength"));

        try {
            allSongs = FXCollections.observableList(songManager.getSongs());
            tableViewLoad(allSongs);
        } catch (Exception e) {
            e.printStackTrace();
        }

        tcName.setCellValueFactory(new PropertyValueFactory<>("title"));
        tcSongs.setCellValueFactory(new PropertyValueFactory<>("songs"));
        tcTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        try {
            allPlaylist = FXCollections.observableList(playlistManager.getPlaylist());
            tableViewLoadPlaylist(allPlaylist);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public SongManager getSongManager() {
        return songManager;
    }

    public void createNewSongButton() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/CreateSongs.fxml"));
        stage.setTitle("New Song");
        stage.setScene(new Scene(root));
        stage.show();
        stage.setOnHiding( event ->
        {try {
            allSongs = FXCollections.observableList(songManager.getSongs());
            tableViewLoad(allSongs);
        } catch (Exception e) {
            e.printStackTrace();
        } });
    }

    public void editSongButton(ActionEvent actionEvent) throws IOException {
        Song selectedSong = tvSongs.getSelectionModel().getSelectedItem();
        FXMLLoader parent = new FXMLLoader(getClass().getResource("/gui/view/EditSongs.fxml"));
        Scene mainWindowScene = null;
        try{
            mainWindowScene = new Scene(parent.load());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        Stage editSongStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        editSongStage.setScene(mainWindowScene);
        EditSongsController editSongsController = parent.getController();
        editSongsController.setSelectedSong(selectedSong);
        editSongStage.show();
    }


    public void createPlaylistButton() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/CreatePlaylist.fxml"));
        stage.setTitle("New Playlist");
        stage.setScene(new Scene(root));
        stage.show();
        stage.setOnHiding( event ->
        { try {
            allPlaylist = FXCollections.observableList(playlistManager.getPlaylist());
            tableViewLoadPlaylist(allPlaylist);
        } catch (Exception e){
            e.printStackTrace();
        } });
    }

    public void editPlaylistButton() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/EditPlaylist.fxml"));
        stage.setTitle("Edit Playlist");
        stage.setScene(new Scene(root));
        stage.show();
        stage.setOnHiding( event ->
        { try {
            allPlaylist = FXCollections.observableList(playlistManager.getPlaylist());
            tableViewLoadPlaylist(allPlaylist);
        } catch (Exception e){
            e.printStackTrace();
        } });
    }

    public void closeTheAppButton() {
        Platform.exit();
    }

    public void seeSongsOnPlaylist(){
        tcSongsOnPlaylist.setCellValueFactory(new PropertyValueFactory<>("title"));
        try {
            songsOnPlaylist = FXCollections.observableList(playlistManager.getSongsOnPlaylist(selectedPlaylist.getId()));
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

    public void currentPlayingSongLabel(){
        LabelPlayerSong.setText("");
    }
    
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
     * Changes songsTable, whenever the searchField changes.
     */
    public void search() {
        try {
            this.tvSongs.setItems(FXCollections.observableList(songManager.searchSongs(tfSearchBar.getText())));
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

    private void selectedPlaylist(){
        this.tvPlaylist.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if ((Playlist) newValue != null) {
                this.selectedPlaylist = (Playlist) newValue;
                seeSongsOnPlaylist();
            }
        }));
    }

    /**
     *
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

    public void btnDeleteSong() throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("WARNING MESSAGE");
        alert.setHeaderText("Warning before you delete song");
        alert.setContentText("Are you sure you want to delete this song!?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            songManager.deleteSong(selectedSong.getId());
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
            this.tvSongs.setItems(FXCollections.observableList(songManager.getSongs()));
            tvSongs.getSelectionModel().select(index);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     *
     */
    private void reloadSongsOnPlaylist() throws Exception {
        try {
            int index = tvSongsOnPlaylist.getSelectionModel().getFocusedIndex();
            this.tvSongsOnPlaylist.setItems(FXCollections.observableList(playlistManager.getSongsOnPlaylist(selectedPlaylist.getId())));
            tvSongsOnPlaylist.getSelectionModel().select(index);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void reloadPlaylistTable() {
        try {
            int index = tvPlaylist.getSelectionModel().getFocusedIndex();
            this.tvPlaylist.setItems(FXCollections.observableList(playlistManager.getPlaylist()));
            tvPlaylist.getSelectionModel().select(index);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void deletePlaylist() throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("WARNING MESSAGE");
        alert.setHeaderText("Warning before you delete playlist");
        alert.setContentText("Are you sure you want to delete this playlist!?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            selectedPlaylist();
            playlistManager.deletePlaylist(selectedPlaylist.getId());
        }else {
            return;
        }
        try{
            allPlaylist = FXCollections.observableList(playlistManager.getPlaylist());
            tableViewLoadPlaylist(allPlaylist);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

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
                playlistManager.deleteFromPlaylist(selectedPlaylist.getId(), selectedSongOnPlaylist.getId());
                reloadSongsOnPlaylist();
                tvSongsOnPlaylist.getSelectionModel().select(index > 0 ? index - 1 : index);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addSongToPlaylistButton() throws SQLException {
        if (selectedSong != null)
            try {
                playlistManager.addSongToPlaylist(selectedPlaylist.getId(), selectedSong.getId());
                reloadSongsOnPlaylist();
                reloadPlaylistTable();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    public void btnUp(){
        System.out.println("testUp");
        if (selectedSongOnPlaylist != null){
            System.out.println("Works");
            try {
                int index = tvSongsOnPlaylist.getSelectionModel().getFocusedIndex() -1;
                int index1 = tvSongsOnPlaylist.getSelectionModel().getFocusedIndex() -0;
                tvSongsOnPlaylist.getSelectionModel().select(index);
                Collections.swap(songsOnPlaylist, index, index1);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public void btnDown() {
        System.out.println("testDown");
        if (selectedSongOnPlaylist != null) {
            System.out.println("Works");
            try {
                int index = tvSongsOnPlaylist.getSelectionModel().getFocusedIndex() + 1;
                int index1 = tvSongsOnPlaylist.getSelectionModel().getFocusedIndex() - 0;
                tvSongsOnPlaylist.getSelectionModel().select(index);
                Collections.swap(songsOnPlaylist, index, index1);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
    public void editPlaylist(String newTitle) {
        try {
            selectedPlaylist.setTitle(newTitle);
            playlistManager.editPlaylist(selectedPlaylist);
            reloadPlaylistTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

