package com.chatpanel;

import net.runelite.api.Client;
import net.runelite.client.RuneLite;
import net.runelite.client.ui.PluginPanel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.*;

import net.runelite.client.util.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.awt.IllegalComponentStateException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



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
    private final Client client;

    public ChatPanelSidebar(ChatPanelConfig config, Client client) {
        this.config = config;
        this.client = client;
        setLayout(new BorderLayout());
        if (!config.hidepopoutButtons()) {
            popoutButton = new JButton("Pop out");
            popoutButton.setVisible(true);
            popoutButton.addActionListener(e -> togglePopout());
            add(popoutButton, BorderLayout.SOUTH);
        }

        if (!CHAT_PANEL_DIR.exists()) {
               createDirectory();
        }

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
        updateFonts();
        tabbedPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.isMiddleMouseButton(e);
                if (SwingUtilities.isRightMouseButton(e)) {
                    int tabIndex = tabbedPane.indexAtLocation(e.getX(), e.getY());
                    if (tabIndex != -1) {
                        tabbedPane.setSelectedIndex(tabIndex);
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

        JMenuItem exportItem = new JMenuItem("Export Log");
        exportItem.addActionListener(e -> exportChatLog(tabIndex));
        popupMenu.add(exportItem);

        JMenuItem searchItem = new JMenuItem("Search");
        popupMenu.add(searchItem);
        searchItem.addActionListener(e -> {
            ChatPanelSearch.SearchWindow searchWindow = new ChatPanelSearch.SearchWindow(popoutFrame, tabbedPane, tabIndex, getParent());
            searchWindow.setVisible(true);
        });

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


    private void exportChatLog(int tabIndex) {
        Component tabComponent = tabbedPane.getComponentAt(tabIndex);
        if (tabComponent instanceof JScrollPane) {
            JTextPane chatArea = (JTextPane) ((JScrollPane) tabComponent).getViewport().getView();
            String chatLog = chatArea.getText();
            JFileChooser fileChooser = new JFileChooser();

            if (config.getLastDIR() != null) {
                fileChooser.setCurrentDirectory(new File(config.getLastDIR()));
            }

            String tabName = tabbedPane.getTitleAt(tabIndex);
            tabName = tabName.replaceAll("[^a-zA-Z0-9]", "_");
            SimpleDateFormat dateFormat;
            switch (config.exportLogDate()) {
                case MM_dd_yy:
                    dateFormat = new SimpleDateFormat("MM_dd_yy");
                    break;
                case dd_MM:
                    dateFormat = new SimpleDateFormat("dd_MM");
                    break;
                case MM_dd:
                    dateFormat = new SimpleDateFormat("MM_dd");
                    break;
                case dd_MM_yy:
                default:
                    dateFormat = new SimpleDateFormat("dd_MM_yy");
                    break;
            }
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
                        config.setLastDIR(selectedFile.getParent());
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
            if (popoutFrame.isUndecorated()){
                popoutFrame.setIconImage(null);
            } else {
                if (!config.hidePopoutIcon()){
                    popoutFrame.setIconImage(ImageUtil.loadImageResource(getClass(), "/ChatPanelimg.png"));
                } else {
                popoutFrame.setIconImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));
                }
            }

            boolean appliedSize = false;
            if (config.rememberPopoutPosition()) {
                appliedSize = restorePopoutBounds();
                if (config.isPopoutMaximized()) {
                    popoutFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                }
            }

            if (!appliedSize) {
                popoutFrame.setSize(config.popoutSize());
                popoutFrame.setLocationRelativeTo(getParent());
            }

            addComponentsForPopout();
            popoutFrame.add(tabbedPane);
            popoutFrame.setMinimumSize(new Dimension(40, 10));
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
            //On moved instead of on closed because if RL closes first it doesn't always save position.
            popoutFrame.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentMoved(ComponentEvent e) {
                    if (config.rememberPopoutPosition()) {
                        savePopoutBounds();
                    }
                }
                @Override
                public void componentResized(ComponentEvent e) {
                    if (config.rememberPopoutPosition()) {
                        savePopoutBounds();
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
            if (popoutTab.isUndecorated()){
                popoutTab.setIconImage(null);
            } else {
                if (!config.hidePopoutIcon()){
                    popoutTab.setIconImage(ImageUtil.loadImageResource(getClass(), "/ChatPanelimg.png"));
                } else {
                    popoutTab.setIconImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));
                }
            }
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
            if (isPopout){
                popoutTab.setLocationRelativeTo(popoutFrame);
            } else {
                popoutTab.setLocation(this.getLocationOnScreen());
            }

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

    private boolean restorePopoutBounds() {
        Rectangle bounds = config.getPopoutBounds();
        if (bounds != null) {
            popoutFrame.setBounds(bounds);
            return true;
        }
        return false;
    }

    private void savePopoutBounds() {
        if (popoutFrame.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
            config.setPopoutMaximized(true);
        } else {
            config.setPopoutMaximized(false);
            config.setPopoutBounds(popoutFrame.getBounds());
        }
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
        } catch (IllegalComponentStateException | UnsupportedOperationException | IllegalArgumentException ignored) {
            //I don't want to spam the log, this seems to only happen on systems where opacity also can't be applied to RL. Maybe a GPU driver thing?
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
        setColors();
        setScrollPaneSizes();
    }

    public void updateFonts(){
        setFontSize();
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

    public static final File CHAT_PANEL_DIR = new File(RuneLite.RUNELITE_DIR.getPath() + File.separator + "chat-panel");
    private static final File CUSTOM_FONT_FILE = new File(CHAT_PANEL_DIR, "customfont.ttf");
    boolean fontLoadErrorShown = true;
    private Font getFontFromConfig(int fontSize) {
        Font baseFont;
        switch (config.fontStyle()) {
            case BOLD:
                baseFont = new Font("Bold", Font.BOLD, fontSize);
                break;
            case ITALIC:
                baseFont = new Font("Italic", Font.ITALIC, fontSize);
                break;
            case ITALIC_BOLD:
                baseFont = new Font("Italic Bold", Font.ITALIC + Font.BOLD, fontSize);
                break;
            default:
                baseFont = new Font("Plain", Font.PLAIN, fontSize);
                break;
        }
        Font selectedFont;

        switch (config.fontFamily()) {
            case CUSTOM_FONT:
                selectedFont = customFontLoader(fontSize, baseFont.getStyle());
                break;
            case SUPERFUNKY:
                selectedFont = fontLoader("/SuperFunky.ttf", fontSize, baseFont.getStyle());
                break;
            case FONT2:
                selectedFont = fontLoader("/MisterPixel.otf", fontSize, baseFont.getStyle());
                break;
            case FONT3:
                selectedFont = fontLoader("/Qaz.ttf", fontSize, baseFont.getStyle());
                break;
            case FONT4:
                selectedFont = fontLoader("/Fonarto.ttf", fontSize, baseFont.getStyle());
                break;
            case FONT5:
                selectedFont = fontLoader("/HomeVideo.ttf", fontSize, baseFont.getStyle());
                break;
            case FONT6:
                selectedFont = fontLoader("/DecemberShow.ttf", fontSize, baseFont.getStyle());
                break;
            case FONT7:
                selectedFont = fontLoader("/Avara.ttf", fontSize, baseFont.getStyle());
                break;
            case FONT8:
                selectedFont = fontLoader("/Funtype.ttf", fontSize, baseFont.getStyle());
                break;
            case NORMAL:
            default:
                selectedFont = baseFont;
                break;
        }
        return selectedFont;
    }

    private Font fontLoader(String fontFileName, int fontSize, int fontStyle) {
        try (InputStream fontStream = getClass().getResourceAsStream(fontFileName)) {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            ge.registerFont(customFont);
            return customFont.deriveFont(fontStyle, (float) fontSize);
        } catch (FontFormatException | IOException ignored) {
            return new Font("Default", fontStyle, fontSize);
        }
    }

    private Font customFontLoader(int fontSize, int fontStyle) {
        if (!CUSTOM_FONT_FILE.exists()) {
            if (!fontLoadErrorShown) {
                String message = "The Custom Font file is empty. \nTo use a Custom Font place a .ttf or .otf file named customfont.ttf into:\n /.runelite/chat-panel/\nFor more info, right click 'Chat Panel', then click 'Support'.";
                String[] options = {"Open Location", "OK"};
                fontLoadErrorShown = true;
                int choice = JOptionPane.showOptionDialog(null, message, "Empty Font File", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                if (choice == 0) {
                    openDIR();
                }
            }
            return new Font("Default", fontStyle, fontSize);
        } else {
            try (InputStream fontStream = new FileInputStream(CUSTOM_FONT_FILE)) {
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
                ge.registerFont(customFont);
                return customFont.deriveFont(fontStyle, (float) fontSize);
            } catch (FontFormatException | IOException e) {
                if (!fontLoadErrorShown) {
                    String message = "Error loading custom font, some fonts don't work.  :( \nHere is the error message that was created:\n" + e.getMessage();
                    String[] options = {"Open Location", "OK"};
                    int choice = JOptionPane.showOptionDialog(null, message, "Font Loading Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
                    fontLoadErrorShown = true;
                    if (choice == 0) {
                        openDIR();
                    }
                }
                return new Font("Default", fontStyle, fontSize);
            }
        }
    }

    private void createDirectory() {
        if (!CHAT_PANEL_DIR.exists()) {
            try {
                boolean dirCreated = CHAT_PANEL_DIR.mkdirs();
                if (!dirCreated) {
                    logger.warn("Failed to create directories. Maybe permission issue?");
                }
            } catch (Exception e) {
                logger.error("Error creating directory: {}", e.getMessage());
            }
        }
    }

    private void openDIR() {
        if (CHAT_PANEL_DIR.exists()) {
            try {
                Desktop desktop = Desktop.getDesktop();
                desktop.open(CUSTOM_FONT_FILE.getParentFile());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error opening file browser \nHere is the error message that was created:\n" + e.getMessage(), "Unknown Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "The Chat Panel directory can't be found in /.runelite/\nThis might be caused by a permission issue.\nYou can try creating the /.runelite/chat-panel/ directory manually.", "Directory Not Found", JOptionPane.ERROR_MESSAGE);
            fontLoadErrorShown = true;
        }
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

    private static final String[] Identifiers = {"Public - ", "Clan - ", "Friends Chat - ", "ClanGuest - ", "ClanGIM - ", "ModChat - ", "ModPrivate - "};
    private Color NameColor(JTextPane chatArea, String cleanedName) {
        if (config.enableMyNameColor()) {
            String baseName = cleanedName;
            for (String identifier : Identifiers) {
                if (cleanedName.startsWith(identifier)) {
                    baseName = cleanedName.substring(identifier.length()).trim();
                    break;
                }
            }
            if (client.getLocalPlayer()!=null) {
                if (Objects.equals(client.getLocalPlayer().getName(), baseName)) {
                    return config.myNameColor();
                }
            }
        }

        if (chatArea == publicChatArea) {
            return config.publicChatNameColor();
        } else if (chatArea == privateChatArea) {
            return config.privateChatNameColor();
        } else if (chatArea == clanChatArea) {
            return config.clanChatNameColor();
        } else if (chatArea == friendsChatArea) {
            return config.friendsChatNameColor();
        } else if (chatArea == gameChatArea) {
            return config.gameChatNameColor();
        } else if (chatArea == allChatArea) {
            return config.allChatNameColor();
        } else if (chatArea == customChatArea) {
            return config.customChatNameColor();
        } else if (chatArea == customChatArea2) {
            return config.custom2ChatNameColor();
        } else if (chatArea == customChatArea3) {
            return config.custom3ChatNameColor();
        } else if (chatArea == combatArea) {
            return config.combatLabelColor();
        }
        return (Color.YELLOW);
    }

    private Color TimestampColor(JTextPane chatArea) {
        if (chatArea == publicChatArea) {
            return config.publicChatTimestampColor();
        } else if (chatArea == privateChatArea) {
            return config.privateChatTimestampColor();
        } else if (chatArea == clanChatArea) {
            return config.clanChatTimestampColor();
        } else if (chatArea == friendsChatArea) {
            return config.friendsChatTimestampColor();
        } else if (chatArea == gameChatArea) {
            return config.gameChatTimestampColor();
        } else if (chatArea == allChatArea) {
            return config.allChatTimestampColor();
        } else if (chatArea == customChatArea) {
            return config.customChatTimestampColor();
        } else if (chatArea == customChatArea2) {
            return config.custom2ChatTimestampColor();
        } else if (chatArea == customChatArea3) {
            return config.custom3ChatTimestampColor();
        } else if (chatArea == combatArea) {
            return config.combatTimestampColor();
        }
        return Color.YELLOW;
    }


    private Color adjustColor(Color color, int offset) {
        int red = Math.max(0, Math.min(255, color.getRed() + (offset * 255 / 100)));
        int green = Math.max(0, Math.min(255, color.getGreen() + (offset * 255 / 100)));
        int blue = Math.max(0, Math.min(255, color.getBlue() + (offset * 255 / 100)));
        return new Color(red, green, blue);
    }

    private void highlightWords(String message, String[] highlightWords, Color highlightColor, boolean partialMatching, StyledDocument doc) {
        List<String> highlightWordsList = new ArrayList<>();
        for (String word : highlightWords) {
            if (!word.trim().isEmpty()) {
                String escapedWord = Pattern.quote(word);
                highlightWordsList.add(escapedWord);
            }
        }
        Pattern pattern = Pattern.compile((partialMatching? "" : "\\b") + "(" + String.join("|", highlightWordsList) + ")" + (partialMatching? "" : "\\b"), Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(message);
        int start = 0;
        while (matcher.find()) {
            String matchedWord = matcher.group();
            int startIndex = doc.getLength() - message.length() + matcher.start();
            int endIndex = startIndex + matchedWord.length();
            SimpleAttributeSet highlightAttrs = new SimpleAttributeSet();
            StyleConstants.setForeground(highlightAttrs, highlightColor);
            doc.setCharacterAttributes(startIndex, matchedWord.length(), highlightAttrs, false);
        }
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
            timestampColor = isOddLine ? adjustColor(TimestampColor(chatArea), config.chatColorOffset()) : TimestampColor(chatArea);
            StyleConstants.setForeground(timestampAttrs, timestampColor);

            SimpleAttributeSet nameAttrs = new SimpleAttributeSet();
            Color nameColor = isOddLine? adjustColor(NameColor(chatArea, cleanedName), config.chatColorOffset()) : NameColor(chatArea, cleanedName);
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

                doc.insertString(doc.getLength(), message, messageAttrs);

                if (!config.highlightWords3().trim().isEmpty()) {
                    String[] highlightWords3Array = config.highlightWords3().split("\\s*,\\s*");
                    highlightWords(message, highlightWords3Array, config.highlightColor3(), config.PartialMatching(), doc);
                }
                if (!config.highlightWords2().trim().isEmpty()) {
                    String[] highlightWords2Array = config.highlightWords2().split("\\s*,\\s*");
                    highlightWords(message, highlightWords2Array, config.highlightColor2(), config.PartialMatching(), doc);
                }
                if (!config.highlightWords().trim().isEmpty()) {
                    String[] highlightWordsArray = config.highlightWords().split("\\s*,\\s*");
                    highlightWords(message, highlightWordsArray, config.highlightColor(), config.PartialMatching(), doc);
                }

                doc.insertString(doc.getLength(), "\n", null);

                for (int i = 0; i < extraLines; i++) {
                    doc.insertString(doc.getLength(), "\n", null);
                }

                int excess = doc.getDefaultRootElement().getElementCount() - config.maxLines();
                if (excess > 1) {
                    try {
                        Element root = doc.getDefaultRootElement();
                        int linesToRemove = (excess + extraLines);
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