package be;

import java.sql.Time;

public class Playlist {

    private int id;
    private String title;
    private int songs;
    private double time;

    public Playlist(int id, String title, int songs, double time){
        this.id = id;
        this.title = title;
        this.songs = songs;
        this.time = time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setSongs(int songs) {
        this.songs = songs;
    }

    public int getSongs() {
        return songs;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "title ='" + title + '\'' +
                ", songs=" + songs +
                ", time=" + time +
                '}';
    }
}

