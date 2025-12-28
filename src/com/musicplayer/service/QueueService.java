package com.musicplayer.service;

import com.musicplayer.model.Song;
import java.util.LinkedList;

public interface QueueService {
    void buildQueue(String keyword);
    void addToQueue(Song song);
    void addToQueue(String keyword);
    void removeFromQueue(int index);
    void clearQueue();
    void shuffleQueue();

    LinkedList<Song> getQueue();
    int getQueueSize();
}
