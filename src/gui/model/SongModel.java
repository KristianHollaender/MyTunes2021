package gui.model;

import be.Song;
import bll.SongManager;
import java.io.IOException;
import java.util.List;

public class SongModel {

    private SongManager songManager;


    /**
     * Constructor for SongModel
     */
    public SongModel() throws IOException {
        songManager = new SongManager();
    }


    /**
     * Edits a song by selecting a song and using the method from SongsDAO.
     * @param song
     * @throws Exception
     */
    public void editSong(Song song) throws Exception {
        songManager.editSong(song);
    }

    /**
     * Gets the list of songs
     * @return
     * @throws Exception
     */
    public List<Song> getSongs() {
        List<Song> allSongs = songManager.getSongs();
        return  allSongs;
    }

    /**
     * Creates a song with a given title, artist, sonLength, category and url using the method from SongsDAO.
     * @param title
     * @param artist
     * @param songLength
     * @param category
     * @param url
     * @return
     * @throws Exception
     */
    public Song createSong(String title, String artist, double songLength, String category, String url) throws Exception {
        return songManager.createSong(title, artist, songLength, category, url);
    }

    /**
     * Deletes a song by taking the ID and using the method from SongsDAO.
     * @param id
     * @throws Exception
     */
    public void deleteSong(int id) throws Exception {
        songManager.deleteSong(id);
    }

    /**
     * Searching through a list of songs using the method from SongsDAO.
     * @param query
     * @return
     * @throws Exception
     */
    public List<Song> searchSong(String query) throws Exception {
        return songManager.searchSong(query);
    }
}
