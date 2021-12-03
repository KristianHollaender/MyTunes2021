package bll;

import be.Song;
import bll.util.SongSearcher;
import dal.SongsDAO;
import gui.controller.MyTunesHomeController;

import java.util.List;

public class SongManager {

    private SongsDAO songsDAO;
    private SongSearcher songSearcher;
    private MyTunesHomeController myTunesHomeController;

    public List<Song> getSongs() throws Exception {
        List<Song> allSongs = songsDAO.getSongs();
        return  allSongs;
    }

    public Song createSong(String title, String artist, float songLength, String category, String url) throws Exception {
        return songsDAO.createSong(title, artist, songLength, category, url);
    }

    public void deleteSong(int id) throws Exception {
        songsDAO.deleteSong(id);
    }

    public void editSong(Song song) throws Exception {
        songsDAO.editSong(song);
    }

    public List<Song> searchSongs(String query) throws Exception {
        List<Song> allSongs = getSongs();
        List<Song> searchResult = songSearcher.search(allSongs, query);
        return searchResult;
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
