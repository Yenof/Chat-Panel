package com.chatpanel;

import net.runelite.client.config.*;

import java.awt.*;

@ConfigGroup("chatpanel")
public interface ChatPanelConfig extends Config {

    enum FontFamily {
        NORMAL("-Default-"),
        FONT7("Avara"),
        FONT6("December Show"),
        FONT4("Fonarto"),
        FONT8("Funtype"),
        FONT5("Home Video"),
        FONT2("Mr. Pixel"),
        FONT3("Qaz"),
        SUPERFUNKY("Super Funky"),
        CUSTOM_FONT("-Custom Font-");
        private final String name;
        FontFamily(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

    }
    enum FontStyle {
        PLAIN("Plain"),
        BOLD("Bold"),
        ITALIC("Italic"),
        ITALIC_BOLD("Italic Bold");
        private final String name;
        FontStyle(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

    }

    enum ExportLogDate {
        dd_MM_yy(" dd_MM_yy"),
        MM_dd_yy(" MM_dd_yy"),
        dd_MM(" dd_MM"),
        MM_dd(" MM_dd");
        private final String name;
        ExportLogDate(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

    }

    @ConfigSection(
            name = "General",
            description = "General settings that affect all tabs",
            closedByDefault = true,
            position = 0
    )
    String generalSection = "general";

    @ConfigSection(
            name = "Public Chat",
            description = "Settings for the Public Chat tab",
            closedByDefault = true,
            position = 11
    )
    String publicChatSection = "publicChat";

    @ConfigSection(
            name = "Private Chat",
            description = "Settings for the Private Chat tab",
            closedByDefault = true,
            position = 10
    )
    String privateChatSection = "privateChat";

    @ConfigSection(
            name = "Clan Chat",
            description = "Settings for the Clan Chat tab",
            closedByDefault = true,
            position = 6
    )
    String clanChatSection = "clanChat";

    @ConfigSection(
            name = "Friends Chat",
            description = "Settings for the Friends Chat tab",
            closedByDefault = true,
            position = 8
    )
    String friendsChatSection = "friendsChat";

    @ConfigSection(
            name = "Game Chat",
            description = "Settings for the Game Chat tab",
            closedByDefault = true,
            position = 9
    )
    String gameChatSection = "gameChat";

    @ConfigSection(
            name = "All Chat",
            description = "Settings for the All Chat tab, not all of the chat.",
            closedByDefault = true,
            position = 5
    )
    String allChatSection = "allChat";

    @ConfigSection(
            name = "Custom Chat",
            description = "Settings for the Custom Chat tab, select all desired chat channels to be displayed. Long list.",
            closedByDefault = true,
            position = 12
    )
    String customChatSection = "customChat";

    @ConfigSection(
            name = "Custom Chat 2",
            description = "Settings for the second Custom Chat tab, select all desired chat channels to be displayed. Long list.",
            closedByDefault = true,
            position = 13
    )
    String custom2ChatSection = "custom2Chat";

    @ConfigSection(
            name = "Custom Chat 3",
            description = "Settings for the third Custom Chat tab, select all desired chat channels to be displayed. Long list.",
            closedByDefault = true,
            position = 14
    )
    String custom3ChatSection = "custom3Chat";

    @ConfigSection(
            name = "Combat",
            description = "Settings for the Combat tab. (Goblin hits Player for: 2), If target unknown: (Player was hit for: 1). Relies on what you are targeting.",
            closedByDefault = true,
            position = 7
    )
    String combatSection = "combat";

    @ConfigSection(
            name = "Pop Out Window",
            description = "Settings for the pop out window",
            closedByDefault = true,
            position = 1
    )
    String popoutSection = "popoutwindow";

    @ConfigSection(
            name = "Highlighting",
            description = "Word highlighting settings",
            closedByDefault = true,
            position = 3
    )
    String highlightingSection = "highlightingSection";

    @ConfigSection(
            name = "Tabs",
            description = "Tab selection. (Recommended 4)",
            closedByDefault = true,
            position = 4
    )
    String tabSection = "tabselection";

    @ConfigSection(
            name = "Message Type Coloring",
            description = "Override colors by chat message type. Affects all Chat Tabs.",
            closedByDefault = true,
            position = 15
    )
    String eventSection = "events";
    
    @ConfigSection(
            name = "Extras",
            description = "Additional settings and tweaks",
            closedByDefault = true,
            position = 16
    )
    String extrasSection = "extras";

    @Range(min = -100, max = 100)
    @ConfigItem(
            keyName = "chatColorOffset",
            name = "Odd Row Shading",
            description = "Tints the colors of odd rows. Negative numbers darken, positive brighten. (Min -100, max 100).",
            section = generalSection,
            position = 1
    )
    default int chatColorOffset() {
        return 0;
    }

    @ConfigItem(
            keyName = "fontFamily",
            name = "Font",
            description = "Choose the font for the Chat Panel. <br>You can place a font file named customfont.ttf into /.runelite/chat-panel/ to use your own font. <br>See README for more info.",
            section = generalSection,
            position = 4
    )
    default FontFamily fontFamily()
    {
        return FontFamily.NORMAL;
    }

    @ConfigItem(
            keyName = "fontStyle",
            name = "Font Style",
            description = "Choose the font style for the chat panel",
            section = generalSection,
            position = 5
    )
    default FontStyle fontStyle()
    {
        return FontStyle.PLAIN;
    }

    @ConfigItem(
            keyName = "TimestampFormat",
            name = "Timestamp Format",
            description = "Enter a custom format (e.g. HH:mm. Sets to this on incorrect entry). Many more options for SimpleDateFormat if searched online",
            section = generalSection,
            position = 7
    )
    default String TimestampFormat()
    {
        return "";
    }

    @Range(min = 50, max = 5000)
    @ConfigItem(
            keyName = "chatAreaHeight",
            name = "Chat Area Height",
            description = "Configures the height of the chat area of the side panel. Recommended below window height",
            section = extrasSection,
            position = 3
    )
    default int chatAreaHeight()
    {
        return 435;
    }

    @Range(max = 50)
    @ConfigItem(
            keyName = "iconPosition",
            name = "Icon Position",
            description = "Set the priority for the sidebar icon's position. (Requires plugin restart)",
            position = 2,
            section = extrasSection
    )
    default int iconPosition()
    {
        return 5;
    }

    @ConfigItem(
            keyName = "hideSidebarIcon",
            name = "Hide Sidebar Icon",
            description = "ONLY use with Auto-Pop out and if you REALLY don't want a sidebar icon. Toggle plugin on/off to restore pop out window if closed. (Requires plugin restart)",
            section = extrasSection,
            position = 3
    )
    default boolean hideSidebarIcon()
    {
        return false;
    }

    @ConfigItem(
            keyName = "publicChatColor",
            name = "Public Chat Color",
            description = "Configures the font color of the public chat messages.",
            section = publicChatSection,
            position = 1
    )
    default Color publicChatColor()
    {
        return new Color(0xF1FF00);
    }

    @ConfigItem(
            keyName = "publicChatBackground",
            name = "Public Chat Background",
            description = "Configures the background color of the public chat.",
            section = publicChatSection,
            position = 0

    )
    default Color publicChatBackground()
    {
        return new Color(0x282828);
    }

    @ConfigItem(
            keyName = "publicChatNameColor",
            name = "Public Chat Name Color",
            description = "Configures the player name color for public chat.",
            section = publicChatSection,
            position = 2
    )
    default Color publicChatNameColor()
    {
        return publicChatColor();
    }

    @ConfigItem(
            keyName = "publicChatTimestampColor",
            name = "Public Chat Timestamp Color",
            description = "Configures the timestamp color for public chat.",
            section = publicChatSection,
            position = 2
    )
    default Color publicChatTimestampColor()
    {
        return publicChatColor();
    }

