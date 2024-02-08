package com.chatpanel;

import net.runelite.client.config.*;

import java.awt.*;

@ConfigGroup("chatpanel")
public interface ChatPanelConfig extends Config {
    enum FontStyle {
        PLAIN("Plain"),
        BOLD("Bold"),
        ITALIC("Italic");

        private final String name;

        FontStyle(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

    }

    @ConfigSection(
            name = "General",
            description = "General settings",
            position = 0
    )
    String generalSection = "general";

    @ConfigSection(
            name = "Public Chat",
            description = "Settings for public chat",
            position = 2
    )
    String publicChatSection = "publicChat";

    @ConfigSection(
            name = "Private Chat",
            description = "Settings for private chat",
            position = 3
    )
    String privateChatSection = "privateChat";

    @ConfigSection(
            name = "Clan Chat",
            description = "Settings for clan chat",
            position = 4
    )
    String clanChatSection = "clanChat";
    @ConfigSection(
            name = "Friends Chat",
            description = "Settings for friends chat",
            position = 4
    )
    String friendsChatSection = "friendsChat";
    @ConfigSection(
            name = "Game Chat",
            description = "Settings for game chat",
            position = 6
    )
    String gameChatSection = "gameChat";
    @ConfigSection(
            name = "All Chat",
            description = "Settings for the All Chat tab, not all of the chat.",
            position = 7
    )
    String allChatSection = "allChat";
    @ConfigSection(
            name = "Pop out Window",
            description = "Settings for the pop out window",
            position = 1
    )
    String popoutSection = "popoutwindow";
    @ConfigSection(
            name = "Tabs",
            description = "Tab selection. (Recommended 4)",
            position = 7
    )
    String tabSection = "tabselection";
    @ConfigItem(
            keyName = "fontStyle",
            name = "Font Style",
            description = "Choose the font style for the chat panel",
            section = generalSection,
            position = 4
    )
    default FontStyle fontStyle() {
        return FontStyle.PLAIN;
    }
    @ConfigItem(
            keyName = "showTimestamp",
            name = "Show Timestamp",
            description = "Toggle to show timestamps in chat messages",
            section = generalSection,
            position = 5
    )
    default boolean showTimestamp() {
        return false;
    }
    @ConfigItem(
            keyName = "use24HourTimestamp",
            name = "Use 24-Hour Timestamp",
            description = "Toggle to use 24-hour time format timestamp",
            section = generalSection,
            position = 6
    )
    default boolean use24HourFormat() {
        return false;
    }
    @Range(min = 50, max = 5000)
    @ConfigItem(
            keyName = "chatAreaHeight",
            name = "Chat Area Height",
            description = "Configures the height of the chat area. Recommended below window height",
            section = generalSection,
            position = 1
    )
    default int chatAreaHeight()
    {
        return 435;
    }
    @ConfigItem(
            keyName = "Icon position",
            name = "Icon position",
            description = "Set the priority for the sidebar icon's position. (Requires plugin restart)",
            position = 3,
            section = generalSection
    )
    default int iconPosition() {return 5;}

    @ConfigItem(
            keyName = "publicChatColor",
            name = "Public Chat Color",
            description = "Configures the font color of the public chat messages.",
            section = publicChatSection
    )
    default Color publicChatColor()
    {
        return new Color(0xF1FF00);
    }

    @ConfigItem(
            keyName = "publicChatBackground",
            name = "Public Chat Background color",
            description = "Configures the background color of the public chat.",
            section = publicChatSection
    )
    default Color publicChatBackground()
    {
        return new Color(0x282828);
    }
    @Range(min = 5, max = 50)
    @ConfigItem(
            keyName = "publicChatFontSize",
            name = "Public Chat Font Size",
            description = "Configures the font size of the public chat messages. (Max 50)",
            section = publicChatSection
    )
    default int publicChatFontSize()
    {
        return 10;
    }

    @ConfigItem(
            keyName = "privateChatColor",
            name = "Private Chat Color",
            description = "Configures the font color of the private chat messages.",
            section = privateChatSection
    )
    default Color privateChatColor()
    {
        return new Color(0x0AFF00);
    }
    @ConfigItem(
            keyName = "privateChatBackground",
            name = "Private Chat Background Color",
            description = "Configures the background color of the private chat.",
            section = privateChatSection
    )
    default Color privateChatBackground()
    {
        return new Color(0x282828);
    }
    @Range(min = 5, max = 50)
    @ConfigItem(
            keyName = "privateChatFontSize",
            name = "Private Chat Font Size",
            description = "Configures the font size of the private chat messages. (Max 50)",
            section = privateChatSection
    )
    default int privateChatFontSize()
    {
        return 14;
    }

    @ConfigItem(
            keyName = "clanChatColor",
            name = "Clan Chat Color",
            description = "Configures the font color of the clan chat messages.",
            section = clanChatSection
    )
    default Color clanChatColor()
    {
        return new Color(0x007DFF);
    }
    @ConfigItem(
            keyName = "clanChatBackgroundColor",
            name = "Clan Chat Background Color",
            description = "Configures the background color of the clan chat.",
            section = clanChatSection
    )
    default Color clanChatBackgroundColor()
    {
        return new Color(0x282828);
    }
    @Range(min = 5, max = 50)
    @ConfigItem(
            keyName = "clanChatFontSize",
            name = "Clan Chat Font Size",
            description = "Configures the font size of the clan chat messages. (Max 50)",
            section = clanChatSection
    )
    default int clanChatFontSize()
    {
        return 12;
    }
    @ConfigItem(
            keyName = "friendsChatColor",
            name = "Friends Chat Color",
            description = "Configures the font color of the Friends Chat messages.",
            section = friendsChatSection
    )
    default Color friendsChatColor()
    {
        return new Color(0x28FF71);
    }

