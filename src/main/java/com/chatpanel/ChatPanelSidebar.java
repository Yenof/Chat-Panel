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
    private static final Logger logger = LoggerFactory.getLogger(ChatPanelSidebar.class);

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
        publicChatArea.setForeground(config.publicChatColor());
        privateChatArea.setForeground(config.privateChatColor());
        clanChatArea.setForeground(config.clanChatColor());
        gameChatArea.setForeground(config.gameChatColor());}

    private void setScrollPaneSizes() {
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

    private void addMessageToChatArea(JTextArea chatArea, String formattedMessage) {
        SwingUtilities.invokeLater(() -> {
            JScrollPane scrollPane = (JScrollPane) chatArea.getParent().getParent();
            JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();

            boolean shouldAutoScroll = (verticalScrollBar.getValue() + verticalScrollBar.getVisibleAmount() == verticalScrollBar.getMaximum());

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
