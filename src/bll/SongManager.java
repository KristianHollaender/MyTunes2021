package bll;

import be.Song;
import dal.SongsDAO;
import gui.controller.MyTunesHomeController;

import java.io.IOException;
import java.util.List;

public class SongManager {

    private SongsDAO songsDAO = new SongsDAO();
    private MyTunesHomeController myTunesHomeController;

    public SongManager() throws IOException {
    }

    public List<Song> getSongs() throws Exception {
        List<Song> allSongs = songsDAO.getSongs();
        return  allSongs;
    }

    public Song createSong(String title, String artist, double songLength, String category, String url) throws Exception {
        return songsDAO.createSong(title, artist, songLength, category, url);
    }

    public void deleteSong(int id) throws Exception {
        songsDAO.deleteSong(id);
    }

    public void editSong(Song song) throws Exception {
        songsDAO.editSong(song);
    }

    public List<Song> searchSongs(String query) throws Exception {
        return songsDAO.searchSong(query);
    }


    public void setMyTunesHomeController(MyTunesHomeController myTunesHomeController) {
        this.myTunesHomeController = myTunesHomeController;
    }

    public static void main(String[] args) throws Exception {
        SongManager songManager = new SongManager();

        List<Song> allSong = songManager.getSongs();

        System.out.println(allSong);
    }
}
