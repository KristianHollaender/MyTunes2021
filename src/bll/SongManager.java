package bll;

import be.Song;
import dal.SongsDAO;

import java.util.List;

public class SongManager {

    private SongsDAO songsDAO;

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


}