    @Range(min = 5, max = 200)
    @ConfigItem(
            keyName = "publicChatFontSize",
            name = "Public Chat Font Size",
            description = "Configures the font size of the public chat messages.",
            section = publicChatSection,
            position = 3
    )
    default int publicChatFontSize()
    {
        return 10;
    }

    @ConfigItem(
            keyName = "privateChatColor",
            name = "Private Chat Color",
            description = "Configures the font color of the private chat messages.",
            section = privateChatSection,
            position = 1
    )
    default Color privateChatColor()
    {
        return new Color(0x0AFF00);
    }

    @ConfigItem(
            keyName = "privateChatBackground",
            name = "Private Chat Background",
            description = "Configures the background color of the private chat.",
            section = privateChatSection,
            position = 0
    )
    default Color privateChatBackground()
    {
        return new Color(0x282828);
    }

    @ConfigItem(
            keyName = "privateChatNameColor",
            name = "Private Chat Name Color",
            description = "Configures the player name color for private chat.",
            section = privateChatSection,
            position = 2
    )
    default Color privateChatNameColor()
    {
        return privateChatColor();
    }

    @ConfigItem(
            keyName = "privateChatTimestampColor",
            name = "Private Chat Timestamp Color",
            description = "Configures the timestamp color for private chat.",
            section = privateChatSection,
            position = 2
    )
    default Color privateChatTimestampColor()
    {
        return privateChatColor();
    }

    @Range(min = 5, max = 200)
    @ConfigItem(
            keyName = "privateChatFontSize",
            name = "Private Chat Font Size",
            description = "Configures the font size of the private chat messages.",
            section = privateChatSection,
            position = 3
    )
    default int privateChatFontSize()
    {
        return 14;
    }

    @ConfigItem(
            keyName = "clanChatColor",
            name = "Clan Chat Color",
            description = "Configures the font color of the clan chat messages.",
            section = clanChatSection,
            position = 1
    )
    default Color clanChatColor()
    {
        return new Color(0x007DFF);
    }

    @ConfigItem(
            keyName = "clanChatBackgroundColor",
            name = "Clan Chat Background",
            description = "Configures the background color of the clan chat.",
            section = clanChatSection,
            position = 0
    )
    default Color clanChatBackgroundColor()
    {
        return new Color(0x282828);
    }

    @ConfigItem(
            keyName = "clanChatNameColor",
            name = "Clan Chat Name Color",
            description = "Configures the player name color for clan chat.",
            section = clanChatSection,
            position = 2
    )
    default Color clanChatNameColor()
    {
        return clanChatColor();
    }

    @ConfigItem(
            keyName = "clanChatTimestampColor",
            name = "Clan Chat Timestamp Color",
            description = "Configures the timestamp color for clan chat.",
            section = clanChatSection,
            position = 2
    )
    default Color clanChatTimestampColor()
    {
        return clanChatColor();
    }

    @Range(min = 5, max = 200)
    @ConfigItem(
            keyName = "clanChatFontSize",
            name = "Clan Chat Font Size",
            description = "Configures the font size of the clan chat messages.",
            section = clanChatSection,
            position = 3
    )
    default int clanChatFontSize()
    {
        return 12;
    }

    @ConfigItem(
            keyName = "friendsChatColor",
            name = "Friends Chat Color",
            description = "Configures the font color of the Friends Chat messages.",
            section = friendsChatSection,
            position = 1
    )
    default Color friendsChatColor()
    {
        return new Color(0x28FF71);
    }

    @ConfigItem(
            keyName = "friendsChatBackground",
            name = "Friends Chat Background",
            description = "Configures the background color of the Friends Chat.",
            section = friendsChatSection,
            position = 0
    )
    default Color friendsChatBackground()
    {
        return new Color(0x282828);
    }

    @ConfigItem(
            keyName = "friendsChatNameColor",
            name = "Friends Chat Name Color",
            description = "Configures the player name color for friends chat.",
            section = friendsChatSection,
            position = 2
    )
    default Color friendsChatNameColor()
    {
        return friendsChatColor();
    }

    @ConfigItem(
            keyName = "friendsChatTimestampColor",
            name = "Friends Chat Timestamp Color",
            description = "Configures the timestamp color for friends chat.",
            section = friendsChatSection,
            position = 2
    )
    default Color friendsChatTimestampColor()
    {
        return friendsChatColor();
    }

    @Range(min = 5, max = 200)
    @ConfigItem(
            keyName = "friendsChatFontSize",
            name = "Friends Chat Font Size",
            description = "Configures the font size of the Friends Chat messages.",
            section = friendsChatSection,
            position = 3
    )
    default int friendsChatFontSize()
    {
        return 10;
    }

    @ConfigItem(
            keyName = "gameChatColor",
            name = "Game Chat Color",
            description = "Configures the font color of the game chat messages.",
            section = gameChatSection,
            position = 1
    )
    default Color gameChatColor()
    {
        return new Color(0xFFFFFF);
    }

    @ConfigItem(
            keyName = "gameChatBackgroundColor",
            name = "Game Chat Background",
            description = "Configures the background color of the game chat.",
            section = gameChatSection,
            position = 0
    )
    default Color gameChatBackgroundColor()
    {
        return new Color(0x282828);
    }

    @ConfigItem(
            keyName = "gameChatNameColor",
            name = "Game Chat Name Color",
            description = "Configures the name color for game chat.",
            section = gameChatSection,
            position = 2
    )
    default Color gameChatNameColor()
    {
        return gameChatColor();
    }

    @ConfigItem(
            keyName = "gameChatTimestampColor",
            name = "Game Chat Timestamp Color",
            description = "Configures the timestamp color for game chat.",
            section = gameChatSection,
            position = 2
    )
    default Color gameChatTimestampColor()
    {
        return gameChatColor();
    }

    @Range(min = 5, max = 200)
    @ConfigItem(
            keyName = "gameChatFontSize",
            name = "Game Chat Font Size",
            description = "Configures the font size of the game chat messages.",
            section = gameChatSection,
            position = 3
    )
    default int gameChatFontSize()
    {
        return 12;
    }

    @ConfigItem(
            keyName = "allChatColor",
            name = "All Chat Color",
            description = "Configures the font color of the All Chat messages.",
            section = allChatSection,
            position = 1
    )
    default Color allChatColor()
    {
        return new Color(0xFFFFFF);
    }

    @ConfigItem(
            keyName = "allChatBackground",
            name = "All Chat Background",
            description = "Configures the background color of the All Chat.",
            section = allChatSection,
            position = 0
    )
    default Color allChatBackground()
    {
        return new Color(0x282828);
    }

    @ConfigItem(
            keyName = "allChatNameColor",
            name = "All Chat Name Color",
            description = "Configures the name color for all chat.",
            section = allChatSection,
            position = 2
    )
    default Color allChatNameColor()
    {
        return allChatColor();
    }

    @ConfigItem(
            keyName = "allChatTimestampColor",
            name = "All Chat Timestamp Color",
            description = "Configures the timestamp color for all chat.",
            section = allChatSection,
            position = 2
    )
    default Color allChatTimestampColor()
    {
        return allChatColor();
    }

    @Range(min = 5, max = 200)
    @ConfigItem(
            keyName = "allChatFontSize",
            name = "All Chat Font Size",
            description = "Configures the font size of the All Chat messages.",
            section = allChatSection,
            position = 3
    )
    default int allChatFontSize()
    {
        return 10;
    }

    @ConfigItem(
            keyName = "Customtab1",
            name = "Name of Tab",
            description = "Configures the display name of the tab. (Requires plugin restart, or hide & show tab)",
            section = customChatSection,
            position = 0
    )
    default String custom1Tabname()
    {
        return "Custom";
    }

