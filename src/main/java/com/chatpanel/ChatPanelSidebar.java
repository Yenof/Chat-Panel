package com.chatpanel;

import net.runelite.client.ui.PluginPanel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.awt.IllegalComponentStateException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;



public class ChatPanelSidebar extends PluginPanel {
    private final JTextPane publicChatArea;
    private final JTextPane privateChatArea;
    private final JTextPane clanChatArea;
    private final JTextPane friendsChatArea;
    private final JTextPane allChatArea;
    private final JTextPane customChatArea;
    private final JTextPane customChatArea2;
    private final JTextPane customChatArea3;
    private final JTextPane gameChatArea;
    private final JTextPane combatArea;
    private final JTabbedPane tabbedPane;
    private final ChatPanelConfig config;
    private static final Logger logger = LoggerFactory.getLogger(ChatPanelSidebar.class);
    private boolean isPopout = false;
    private JFrame popoutFrame;
    private JFrame popoutTab;
    private JButton popoutButton;
    private JButton popinButton;
    private JButton popinButton2;
    private boolean overrideUndecorated;
    private static final int AUTO_POP_DELAY_MS = 180; //This prevents the pop out window from messing up RL's icon.
    private Timer autoPopTimer;
    private final List<JFrame> popoutTabs = new ArrayList<>();

