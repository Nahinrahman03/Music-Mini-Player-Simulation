package com.musicplayer.model;

public class Song {

    private final String title;
    private final String artist;
    private final String album;
    private final int duration; // in seconds
    private final String genre;
    private final String filePath;

    public Song(String title, String artist, String album, int duration, String genre, String filePath) {
        this.title = title != null ? title.trim() : "";
        this.artist = artist != null ? artist.trim() : "";
        this.album = album != null ? album.trim() : "";
        this.duration = duration;
        this.genre = genre != null ? genre.trim() : "";
        this.filePath = filePath;
    }

    public boolean matches(String keyword) {
        if (keyword == null || keyword.isEmpty()) return false;
        String searchText = (title + " " + artist + " " + album + " " + genre).toLowerCase();
        return searchText.contains(keyword.toLowerCase());
    }

    public String getDetail() {
        return String.format("\"%s\"  –  %s  (%s)", title, artist, album);
    }

    public String getDurationFormatted() {
        int minutes = duration / 60;
        int seconds = duration % 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    @Override
    public String toString() {
        return String.format("%s - %s", title, artist);
    }

    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public String getAlbum() { return album; }
    public String getGenre() { return genre; }
    public int getDuration() { return duration; }
    public String getFilePath() { return filePath; }
}
