package net.runelite.client.plugins.chatpanel;

import com.google.inject.Provides;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.events.ConfigChanged;
import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;

@PluginDescriptor(
        name = "Chat Panel",
        description = "Capture chat messages and display them in a RuneLite panel",
        tags = {"chat", "panel", "capture", "messages"}
)
public class ChatPanelPlugin extends Plugin
{
    @Inject
    private ClientToolbar clientToolbar;

    @Inject
    private ChatPanelConfig config;

    private ChatPanelSidebar chatPanelSidebar;
    private NavigationButton navButton;

    @Provides
    ChatPanelConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(ChatPanelConfig.class);
    }

    @Override
    protected void startUp() throws Exception
    {
        chatPanelSidebar = new ChatPanelSidebar(config);

        final BufferedImage icon = ImageUtil.loadImageResource(getClass(), "/ChatPanelimg.png");

        navButton = NavigationButton.builder()
                .tooltip("Chat Panel")
                .icon(icon)
                .priority(5)
                .panel(chatPanelSidebar)
                .build();

        clientToolbar.addNavigation(navButton);
    }

    @Override
    protected void shutDown() throws Exception
    {
        clientToolbar.removeNavigation(navButton);
    }

    @Subscribe
    public void onChatMessage(ChatMessage event)
    {
        String cleanedName = cleanString(event.getName());
        String cleanedMessage = cleanString(event.getMessage());
        String timestamp = config.showTimestamp() ? getCurrentTimestamp() : "";

        switch (event.getType())
        {
            case PUBLICCHAT:
            case MODCHAT:
                chatPanelSidebar.addPublicChatMessage(timestamp, cleanedName, cleanedMessage);
                break;
            case PRIVATECHAT:
            case MODPRIVATECHAT:
                chatPanelSidebar.addPrivateChatMessage(timestamp, cleanedName, cleanedMessage);
                break;
            case CLAN_CHAT:
            case CLAN_MESSAGE:
            case CLAN_GUEST_MESSAGE:
            case CLAN_GIM_CHAT:
            case CLAN_GUEST_CHAT:
                chatPanelSidebar.addClanChatMessage(timestamp, cleanedName, cleanedMessage);
                break;
            case PRIVATECHATOUT:
                chatPanelSidebar.addPrivateChatMessage(timestamp, "You", cleanedMessage);
                break;
            case GAMEMESSAGE:
            case ENGINE:
            case NPC_EXAMINE:
            case SPAM:
            case DIALOG:
            case ITEM_EXAMINE:
            case OBJECT_EXAMINE:
            case WELCOME:
                chatPanelSidebar.addGameChatMessage(timestamp, cleanedMessage);
                break;
        }
    }
    private String getCurrentTimestamp() {
        SimpleDateFormat dateFormat;

        if (config.use24HourFormat()) {
            dateFormat = new SimpleDateFormat("HH:mm");
        } else {
            dateFormat = new SimpleDateFormat("hh:mm");
        }

        return dateFormat.format(new Date());
    }
    private String cleanString(String message)
    {
        return message.replaceAll("<img=[0-9]+>", "");
    }

    @Subscribe
    public void onConfigChanged(ConfigChanged event)
    {
        if (!"chatpanel".equals(event.getGroup()))
        {
            return;
        }
        chatPanelSidebar.updateChatStyles();
    }
}