    @ConfigItem(
            keyName = "customChatColor",
            name = "Text Color",
            description = "Configures the font color.",
            section = customChatSection,
            position = 2
    )
    default Color customChatColor()
    {
        return new Color(0xFFFFFF);
    }

    @ConfigItem(
            keyName = "customChatBackgroundColor",
            name = "Background Color",
            description = "Configures the background color.",
            section = customChatSection,
            position = 1
    )
    default Color customChatBackgroundColor()
    {
        return new Color(0x282828);
    }

    @ConfigItem(
            keyName = "customChatNameColor",
            name = "Name Color",
            description = "Configures the name color.",
            section = customChatSection,
            position = 3
    )
    default Color customChatNameColor()
    {
        return customChatColor();
    }

    @ConfigItem(
            keyName = "customChatTimestampColor",
            name = "Timestamp Color",
            description = "Configures the timestamp color.",
            section = customChatSection,
            position = 3
    )
    default Color customChatTimestampColor()
    {
        return customChatColor();
    }

    @Range(min = 5, max = 200)
    @ConfigItem(
            keyName = "customChatFontSize",
            name = "Font Size",
            description = "Configures the font size.",
            section = customChatSection,
            position = 4
    )
    default int customChatFontSize()
    {
        return 12;
    }

    @ConfigItem(
            keyName = "identifier1",
            name = "Identify Sources of Messages",
            description = "Show sources of messages (e.g. [Clan - Player]: Hello!)",
            section = customChatSection,
            position = 5
    )
    default boolean identifier1()
    {
        return false;
    }

    @ConfigItem(
            keyName = "Custom2tab",
            name = "Name of Tab",
            description = "Configures the display name of the tab. (Requires plugin restart, or hide & show tab)",
            section = custom2ChatSection,
            position = 0
    )
    default String custom2Tabname()
    {
        return "Custom 2";
    }

    @ConfigItem(
            keyName = "custom2ChatColor",
            name = "Text Color",
            description = "Configures the font color.",
            section = custom2ChatSection,
            position = 2
    )
    default Color custom2ChatColor()
    {
        return new Color(0xFFFFFF);
    }

    @ConfigItem(
            keyName = "custom2ChatBackgroundColor",
            name = "Background Color",
            description = "Configures the background color.",
            section = custom2ChatSection,
            position = 1
    )
    default Color custom2ChatBackgroundColor()
    {
        return new Color(0x282828);
    }

    @ConfigItem(
            keyName = "custom2ChatNameColor",
            name = "Name Color",
            description = "Configures the name color.",
            section = custom2ChatSection,
            position = 3
    )
    default Color custom2ChatNameColor()
    {
        return custom2ChatColor();
    }

    @ConfigItem(
            keyName = "custom2ChatTimestampColor",
            name = "Timestamp Color",
            description = "Configures the timestamp color.",
            section = custom2ChatSection,
            position = 3
    )
    default Color custom2ChatTimestampColor()
    {
        return custom2ChatColor();
    }

    @Range(min = 5, max = 200)
    @ConfigItem(
            keyName = "custom2ChatFontSize",
            name = "Font Size",
            description = "Configures the font size.",
            section = custom2ChatSection,
            position = 4
    )
    default int custom2ChatFontSize()
    {
        return 12;
    }

    @ConfigItem(
            keyName = "identifier2",
            name = "Identify Sources of Messages",
            description = "Show sources of messages (e.g. [Clan - Player]: Hello!)",
            section = custom2ChatSection,
            position = 5
    )
    default boolean identifier2()
    {
        return false;
    }

    @ConfigItem(
            keyName = "Custom3tab",
            name = "Name of Tab",
            description = "Configures the display name of the tab. (Requires plugin restart, or hide & show tab)",
            section = custom3ChatSection,
            position = 0
    )
    default String custom3Tabname()
    {
        return "Custom 3";
    }

    @ConfigItem(
            keyName = "custom3ChatColor",
            name = "Text Color",
            description = "Configures the font color.",
            section = custom3ChatSection,
            position = 2
    )
    default Color custom3ChatColor()
    {
        return new Color(0xFFFFFF);
    }

    @ConfigItem(
            keyName = "custom3ChatBackgroundColor",
            name = "Background Color",
            description = "Configures the background color.",
            section = custom3ChatSection,
            position = 1
    )
    default Color custom3ChatBackgroundColor()
    {
        return new Color(0x282828);
    }

    @ConfigItem(
            keyName = "custom3ChatNameColor",
            name = "Name Color",
            description = "Configures the name color.",
            section = custom3ChatSection,
            position = 3
    )
    default Color custom3ChatNameColor()
    {
        return custom3ChatColor();
    }

    @ConfigItem(
            keyName = "custom3ChatTimestampColor",
            name = "Timestamp Color",
            description = "Configures the timestamp color.",
            section = custom3ChatSection,
            position = 3
    )
    default Color custom3ChatTimestampColor()
    {
        return custom3ChatColor();
    }

    @Range(min = 5, max = 200)
    @ConfigItem(
            keyName = "custom3ChatFontSize",
            name = "Font Size",
            description = "Configures the font size.",
            section = custom3ChatSection,
            position = 4
    )
    default int custom3ChatFontSize()
    {
        return 12;
    }

    @ConfigItem(
            keyName = "identifier3",
            name = "Identify Sources of Messages",
            description = "Show sources of messages (e.g. [Clan - Player]: Hello!)",
            section = custom3ChatSection,
            position = 5
    )
    default boolean identifier3()
    {
        return false;
    }

    @ConfigItem(
            keyName = "combatTextColor",
            name = "Text Color",
            description = "Configures the font color.",
            section = combatSection,
            position = 2
    )
    default Color combatTextColor()
    {
        return new Color(0xFFFFFF);
    }

    @ConfigItem(
            keyName = "combatBackgroundColor",
            name = "Background Color",
            description = "Configures the background color.",
            section = combatSection,
            position = 1
    )
    default Color combatBackgroundColor()
    {
        return new Color(0x282828);
    }

    @ConfigItem(
            keyName = "combatLabelColor",
            name = "Label Color",
            description = "Configures the color of the labels in this tab (e.g. Combat, Death).",
            section = combatSection,
            position = 3
    )
    default Color combatLabelColor()
    {
        return combatTextColor();
    }

    @ConfigItem(
            keyName = "combatTimestampColor",
            name = "Timestamp Color",
            description = "Configures the timestamp color.",
            section = combatSection,
            position = 3
    )
    default Color combatTimestampColor()
    {
        return combatTextColor();
    }

    @ConfigItem(
            keyName = "onlyshowMyHitsplats",
            name = "Only Show My Combat Events",
            description = "Show only damage events related to you, or shows all nearby player's events.",
            section = combatSection,
            position = 5
    )
    default boolean onlyshowMyHitsplats()
    {
        return true;
    }

    @ConfigItem(
            keyName = "hidezerodamageHitsplats",
            name = "Hide Zero Damage Events",
            description = "Hide when things hit 0's",
            section = combatSection,
            position = 7
    )
    default boolean hidezerodamageHitsplats()
    {
        return false;
    }

    @Range(min = 5, max = 200)
    @ConfigItem(
            keyName = "combatFontSize",
            name = "Font Size",
            description = "Configures the font size.",
            section = combatSection,
            position = 4
    )
    default int combatFontSize()
    {
        return 12;
    }

    @ConfigItem(
            keyName = "displayDeaths",
            name = "Show Deaths",
            description = "Displays a message when things die",
            section = combatSection,
            position = 6
    )
    default boolean displayDeaths()
    {
        return true;
    }

    @ConfigItem(
            keyName = "identifierC",
            name = "Add Combat Labels",
            description = "Add labels (Combat, Death) before messages",
            section = combatSection,
            position = 12
    )
    default boolean identifierC()
    {
        return false;
    }

