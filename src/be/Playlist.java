package be;

import java.sql.Time;

public class Playlist {

    private String name;
    private int songs;
    private double time;

    public Playlist(String name, int songs, double time){
        this.name = name;
        this.songs = songs;
        this.time = time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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
                "name='" + name + '\'' +
                ", songs=" + songs +
                ", time=" + time +
                '}';
    }
}

