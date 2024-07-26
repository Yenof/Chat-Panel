package com.chatpanel;

import net.runelite.client.util.ImageUtil;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class ChatPanelSearch {
    public static class SearchWindow extends JDialog {
        private final JTabbedPane tabbedPane;
        private int currentIndex = -1;
        private final String originalTitle;

        public SearchWindow(JFrame popoutFrame, JTabbedPane tabbedPane, int tabIndex, Container panelLocation) {

            this.tabbedPane = tabbedPane;
            String tabName = tabbedPane.getTitleAt(tabIndex);
            originalTitle = "Search " + tabName;
            setTitle(originalTitle);
            setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            setSize(250, 100);
            setAlwaysOnTop(true);
            if (popoutFrame.isVisible()) {
                Point popoutFrameLocation = popoutFrame.getLocation();
                int X = popoutFrameLocation.x + 10;
                int Y = popoutFrameLocation.y;
                setLocation(X, Y);
            } else {
                Point parentLocation = panelLocation.getLocationOnScreen();
                int x = parentLocation.x;
                int y = parentLocation.y - 20;
                setLocation(x, y);
            }
            if (!isUndecorated()){
                setIconImage(ImageUtil.loadImageResource(getClass(), "/ChatPanelimg.png"));
            }

            JPanel searchPanel = new JPanel();
            searchPanel.setLayout(new BorderLayout());
            getContentPane().add(searchPanel, BorderLayout.CENTER);

            JPanel searchBox = new JPanel();
            searchBox.setLayout(new FlowLayout());
            JTextField findField = new JTextField(15);
            searchBox.add(findField);
            searchPanel.add(searchBox, BorderLayout.NORTH);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout());
            JButton findLastButton = new JButton("Find Last");
            buttonPanel.add(findLastButton);
            JButton findNextButton = new JButton("Find Next");
            buttonPanel.add(findNextButton);
            searchPanel.add(buttonPanel, BorderLayout.SOUTH);

            findField.addActionListener(e -> {
                try {
                    find(findField.getText(), tabIndex, true);
                } catch (BadLocationException ignored) {}
            });
            findNextButton.addActionListener(e -> {
                try {
                    find(findField.getText(), tabIndex, true);
                } catch (BadLocationException ignored) {}
            });
            findLastButton.addActionListener(e -> {
                try {
                    find(findField.getText(), tabIndex, false);
                } catch (BadLocationException ignored) {}
            });
            addWindowFocusListener(new WindowFocusListener() {
                @Override
                public void windowGainedFocus(WindowEvent ignored) {}
                @Override
                public void windowLostFocus(WindowEvent e) {
                    Component tabComponent = tabbedPane.getComponentAt(tabIndex);
                        if (tabComponent instanceof JScrollPane) {
                            JTextPane chatArea = (JTextPane) ((JScrollPane) tabComponent).getViewport().getView();
                            DefaultHighlighter highlighter = (DefaultHighlighter) chatArea.getHighlighter();
                            highlighter.removeAllHighlights();
                        }
                        dispose();
                    }
                });
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    Component tabComponent = tabbedPane.getComponentAt(tabIndex);
                    if (tabComponent instanceof JScrollPane) {
                        JTextPane chatArea = (JTextPane) ((JScrollPane) tabComponent).getViewport().getView();
                        DefaultHighlighter highlighter = (DefaultHighlighter) chatArea.getHighlighter();
                        highlighter.removeAllHighlights();
                    }
                }
            });
        }

        private void find(String searchText, int tabIndex, boolean direction) throws BadLocationException {
            Component tabComponent = tabbedPane.getComponentAt(tabIndex);
            if (tabComponent instanceof JScrollPane) {
                JTextPane chatArea = (JTextPane) ((JScrollPane) tabComponent).getViewport().getView();
                DefaultHighlighter highlighter = (DefaultHighlighter) chatArea.getHighlighter();
                highlighter.removeAllHighlights();
                String text = chatArea.getDocument().getText(0, chatArea.getDocument().getLength());
                int newIndex = getNewIndex(searchText, direction, text);
                if (newIndex != -1) {
                    highlight(chatArea, newIndex, searchText.length());
                    chatArea.setCaretPosition(newIndex);
                    Rectangle rect = (Rectangle) chatArea.modelToView2D(newIndex);
                    chatArea.scrollRectToVisible(rect);
                    setTitle(originalTitle);
                } else {
                    setTitle("No Match");
                }
                currentIndex = newIndex;
            }
        }

        private int getNewIndex(String searchText, boolean direction, String text) {
            int startIndex = direction ? currentIndex + 1 : currentIndex - searchText.length();
            if (startIndex < 0) {
                startIndex = direction ? 0 : text.length() - searchText.length();
            }
            int newIndex = direction ? text.toLowerCase().indexOf(searchText.toLowerCase(), startIndex) : text.toLowerCase().lastIndexOf(searchText.toLowerCase(), startIndex);
            if (newIndex == -1) { // Loop back to the start/end of the document
                newIndex = direction ? text.toLowerCase().indexOf(searchText.toLowerCase()) : text.toLowerCase().lastIndexOf(searchText.toLowerCase());
            }
            return newIndex;
        }

        private void highlight(JTextPane chatArea, int startIndex, int length) throws BadLocationException {
            Color textColor = chatArea.getForeground();
            Color oppositeColor = getOppositeColor(textColor);
            DefaultHighlighter highlighter = (DefaultHighlighter) chatArea.getHighlighter();
            highlighter.addHighlight(startIndex, startIndex + length, new DefaultHighlighter.DefaultHighlightPainter(oppositeColor));
        }

        private Color getOppositeColor(Color color) {
            int red = 255 - color.getRed();
            int green = 255 - color.getGreen();
            int blue = 255 - color.getBlue();
            return new Color(red, green, blue);
        }
    }
}
