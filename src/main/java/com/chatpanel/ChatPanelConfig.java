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
            closedByDefault = true,
            position = 3
    )
    String privateChatSection = "privateChat";

    @ConfigSection(
            name = "Clan Chat",
            description = "Settings for clan chat",
            closedByDefault = true,
            position = 4
    )
    String clanChatSection = "clanChat";

    @ConfigSection(
            name = "Friends Chat",
            description = "Settings for friends chat",
            closedByDefault = true,
            position = 4
    )
    String friendsChatSection = "friendsChat";

    @ConfigSection(
            name = "Game Chat",
            description = "Settings for game chat",
            closedByDefault = true,
            position = 6
    )
    String gameChatSection = "gameChat";

    @ConfigSection(
            name = "All Chat",
            description = "Settings for the All Chat tab, not all of the chat.",
            closedByDefault = true,
            position = 7
    )
    String allChatSection = "allChat";

    @ConfigSection(
            name = "Custom Chat",
            description = "Settings for the Custom Chat tab, select all desired chat channels to be displayed. Long list.",
            closedByDefault = true,
            position = 8
    )
    String customChatSection = "customChat";

    @ConfigSection(
            name = "Pop out Window",
            description = "Settings for the pop out window",
            position = 1
    )
    String popoutSection = "popoutwindow";

    @ConfigSection(
            name = "Tabs",
            description = "Tab selection. (Recommended 4)",
            position = 9
    )
    String tabSection = "tabselection";

    @ConfigItem(
            keyName = "fontStyle",
            name = "Font Style",
            description = "Choose the font style for the chat panel",
            section = generalSection,
            position = 4
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
            section = generalSection,
            position = 1
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
            position = 3,
            section = generalSection
    )
    default int iconPosition()
    {
        return 5;
    }

    @ConfigItem(
            keyName = "hideSidebarIcon",
            name = "Hide Sidebar Icon",
            description = "ONLY use with Auto-Pop out and if you REALLY don't want a sidebar icon. Toggle plugin on/off to restore pop out window if closed. (Requires plugin restart)",
            section = generalSection,
            position = 7
    )
    default boolean hideSidebarIcon()
    {
        return false;
    }

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
            name = "Public Chat Background",
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
            name = "Private Chat Background",
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
            name = "Clan Chat Background",
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
            name = "Friends Chat Background",
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
            name = "Game Chat Background",
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
            name = "All Chat Background",
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

    @ConfigItem(
            keyName = "customChatColor",
            name = "Text Color",
            description = "Configures the font color of the custom chat messages.",
            section = customChatSection,
            position = 1
    )
    default Color customChatColor()
    {
        return new Color(0xFFFFFF);
    }

    @ConfigItem(
            keyName = "customChatBackgroundColor",
            name = "Background color",
            description = "Configures the background color of the custom chat.",
            section = customChatSection,
            position = 0
    )
    default Color customChatBackgroundColor()
    {
        return new Color(0x282828);
    }

    @Range(min = 5, max = 50)
    @ConfigItem(
            keyName = "customChatFontSize",
            name = "Font Size",
            description = "Configures the font size of the custom chat messages. (Max 50)",
            section = customChatSection,
            position = 2
    )
    default int customChatFontSize()
    {
        return 12;
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
            name = "Show Pop Out Closure Warning",
            description = "Show the warning on closing the pop out window with Hide Sidebar Icon on",
            section = popoutSection,
            position = 3
    )
    default boolean PopoutWarning()
    {
        return true;
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
            keyName = "DisablePopOut",
            name = "Disable Pop Out",
            description = "Hides the side panel pop out button, does NOT work with Auto-pop out. (Requires plugin restart)",
            position = 6,
            section = popoutSection
    )
    default boolean DisablePopout()
    {
        return false;
    }

    @ConfigItem(
            keyName = "showPublicChat",
            name = "Show Public Chat",
            description = "Show/hide the Public Chat tab",
            section = tabSection,
            position = 0
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
            position = 1
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
            position = 2
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
            position = 5
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
        return true;
    }


    // Start of Custom Chat channels
    @ConfigItem(
            keyName = "Broadcast",
            name = "Broadcast",
            description = "Display broadcast messages",
            section = customChatSection,
            position = 3
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
            position = 4
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
            position = 5
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
            position = 6
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
            position = 7
    )
    default boolean CustomClanChatEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ClanGimChat",
            name = "Clan GIM Chat",
            description = "Display Clan GIM chat messages",
            section = customChatSection,
            position = 8
    )
    default boolean CustomClanGimChatEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ClanGimMessage",
            name = "Clan GIM Message",
            description = "Display Clan GIM messages",
            section = customChatSection,
            position = 9
    )
    default boolean CustomClanGimMessageEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ClanGuestChat",
            name = "Clan Guest Chat",
            description = "Display Clan Guest chat messages",
            section = customChatSection,
            position = 10
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
            position = 11
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
            position = 12
    )
    default boolean CustomClanMessageEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "Console",
            name = "Console",
            description = "Display console messages",
            section = customChatSection,
            position = 13
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
            position = 14
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
            position = 15
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
            position = 16
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
            position = 17
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
            position = 18
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
            position = 19
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
            position = 20
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
            position = 21
    )
    default boolean CustomItemExamineEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ModAutoTyper",
            name = "Mod Auto Typer",
            description = "Display mod auto-typer messages",
            section = customChatSection,
            position = 22
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
            position = 23
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
            position = 24
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
            position = 25
    )
    default boolean CustomNpcExamineEnabled()
    {
        return false;
    }

    @ConfigItem(
            keyName = "ObjectExamine",
            name = "Object Examine",
            description = "Display object examine messages",
            section = customChatSection,
            position = 26
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
            position = 27
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
            position = 28
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
            position = 29
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
            position = 30
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
            position = 31
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
            position = 32
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
            position = 33
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
            position = 34
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
            position = 35
    )
    default boolean CustomWelcomeEnabled()
    {
        return false;
    }
}