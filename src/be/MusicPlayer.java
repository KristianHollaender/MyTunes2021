package be;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class MusicPlayer {

    protected MediaPlayer mediaPlayer;
    protected Media media;
    protected Song song;

    /**
     * Get the value of Media.
     * @return the value of Media.
     */
    public Media getMedia() {
        return media;
    }

    /**
     * Get the id of song
     * @return the value of song.
     */
    public Song getSong() {
        return song;
    }

    /**
     * Set the value of song
     * @param song new value of song
     */
    public void setSong(Song song) {
        if (song != null && this.song != song) {
            this.song = song;
            if (!song.getUrl().isBlank()) {
                media = new Media(new File(song.getUrl()).toURI().toString());
                mediaPlayer = new MediaPlayer(media);
            }
        }
    }

    /**
     * Get the id of Media player
     * @return the value of Media player
     */
    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }



    /**
     * If possible, make it play.
     */
    public void play() {
        if (mediaPlayer != null)
            mediaPlayer.play();
    }

    /**
     * If possible, make it pause.
     */
    public void pause() {
        if (mediaPlayer != null)
            mediaPlayer.pause();
    }


    /**
     * Get the value of Volume.
     * @return the value of Volume.
     */
    public double getVolume() {
        if (mediaPlayer != null)
            return mediaPlayer.getVolume();
        return 100;
    }

    /**
     * Set the value of Volume
     * @param volume new value of volume
     */
    public void setVolume(double volume) {
        if (mediaPlayer != null)
            mediaPlayer.setVolume(volume);
    }
}