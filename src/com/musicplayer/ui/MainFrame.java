package com.musicplayer.ui;

import com.musicplayer.model.PlaybackMode;
import com.musicplayer.model.Song;
import com.musicplayer.service.Player;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Map;
import java.util.Random;

public class MainFrame extends JFrame {

    private final Player player = new Player();

    private final DefaultListModel<String> queueModel = new DefaultListModel<>();
    private final DefaultListModel<String> libraryModel = new DefaultListModel<>();
    private final JList<String> queueJList = new JList<>(queueModel);
    private final JList<String> libraryJList = new JList<>(libraryModel);

    private final JLabel nowPlayingLabel = new JLabel("No song playing", SwingConstants.CENTER);
    private final JLabel timeLabel = new JLabel("0:00 / 0:00", SwingConstants.CENTER);
    private final JProgressBar progressBar = new JProgressBar();
    private final JComboBox<PlaybackMode> modeBox = new JComboBox<>(PlaybackMode.values());
    private final JButton playPauseBtn = new JButton("▶");
    private final JButton stopBtn = new JButton("⏹");
    private final JButton nextBtn = new JButton("⏭");

    private final JLabel statsLabel = new JLabel("Songs: 0 | Queue: 0", SwingConstants.CENTER);

    public MainFrame() {
        super("\uD83C\uDFB5 Advanced Music Player");
        initializePlayer();
        buildModernUI();
        setSize(800, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initializePlayer() {
        player.addSong(new Song("Neon Dreams", "Anya Sharma", "Electric Nights", 245, "Synthpop", ""));
        player.addSong(new Song("Starlight Serenade", "Luna Park", "Cosmic Journey", 193, "Ambient", ""));
        player.addSong(new Song("Cosmic Waltz", "Orion", "Space Odyssey", 321, "Electronic", ""));
        player.addSong(new Song("Midnight Echoes", "DJ Qnight", "Nocturnal", 276, "House", ""));
        player.addSong(new Song("Echoes of Tomorrow", "Stellar", "Future Memories", 234, "Chillout", ""));
        player.addSong(new Song("Electric Sky", "Neon Pulse", "Digital Dreams", 198, "EDM", ""));
        player.addSong(new Song("Moonlit Sonata", "Anya Sharma", "Piano Reflections", 287, "Classical", ""));
        player.addSong(new Song("Urban Jungle", "Metro Beats", "City Lights", 312, "Hip Hop", ""));
        player.addSong(new Song("Ocean Breeze", "Coastal Waves", "Natural Sounds", 256, "Ambient", ""));

        refreshLibraryModel();
    }

    private void buildModernUI() {
        setLayout(new BorderLayout(10, 10));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));

        setIconImage(UIUtils.createDefaultIcon());

        JPanel topPanel = createNowPlayingPanel();
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = createCenterPanel();
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = createBottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);

