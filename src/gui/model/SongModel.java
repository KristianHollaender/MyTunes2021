package gui.model;

import be.Song;
import dal.SongsDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;

public class SongModel {

    private ObservableList<Song> allSongs = FXCollections.observableArrayList();
    SongsDAO songsDAO = new SongsDAO();


    public SongModel() throws IOException {
    }

    private ObservableList<Song> getAllSong(){
        allSongs = FXCollections.observableArrayList();
        allSongs.addAll(Song)
    }
}