    public ChatPanelSidebar(ChatPanelConfig config) {
        this.config = config;
        setLayout(new BorderLayout());
        if (!config.hidepopoutButtons()) {
            popoutButton = new JButton("Pop out");
            popoutButton.setVisible(true);
            popoutButton.addActionListener(e -> togglePopout());
            add(popoutButton, BorderLayout.SOUTH);}

        publicChatArea = createTextPane();
        privateChatArea = createTextPane();
        clanChatArea = createTextPane();
        friendsChatArea = createTextPane();
        gameChatArea = createTextPane();
        allChatArea = createTextPane();
        customChatArea = createTextPane();
        customChatArea2 = createTextPane();
        customChatArea3 = createTextPane();
        combatArea = createTextPane();

        tabbedPane = new JTabbedPane();
        createTabs();

        add(tabbedPane, BorderLayout.CENTER);
        updateChatStyles();
        tabbedPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.isMiddleMouseButton(e);
                if (SwingUtilities.isRightMouseButton(e)) {
                    int tabIndex = tabbedPane.indexAtLocation(e.getX(), e.getY());
                    if (tabIndex != -1) {
                        showPopupMenu(e.getComponent(), e.getX(), e.getY(), tabIndex);
                    }
                }
                if (SwingUtilities.isMiddleMouseButton(e)) {
                    int tabIndex = tabbedPane.indexAtLocation(e.getX(), e.getY());
                    if (tabIndex != -1) {
                        popOutTab(tabIndex);
                    }
                }
            }
        });
        if (config.AutoPop() && !isPopout()) {
            autoPopTimer = new Timer(AUTO_POP_DELAY_MS, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    togglePopout();
                    autoPopTimer.stop();
                }
            });
            autoPopTimer.start();
        }
    }

    private void showPopupMenu(Component component, int x, int y, int tabIndex) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem popoutItem = new JMenuItem("Pop Out");
        popoutItem.addActionListener(e -> popOutTab(tabIndex));
        popupMenu.add(popoutItem);

        JMenuItem resetHistoryItem = new JMenuItem("Reset History");
        resetHistoryItem.addActionListener(e -> resetTabHistory(tabIndex));
        popupMenu.add(resetHistoryItem);

        JMenuItem exportItem = new JMenuItem("Export log");
        exportItem.addActionListener(e -> exportChatLog(tabIndex));
        popupMenu.add(exportItem);

        popupMenu.show(component, x, y);

        popupMenu.show(component, x, y);
    }

    private void createTabs() {
        tabbedPane.removeAll();

        if (config.showPublicChat()) {
            tabbedPane.addTab("Public", createScrollPane(publicChatArea));
            tabbedPane.setToolTipTextAt(tabbedPane.indexOfTab("Public"), "Right click for options. MMB to pop out tab");
        }

        if (config.showPrivateChat()) {
            tabbedPane.addTab("Private", createScrollPane(privateChatArea));
            tabbedPane.setToolTipTextAt(tabbedPane.indexOfTab("Private"), "Right click for options. MMB to pop out tab");
        }

        if (config.showClanChat()) {
            tabbedPane.addTab("Clan", createScrollPane(clanChatArea));
            tabbedPane.setToolTipTextAt(tabbedPane.indexOfTab("Clan"), "Right click for options. MMB to pop out tab");
        }

        if (config.showGameChat()) {
            tabbedPane.addTab("Game", createScrollPane(gameChatArea));
            tabbedPane.setToolTipTextAt(tabbedPane.indexOfTab("Game"), "Right click for options. MMB to pop out tab");
        }

        if (config.showAllChat()) {
            tabbedPane.addTab("All", createScrollPane(allChatArea));
            tabbedPane.setToolTipTextAt(tabbedPane.indexOfTab("All"), "Right click for options. MMB to pop out tab");
        }

        if (config.showFriendsChat()) {
            tabbedPane.addTab("Friends", createScrollPane(friendsChatArea));
            tabbedPane.setToolTipTextAt(tabbedPane.indexOfTab("Friends"), "Right click for options. MMB to pop out tab");
        }

        if (config.showCustomChat()) {
            String tabTitle = config.custom1Tabname();
            tabbedPane.addTab(tabTitle, createScrollPane(customChatArea));
            tabbedPane.setToolTipTextAt(tabbedPane.indexOfTab(tabTitle), "Right click for options. MMB to pop out tab");
        }

        if (config.showCustom2Chat()) {
            String tabTitle = config.custom2Tabname();
            tabbedPane.addTab(tabTitle, createScrollPane(customChatArea2));
            tabbedPane.setToolTipTextAt(tabbedPane.indexOfTab(tabTitle), "Right click for options. MMB to pop out tab");
        }

        if (config.showCustom3Chat()) {
            String tabTitle = config.custom3Tabname();
            tabbedPane.addTab(tabTitle, createScrollPane(customChatArea3));
            tabbedPane.setToolTipTextAt(tabbedPane.indexOfTab(tabTitle), "Right click for options. MMB to pop out tab");
        }

        if (config.showCombatTab()) {
            String tabTitle = "Combat";
            tabbedPane.addTab(tabTitle, createScrollPane(combatArea));
            tabbedPane.setToolTipTextAt(tabbedPane.indexOfTab(tabTitle), "Right click for options. MMB to pop out tab");
        }
    }

    public void reloadPlugin() {
        for (JFrame popoutTab : popoutTabs) {
            popoutTab.dispose();
        }
        popoutTabs.clear();
        createTabs();
    }

    private String lastDirectoryPath;

    private void exportChatLog(int tabIndex) {
        Component tabComponent = tabbedPane.getComponentAt(tabIndex);
        if (tabComponent instanceof JScrollPane) {
            JTextPane chatArea = (JTextPane) ((JScrollPane) tabComponent).getViewport().getView();
            String chatLog = chatArea.getText();
            JFileChooser fileChooser = new JFileChooser();

            if (lastDirectoryPath != null) {
                fileChooser.setCurrentDirectory(new File(lastDirectoryPath));
            }

            String tabName = tabbedPane.getTitleAt(tabIndex);
            SimpleDateFormat dateFormat = new SimpleDateFormat("d_M");
            String currentTime = dateFormat.format(new Date());
            String defaultFileName = tabName + "_" + currentTime + ".txt";
            fileChooser.setSelectedFile(new File(defaultFileName));
            fileChooser.setFileFilter(new FileNameExtensionFilter("Text files (*.txt)", "txt"));

            while (true) {
                int result = fileChooser.showSaveDialog(this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    if (selectedFile.exists()) {
                        int overwriteResult = JOptionPane.showConfirmDialog(this,
                                "File already exists. Do you want to overwrite it?",
                                "Confirm Overwrite",
                                JOptionPane.YES_NO_OPTION);
                        if (overwriteResult == JOptionPane.NO_OPTION) {
                            continue;
                        }
                    }
                    try (PrintWriter writer = new PrintWriter(selectedFile)) {
                        writer.println(chatLog);
                        JOptionPane.showMessageDialog(this, "Chat log exported successfully!", "", JOptionPane.INFORMATION_MESSAGE);
                        lastDirectoryPath = selectedFile.getParent();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this, "Error exporting chat log: " + ex.getMessage(), "Unknown Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                } else {
                    break;
                }
            }
        }
    }

    private void resetTabHistory(int tabIndex) {
        Component tabComponent = tabbedPane.getComponentAt(tabIndex);
        if (tabComponent instanceof JScrollPane) {
            JTextPane chatArea = (JTextPane) ((JScrollPane) tabComponent).getViewport().getView();
            chatArea.setText("");
        }
    }

    private void togglePopout() {
        if (isPopout) {
            // Restore to side panel
            isPopout = false;
                if (popoutTab != null) {
                popoutTab.dispose();
                }
            popoutFrame.dispose();
            addComponentsForSidePanel();
            add(tabbedPane, BorderLayout.CENTER);
            updateChatStyles();
            reloadPlugin();
        } else {
            isPopout = true;
            popoutFrame = new JFrame("Chat Panel") {
                @Override
                public boolean isUndecorated() {
                    return overrideUndecorated || super.isUndecorated();
                }
            };
            addComponentsForPopout();
            popoutFrame.add(tabbedPane);
            popoutFrame.setSize(config.popoutSize());
            popoutFrame.setMinimumSize(new Dimension(40, 10));
            popoutFrame.setLocationRelativeTo(null);
            popoutFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            if (config.popoutAlwaysOnTop()) {
                popoutFrame.setAlwaysOnTop(true);
            }
            setCactus(config.popoutOpacity() / 100.0f);

            popoutFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    if (config.hideSidebarIcon() && popoutFrame != null && !config.hidePopoutWarning()) {
                        //JCheckBox checkBox = new JCheckBox("Do not show this message again. I have read and understand how to retrieve the pop out window"); Can't get working :C
                        Object[] options = {"OK", "Cancel",};
                        int choice = JOptionPane.showOptionDialog(
                                popoutFrame,
                                "<html><body style='width: 500px;'>The sidebar icon is currently set to hidden (Pop out button hidden too). <br> To relaunch the pop out window, toggle the plugin off/on with Auto-Pop option on. <br> This warning can be turned off in config.</body></html>",
                                "Closing Pop Out with Sidebar Icon Hidden",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.INFORMATION_MESSAGE,
                                null,
                                options,
                                options[0]
                        );
                        if (choice == JOptionPane.CANCEL_OPTION) {
                            e.getWindow().setVisible(true);
                        } else if (choice == JOptionPane.OK_OPTION) {
                            // if (checkBox.isSelected()) Can't get working yet.
                           // {
                           //     config.hidePopoutWarning();
                           // }
                            isPopout = false;
                            popoutFrame.dispose();
                            addComponentsForSidePanel();
                            add(tabbedPane, BorderLayout.CENTER);
                            updateChatStyles();
                        }
                    } else {
                        isPopout = false;
                        if (popoutFrame != null) {
                            popoutFrame.dispose();
                        }
                        addComponentsForSidePanel();
                        add(tabbedPane, BorderLayout.CENTER);
                        updateChatStyles();
                    }
                }
            });
            popoutFrame.setVisible(true);
        }
    }

    private void popOutTab(int tabIndex) {
        Component tabComponent = tabbedPane.getComponentAt(tabIndex);
        String tabTitle = tabbedPane.getTitleAt(tabIndex);
        if (tabComponent instanceof JScrollPane) {
            JTextPane chatArea = (JTextPane) ((JScrollPane) tabComponent).getViewport().getView();
            tabbedPane.remove(tabIndex);

            popoutTab = new JFrame(tabTitle) {
                @Override
                public boolean isUndecorated() {
                    return overrideUndecorated || super.isUndecorated();
                }
            };
            popoutTab.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            popoutTab.setAlwaysOnTop(config.popoutAlwaysOnTop());
            popoutTab.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    restorePoppedOutTab(tabIndex, chatArea, tabTitle);
                }
            });
            popoutTabs.add(popoutTab);
            JScrollPane scrollPane = new JScrollPane(chatArea);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            popoutTab.add(scrollPane);
            setCactus(config.popoutOpacity() / 100.0f);
            popoutTab.setSize(config.popoutSize());
            popoutTab.setVisible(true);
            popoutTab.setMinimumSize(new Dimension(40, 10));
        }
    }

    private void restorePoppedOutTab(int tabIndex, JTextPane chatArea, String tabTitle) {
        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setPreferredSize(new Dimension(Integer.MAX_VALUE, config.chatAreaHeight()));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        tabbedPane.insertTab(tabTitle, null, scrollPane, null, tabbedPane.getTabCount());
        tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
    }

    void setCactus(float opacity) {
        overrideUndecorated = true;
        try {
            if (popoutTab != null) {
                popoutTab.setOpacity(config.popoutOpacity() / 100.0f);
            }
            if (popoutFrame != null) {
                popoutFrame.setOpacity(config.popoutOpacity() / 100.0f);
            }
        } catch (IllegalComponentStateException | UnsupportedOperationException | IllegalArgumentException ex) {
            logger.warn("unable to set opacity {}", opacity, ex);
        } finally {
            overrideUndecorated = false;
        }
    }

    private void addComponentsForSidePanel() {
        if (popoutButton != null) {
            popoutButton.setVisible(true);
        }
        if (popinButton != null) {
            popinButton.setVisible(false);
            if (!config.hidepopoutButtons()) {
                add(popoutButton, BorderLayout.SOUTH);
            }
            remove(popinButton);
        }
        if (popinButton2 != null) {
            popinButton2.setVisible(false);
        }
    }

    private void addComponentsForPopout() {
        if (!config.hidepopoutButtons()) {
            popinButton = new JButton("Pop In");
            popinButton.addActionListener(e -> {
                if (config.hideSidebarIcon() && popoutFrame != null && !config.hidePopoutWarning()) {
                    //JCheckBox checkBox = new JCheckBox("Do not show this message again. I have read and understand how to retrieve the pop out window"); Can't get working yet
                    Object[] options = {"OK", "Cancel"};
                    int choice = JOptionPane.showOptionDialog(
                            popoutFrame,
                            "<html><body style='width: 500px;'>The sidebar icon is currently set to hidden (Pop out button hidden too). <br> To relaunch the pop out window, toggle the plugin off/on with Auto-Pop option on. <br> This warning can be turned off in config.</body></html>",
                            "Closing Pop Out with Sidebar Icon Hidden",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.INFORMATION_MESSAGE,
                            null,
                            options,
                            options[0]
                    );
                    if (choice == JOptionPane.CANCEL_OPTION) {
                        setVisible(true);
                    } else if (choice == JOptionPane.OK_OPTION) {
                        // if (checkBox.isSelected()) Can't get working yet.
                        //{
                       //     config.hidePopoutWarning();
                        //}
                        togglePopout();
                    }
                } else {
                    togglePopout();
                }
            });
            popoutFrame.add(popinButton, BorderLayout.SOUTH);
        }
        popinButton2 = new JButton("Pop in");
        popinButton2.addActionListener(e -> togglePopout());
        add(popinButton2, BorderLayout.SOUTH);
        if (popoutButton != null){
            remove(popoutButton);
        }
        if (popinButton2 != null) {
            popinButton2.setVisible(true);
            add(popinButton2, BorderLayout.SOUTH);
        }
    }

    public boolean isPopout() {
        return isPopout;
    }

    public void closePopout() {
        for (JFrame popoutTab : popoutTabs) {
            popoutTab.dispose();
        }
        popoutTabs.clear();
        if (popoutFrame != null) {
            popoutFrame.dispose();
        }
        isPopout = false;
    }

    //Wrap editor is to mimic wrapping that was in JTextArea, before switching to JTextPane. smh, must be a better way.
    public static class WrapEditorKit extends StyledEditorKit {
        @Override
        public ViewFactory getViewFactory() {
            return new WrapColumnFactory();
        }

        static class WrapColumnFactory implements ViewFactory {
            @Override
            public View create(Element elem) {
                String kind = elem.getName();
                if (kind != null) {
                    switch (kind) {
                        case AbstractDocument.ContentElementName:
                            return new WrapLabelView(elem);
                        case AbstractDocument.ParagraphElementName:
                            return new ParagraphView(elem);
                        case AbstractDocument.SectionElementName:
                            return new BoxView(elem, View.Y_AXIS);
                        case StyleConstants.ComponentElementName:
                            return new ComponentView(elem);
                        case StyleConstants.IconElementName:
                            return new IconView(elem);
                    }
                }
                return new LabelView(elem);
            }
        }

        static class WrapLabelView extends LabelView {
            public WrapLabelView(Element elem) {
                super(elem);
            }

            @Override
            public float getMinimumSpan(int axis) {
                switch (axis) {
                    case View.X_AXIS:
                        return 0;
                    case View.Y_AXIS:
                        return super.getMinimumSpan(axis);
                    default:
                        throw new IllegalArgumentException("Invalid axis: " + axis);
                }
            }
        }
    }

    private JTextPane createTextPane() {
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setBorder(new EmptyBorder(5, 2, 5, 2));
        SimpleAttributeSet attributes = new SimpleAttributeSet();
        textPane.getStyledDocument().setParagraphAttributes(0, textPane.getDocument().getLength(), attributes, false);
        textPane.setEditorKit(new WrapEditorKit());
        return textPane;
    }

    private JScrollPane createScrollPane(JTextPane chatArea) {
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
        customChatArea.setFont(getFontFromConfig(config.customChatFontSize()));
        customChatArea2.setFont(getFontFromConfig(config.custom2ChatFontSize()));
        customChatArea3.setFont(getFontFromConfig(config.custom3ChatFontSize()));
        combatArea.setFont(getFontFromConfig(config.combatFontSize()));
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
		if (config.chatColorOffset()!= 0) {

			int offset = config.chatColorOffset();}
		else {
			int offset = 0;
		}
		int offset = 0;

        publicChatArea.setBackground(config.publicChatBackground());
        publicChatArea.setForeground(adjustColor(config.publicChatColor(), offset));
        privateChatArea.setBackground(config.privateChatBackground());
        privateChatArea.setForeground(adjustColor(config.privateChatColor(), offset));
        clanChatArea.setBackground(config.clanChatBackgroundColor());
        clanChatArea.setForeground(adjustColor(config.clanChatColor(), offset));
        friendsChatArea.setBackground(config.friendsChatBackground());
        friendsChatArea.setForeground(adjustColor(config.friendsChatColor(), offset));
        gameChatArea.setBackground(config.gameChatBackgroundColor());
        gameChatArea.setForeground(adjustColor(config.gameChatColor(), offset));
        allChatArea.setBackground(config.allChatBackground());
        allChatArea.setForeground(adjustColor(config.allChatColor(), offset));
        customChatArea.setBackground(config.customChatBackgroundColor());
        customChatArea.setForeground(adjustColor(config.customChatColor(), offset));
        customChatArea2.setBackground(config.custom2ChatBackgroundColor());
        customChatArea2.setForeground(adjustColor(config.custom2ChatColor(), offset));
        customChatArea3.setBackground(config.custom3ChatBackgroundColor());
        customChatArea3.setForeground(adjustColor(config.custom3ChatColor(), offset));
        combatArea.setBackground(config.combatBackgroundColor());
        combatArea.setForeground(adjustColor(config.combatTextColor(), offset));
    }

    private Color NameColor(JTextPane chatArea) {
        if (config.chatColorOffset()!= 0) {

            int offset = config.chatColorOffset();}
        else {
            int offset = 0;
        }
        int offset = 0;

        if (chatArea == publicChatArea) {
            return adjustColor(config.publicChatNameColor(), offset);
        } else if (chatArea == privateChatArea) {
            return adjustColor(config.privateChatNameColor(), offset);
        } else if (chatArea == clanChatArea) {
            return adjustColor(config.clanChatNameColor(), offset);
        } else if (chatArea == friendsChatArea) {
            return adjustColor(config.friendsChatNameColor(), offset);
        } else if (chatArea == gameChatArea) {
            return adjustColor(config.gameChatNameColor(), offset);
        } else if (chatArea == allChatArea) {
            return adjustColor(config.allChatNameColor(), offset);
        } else if (chatArea == customChatArea) {
            return adjustColor(config.customChatNameColor(), offset);
        } else if (chatArea == customChatArea2) {
            return adjustColor(config.custom2ChatNameColor(), offset);
        } else if (chatArea == customChatArea3) {
            return adjustColor(config.custom3ChatNameColor(), offset);
        } else if (chatArea == combatArea) {
            return adjustColor(config.combatLabelColor(), offset);
        }
        return adjustColor(Color.YELLOW, offset);
    }

	private Color TimestampColor(JTextPane chatArea) {
		config.CustomTimestamp();
		return config.TimestampColorOverride();
	}


    private Color adjustColor(Color color, int offset) {
        int red = Math.max(0, Math.min(255, color.getRed() + (offset * 255 / 100)));
        int green = Math.max(0, Math.min(255, color.getGreen() + (offset * 255 / 100)));
        int blue = Math.max(0, Math.min(255, color.getBlue() + (offset * 255 / 100)));
        return new Color(red, green, blue);
    }

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
        if (tabbedPane.getTabCount() > 4) {
            setScrollPaneSize((JScrollPane) tabbedPane.getComponentAt(4));
        }
        if (tabbedPane.getTabCount() > 5) {
            setScrollPaneSize((JScrollPane) tabbedPane.getComponentAt(5));
        }
        if (tabbedPane.getTabCount() > 6) {
            setScrollPaneSize((JScrollPane) tabbedPane.getComponentAt(6));
        }
        if (tabbedPane.getTabCount() > 7) {
            setScrollPaneSize((JScrollPane) tabbedPane.getComponentAt(7));
        }
        if (tabbedPane.getTabCount() > 8) {
            setScrollPaneSize((JScrollPane) tabbedPane.getComponentAt(8));
        }
        if (tabbedPane.getTabCount() > 9) {
            setScrollPaneSize((JScrollPane) tabbedPane.getComponentAt(9));
        }
    }
    public void addPublicChatMessage(String timestamp, String cleanedName, String message) {
        addMessageToChatArea(publicChatArea, timestamp, cleanedName, message);
    }

    public void addPrivateChatMessage(String timestamp, String name, String message) {
        addMessageToChatArea(privateChatArea, timestamp, name, message);
    }

    public void addClanChatMessage(String timestamp, String name, String message) {
        addMessageToChatArea(clanChatArea, timestamp, name, message);
    }

    public void addFriendsChatMessage(String timestamp, String name, String message) {
        addMessageToChatArea(friendsChatArea, timestamp, name, message);
    }

    public void addAllChatMessage(String timestamp, String cleanedName, String cleanedMessage) {
        cleanedMessage = filterAllChatMessage(cleanedMessage);
        addMessageToChatArea(allChatArea, timestamp, cleanedName, cleanedMessage);
    }

    public void addCustomChatMessage(String timestamp, String cleanedName, String cleanedMessage) {
        cleanedMessage = filterAllChatMessage(cleanedMessage);
        addMessageToChatArea(customChatArea, timestamp, cleanedName, cleanedMessage);
    }

    public void addCustom2ChatMessage(String timestamp, String cleanedName, String cleanedMessage) {
        cleanedMessage = filterAllChatMessage(cleanedMessage);
        addMessageToChatArea(customChatArea2, timestamp, cleanedName, cleanedMessage);
    }

    public void addCustom3ChatMessage(String timestamp, String cleanedName, String cleanedMessage) {
        cleanedMessage = filterAllChatMessage(cleanedMessage);
        addMessageToChatArea(customChatArea3, timestamp, cleanedName, cleanedMessage);
    }

    public void addGameChatMessage(String timestamp, String cleanedName, String cleanedMessage) {
        cleanedMessage = filterAllChatMessage(cleanedMessage);
        addMessageToChatArea(gameChatArea, timestamp, cleanedName, cleanedMessage);
    }
    public void addCombatMessage(String timestamp, String cleanedName, String combatMessage) {
        addMessageToChatArea(combatArea, timestamp, cleanedName, combatMessage);
    }

    private String filterAllChatMessage(String message) {
        return message.replaceAll("<col=[0-9a-fA-F]+>|</col>", "").replace("<br>", " ").replace("<colHIGHLIGHT>", "").replace("<colNORMAL>", "");
    }

    private void addMessageToChatArea(JTextPane chatArea, String timestamp, String cleanedName, String message) {
        SwingUtilities.invokeLater(() -> {
            StyledDocument doc = chatArea.getStyledDocument();
            JScrollPane scrollPane = (JScrollPane) chatArea.getParent().getParent();
            JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();

            boolean shouldAutoScroll = (verticalScrollBar.getValue() + verticalScrollBar.getVisibleAmount() == verticalScrollBar.getMaximum());

            int extraLines = config.lineSpacing();
            int offset = extraLines;

            Color baseColor = chatArea.getForeground();
            int lineCount = doc.getDefaultRootElement().getElementCount();
            int effectiveLineCount = (lineCount + extraLines - 1) / (extraLines + 1);
            boolean isOddLine = (effectiveLineCount - 1) % 2!= 0;

            SimpleAttributeSet timestampAttrs = new SimpleAttributeSet();
            Color timestampColor;
            if (config.CustomTimestamp()) {
                timestampColor = isOddLine ? adjustColor(TimestampColor(chatArea), config.chatColorOffset()) : TimestampColor(chatArea);
            } else {
                timestampColor = isOddLine ? adjustColor(baseColor, config.chatColorOffset()) : baseColor;
            }
            StyleConstants.setForeground(timestampAttrs, timestampColor);

            SimpleAttributeSet nameAttrs = new SimpleAttributeSet();
            Color nameColor = isOddLine? adjustColor(NameColor(chatArea), config.chatColorOffset()) : NameColor(chatArea);
            StyleConstants.setForeground(nameAttrs, nameColor);

            SimpleAttributeSet messageAttrs = new SimpleAttributeSet();
            Color messageColor = isOddLine? adjustColor(baseColor, config.chatColorOffset()) : baseColor;
            StyleConstants.setForeground(messageAttrs, messageColor);

            try {
				if (!config.TimestampFormat().isEmpty()) {
					doc.insertString(doc.getLength(), "[" + timestamp + "] ", timestampAttrs);
				}
				if (!cleanedName.isEmpty()) {
                    doc.insertString(doc.getLength(), "[" + cleanedName + "]: ", nameAttrs);
                }
                doc.insertString(doc.getLength(), message + "\n", messageAttrs);

                for (int i = 0; i < extraLines; i++) {
                    doc.insertString(doc.getLength(), "\n", null);
                }

                int excess = doc.getDefaultRootElement().getElementCount() - config.maxLines();
                if (excess > 1) {
                    try {
                        Element root = doc.getDefaultRootElement();
                        int linesToRemove = Math.min(excess + extraLines, config.maxLines());
                        int endOffset = 0;
                        for (int i = 0; i < linesToRemove; i++) {
                            Element line = root.getElement(i);
                            endOffset = line.getEndOffset();
                        }
                        doc.remove(0, endOffset);
                    } catch (BadLocationException e) {
                        logger.error("Error removing excess lines from chat", e);
                    }
                }
            } catch (BadLocationException e) {
                logger.error("Error managing chat lines", e);
            }

            if (shouldAutoScroll) {
                chatArea.setCaretPosition(doc.getLength());
            }
        });
    }
}