package com.chatpanel;

import net.runelite.client.ui.PluginPanel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.text.BadLocationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatPanelSidebar extends PluginPanel {
    private final JTextArea publicChatArea;
    private final JTextArea privateChatArea;
    private final JTextArea clanChatArea;
    private static final int MAX_CHAT_LINES = 10000;
    private final JTextArea gameChatArea;
    private final JTabbedPane tabbedPane;
    private final ChatPanelConfig config;
    private static final Logger logger = LoggerFactory.getLogger(ChatPanelSidebar.class);
    private boolean isPopout = false;
    private JFrame popoutFrame;
    private final JButton popoutButton;
    private JButton popinButton;
    private final JButton popinButton2;
    public ChatPanelSidebar(ChatPanelConfig config) {
        this.config = config;
        setLayout(new BorderLayout());
        popoutButton = new JButton("Pop out");
        popoutButton.addActionListener(e -> togglePopout());
        add(popoutButton, BorderLayout.SOUTH);

        popinButton2 = new JButton("Pop in");
        popinButton2.addActionListener(e -> togglePopout());
        popinButton2.setVisible(true);
        add(popinButton2, BorderLayout.SOUTH);

        publicChatArea = createChatArea();
        privateChatArea = createChatArea();
        clanChatArea = createChatArea();
        gameChatArea = createChatArea();

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Public", createScrollPane(publicChatArea));
        tabbedPane.addTab("Private", createScrollPane(privateChatArea));
        tabbedPane.addTab("Clan", createScrollPane(clanChatArea));
        tabbedPane.addTab("Game", createScrollPane(gameChatArea));

        add(tabbedPane, BorderLayout.CENTER);
        updateChatStyles();
    }
    private void togglePopout() {
        if (isPopout) {
            // Restore to side panel
            isPopout = false;
            popoutFrame.dispose();
            addComponentsForSidePanel();
            add(tabbedPane, BorderLayout.CENTER);
            updateChatStyles();
        } else {
            isPopout = true;
            popoutFrame = new JFrame("Chat Panel");
            popoutFrame.add(tabbedPane);
            addComponentsForPopout();
            popoutFrame.setSize(300, 400);
            popoutFrame.setMinimumSize(new Dimension(40, 10));
            popoutFrame.setLocationRelativeTo(null);
            popoutFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            if (config.popoutAlwaysOnTop()) {
                popoutFrame.setAlwaysOnTop(true);
            }
            popoutFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    togglePopout();
                }
            });

            popoutFrame.setVisible(true);
        }

    }

    private void addComponentsForSidePanel() {
        if (popoutButton != null) {
            popoutButton.setVisible(true);
        }
        if (popinButton != null) {
            popinButton.setVisible(false);
            add(popoutButton, BorderLayout.SOUTH);
            remove(popinButton);
        }
        if (popinButton2 != null) {
            popinButton2.setVisible(false);
        }
    }

    private void addComponentsForPopout() {
        popinButton = new JButton("Pop In");
        popinButton.addActionListener(e -> togglePopout());
        popoutFrame.add(popinButton, BorderLayout.SOUTH);
        remove(popoutButton);
        if (popinButton2 != null) {
            popinButton2.setVisible(true);
            add(popinButton2, BorderLayout.SOUTH);
        }
    }
    private JTextArea createChatArea() {
        JTextArea chatArea = new JTextArea();
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setEditable(false);
        chatArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        return chatArea;
    }

    private JScrollPane createScrollPane(JTextArea chatArea) {
        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        setScrollPaneSize(scrollPane);
        return scrollPane;
    }

    private void setScrollPaneSize(JScrollPane scrollPane) {
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, config.chatAreaHeight()));
        scrollPane.setPreferredSize(new Dimension(Integer.MAX_VALUE, config.chatAreaHeight()));
    }

    public void updateChatStyles() {
        setFontSize();
        setColors();
        setScrollPaneSizes();
    }

    private void setFontSize() {
        publicChatArea.setFont(getFontFromConfig(config.publicChatFontSize()));
        privateChatArea.setFont(getFontFromConfig(config.privateChatFontSize()));
        clanChatArea.setFont(getFontFromConfig(config.clanChatFontSize()));
        gameChatArea.setFont(getFontFromConfig(config.gameChatFontSize()));
    }

    private Font getFontFromConfig(int fontSize) {
        Font selectedFont;

        switch (config.fontStyle()) {
            case BOLD:
                selectedFont = new Font("Bold", Font.BOLD, fontSize);
                break;
            case ITALIC:
                selectedFont = new Font("Italic", Font.ITALIC, fontSize);
                break;
            default:
                selectedFont = new Font("Plain", Font.PLAIN, fontSize);
                break;
        }

        return selectedFont;
    }

    private void setColors() {
        publicChatArea.setBackground(config.publicChatBackground());
        publicChatArea.setForeground(config.publicChatColor());
        privateChatArea.setBackground(config.privateChatBackground());
        privateChatArea.setForeground(config.privateChatColor());
        clanChatArea.setBackground(config.clanChatBackgroundColor());
        clanChatArea.setForeground(config.clanChatColor());
        gameChatArea.setBackground(config.gameChatBackgroundColor());
        gameChatArea.setForeground(config.gameChatColor());}

    private void setScrollPaneSizes() {
        setScrollPaneSize((JScrollPane) tabbedPane.getComponentAt(0));
        setScrollPaneSize((JScrollPane) tabbedPane.getComponentAt(1));
        setScrollPaneSize((JScrollPane) tabbedPane.getComponentAt(2));
        setScrollPaneSize((JScrollPane) tabbedPane.getComponentAt(3));
    }

    public void addPublicChatMessage(String timestamp, String cleanedName, String message) {
        String formattedMessage = config.showTimestamp()
                ? "" + timestamp + " [" + cleanedName + "]: " + message
                : "" + timestamp + "[" + cleanedName + "]: " + message;
        addMessageToChatArea(publicChatArea, formattedMessage);
    }

    public void addPrivateChatMessage(String timestamp, String name, String message) {
        String formattedMessage = config.showTimestamp()
                ? "" + timestamp + " " + "[" + name + "]: " + message
                : "[" + name + "]: " + message;
        addMessageToChatArea(privateChatArea, formattedMessage);
    }
    public void addClanChatMessage(String timestamp, String name, String message) {
        String formattedMessage = config.showTimestamp() && !name.isEmpty()
                ? "" + timestamp + " " + "[" + name + "]: " + message
                : (name.isEmpty() ? message : "[" + name + "]: " + message);
        addMessageToChatArea(clanChatArea, formattedMessage);
    }

    public void addGameChatMessage(String timestamp, String cleanedMessage) {
        cleanedMessage = filterGameChatMessage(cleanedMessage);
        if (shouldHideGameChatMessage(cleanedMessage)) {
            return;
        }
        String formattedMessage = config.showTimestamp()
                ? "[" + timestamp + "] " + cleanedMessage
                : timestamp + cleanedMessage;
        addMessageToChatArea(gameChatArea, formattedMessage);
    }
    private String filterGameChatMessage(String message) {
        return message.replaceAll("<col=[0-9a-fA-F]+>|</col>", "").replace("<br>", " ").replace("|", ": ");
    }
    private boolean shouldHideGameChatMessage(String message) {
        return message.contains("<colNORMAL>");
    }
    public void addTimestamp(String timestamp) {
        publicChatArea.append(timestamp);
    }

    public void addCleanedName(String cleanedName) {
        publicChatArea.append(cleanedName);
    }
    private void addMessageToChatArea(JTextArea chatArea, String formattedMessage) {
        SwingUtilities.invokeLater(() -> {
            JScrollPane scrollPane = (JScrollPane) chatArea.getParent().getParent();
            JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();

            boolean shouldAutoScroll = (verticalScrollBar.getValue() + verticalScrollBar.getVisibleAmount() == verticalScrollBar.getMaximum());
            int extraLines = config.lineSpacing();
            for (int i = 0; i < extraLines; i++) {
                chatArea.append("\n");
            }

            chatArea.append(formattedMessage + "\n");
            try {
                int excess = chatArea.getLineCount() - MAX_CHAT_LINES;
                if (excess > 0) {
                    int end = chatArea.getLineEndOffset(excess - 1);
                    chatArea.replaceRange("", 0, end);
                }
            } catch (BadLocationException e) {
                logger.error("Error managing chat lines", e);
            }

            if (shouldAutoScroll) {
                chatArea.setCaretPosition(chatArea.getDocument().getLength());
            }
        });
    }
}
