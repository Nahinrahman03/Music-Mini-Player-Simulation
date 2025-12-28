package com.musicplayer.service;

import com.musicplayer.model.Song;
import java.util.List;

public interface LibraryService {
    void addSong(Song song);
    void removeSong(Song song);
    List<Song> getLibrary();
    int getLibrarySize();
}