    @Range(max = 10)
    @ConfigItem(
            keyName = "lineSpacing",
            name = "Line Spacing",
            description = "Adjust the spacing between chat messages",
            section = generalSection,
            position = 2

    )
    default int lineSpacing()
    {
        return 0;
    }

    @ConfigItem(
            keyName = "popoutAlwaysOnTop",
            name = "Always on Top",
            description = "Keep the pop out window always on top of other windows",
            section = popoutSection,
            position = 2
    )
    default boolean popoutAlwaysOnTop()
    {
        return false;
    }

    @ConfigItem(
            keyName = "popoutSize",
            name = "Size",
            description = "Set the initial size of the pop out window",
            section = popoutSection,
            position = 0
    )
    default Dimension popoutSize()
    {
        return new Dimension(300, 400);
    }

    @Range(min = 10, max = 100)
    @ConfigItem(
            keyName = "PopoutOpacity",
            name = "Window Opacity",
            description = "Set the pop out window's opacity",
            position = 1,
            section = popoutSection
    )
    default int popoutOpacity()
    {
        return 100;
    }

    @ConfigItem(
            keyName = "PopoutWarning",
            name = "Hide Pop Out Closure Warning",
            description = "Show the warning on closing the pop out window with Hide Sidebar Icon on",
            section = extrasSection,
            position = 6
    )
    default boolean hidePopoutWarning()
    {
        return false;
    }

    @ConfigItem(
            keyName = "AutoPop",
            name = "Auto-Pop Out Window",
            description = "Pop out window opens automatically when plugin turned on, including on launch",
            position = 5,
            section = popoutSection
    )
    default boolean AutoPop()
    {
        return false;
    }

    @ConfigItem(
            keyName = "rememberPopoutPosition",
            name = "Remember Pop Out Position",
            description = "Remember the position and size of the pop out window between launches.<br>If you lose track of your pop out window, turn this setting off and pop in and out.",
            section = popoutSection,
            position = 7
    )
    default boolean rememberPopoutPosition() {
        return false;
    }

    @ConfigItem(
            keyName = "DisablePopOut",
            name = "Hide Pop Out/Pop In Buttons",
            description = "Hides pop out button and pop in button, except in empty side panel. (Requires plugin restart)",
            position = 5,
            section = extrasSection
    )
    default boolean hidepopoutButtons()
    {
        return false;
    }

    @ConfigItem(
            keyName = "showPublicChat",
            name = "Show Public Chat",
            description = "Show/hide the Public Chat tab",
            section = tabSection,
            position = 6
    )
    default boolean showPublicChat()
    {
        return true;
    }

    @ConfigItem(
            keyName = "showPrivateChat",
            name = "Show Private Chat",
            description = "Show/hide the Private Chat tab",
            section = tabSection,
            position = 5
    )
    default boolean showPrivateChat()
    {
        return true;
    }

    @ConfigItem(
            keyName = "showClanChat",
            name = "Show Clan Chat",
            description = "Show/hide the Clan Chat tab",
            section = tabSection,
            position = 1
    )
    default boolean showClanChat()
    {
        return true;
    }

    @ConfigItem(
            keyName = "showGameChat",
            name = "Show Game Chat",
            description = "Show/hide the Game tab",
            section = tabSection,
            position = 4
    )
    default boolean showGameChat()
    {
        return true;
    }

    @ConfigItem(
            keyName = "showAllChat",
            name = "Show All Chat",
            description = "Show/hide the All Chat tab",
            section = tabSection,
            position = 0
    )
    default boolean showAllChat()
    {
        return false;
    }

    @ConfigItem(
            keyName = "showFriendsChat",
            name = "Show Friends Chat Channel",
            description = "Show/hide the Friends Chat tab",
            section = tabSection,
            position = 3
    )
    default boolean showFriendsChat()
    {
        return false;
    }

    @ConfigItem(
            keyName = "showCustomChat",
            name = "Show Custom Chat",
            description = "Show/hide the Custom Chat tab, have fun!",
            section = tabSection,
            position = 9
    )
    default boolean showCustomChat()
    {
        return false;
    }

    @ConfigItem(
            keyName = "showCustom2Chat",
            name = "Show Custom Chat 2",
            description = "Show/hide the second Custom Chat tab, have more fun!",
            section = tabSection,
            position = 10
    )
    default boolean showCustom2Chat()
    {
        return false;
    }

    @ConfigItem(
            keyName = "showCustom3Chat",
            name = "Show Custom Chat 3",
            description = "Show/hide the third Custom Chat tab, have all the fun!",
            section = tabSection,
            position = 11
    )
    default boolean showCustom3Chat()
    {
        return false;
    }

    @ConfigItem(
            keyName = "showCombatTab",
            name = "Show Combat Tab",
            description = "Show/hide Combat tab. (Goblin hits Player for: 2), If target unknown: (Player was hit for: 1) <br> Provides very basic combat logging, there are other plugins for more advanced logging",
            section = tabSection,
            position = 2
    )
    default boolean showCombatTab()
    {
        return false;
    }

    @Range(min = 20, max = 200000)
    @ConfigItem(
            keyName = "maxchatlines",
            name = "Max Lines",
            description = "Max lines to display in each tab, starts getting laggy above 15,000 but feel free. (Min 20, Max 200,000)",
            section = extrasSection,
            position = 0
    )
    default int maxLines()
    {
        return 10000;
    }

	@ConfigItem(
		keyName = "enableMyName",
		name = "Enable My Name Color",
		description = "Enables recoloring your username, overriding chat colors. Select color below.",
		section = extrasSection,
		position = 8
	)
	default boolean enableMyNameColor()
	{
		return false;
	}

	@ConfigItem(
		keyName = "myNameColor",
		name = "My Name Color",
		description = "Configures the color of your username, overriding name color for chat tabs. Requires Enable My Name Color to be checked.",
		section = extrasSection,
		position = 9
	)
	default Color myNameColor()
	{
		return new Color(0xFFFFFF);
	}

    @ConfigItem(
            keyName = "hidePopoutIcon",
            name = "Hide Title Bar Icon",
            description = "Removes the icon from the title bar of the pop out windows, if they have one. Pop in and out to apply.",
            section = extrasSection,
            position = 4
    )
    default boolean hidePopoutIcon()
    {
        return false;
    }

    @ConfigItem(
            keyName = "exportLogDate",
            name = "Export Log Date",
            description = "Choose the date format for Export Log.",
            section = extrasSection,
            position = 7
    )
    default ExportLogDate exportLogDate()
    {
        return ExportLogDate.dd_MM_yy;
    }

	@ConfigItem(
		keyName = "highlightWords",
		name = "Highlight Words",
		description = "List of words to highlight, separated by commas.",
		section = highlightingSection,
		position = 1
	)
	default String highlightWords()
	{
		return "";
	}

    @ConfigItem(
            keyName = "highlightColor",
            name = "Highlight Color",
            description = "Configures the color of highlighted words.",
            section = highlightingSection,
            position = 4
    )
    default Color highlightColor()
    {
        return new Color(0x00FF0A);
    }

    @ConfigItem(
            keyName = "highlightWords2",
            name = "Highlight Words 2",
            description = "Second list of words to highlight, separated by commas.",
            section = highlightingSection,
            position = 6
    )
    default String highlightWords2()
    {
        return "";
    }

    @ConfigItem(
            keyName = "highlightColor2",
            name = "Highlight Color 2",
            description = "Configures the color of Highlight Words 2.",
            section = highlightingSection,
            position = 7
    )
    default Color highlightColor2()
    {
        return new Color(0xFFA07A);
    }


    @ConfigItem(
            keyName = "highlightWords3",
            name = "Highlight Words 3",
            description = "Third list of words to highlight, separated by commas.",
            section = highlightingSection,
            position = 9
    )
    default String highlightWords3()
    {
        return "";
    }

