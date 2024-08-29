package com.slimtrade.gui.development;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.slimtrade.App;
import com.slimtrade.core.enums.PathOfExileLeague;
import com.slimtrade.core.jna.NativeMouseAdapter;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.ninja.NinjaEndpoint;
import com.slimtrade.core.utility.DesignerCopyMonitor;
import com.slimtrade.core.utility.HttpRequester;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.ComponentPair;
import com.slimtrade.gui.listening.TextChangeListener;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.windows.CustomDialog;
import com.slimtrade.modules.stopwatch.Stopwatch;
import com.slimtrade.modules.updater.ZLogger;
import org.jnativehook.mouse.NativeMouseEvent;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * This is a window used only during development. It is used to get measurements for in game UI elements.
 * Measurements are recorded at 1920x1080 resolution, then scaled for resolution.
 * Also has some tools for fetching data from public APIs.
 * Data is saved in debug folder the SlimTrade settings directory.
 */
public class DesignerConfigWindow extends CustomDialog {

    private static final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    private static final String START_MONITOR_TEXT = "Start Copy Monitor";
    private static final String STOP_MONITOR_TEXT = "Stop Copy Monitor";

    private final JTextField inputX = createTextFiled(50);
    private final JTextField inputY = createTextFiled(50);
    private final JTextField inputWidth = createTextFiled(34);
    private final JTextField inputHeight = createTextFiled(34);

    private final JTextField inputCountX = createTextFiled(1);
    private final JTextField inputCountY = createTextFiled(1);
    private final JTextField inputOffsetX = createTextFiled(10);
    private final JTextField inputOffsetY = createTextFiled(10);

