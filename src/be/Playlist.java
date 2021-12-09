package be;

import java.sql.Time;

public class Playlist {

    private int id;
    private String title;
    private int songs;
    private double time;

    /**
     * Constructor id, title, songs, and time
     * @param id
     * @param title
     * @param songs
     * @param time
     */
    public Playlist(int id, String title, int songs, double time){
        this.id = id;
        this.title = title;
        this.songs = songs;
        this.time = time;
    }

    /**
     * Constructor with id and title
     * @param id
     * @param title
     */
    public Playlist(int id, String title){
        this.id = id;
        this.title = title;
    }

    /**
     * Sets the value of id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the value of id
     * return
     */
    public int getId() {
        return id;
    }

    /**
     * Sets value of title
     * @param title
     */
    public void setTitle(String title) {

        this.title = title;
    }

    /**
     *Gets value of title
     * return
     */
    public String getTitle() {
        return title;
    }

    /**
     *Sets value of songs
     * @param songs
     */
    public void setSongs(int songs) {
        this.songs = songs;
    }

    /**
     *Gets value of songs
     * return
     */
    public int getSongs() {
        return songs;
    }

    /**
     *Sets value of time
     * @param time
     */
    public void setTime(double time) {
        this.time = time;
    }

    /**
     *Gets value of time
     * return
     */
    public double getTime() {
        return time;
    }
}