    @ConfigItem(
            keyName = "highlightColor3",
            name = "Highlight Color 3",
            description = "Configures the color of Highlight Words 3.",
            section = highlightingSection,
            position = 10
    )
    default Color highlightColor3()
    {
        return new Color(0xA0FF7A);
    }

    @ConfigItem(
            keyName = "partialMatching",
            name = "Partial Word Highlighting",
            description = "Allows partial matches of highlight words. Sell would highlight like: <span style='color:yellow'>Sell</span>ing",
            section = highlightingSection,
            position = 12
    )
    default boolean PartialMatching()
    {
        return true;
    }



    // Start of hidden entries



    @ConfigItem(
            keyName = "versionNumber",
            name = "Get Version",
            description = "",
            hidden = true
    )
    default double getVersion()
    {
        return 0;
    }

    @ConfigItem(
            keyName = "versionNumber",
            name = "Set Version",
            description = "The version when the update message was last shown.",
            hidden = true
    )
    void setVersion(double version);

    @ConfigItem(
            keyName = "lastDir",
            name = "Get Last Directory",
            description = "",
            hidden = true
    )
    default String getLastDIR()
    {
        return"";
    }

    @ConfigItem(
            keyName = "lastDir",
            name = "Set Last Directory",
            description = "Sets the last directory used to export log.",
            hidden = true
    )
    void setLastDIR(String String);

    @ConfigItem(
            keyName = "popoutMaximized",
            name = "Get Maximized",
            description = "Get if the pop out window was maximized",
            hidden = true
    )
    default boolean isPopoutMaximized() {
        return false;
    }

    @ConfigItem(
            keyName = "popoutMaximized",
            name = "Set Maximized",
            description = "Set if the pop out window was maximized",
            hidden = true
    )
    void setPopoutMaximized(boolean maximized);

    @ConfigItem(
            keyName = "popoutBounds",
            name = "Get Bounds",
            description = "Get bounds of pop out window",
            hidden = true
    )
    default Rectangle getPopoutBounds() {
        return null;
    }

    @ConfigItem(
            keyName = "popoutBounds",
            name = "Set Bounds",
            description = "Set bounds of pop out window",
            hidden = true
    )
    void setPopoutBounds(Rectangle bounds);



    // Start of Custom Chat channels...



