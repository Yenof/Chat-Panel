package net.runelite.client.plugins.chatpanel;

import net.runelite.client.ui.PluginPanel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
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

    public ChatPanelSidebar(ChatPanelConfig config) {
        this.config = config;
        setLayout(new BorderLayout());

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

    private JTextArea createChatArea() {
        JTextArea chatArea = new JTextArea();
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setEditable(false);
        chatArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        chatArea.setBackground(Color.BLACK);
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
        publicChatArea.setForeground(config.publicChatColor());
        publicChatArea.setFont(new Font("SansSerif", Font.PLAIN, config.publicChatFontSize()));
        privateChatArea.setForeground(config.privateChatColor());
        privateChatArea.setFont(new Font("SansSerif", Font.PLAIN, config.privateChatFontSize()));
        clanChatArea.setForeground(config.clanChatColor());
        clanChatArea.setFont(new Font("SansSerif", Font.PLAIN, config.clanChatFontSize()));
        gameChatArea.setForeground(config.gameChatColor());
        gameChatArea.setFont(new Font("SansSerif", Font.PLAIN, config.gameChatFontSize()));

        setScrollPaneSize((JScrollPane) tabbedPane.getComponentAt(0));
        setScrollPaneSize((JScrollPane) tabbedPane.getComponentAt(1));
        setScrollPaneSize((JScrollPane) tabbedPane.getComponentAt(2));
        setScrollPaneSize((JScrollPane) tabbedPane.getComponentAt(3));
    }

    public void addPublicChatMessage(String name, String message) {
        addMessageToChatArea(publicChatArea, "[" + name + "]: " + message);
    }

    public void addPrivateChatMessage(String name, String message) {
        addMessageToChatArea(privateChatArea, "[" + name + "]: " + message);
    }

    public void addClanChatMessage(String name, String message) {
        addMessageToChatArea(clanChatArea, "[" + name + "]: " + message);
    }

    public void addGameChatMessage(String message) {
        addMessageToChatArea(gameChatArea, message);
    }

    private static final Logger logger = LoggerFactory.getLogger(ChatPanelSidebar.class);


    private void addMessageToChatArea(JTextArea chatArea, String formattedMessage) {
        SwingUtilities.invokeLater(() -> {
            JScrollPane scrollPane = (JScrollPane) chatArea.getParent().getParent();
            JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();

            boolean shouldAutoScroll = (verticalScrollBar.getValue() + verticalScrollBar.getVisibleAmount() == verticalScrollBar.getMaximum());

            // Append the new message
            chatArea.append(formattedMessage + "\n");

            // Remove old messages if exceeding max lines
            try {
                int excess = chatArea.getLineCount() - MAX_CHAT_LINES;
                if (excess > 0) {
                    int end = chatArea.getLineEndOffset(excess - 1);
                    chatArea.replaceRange("", 0, end);
                }
            } catch (BadLocationException e) {
                logger.error("Error managing chat lines", e);
            }

            // Only auto-scroll if the user was at the bottom
            if (shouldAutoScroll) {
                chatArea.setCaretPosition(chatArea.getDocument().getLength());
            }
        });
    }
}

