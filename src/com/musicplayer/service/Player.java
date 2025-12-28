package com.musicplayer.service;

import com.musicplayer.model.PlaybackMode;
import com.musicplayer.model.Song;

import javax.swing.Timer;
import java.util.*;

public class Player implements PlaybackControl, QueueService, LibraryService {

    private final List<Song> library = new ArrayList<>();
    private final LinkedList<Song> queue = new LinkedList<>();
    private Song nowPlaying;
    private boolean isPlaying = false;
    private int currentPosition = 0; // in seconds
    private Timer playbackTimer;

    private PlaybackMode mode = PlaybackMode.NO_REPEAT;

    private final Map<String, Integer> playCount = new HashMap<>();

    @Override
    public void addSong(Song song) {
        if (song == null) return;
        library.add(song);
        library.sort(Comparator.comparing(Song::getTitle, String.CASE_INSENSITIVE_ORDER));
    }

    @Override
    public void removeSong(Song song) {
        if (song == null) return;
        library.remove(song);
        queue.remove(song);
    }

    @Override
    public List<Song> getLibrary() {
        return Collections.unmodifiableList(library);
    }

    @Override
    public int getLibrarySize() {
        return library.size();
    }

    @Override
    public void buildQueue(String keyword) {
        queue.clear();
        if (keyword == null || keyword.isEmpty()) return;
        for (Song s : library) {
            if (s.matches(keyword)) {
                queue.add(s);
            }
        }
    }

    @Override
    public void addToQueue(Song song) {
        if (song != null) {
            queue.add(song);
        }
    }

    @Override
    public void addToQueue(String keyword) {
        if (keyword == null || keyword.isEmpty()) return;
        for (Song s : library) {
            if (s.matches(keyword)) {
                queue.add(s);
                break;
            }
        }
    }

    @Override
    public void removeFromQueue(int index) {
        if (index >= 0 && index < queue.size()) {
            queue.remove(index);
        }
    }

    @Override
    public void clearQueue() {
        queue.clear();
    }

    @Override
    public void shuffleQueue() {
        Collections.shuffle(queue);
    }

    @Override
    public LinkedList<Song> getQueue() {
        return queue;
    }

    @Override
    public int getQueueSize() {
        return queue.size();
    }

    @Override
    public void play() {
        if (nowPlaying == null && !queue.isEmpty()) {
            nowPlaying = queue.peekFirst();
        }
        if (nowPlaying != null) {
            isPlaying = true;
            startPlaybackTimer();
            incrementPlayCount(nowPlaying.getTitle());
        }
    }

    @Override
    public void pause() {
        isPlaying = false;
        stopPlaybackTimer();
    }

    @Override
    public void stop() {
        isPlaying = false;
        currentPosition = 0;
        stopPlaybackTimer();
    }

    @Override
    public void playNext() {
        stop();
        if (queue.isEmpty()) {
            nowPlaying = null;
            return;
        }

        switch (mode) {
            case REPEAT_ONE:
                break;
            case REPEAT_ALL:
                Song head = queue.removeFirst();
                queue.addLast(head);
                nowPlaying = head;
                break;
            case NO_REPEAT:
                nowPlaying = queue.pollFirst();
                break;
            case SHUFFLE:
                int idx = (int) (Math.random() * queue.size());
                nowPlaying = queue.remove(idx);
                queue.addLast(nowPlaying);
                break;
            default:
                break;
        }

        currentPosition = 0;
        if (nowPlaying != null) {
            incrementPlayCount(nowPlaying.getTitle());
        }
    }

    @Override
    public void seek(int seconds) {
        if (nowPlaying != null) {
            currentPosition = Math.max(0, Math.min(seconds, nowPlaying.getDuration()));
        }
    }

    private void startPlaybackTimer() {
        stopPlaybackTimer();
        playbackTimer = new Timer(1000, e -> {
            if (isPlaying && nowPlaying != null) {
                currentPosition++;
                if (currentPosition >= nowPlaying.getDuration()) {
                    playNext();
                    if (isPlaying) {
                        play();
                    }
                }
            }
        });
        playbackTimer.start();
    }

    private void stopPlaybackTimer() {
        if (playbackTimer != null) {
            playbackTimer.stop();
            playbackTimer = null;
        }
    }

    private void incrementPlayCount(String title) {
        playCount.put(title, playCount.getOrDefault(title, 0) + 1);
    }

    public Song getNowPlaying() { return nowPlaying; }
    public int getCurrentPosition() { return currentPosition; }
    public Map<String, Integer> getPlayCount() { return Collections.unmodifiableMap(playCount); }

    @Override
    public boolean isPlaying() { return isPlaying; }

    @Override
    public PlaybackMode getMode() { return mode; }

    @Override
    public void setMode(PlaybackMode mode) {
        if (mode != null) {
            this.mode = mode;
        }
    }
}