    private final JButton moveCellOnNextClickButton = new JButton("Move Cell On Next Click");
    private final JCheckBox squareCellCheckbox = new JCheckBox("Square");
    private final JButton copyMonitorButton = new JButton(START_MONITOR_TEXT);
    private final JButton copyTextButton = new JButton("Copy Text");
    private final JTextArea copyTextArea = new JTextArea(8, 20);
    private final JButton copyPositionAndOffsetButton = new JButton("Copy Position & Offset");
    private final JButton fetchNinjaButton = new JButton("Download Full Poe.Ninja Datasets");

    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() - 1);

    private boolean moveCellOnNextClick = false;
    private boolean runCopyMonitor = false;

    public DesignerConfigWindow() {
        super("Designer Window");
        setFocusable(true);
        setFocusableWindowState(true);
        squareCellCheckbox.setSelected(true);
        inputHeight.setEnabled(false);

        JPanel positionPanel = new JPanel();
        positionPanel.add(new ComponentPair(new JLabel("X"), inputX));
        positionPanel.add(new ComponentPair(new JLabel("Y"), inputY));
        JPanel sizePanel = new JPanel();
        sizePanel.add(new ComponentPair(new JLabel("Width"), inputWidth));
        sizePanel.add(new ComponentPair(new JLabel("Height"), inputHeight));
        sizePanel.add(squareCellCheckbox);
        JPanel countPanel = new JPanel();
        countPanel.add(new ComponentPair(new JLabel("Count X"), inputCountX));
        countPanel.add(new ComponentPair(new JLabel("Count Y"), inputCountY));
        JPanel offsetPanel = new JPanel();
        offsetPanel.add(new ComponentPair(new JLabel("Offset X"), inputOffsetX));
        offsetPanel.add(new ComponentPair(new JLabel("Offset Y"), inputOffsetY));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(positionPanel);
        panel.add(sizePanel);
        panel.add(countPanel);
        panel.add(offsetPanel);
        panel.add(copyPositionAndOffsetButton);
        panel.add(moveCellOnNextClickButton);
        panel.add(copyMonitorButton);
        panel.add(copyTextButton);
        panel.add(fetchNinjaButton);
        panel.add(new JScrollPane(copyTextArea));
        contentPanel.add(panel);
        setMinimumSize(null);
        pack();
        applyProperties();
        addListeners();
    }

    private void addListeners() {
        fetchNinjaButton.addActionListener(e -> installNinjaDataset());
        copyPositionAndOffsetButton.addActionListener(e -> {
            String value = "# " + inputX.getText() + ", " + inputY.getText() + "\n" + "spacing: " + inputOffsetX.getText() + ", " + inputOffsetY.getText();
            clipboard.setContents(new StringSelection(value), null);
        });
        copyTextButton.addActionListener(e -> clipboard.setContents(new StringSelection(copyTextArea.getText()), null));
        copyMonitorButton.addActionListener(e -> {
            runCopyMonitor = !runCopyMonitor;
            if (runCopyMonitor) {
                DesignerCopyMonitor.start(copyTextArea);
                copyMonitorButton.setText(STOP_MONITOR_TEXT);
            } else {
                DesignerCopyMonitor.stop();
                copyMonitorButton.setText(START_MONITOR_TEXT);
            }
        });
        moveCellOnNextClickButton.addActionListener(e -> moveCellOnNextClick = true);
        inputWidth.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (squareCellCheckbox.isSelected()) inputHeight.setText(inputWidth.getText());
            }
        });
        inputWidth.getDocument().addDocumentListener(new TextChangeListener() {
            @Override
            public void onTextChange(DocumentEvent e) {
                if (squareCellCheckbox.isSelected()) inputHeight.setText(inputWidth.getText());
            }
        });
        squareCellCheckbox.addActionListener(e -> {
            inputHeight.setEnabled(!squareCellCheckbox.isSelected());
            if (squareCellCheckbox.isSelected()) {
                inputHeight.setText(inputWidth.getText());
            }
        });
        App.globalMouseListener.addMouseAdapter(new NativeMouseAdapter() {
            @Override
            public void nativeMousePressed(NativeMouseEvent nativeMouseEvent) {
                if (moveCellOnNextClick) {
                    Point mousePos = nativeMouseEvent.getPoint();
                    Point windowPos = FrameManager.stashAlignmentDesignerWindow.getLocationOnScreen();
                    Point result = new Point(mousePos.x - windowPos.x, mousePos.y - windowPos.y);
                    inputX.setText(result.x + "");
                    inputY.setText(result.y + "");
                    moveCellOnNextClick = false;
                }
                if (runCopyMonitor && nativeMouseEvent.getButton() == 3) DesignerCopyMonitor.lineBreak();
            }
        });
    }

    private void installNinjaDataset() {
        ZLogger.log("Downloading all datasets from poe.ninja, saving to '" + SaveManager.getDebugDirectory() + "'...");
        fetchNinjaButton.setEnabled(false);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Stopwatch.start();
        for (NinjaEndpoint endpoint : NinjaEndpoint.values()) {
            for (PathOfExileLeague league : PathOfExileLeague.values()) {
                if (league == PathOfExileLeague.SSF_BTW) continue;
                executor.submit(() -> {
                    String url = endpoint.getURL(league);
                    String value = HttpRequester.getPageContents(url);
                    BufferedWriter writer = ZUtil.getBufferedWriter(SaveManager.getDebugDirectory() + league + File.separator + endpoint.type + ".txt");
                    try {
                        JsonElement json = JsonParser.parseString(value);
                        writer.write(gson.toJson(json));
                        writer.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
        new Thread(() -> {
            executor.shutdown();
            try {
                if (executor.awaitTermination(10, TimeUnit.SECONDS))
                    System.out.println("Datasets downloaded successfully in " + Stopwatch.getElapsedSeconds() + " seconds.");
                else
                    System.out.println("Failed to download datasets.");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void applyProperties() {
        try {
            int x = Integer.parseInt(inputX.getText());
            int y = Integer.parseInt(inputY.getText());
            int width = Integer.parseInt(inputWidth.getText());
            int height = Integer.parseInt(inputHeight.getText());
            int countX = Integer.parseInt(inputCountX.getText());
            int countY = Integer.parseInt(inputCountY.getText());
            int offsetX = Integer.parseInt(inputOffsetX.getText());
            int offsetY = Integer.parseInt(inputOffsetY.getText());
            FrameManager.stashAlignmentDesignerWindow.getStashAlignmentDesigner().applyOffsets(countX, offsetX, countY, offsetY);
            FrameManager.stashAlignmentDesignerWindow.getStashAlignmentDesigner().applyProperties(x, y, width, height);
        } catch (NumberFormatException ignore) {
            // Do nothing
        }
    }

    private JTextField createTextFiled(int initialValue) {
        JTextField textField = new JTextField(Integer.toString(initialValue), 3);
        textField.getDocument().addDocumentListener(new TextChangeListener() {
            @Override
            public void onTextChange(DocumentEvent e) {
                applyProperties();
            }
        });
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                int adjust;
                if (keyCode == KeyEvent.VK_UP) adjust = 1;
                else if (keyCode == KeyEvent.VK_DOWN) adjust = -1;
                else return;
                int value;
                try {
                    value = Integer.parseInt(textField.getText());
                } catch (NumberFormatException ignore) {
                    return;
                }
                value += adjust;
                textField.setText(Integer.toString(value));
            }
        });
        return textField;
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        FrameManager.stashAlignmentDesignerWindow.setVisible(b);
    }

}
