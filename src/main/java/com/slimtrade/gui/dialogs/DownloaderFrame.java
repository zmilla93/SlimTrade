package com.slimtrade.gui.dialogs;

import com.slimtrade.core.update.IDownloadTracker;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class DownloaderFrame extends JFrame implements IDownloadTracker {

    private Container border = this.getContentPane();
    private JPanel container = new JPanel(new GridBagLayout());
    private JProgressBar progressBar;
    private JLabel label = new JLabel();

    private GridBagConstraints gc = new GridBagConstraints();
    private boolean error = false;
    private final String DEFAULT_TEXT;

    public DownloaderFrame(String appName, String version) {

        setTitle(appName + " Patcher");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(300, 100));
        setAlwaysOnTop(true);
        setIconImage(new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("icons/default/tagx64.png"))).getImage());
        DEFAULT_TEXT = "Updating to Slimtrade " + version + "...";

        int i = 5;
        border.setLayout(new BorderLayout());
        border.add(Box.createVerticalStrut(i), BorderLayout.NORTH);
        border.add(Box.createVerticalStrut(i), BorderLayout.SOUTH);
        border.add(Box.createHorizontalStrut(i), BorderLayout.EAST);
        border.add(Box.createHorizontalStrut(i), BorderLayout.WEST);
        border.add(container);

        gc.gridx = 0;
        gc.gridy = 0;
        container.add(label, gc);
        gc.insets.left = 0;
        gc.gridx = 0;
        gc.gridy++;

        gc.weightx = 1;
        gc.fill = GridBagConstraints.BOTH;
        gc.gridwidth = 2;
        gc.insets.top = 5;
        progressBar = new JProgressBar();
        progressBar.setMaximum(100000);
        container.add(progressBar, gc);
        gc.gridy++;

        label.setText(DEFAULT_TEXT);

        pack();
    }

    public void setFileMessage(String text) {
        label.setText(text);
        pack();
    }

    public void setProgress(int progress) {
        progressBar.setValue(progress);
    }

    public void error(String text) {
        error(text, false);
    }

    public void error(String text, boolean bold) {
        if (!error) {
            container.removeAll();
            gc = new GridBagConstraints();
            gc.gridx = 0;
            gc.gridy = 0;
            error = true;
        }
        JLabel label = new JLabel(text);
        Font f = label.getFont();
        if (!bold) {
            label.setFont(f.deriveFont(f.getStyle() & ~Font.BOLD));
        }
        container.add(label, gc);

        gc.gridy++;
        pack();
    }

    public void addVerticalStrut() {
        int i = 10;
        container.add(Box.createVerticalStrut(i), gc);
        gc.gridy++;
    }

    @Override
    public void downloadPercentCallback(int progress) {
        SwingUtilities.invokeLater(() -> {
            setProgress(progress);
        });
        textCallback(DEFAULT_TEXT);
    }

    @Override
    public void textCallback(String message) {
        SwingUtilities.invokeLater(() -> {
            setFileMessage(message);
        });
    }

}
