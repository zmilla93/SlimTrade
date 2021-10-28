package com.slimtrade.gui.dialogs.testing;

import com.slimtrade.gui.custom.CustomLabel;

import javax.swing.*;
import java.awt.*;

public class FontTestWindow extends JFrame {

    public FontTestWindow(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(800, 400);

        JPanel p1 = new JPanel();
        p1.setLayout(new BoxLayout(p1, BoxLayout.PAGE_AXIS));

        JPanel p2 = new JPanel();
        p2.setLayout(new BoxLayout(p2, BoxLayout.PAGE_AXIS));

        String[] text = new String[]{
                "Hello World",
                "Привет, мир (Cyrillic)",
                "สวัสดีชาวโลก (Thai)",
                "안녕하세요 세계 (Korean)",
                "你好，世界 (Chinese)",
                "こんにちは世界 (Japanese)",
                "한자 (Korean Hanja)",
                "한글 (Hangul)",
                "조선글 (Chosŏn'gŭl)",
                "片仮名、カタカナ (Katakana)",
                "平仮名, ひらがな (Hiragana)",
                "注音符號, 注音符号, ㄅㄆㄇㄈ (Bopomofo)",
        };

        p1.add(new JLabel("Default JLabels"));
        for (String s : text) {
            p1.add(new JLabel(s));
        }
        p2.add(new CustomLabel("Custom Labels"));
        for (String s : text) {
            p2.add(new CustomLabel(s));
        }
        p1.setBackground(Color.green);
        p2.setBackground(Color.yellow);

        Container container = getContentPane();
        container.setLayout(new FlowLayout());
        container.add(p1);
        container.add(p2);
    }

}
