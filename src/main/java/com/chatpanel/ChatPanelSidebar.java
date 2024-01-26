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
    private final JTextArea friendsChatArea;
    private static final int MAX_CHAT_LINES = 10000;
    private final JTextArea gameChatArea;
    private final JTabbedPane tabbedPane;
    private final ChatPanelConfig config;
    private static final Logger logger = LoggerFactory.getLogger(ChatPanelSidebar.class);
    private boolean isPopout = false;
    private JFrame popoutFrame;
    private final JButton popoutButton;
    private JButton popinButton;
    private JButton popinButton2;
    private final JTextArea allChatArea;

    public ChatPanelSidebar(ChatPanelConfig config) {

        this.config = config;
        setLayout(new BorderLayout());
        popoutButton = new JButton("Pop out");
        popoutButton.setVisible(true);
        popoutButton.addActionListener(e -> togglePopout());
        add(popoutButton, BorderLayout.SOUTH);


        publicChatArea = createChatArea();
        privateChatArea = createChatArea();
        clanChatArea = createChatArea();
        friendsChatArea= createChatArea();
        gameChatArea = createChatArea();
        allChatArea = createChatArea();

        tabbedPane = new JTabbedPane();
        createTabs();

        add(tabbedPane, BorderLayout.CENTER);
        updateChatStyles();
    }

    private void createTabs() {
        if (config.showPublicChat()) {
            tabbedPane.addTab("Public", createScrollPane(publicChatArea));
        }

        if (config.showPrivateChat()) {
            tabbedPane.addTab("Private", createScrollPane(privateChatArea));
        }

        if (config.showClanChat()) {
            tabbedPane.addTab("Clan", createScrollPane(clanChatArea));
        }

        if (config.showGameChat()) {
            tabbedPane.addTab("Game", createScrollPane(gameChatArea));
        }
        if (config.showAllChat()) {
            tabbedPane.addTab("All", createScrollPane(allChatArea));
        }
        if (config.showFriendsChat()) {
            tabbedPane.addTab("Friends", createScrollPane(friendsChatArea));
        }
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
            popoutFrame.setSize(config.popoutSize());
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
        popinButton2 = new JButton("Pop in");
        popinButton2.addActionListener(e -> togglePopout());
        add(popinButton2, BorderLayout.SOUTH);
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
        chatArea.setBorder(new EmptyBorder(5, 2, 5, 2));
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
        friendsChatArea.setFont(getFontFromConfig(config.friendsChatFontSize()));
        gameChatArea.setFont(getFontFromConfig(config.gameChatFontSize()));
        allChatArea.setFont(getFontFromConfig(config.allChatFontSize()));
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
        friendsChatArea.setBackground(config.friendsChatBackground());
        friendsChatArea.setForeground(config.friendsChatColor());
        gameChatArea.setBackground(config.gameChatBackgroundColor());
        gameChatArea.setForeground(config.gameChatColor());
        allChatArea.setBackground(config.allChatBackground());
        allChatArea.setForeground(config.allChatColor());}

    private void setScrollPaneSizes() {
        if (tabbedPane.getTabCount() > 0) {
            setScrollPaneSize((JScrollPane) tabbedPane.getComponentAt(0));
        }
        if (tabbedPane.getTabCount() > 1) {
            setScrollPaneSize((JScrollPane) tabbedPane.getComponentAt(1));
        }
        if (tabbedPane.getTabCount() > 2) {
            setScrollPaneSize((JScrollPane) tabbedPane.getComponentAt(2));
        }
        if (tabbedPane.getTabCount() > 3) {
            setScrollPaneSize((JScrollPane) tabbedPane.getComponentAt(3));
        }
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
    public void addFriendsChatMessage(String timestamp, String name, String message) {
        String formattedMessage = config.showTimestamp() && !name.isEmpty()
                ? "" + timestamp + " " + "[" + name + "]: " + message
                : (name.isEmpty() ? message : "[" + name + "]: " + message);
        addMessageToChatArea(friendsChatArea, formattedMessage);
    }
    public void addAllChatMessage(String timestamp, String cleanedName, String cleanedMessage) {
        cleanedMessage = filterAllChatMessage(cleanedMessage);
        String s = cleanedName.isEmpty() ? "" : cleanedName + ": ";
        String formattedMessage = config.showTimestamp()
                ? "[" + timestamp + "] " + s + cleanedMessage
                : s + cleanedMessage;
        if (!shouldHideAllChatMessage(formattedMessage)) {
            addMessageToChatArea(allChatArea, formattedMessage);
        }
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
        return message.replaceAll("<col=[0-9a-fA-F]+>|</col>", "").replace("<br>", " ").replace("|", ": ").replace("<colHIGHLIGHT>", "");
    }
    private boolean shouldHideGameChatMessage(String message) {
        return message.contains("<colNORMAL>");
    }
    private String filterAllChatMessage(String message) {
        return message.replaceAll("<col=[0-9a-fA-F]+>|</col>", "").replace("<br>", " ").replace("|", ": ").replace("<colHIGHLIGHT>", "").replace("<colNORMAL>", "");
    }
    private boolean shouldHideAllChatMessage(String message) {
        return message.contains("<colNORMALimpossible>");
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