    @ConfigItem(
            keyName = "Broadcast",
            name = "Broadcast",
            description = "Display broadcast messages",
            section = customChatSection,
            position = 7
    )
    default boolean CustomBroadcastEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ChalreqClanChat",
            name = "Chalreq Clan Chat",
            description = "Display chalreq clan chat messages",
            section = customChatSection,
            position = 8
    )
    default boolean CustomChalreqClanChatEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ChalreqFriendsChat",
            name = "Chalreq Friends Chat",
            description = "Display chalreq friends chat messages",
            section = customChatSection,
            position = 9
    )
    default boolean CustomChalreqFriendsChatEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ChalreqTrade",
            name = "Chalreq Trade",
            description = "Display chalreq trade messages",
            section = customChatSection,
            position = 10
    )
    default boolean CustomChalreqTradeEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ClanChat",
            name = "Clan Chat",
            description = "Display clan chat messages",
            section = customChatSection,
            position = 11
    )
    default boolean CustomClanChatEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ClanGimChat",
            name = "Clan GIM Chat",
            description = "Display clan GIM chat messages",
            section = customChatSection,
            position = 12
    )
    default boolean CustomClanGimChatEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ClanGimMessage",
            name = "Clan GIM Message",
            description = "Display clan GIM messages",
            section = customChatSection,
            position = 13
    )
    default boolean CustomClanGimMessageEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ClanGuestChat",
            name = "Clan Guest Chat",
            description = "Display clan guest chat messages",
            section = customChatSection,
            position = 14
    )
    default boolean CustomClanGuestChatEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ClanGuestMessage",
            name = "Clan Guest Message",
            description = "Display clan guest messages",
            section = customChatSection,
            position = 15
    )
    default boolean CustomClanGuestMessageEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ClanMessage",
            name = "Clan Message",
            description = "Display clan messages",
            section = customChatSection,
            position = 16
    )
    default boolean CustomClanMessageEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "Combat",
            name = "Combat",
            description = "Display combat messages",
            section = customChatSection,
            position = 17
    )
    default boolean CustomCombatEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "Console",
            name = "Console",
            description = "Display console messages",
            section = customChatSection,
            position = 18
    )
    default boolean CustomConsoleEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "Dialog",
            name = "Dialog",
            description = "Display dialog messages",
            section = customChatSection,
            position = 19
    )
    default boolean CustomDialogEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "Engine",
            name = "Engine",
            description = "Display engine messages",
            section = customChatSection,
            position = 20
    )
    default boolean CustomEngineEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "FriendNotification",
            name = "Friend Notification",
            description = "Display friend notifications",
            section = customChatSection,
            position = 21
    )
    default boolean CustomFriendNotificationEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "FriendsChat",
            name = "Friends Chat",
            description = "Display friends chat messages",
            section = customChatSection,
            position = 22
    )
    default boolean CustomFriendsChatEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "FriendsChatNotification",
            name = "Friends Chat Notification",
            description = "Display friends chat notifications",
            section = customChatSection,
            position = 23
    )
    default boolean CustomFriendsChatNotificationEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "GameMessage",
            name = "Game Message",
            description = "Display game messages",
            section = customChatSection,
            position = 24
    )
    default boolean CustomGameMessageEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "IgnoreNotification",
            name = "Ignore Notification",
            description = "Display ignore notifications",
            section = customChatSection,
            position = 25
    )
    default boolean CustomIgnoreNotificationEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ItemExamine",
            name = "Item Examine",
            description = "Display item examine messages",
            section = customChatSection,
            position = 26
    )
    default boolean CustomItemExamineEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "Mesbox",
            name = "Mesbox",
            description = "Display Mesbox messages",
            section = customChatSection,
            position = 27
    )
    default boolean CustomMesboxEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ModAutoTyper",
            name = "Mod Auto Typer",
            description = "Display mod auto-typer messages",
            section = customChatSection,
            position = 27
    )
    default boolean CustomModAutoTyperEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ModChat",
            name = "Mod Chat",
            description = "Display mod chat messages",
            section = customChatSection,
            position = 28
    )
    default boolean CustomModChatEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ModPrivateChat",
            name = "Mod Private Chat",
            description = "Display mod private chat messages",
            section = customChatSection,
            position = 29
    )
    default boolean CustomModPrivateChatEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "NpcExamine",
            name = "NPC Examine",
            description = "Display NPC examine messages",
            section = customChatSection,
            position = 30
    )
    default boolean CustomNpcExamineEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "NpcSay",
            name = "NPC Say",
            description = "Display NPC Say messages",
            section = customChatSection,
            position = 30
    )
    default boolean CustomNpcSayEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ObjectExamine",
            name = "Object Examine",
            description = "Display object examine messages",
            section = customChatSection,
            position = 31
    )
    default boolean CustomObjectExamineEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "PrivateChat",
            name = "Private Chat",
            description = "Display private chat messages",
            section = customChatSection,
            position = 32
    )
    default boolean CustomPrivateChatEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "PrivateChatout",
            name = "Private Chat Out",
            description = "Display private chat out messages",
            section = customChatSection,
            position = 33
    )
    default boolean CustomPrivateChatoutEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "PublicChat",
            name = "Public Chat",
            description = "Display public chat messages",
            section = customChatSection,
            position = 34
    )
    default boolean CustomPublicChatEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "Spam",
            name = "Spam",
            description = "Display spam messages",
            section = customChatSection,
            position = 35
    )
    default boolean CustomSpamEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "Trade",
            name = "Trade",
            description = "Display trade messages",
            section = customChatSection,
            position = 36
    )
    default boolean CustomTradeEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "TradeReq",
            name = "Trade Req",
            description = "Display trade request messages",
            section = customChatSection,
            position = 37
    )
    default boolean CustomTradeReqEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "TradeSent",
            name = "Trade Sent",
            description = "Display trade sent messages",
            section = customChatSection,
            position = 38
    )
    default boolean CustomTradeSentEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "Unknown",
            name = "Unknown",
            description = "Display 'unknown' chat channel",
            section = customChatSection,
            position = 39
    )
    default boolean CustomUnknownEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "Welcome",
            name = "Welcome",
            description = "Display welcome messages",
            section = customChatSection,
            position = 40
    )
    default boolean CustomWelcomeEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "Broadcast2",
            name = "Broadcast",
            description = "Display broadcast messages",
            section = custom2ChatSection,
            position = 7
    )
    default boolean Custom2BroadcastEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ChalreqClanChat2",
            name = "Chalreq Clan Chat",
            description = "Display chalreq clan chat messages",
            section = custom2ChatSection,
            position = 8
    )
    default boolean Custom2ChalreqClanChatEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ChalreqFriendsChat2",
            name = "Chalreq Friends Chat",
            description = "Display chalreq friends chat messages",
            section = custom2ChatSection,
            position = 9
    )
    default boolean Custom2ChalreqFriendsChatEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ChalreqTrade2",
            name = "Chalreq Trade",
            description = "Display chalreq trade messages",
            section = custom2ChatSection,
            position = 10
    )
    default boolean Custom2ChalreqTradeEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ClanChat2",
            name = "Clan Chat",
            description = "Display clan chat messages",
            section = custom2ChatSection,
            position = 11
    )
    default boolean Custom2ClanChatEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ClanGimChat2",
            name = "Clan GIM Chat",
            description = "Display clan GIM chat messages",
            section = custom2ChatSection,
            position = 12
    )
    default boolean Custom2ClanGimChatEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ClanGimMessage2",
            name = "Clan GIM Message",
            description = "Display clan GIM messages",
            section = custom2ChatSection,
            position = 13
    )
    default boolean Custom2ClanGimMessageEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ClanGuestChat2",
            name = "Clan Guest Chat",
            description = "Display clan guest chat messages",
            section = custom2ChatSection,
            position = 14
    )
    default boolean Custom2ClanGuestChatEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ClanGuestMessage2",
            name = "Clan Guest Message",
            description = "Display clan guest messages",
            section = custom2ChatSection,
            position = 15
    )
    default boolean Custom2ClanGuestMessageEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ClanMessage2",
            name = "Clan Message",
            description = "Display clan messages",
            section = custom2ChatSection,
            position = 16
    )
    default boolean Custom2ClanMessageEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "Combat2",
            name = "Combat",
            description = "Display combat messages",
            section = custom2ChatSection,
            position = 17
    )
    default boolean Custom2CombatEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "Console2",
            name = "Console",
            description = "Display console messages",
            section = custom2ChatSection,
            position = 18
    )
    default boolean Custom2ConsoleEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "Dialog2",
            name = "Dialog",
            description = "Display dialog messages",
            section = custom2ChatSection,
            position = 19
    )
    default boolean Custom2DialogEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "Engine2",
            name = "Engine",
            description = "Display engine messages",
            section = custom2ChatSection,
            position = 20
    )
    default boolean Custom2EngineEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "FriendNotification2",
            name = "Friend Notification",
            description = "Display friend notifications",
            section = custom2ChatSection,
            position = 21
    )
    default boolean Custom2FriendNotificationEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "FriendsChat2",
            name = "Friends Chat",
            description = "Display friends chat messages",
            section = custom2ChatSection,
            position = 22
    )
    default boolean Custom2FriendsChatEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "FriendsChatNotification2",
            name = "Friends Chat Notification",
            description = "Display friends chat notifications",
            section = custom2ChatSection,
            position = 23
    )
    default boolean Custom2FriendsChatNotificationEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "GameMessage2",
            name = "Game Message",
            description = "Display game messages",
            section = custom2ChatSection,
            position = 24
    )
    default boolean Custom2GameMessageEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "IgnoreNotification2",
            name = "Ignore Notification",
            description = "Display ignore notifications",
            section = custom2ChatSection,
            position = 25
    )
    default boolean Custom2IgnoreNotificationEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ItemExamine2",
            name = "Item Examine",
            description = "Display item examine messages",
            section = custom2ChatSection,
            position = 26
    )
    default boolean Custom2ItemExamineEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "Mesbox2",
            name = "Mesbox",
            description = "Display Mesbox messages",
            section = custom2ChatSection,
            position = 27
    )
    default boolean Custom2MesboxEnabled()
    {
        return false;
    }
    
    @ConfigItem(
            keyName = "ModAutoTyper2",
            name = "Mod Auto Typer",
            description = "Display mod auto-typer messages",
            section = custom2ChatSection,
            position = 27
    )
    default boolean Custom2ModAutoTyperEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ModChat2",
            name = "Mod Chat",
            description = "Display mod chat messages",
            section = custom2ChatSection,
            position = 28
    )
    default boolean Custom2ModChatEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ModPrivateChat2",
            name = "Mod Private Chat",
            description = "Display mod private chat messages",
            section = custom2ChatSection,
            position = 29
    )
    default boolean Custom2ModPrivateChatEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "NpcExamine2",
            name = "NPC Examine",
            description = "Display NPC examine messages",
            section = custom2ChatSection,
            position = 30
    )
    default boolean Custom2NpcExamineEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "NpcSay2",
            name = "NPC Say",
            description = "Display NPC Say messages",
            section = custom2ChatSection,
            position = 30
    )
    default boolean Custom2NpcSayEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ObjectExamine2",
            name = "Object Examine",
            description = "Display object examine messages",
            section = custom2ChatSection,
            position = 31
    )
    default boolean Custom2ObjectExamineEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "PrivateChat2",
            name = "Private Chat",
            description = "Display private chat messages",
            section = custom2ChatSection,
            position = 32
    )
    default boolean Custom2PrivateChatEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "PrivateChatout2",
            name = "Private Chat Out",
            description = "Display private chat out messages",
            section = custom2ChatSection,
            position = 33
    )
    default boolean Custom2PrivateChatoutEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "PublicChat2",
            name = "Public Chat",
            description = "Display public chat messages",
            section = custom2ChatSection,
            position = 34
    )
    default boolean Custom2PublicChatEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "Spam2",
            name = "Spam",
            description = "Display spam messages",
            section = custom2ChatSection,
            position = 35
    )
    default boolean Custom2SpamEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "Trade2",
            name = "Trade",
            description = "Display trade messages",
            section = custom2ChatSection,
            position = 36
    )
    default boolean Custom2TradeEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "TradeReq2",
            name = "Trade Req",
            description = "Display trade request messages",
            section = custom2ChatSection,
            position = 37
    )
    default boolean Custom2TradeReqEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "TradeSent2",
            name = "Trade Sent",
            description = "Display trade sent messages",
            section = custom2ChatSection,
            position = 38
    )
    default boolean Custom2TradeSentEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "Unknown2",
            name = "Unknown",
            description = "Display 'unknown' chat channel",
            section = custom2ChatSection,
            position = 39
    )
    default boolean Custom2UnknownEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "Welcome2",
            name = "Welcome",
            description = "Display welcome messages",
            section = custom2ChatSection,
            position = 40
    )
    default boolean Custom2WelcomeEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "Broadcast3",
            name = "Broadcast",
            description = "Display broadcast messages",
            section = custom3ChatSection,
            position = 7
    )
    default boolean Custom3BroadcastEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ChalreqClanChat3",
            name = "Chalreq Clan Chat",
            description = "Display chalreq clan chat messages",
            section = custom3ChatSection,
            position = 8
    )
    default boolean Custom3ChalreqClanChatEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ChalreqFriendsChat3",
            name = "Chalreq Friends Chat",
            description = "Display chalreq friends chat messages",
            section = custom3ChatSection,
            position = 9
    )
    default boolean Custom3ChalreqFriendsChatEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ChalreqTrade3",
            name = "Chalreq Trade",
            description = "Display chalreq trade messages",
            section = custom3ChatSection,
            position = 10
    )
    default boolean Custom3ChalreqTradeEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ClanChat3",
            name = "Clan Chat",
            description = "Display clan chat messages",
            section = custom3ChatSection,
            position = 11
    )
    default boolean Custom3ClanChatEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ClanGimChat3",
            name = "Clan GIM Chat",
            description = "Display clan GIM chat messages",
            section = custom3ChatSection,
            position = 12
    )
    default boolean Custom3ClanGimChatEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ClanGimMessage3",
            name = "Clan GIM Message",
            description = "Display clan GIM messages",
            section = custom3ChatSection,
            position = 13
    )
    default boolean Custom3ClanGimMessageEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ClanGuestChat3",
            name = "Clan Guest Chat",
            description = "Display clan guest chat messages",
            section = custom3ChatSection,
            position = 14
    )
    default boolean Custom3ClanGuestChatEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ClanGuestMessage3",
            name = "Clan Guest Message",
            description = "Display clan guest messages",
            section = custom3ChatSection,
            position = 15
    )
    default boolean Custom3ClanGuestMessageEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ClanMessage3",
            name = "Clan Message",
            description = "Display clan messages",
            section = custom3ChatSection,
            position = 16
    )
    default boolean Custom3ClanMessageEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "Combat3",
            name = "Combat",
            description = "Display combat messages",
            section = custom3ChatSection,
            position = 17
    )
    default boolean Custom3CombatEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "Console3",
            name = "Console",
            description = "Display console messages",
            section = custom3ChatSection,
            position = 18
    )
    default boolean Custom3ConsoleEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "Dialog3",
            name = "Dialog",
            description = "Display dialog messages",
            section = custom3ChatSection,
            position = 19
    )
    default boolean Custom3DialogEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "Engine3",
            name = "Engine",
            description = "Display engine messages",
            section = custom3ChatSection,
            position = 20
    )
    default boolean Custom3EngineEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "FriendNotification3",
            name = "Friend Notification",
            description = "Display friend notifications",
            section = custom3ChatSection,
            position = 21
    )
    default boolean Custom3FriendNotificationEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "FriendsChat3",
            name = "Friends Chat",
            description = "Display friends chat messages",
            section = custom3ChatSection,
            position = 22
    )
    default boolean Custom3FriendsChatEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "FriendsChatNotification3",
            name = "Friends Chat Notification",
            description = "Display friends chat notifications",
            section = custom3ChatSection,
            position = 23
    )
    default boolean Custom3FriendsChatNotificationEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "GameMessage3",
            name = "Game Message",
            description = "Display game messages",
            section = custom3ChatSection,
            position = 24
    )
    default boolean Custom3GameMessageEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "IgnoreNotification3",
            name = "Ignore Notification",
            description = "Display ignore notifications",
            section = custom3ChatSection,
            position = 25
    )
    default boolean Custom3IgnoreNotificationEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ItemExamine3",
            name = "Item Examine",
            description = "Display item examine messages",
            section = custom3ChatSection,
            position = 26
    )
    default boolean Custom3ItemExamineEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "Mesbox3",
            name = "Mesbox",
            description = "Display Mesbox messages",
            section = custom3ChatSection,
            position = 27
    )
    default boolean Custom3MesboxEnabled()
    {
        return false;
    }
    
    @ConfigItem(
            keyName = "ModAutoTyper3",
            name = "Mod Auto Typer",
            description = "Display mod auto-typer messages",
            section = custom3ChatSection,
            position = 27
    )
    default boolean Custom3ModAutoTyperEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ModChat3",
            name = "Mod Chat",
            description = "Display mod chat messages",
            section = custom3ChatSection,
            position = 28
    )
    default boolean Custom3ModChatEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ModPrivateChat3",
            name = "Mod Private Chat",
            description = "Display mod private chat messages",
            section = custom3ChatSection,
            position = 29
    )
    default boolean Custom3ModPrivateChatEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "NpcExamine3",
            name = "NPC Examine",
            description = "Display NPC examine messages",
            section = custom3ChatSection,
            position = 30
    )
    default boolean Custom3NpcExamineEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "NpcSay3",
            name = "NPC Say",
            description = "Display NPC Say messages",
            section = custom3ChatSection,
            position = 30
    )
    default boolean Custom3NpcSayEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ObjectExamine3",
            name = "Object Examine",
            description = "Display object examine messages",
            section = custom3ChatSection,
            position = 31
    )
    default boolean Custom3ObjectExamineEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "PrivateChat3",
            name = "Private Chat",
            description = "Display private chat messages",
            section = custom3ChatSection,
            position = 32
    )
    default boolean Custom3PrivateChatEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "PrivateChatout3",
            name = "Private Chat Out",
            description = "Display private chat out messages",
            section = custom3ChatSection,
            position = 33
    )
    default boolean Custom3PrivateChatoutEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "PublicChat3",
            name = "Public Chat",
            description = "Display public chat messages",
            section = custom3ChatSection,
            position = 34
    )
    default boolean Custom3PublicChatEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "Spam3",
            name = "Spam",
            description = "Display spam messages",
            section = custom3ChatSection,
            position = 35
    )
    default boolean Custom3SpamEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "Trade3",
            name = "Trade",
            description = "Display trade messages",
            section = custom3ChatSection,
            position = 36
    )
    default boolean Custom3TradeEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "TradeReq3",
            name = "Trade Req",
            description = "Display trade request messages",
            section = custom3ChatSection,
            position = 37
    )
    default boolean Custom3TradeReqEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "TradeSent3",
            name = "Trade Sent",
            description = "Display trade sent messages",
            section = custom3ChatSection,
            position = 38
    )
    default boolean Custom3TradeSentEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "Unknown3",
            name = "Unknown",
            description = "Display 'unknown' chat channel",
            section = custom3ChatSection,
            position = 39
    )
    default boolean Custom3UnknownEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "Welcome3",
            name = "Welcome",
            description = "Display welcome messages",
            section = custom3ChatSection,
            position = 40
    )
    default boolean Custom3WelcomeEnabled()
    {
        return false;
    }


    // Start of type based coloring entries.


    @ConfigItem(
            keyName = "broadcastColor",
            name = "Broadcast",
            description = "Configures the text color for broadcast messages.",
            section = eventSection,
            position = 1
    )
    default Color broadcastColor() {
        return null;
    }

    @ConfigItem(
            keyName = "chalReqClanColor",
            name = "ChalReq Clan",
            description = "Configures the text color for clan challenge requests.",
            section = eventSection,
            position = 2
    )
    default Color chalReqClanColor() {
        return null;
    }

    @ConfigItem(
            keyName = "chalReqFriendsColor",
            name = "ChalReq Friends",
            description = "Configures the text color for friends challenge requests.",
            section = eventSection,
            position = 3
    )
    default Color chalReqFriendsColor() {
        return null;
    }

    @ConfigItem(
            keyName = "chalReqTradeColor",
            name = "ChalReq Trade",
            description = "Configures the text color for trade challenge requests.",
            section = eventSection,
            position = 4
    )
    default Color chalReqTradeColor() {
        return null;
    }

    @ConfigItem(
            keyName = "clanColor",
            name = "Clan Chat",
            description = "Configures the text color for clan chat messages.",
            section = eventSection,
            position = 5
    )
    default Color clanColor() {
        return null;
    }

    @ConfigItem(
            keyName = "clanGuestChatColor",
            name = "Clan Guest Chat",
            description = "Configures the text color for clan guest chat messages.",
            section = eventSection,
            position = 6
    )
    default Color clanGuestChatColor() {
        return null;
    }

    @ConfigItem(
            keyName = "clanGuestMessageColor",
            name = "Clan Guest Message",
            description = "Configures the text color for clan guest messages.",
            section = eventSection,
            position = 7
    )
    default Color clanGuestMessageColor() {
        return null;
    }

    @ConfigItem(
            keyName = "clanGimChatColor",
            name = "Clan GIM Chat",
            description = "Configures the text color for clan GIM chat messages.",
            section = eventSection,
            position = 8
    )
    default Color clanGimChatColor() {
        return null;
    }

    @ConfigItem(
            keyName = "clanGimMessageColor",
            name = "Clan GIM Message",
            description = "Configures the text color for clan GIM messages.",
            section = eventSection,
            position = 9
    )
    default Color clanGimMessageColor() {
        return null;
    }

    @ConfigItem(
            keyName = "clanMessageColor",
            name = "Clan Message",
            description = "Configures the text color for clan messages.",
            section = eventSection,
            position = 10
    )
    default Color clanMessageColor() {
        return null;
    }

    @ConfigItem(
            keyName = "combatColor",
            name = "Combat",
            description = "Configures the text color for combat messages.",
            section = eventSection,
            position = 11
    )
    default Color combatColor() {
        return null;
    }

    @ConfigItem(
            keyName = "consoleColor",
            name = "Console",
            description = "Configures the text color for console messages.",
            section = eventSection,
            position = 12
    )
    default Color consoleColor() {
        return null;
    }

    @ConfigItem(
            keyName = "deatholor",
            name = "Death",
            description = "Configures the text color for death messages.",
            section = eventSection,
            position = 13
    )
    default Color deathColor() {
        return null;
    }

    @ConfigItem(
            keyName = "dialogColor",
            name = "Dialog",
            description = "Configures the text color for dialog messages.",
            section = eventSection,
            position = 13
    )
    default Color dialogColor() {
        return null;
    }

    @ConfigItem(
            keyName = "engineColor",
            name = "Engine",
            description = "Configures the text color for engine messages.",
            section = eventSection,
            position = 14
    )
    default Color engineColor() {
        return null;
    }

    @ConfigItem(
            keyName = "friendNotificationColor",
            name = "Friend Notification",
            description = "Configures the text color for friend notifications.",
            section = eventSection,
            position = 15
    )
    default Color friendNotificationColor() {
        return null;
    }

    @ConfigItem(
            keyName = "friendsColor",
            name = "Friends Chat",
            description = "Configures the text color for friends chat messages.",
            section = eventSection,
            position = 16
    )
    default Color friendsColor() {
        return null;
    }

    @ConfigItem(
            keyName = "friendsChatNotificationColor",
            name = "Friends Chat Notification",
            description = "Configures the text color for friends chat notifications.",
            section = eventSection,
            position = 16
    )
    default Color friendsChatNotificationColor() {
        return null;
    }

    @ConfigItem(
            keyName = "gameMessageColor",
            name = "Game Message",
            description = "Configures the text color for game messages.",
            section = eventSection,
            position = 17
    )
    default Color gameMessageColor() {
        return null;
    }

        @ConfigItem(
            keyName = "ignoreNotificationColor",
            name = "Ignore Notification",
            description = "Configures the text color for ignore notifications.",
            section = eventSection,
            position = 18
    )
    default Color ignoreNotificationColor() {
            return null;
    }

    @ConfigItem(
            keyName = "itemExamineColor",
            name = "Item Examine",
            description = "Configures the text color for item examine messages.",
            section = eventSection,
            position = 18
    )
    default Color itemExamineColor() {
        return null;
    }

    @ConfigItem(
            keyName = "mesBoxColor",
            name = "Message Box",
            description = "Configures the text color for message box messages.",
            section = eventSection,
            position = 19
    )
    default Color mesboxColor() {
        return null;
    }

    @ConfigItem(
            keyName = "modAutoTyperColor",
            name = "Mod Auto-Typer",
            description = "Configures the text color for mod auto-typer messages.",
            section = eventSection,
            position = 20
    )
    default Color modAutoTyperColor() {
        return null;
    }

    @ConfigItem(
            keyName = "modChatColor",
            name = "Mod Chat",
            description = "Configures the text color for ModChat messages.",
            section = eventSection,
            position = 21
    )
    default Color modChatColor() {
        return null;
    }

    @ConfigItem(
            keyName = "modPrivateChatColor",
            name = "Mod Private Chat",
            description = "Configures the text color for Mod private chat messages.",
            section = eventSection,
            position = 22
    )
    default Color modPrivateChatColor() {
        return null;
    }

    @ConfigItem(
            keyName = "npcExamineColor",
            name = "NPC Examine",
            description = "Configures the text color for NPC examine messages.",
            section = eventSection,
            position = 23
    )
    default Color npcExamineColor() {
        return null;
    }

    @ConfigItem(
            keyName = "npcSayColor",
            name = "NPC Say",
            description = "Configures the text color for NPC messages.",
            section = eventSection,
            position = 24
    )
    default Color npcSayColor() {
        return null;
    }

    @ConfigItem(
            keyName = "objectExamineColor",
            name = "Object Examine",
            description = "Configures the font color of object Examine text.",
            section = eventSection,
            position = 24
    )
    default Color objectExamineColor() {
        return null;
    }

    @ConfigItem(
            keyName = "privateColor",
            name = "Private Chat",
            description = "Configures the text color for incoming private chat messages.",
            section = eventSection,
            position = 25
    )
    default Color privateColor() {
        return null;
    }

    @ConfigItem(
            keyName = "privateChatOutColor",
            name = "Private Chat Out",
            description = "Configures the text color for outgoing private chat messages.",
            section = eventSection,
            position = 26
    )
    default Color privateChatOutColor() {
        return null;
    }

    @ConfigItem(
            keyName = "publicColor",
            name = "Public Chat",
            description = "Configures the text color for public chat messages.",
            section = eventSection,
            position = 27
    )
    default Color publicColor() {
        return null;
    }

    @ConfigItem(
            keyName = "spamColor",
            name = "Spam Message",
            description = "Configures the text color for spam messages.",
            section = eventSection,
            position = 28
    )
    default Color spamColor() {
        return null;
    }

    @ConfigItem(
            keyName = "tradeColor",
            name = "Trade Message",
            description = "Configures the text color for trade messages.",
            section = eventSection,
            position = 29
    )
    default Color tradeColor() {
        return null;
    }

    @ConfigItem(
            keyName = "tradeReqColor",
            name = "Trade Request",
            description = "Configures the text color for trade requests.",
            section = eventSection,
            position = 30
    )
    default Color tradeReqColor() {
        return null;
    }

    @ConfigItem(
            keyName = "tradeSentColor",
            name = "Trade Sent Message",
            description = "Configures the text color for trade sent messages.",
            section = eventSection,
            position = 31
    )
    default Color tradeSentColor() {
        return null;
    }

    @ConfigItem(
            keyName = "unknownColor",
            name = "Unknown Message",
            description = "Configures the text color for unknown messages, spooky.",
            section = eventSection,
            position = 32
    )
    default Color unknownColor() {
        return null;
    }

    @ConfigItem(
            keyName = "welcomeColor",
            name = "Welcome Message",
            description = "Configures the text color for the welcome message.",
            section = eventSection,
            position = 33
    )
    default Color welcomeColor() {
        return null;
    }
}