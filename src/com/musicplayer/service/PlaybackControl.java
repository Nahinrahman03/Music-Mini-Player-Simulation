package com.musicplayer.service;

import com.musicplayer.model.PlaybackMode;

public interface PlaybackControl {
    void play();
    void pause();
    void stop();
    void playNext();
    void seek(int seconds);

    boolean isPlaying();
    PlaybackMode getMode();
    void setMode(PlaybackMode mode);
}