        new Timer(1000, e -> updatePlaybackInfo()).start();
    }

    private JPanel createNowPlayingPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Now Playing"));

        nowPlayingLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

        progressBar.setStringPainted(true);

        timeLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        UIUtils.styleControlButton(playPauseBtn);
        UIUtils.styleControlButton(stopBtn);
        UIUtils.styleControlButton(nextBtn);

        playPauseBtn.addActionListener(e -> togglePlayPause());
        stopBtn.addActionListener(e -> {
            player.stop();
            playPauseBtn.setText("\u25B6");
        });
        nextBtn.addActionListener(e -> {
            player.playNext();
            if (player.isPlaying()) {
                player.play();
                playPauseBtn.setText("\u23F8");
            }
        });

        controlPanel.add(playPauseBtn);
        controlPanel.add(stopBtn);
        controlPanel.add(nextBtn);

        panel.add(nowPlayingLabel, BorderLayout.NORTH);
        panel.add(progressBar, BorderLayout.CENTER);
        panel.add(timeLabel, BorderLayout.SOUTH);
        panel.add(controlPanel, BorderLayout.EAST);

        return panel;
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 15, 0));

        JPanel libPanel = createLibraryPanel();
        JPanel queuePanel = createQueuePanel();

        panel.add(libPanel);
        panel.add(queuePanel);

        return panel;
    }

    private JPanel createLibraryPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Music Library"));

        JPanel searchPanel = new JPanel(new BorderLayout(5, 0));
        JTextField searchField = new JTextField();
        JButton searchBtn = new JButton("Search");

        searchBtn.addActionListener(e -> {
            String query = searchField.getText();
            if (query != null && !query.trim().isEmpty()) {
                filterLibrary(query.trim());
            } else {
                refreshLibraryModel();
            }
        });

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchBtn, BorderLayout.EAST);

        libraryJList.setCellRenderer(new SongListRenderer());
        libraryJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        libraryJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int index = libraryJList.locationToIndex(evt.getPoint());
                    if (index >= 0 && index < player.getLibrary().size()) {
                        Song selected = player.getLibrary().get(index);
                        player.addToQueue(selected);
                        refreshQueueModel();
                    }
                }
            }
        });

        JPanel libControls = new JPanel(new GridLayout(1, 3, 5, 0));
        JButton addToQueueBtn = new JButton("Add to Queue");
        JButton showStatsBtn = new JButton("Statistics");
        JButton addFilesBtn = new JButton("Add Files");

        addToQueueBtn.addActionListener(e -> {
            int index = libraryJList.getSelectedIndex();
            if (index >= 0 && index < player.getLibrary().size()) {
                player.addToQueue(player.getLibrary().get(index));
                refreshQueueModel();
            }
        });

        showStatsBtn.addActionListener(e -> showStatistics());

        addFilesBtn.addActionListener(e -> addOfflineFiles());

        libControls.add(addToQueueBtn);
        libControls.add(showStatsBtn);
        libControls.add(addFilesBtn);

        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(libraryJList), BorderLayout.CENTER);
        panel.add(libControls, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createQueuePanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Play Queue"));

        queueJList.setCellRenderer(new SongListRenderer());
        queueJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel queueControls = new JPanel(new GridLayout(2, 2, 5, 5));

        JButton removeBtn = new JButton("Remove");
        JButton clearBtn = new JButton("Clear Queue");
        JButton shuffleBtn = new JButton("Shuffle");
        JButton buildQueueBtn = new JButton("Build Queue");

        removeBtn.addActionListener(e -> {
            int index = queueJList.getSelectedIndex();
            if (index >= 0) {
                player.removeFromQueue(index);
                refreshQueueModel();
            }
        });

        clearBtn.addActionListener(e -> {
            player.clearQueue();
            refreshQueueModel();
        });

        shuffleBtn.addActionListener(e -> {
            player.shuffleQueue();
            refreshQueueModel();
        });

        buildQueueBtn.addActionListener(e -> {
            String keyword = JOptionPane.showInputDialog(this, "Enter keyword to build queue:");
            if (keyword != null && !keyword.trim().isEmpty()) {
                player.buildQueue(keyword.trim());
                refreshQueueModel();
            }
        });

        queueControls.add(removeBtn);
        queueControls.add(clearBtn);
        queueControls.add(shuffleBtn);
        queueControls.add(buildQueueBtn);

        panel.add(new JScrollPane(queueJList), BorderLayout.CENTER);
        panel.add(queueControls, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.add(new JLabel("Playback Mode:"));
        modeBox.setSelectedItem(player.getMode());
        modeBox.addActionListener(e -> player.setMode((PlaybackMode) modeBox.getSelectedItem()));
        leftPanel.add(modeBox);

        statsLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));

        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(statsLabel, BorderLayout.EAST);

        return panel;
    }

    private void togglePlayPause() {
        if (player.isPlaying()) {
            player.pause();
            playPauseBtn.setText("\u25B6");
        } else {
            player.play();
            playPauseBtn.setText("\u23F8");
        }
    }

    private void updatePlaybackInfo() {
        if (player.getNowPlaying() != null) {
            Song current = player.getNowPlaying();
            nowPlayingLabel.setText(current.getDetail());

            int progress = (int) ((double) player.getCurrentPosition() / current.getDuration() * 100);
            progressBar.setValue(progress);
            progressBar.setString(progress + "%");

            String currentTime = UIUtils.formatTime(player.getCurrentPosition());
            String totalTime = current.getDurationFormatted();
            timeLabel.setText(currentTime + " / " + totalTime);
        } else {
            nowPlayingLabel.setText("No song playing");
            progressBar.setValue(0);
            progressBar.setString("0%");
            timeLabel.setText("0:00 / 0:00");
        }

        statsLabel.setText(String.format("Songs: %d | Queue: %d",
                player.getLibrarySize(), player.getQueueSize()));
    }

    private void refreshLibraryModel() {
        libraryModel.clear();
        for (Song song : player.getLibrary()) {
            libraryModel.addElement(song.toString() + " (" + song.getDurationFormatted() + ")");
        }
    }

    private void refreshQueueModel() {
        queueModel.clear();
        for (Song song : player.getQueue()) {
            queueModel.addElement(song.toString() + " (" + song.getDurationFormatted() + ")");
        }
    }

    private void filterLibrary(String query) {
        libraryModel.clear();
        for (Song song : player.getLibrary()) {
            if (song.matches(query)) {
                libraryModel.addElement(song.toString() + " (" + song.getDurationFormatted() + ")");
            }
        }
    }

    private void showStatistics() {
        Map<String, Integer> playCount = player.getPlayCount();
        StringBuilder stats = new StringBuilder("Play Statistics:\n\n");

        if (playCount.isEmpty()) {
            stats.append("No songs played yet.");
        } else {
            for (Map.Entry<String, Integer> entry : playCount.entrySet()) {
                stats.append(entry.getKey()).append(": ").append(entry.getValue()).append(" plays\n");
            }
        }

        JOptionPane.showMessageDialog(this, stats.toString(), "Music Statistics",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void addOfflineFiles() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Music Files");
        fileChooser.setMultiSelectionEnabled(true);

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Audio Files", "mp3", "wav", "aiff", "aif", "au", "m4a");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File[] files = fileChooser.getSelectedFiles();
            int addedCount = 0;

            for (File file : files) {
                if (isAudioFile(file)) {
                    addFileToLibrary(file);
                    addedCount++;
                }
            }

            refreshLibraryModel();
            JOptionPane.showMessageDialog(this,
                    "Added " + addedCount + " song(s) to library",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private boolean isAudioFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".mp3") || name.endsWith(".wav") ||
                name.endsWith(".aiff") || name.endsWith(".aif") ||
                name.endsWith(".au") || name.endsWith(".m4a");
    }

    private void addFileToLibrary(File file) {
        String fileName = file.getName();
        String title = removeFileExtension(fileName);
        String artist = "Unknown Artist";
        String album = "Unknown Album";
        String genre = "Unknown Genre";
        int duration = 180 + new Random().nextInt(300);

        Song newSong = new Song(title, artist, album, duration, genre, file.getAbsolutePath());
        player.addSong(newSong);
    }

    private String removeFileExtension(String filename) {
        int lastDot = filename.lastIndexOf('.');
        if (lastDot == -1) return filename;
        return filename.substring(0, lastDot);
    }
}
