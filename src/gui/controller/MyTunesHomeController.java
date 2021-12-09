package gui.controller;

import be.MusicPlayer;
import be.Playlist;
import be.Song;
import bll.PlaylistManager;
import bll.SongManager;
import dal.PlaylistDAO;
import dal.SongsDAO;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
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
    private Song songPlaying;
    private Stage stage = new Stage();

    private Playlist selectedPlaylist;

    private ObservableList<Song> allSongs = FXCollections.observableArrayList();
    private ObservableList<Playlist> allPlaylist = FXCollections.observableArrayList();
    private ObservableList<Song> songsOnPlaylist = FXCollections.observableArrayList();

    private PlaylistDAO playlistDAO = new PlaylistDAO();
    private SongsDAO songsDAO = new SongsDAO();
    private SongManager songManager = new SongManager();
    private MusicPlayer musicPlayer = new MusicPlayer();
    private PlaylistManager playlistManager = new PlaylistManager();

    private static boolean isPlaying = false;


    public MyTunesHomeController() throws Exception {
    }

    public SongManager getSongManager() {
        return songManager;
    }

    public void createNewSong(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/EditSongs.fxml"));
        stage.setTitle("New/Edit Song");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void editSong(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/EditSongs.fxml"));
        stage.setTitle("New/Edit Song");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void createPlaylist(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/EditPlaylist.fxml"));
        stage.setTitle("New/Edit Playlist");
        stage.setScene(new Scene(root));
        stage.show();
        stage.setOnHiding( event -> { try{
            allPlaylist = FXCollections.observableList(playlistDAO.getPlaylist());
            tableViewLoadPlaylist(allPlaylist);
        } catch (Exception e){
            e.printStackTrace();
        } });
    }

    public void editPlaylist(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/view/EditPlaylist.fxml"));
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

        tcName.setCellValueFactory(new PropertyValueFactory<>("title"));
        tcSongs.setCellValueFactory(new PropertyValueFactory<>("songs"));
        tcTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        try{
            allPlaylist = FXCollections.observableList(playlistDAO.getPlaylist());
            tableViewLoadPlaylist(allPlaylist);
        } catch (Exception e){
            e.printStackTrace();
        }

        selectedPlaylist();
        changeVolume();
        selectedSong();
    }

    public void seeSongsOnPlaylist(){
        //tcSongsOnPlaylist.setCellValueFactory(new PropertyValueFactory<>("Songs")); //THIS LINE DONT WORK
        try{
            songsOnPlaylist = FXCollections.observableList(playlistDAO.getSongsOnPlaylist(selectedPlaylist.getId()));
            tableViewLoadSongsOnPlaylist(songsOnPlaylist);
            List<Song> allPlaylist = playlistManager.getSongsOnPlaylist(selectedPlaylist.getId());
            System.out.println(allPlaylist);// Getting 123 from playlist in console
            songsOnPlaylist.equals(allPlaylist);
            System.out.println(songsOnPlaylist.equals(allPlaylist));
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



    public void currentPlayingSong(){
        LabelPlayerSong.setText("");
    }
    
    public void playButton(){
        if(selectedSong != null && !isPlaying) {
            songPlaying = selectedSong;
            musicPlayer.setSong(songPlaying);
            musicPlayer.play();
            LabelPlayerSong.setText(selectedSong.getTitle() + " is Playing");
            btnSongPlayer.setText("=");
            isPlaying = !isPlaying;
        } else if (songPlaying != null){
            musicPlayer.pause();
            btnSongPlayer.setText("▼");
            isPlaying = !isPlaying;
            LabelPlayerSong.setText(selectedSong.getTitle() + " is paused");
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
    public void reloadSongTable() {
        try {
            int index = tvSongs.getSelectionModel().getFocusedIndex();
            this.tvSongs.setItems(FXCollections.observableList(songManager.getSongs()));
            tvSongs.getSelectionModel().select(index);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void deletePlaylist(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("WARNING MESSAGE");
        alert.setHeaderText("Warning before you delete playlist");
        alert.setContentText("Are you sure you want to delete this playlist!?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            selectedPlaylist();
            playlistDAO.deletePlaylist(selectedPlaylist.getId());
        }else {
            return;
        }
        try{
            allPlaylist = FXCollections.observableList(playlistDAO.getPlaylist());
            tableViewLoadPlaylist(allPlaylist);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void songForward(ActionEvent actionEvent) throws IOException {
        int index = allSongs.indexOf(songPlaying) + 1;
        try{
            songPlaying = allSongs.get(index);
        }
        catch (Exception e){
            songPlaying = allSongs.get(0);
        }
        musicPlayer.pause();
        musicPlayer.setSong(songPlaying);
        musicPlayer.play();
        LabelPlayerSong.setText(songPlaying.getTitle() + " is Playing");
        btnSongPlayer.setText("=");
        isPlaying = true;
    }

    public void songBack(ActionEvent actionEvent) throws IOException {
        int index = allSongs.indexOf(songPlaying) - 1;
        try{
            songPlaying = allSongs.get(index);
        }
        catch (Exception e){
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
     * Edits the selected playlist.
     *
     * @param newTitle new title
     */
    public void editPlaylist(String newTitle) {
        try {
            selectedPlaylist.setTitle(newTitle);
            playlistManager.editPlaylist(selectedPlaylist);
            tableViewLoadPlaylist(allPlaylist);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