    @ConfigItem(
            keyName = "friendsChatBackground",
            name = "Friends Chat Background color",
            description = "Configures the background color of the Friends Chat.",
            section = friendsChatSection
    )
    default Color friendsChatBackground()
    {
        return new Color(0x282828);
    }
    @Range(min = 5, max = 50)
    @ConfigItem(
            keyName = "friendsChatFontSize",
            name = "Friends Chat Font Size",
            description = "Configures the font size of the Friends Chat messages. (Max 50)",
            section = friendsChatSection
    )
    default int friendsChatFontSize()
    {
        return 10;
    }

    @ConfigItem(
            keyName = "gameChatColor",
            name = "Game Chat Color",
            description = "Configures the font color of the game chat messages.",
            section = gameChatSection
    )
    default Color gameChatColor()
    {
        return new Color(0xFFFFFF);
    }
    @ConfigItem(
            keyName = "gameChatBackgroundColor",
            name = "Game Chat Background Color",
            description = "Configures the background color of the game chat.",
            section = gameChatSection
    )
    default Color gameChatBackgroundColor()
    {
        return new Color(0x282828);
    }

    @Range(min = 5, max = 50)
    @ConfigItem(
            keyName = "gameChatFontSize",
            name = "Game Chat Font Size",
            description = "Configures the font size of the game chat messages. (Max 50)",
            section = gameChatSection
    )
    default int gameChatFontSize()
    {
        return 12;
    }
    @ConfigItem(
            keyName = "allChatColor",
            name = "All Chat Color",
            description = "Configures the font color of the All Chat messages.",
            section = allChatSection
    )
    default Color allChatColor()
    {
        return new Color(0xFFFFFF);
    }

    @ConfigItem(
            keyName = "allChatBackground",
            name = "All Chat Background color",
            description = "Configures the background color of the All Chat.",
            section = allChatSection
    )
    default Color allChatBackground()
    {
        return new Color(0x282828);
    }
    @Range(min = 5, max = 50)
    @ConfigItem(
            keyName = "allChatFontSize",
            name = "All Chat Font Size",
            description = "Configures the font size of the All Chat messages. (Max 50)",
            section = allChatSection
    )
    default int allChatFontSize()
    {
        return 10;
    }

    @Range ( max =10)
    @ConfigItem(
            keyName = "lineSpacing",
            name = "Line Spacing",
            description = "Adjust the spacing between chat messages",
            section = generalSection,
            position = 2

    )
    default int lineSpacing(){return 0;}

    @ConfigItem(
            keyName = "popoutAlwaysOnTop",
            name = "Pop out Always on Top",
            description = "Keep the pop out window always on top of other windows",
            section = popoutSection,
            position = 2
    )
    default boolean popoutAlwaysOnTop() {
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
    @Range(
            min = 10,
            max = 100
    )
    @ConfigItem(
            keyName = "PopoutOpacity",
            name = "Window opacity",
            description = "Set the pop out window's opacity",
            position = 1,
            section = popoutSection
    )
    default int popoutOpacity() {return 100;}
    @ConfigItem(
            keyName = "showPublicChat",
            name = "Show Public Chat",
            description = "Show/hide the Public Chat tab",
            section = tabSection,
            position = 0
    )
    default boolean showPublicChat() {return true;}
    @ConfigItem(
            keyName = "showPrivateChat",
            name = "Show Private Chat",
            description = "Show/hide the Private Chat tab",
            section = tabSection,
            position = 1
    )
    default boolean showPrivateChat() {return true;}
    @ConfigItem(
            keyName = "showClanChat",
            name = "Show Clan Chat",
            description = "Show/hide the Clan Chat tab",
            section = tabSection,
            position = 2
    )
    default boolean showClanChat() {return true;}
    @ConfigItem(
            keyName = "showGameChat",
            name = "Show Game Chat",
            description = "Show/hide the Game tab",
            section = tabSection,
            position = 4
    )
    default boolean showGameChat() {return true;}
    @ConfigItem(
            keyName = "showAllChat",
            name = "Show All Chat",
            description = "Show/hide the All Chat tab",
            section = tabSection,
            position = 5
    )
    default boolean showAllChat() {
        return false;
    }
    @ConfigItem(
            keyName = "showFriendsChat",
            name = "Show Friends Chat Channel",
            description = "Show/hide the Friends Chat tab",
            section = tabSection,
            position = 3
    )
    default boolean showFriendsChat() {return false;}
    @ConfigItem(
            keyName = "hidepopoutbutton",
            name = "Hide pop out button",
            description = "Hides the pop out button, basically disables pop out mode. (Requires plugin restart)",
            section = popoutSection,
            position = 5
    )
    default boolean hidepopoutbutton() {
        return false;
    }

}
