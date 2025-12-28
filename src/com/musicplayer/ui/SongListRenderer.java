package com.musicplayer.ui;

import javax.swing.*;
import java.awt.*;

public class SongListRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (index % 2 == 0) {
            label.setBackground(isSelected ? new Color(200, 220, 255) : new Color(240, 240, 240));
        } else {
            label.setBackground(isSelected ? new Color(200, 220, 255) : Color.WHITE);
        }

        label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return label;
    }
}
