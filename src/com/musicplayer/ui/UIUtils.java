package com.musicplayer.ui;

import javax.swing.JButton;
import java.awt.*;
import java.awt.image.BufferedImage;

public class UIUtils {

    private UIUtils() {
    }

    public static void styleControlButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(50, 30));
        button.setFocusPainted(false);
    }

    public static Image createDefaultIcon() {
        int size = 32;
        BufferedImage icon = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = icon.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK);
        g2d.fillOval(8, 4, 16, 16);
        g2d.fillRect(20, 12, 8, 16);
        g2d.dispose();
        return icon;
    }

    public static String formatTime(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return String.format("%d:%02d", minutes, secs);
    }
}
