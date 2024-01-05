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

    @ConfigItem(
            keyName = "fontStyle",
            name = "Font Style",
            description = "Choose the font style for the chat panel",
            section = generalSection,
            position = 3
    )
    default FontStyle fontStyle() {
        return FontStyle.PLAIN;
    }
    @ConfigItem(
            keyName = "showTimestamp",
            name = "Show Timestamp",
            description = "Toggle to show timestamps in chat messages",
            section = generalSection,
            position = 4
    )
    default boolean showTimestamp() {
        return false;
    }
    @ConfigItem(
            keyName = "use24HourTimestamp",
            name = "Use 24-Hour Timestamp",
            description = "Toggle to use 24-hour time format timestamp",
            section = generalSection,
            position = 5
    )
    default boolean use24HourFormat() {
        return false;
    }
    @ConfigSection(
            name = "Public Chat",
            description = "Settings for public chat",
            position = 1
    )
    String publicChatSection = "publicChat";

    @ConfigSection(
            name = "Private Chat",
            description = "Settings for private chat",
            position = 2
    )
    String privateChatSection = "privateChat";

    @ConfigSection(
            name = "Clan Chat",
            description = "Settings for clan chat",
            position = 3
    )
    String clanChatSection = "clanChat";

    @ConfigSection(
            name = "Game Chat",
            description = "Settings for game chat",
            position = 4
    )
    String gameChatSection = "gameChat";

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

    @Range ( max =10)
    @ConfigItem(
            keyName = "lineSpacing",
            name = "Line Spacing",
            description = "Adjust the spacing between chat messages",
            section = generalSection,
            position = 2

    )
    default int lineSpacing(){return 0;}
}
