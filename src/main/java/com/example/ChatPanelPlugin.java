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

        final BufferedImage icon = ImageUtil.loadImageResource(getClass(), "/net/runelite/client/plugins/chatpanel/ChatPanelimg.png");

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
        switch (event.getType())
        {
            case PUBLICCHAT:
                chatPanelSidebar.addPublicChatMessage(event.getName(), event.getMessage());
                break;
            case PRIVATECHAT:
                chatPanelSidebar.addPrivateChatMessage(event.getName(), event.getMessage());
                break;
            case CLAN_CHAT:
            case CLAN_MESSAGE:
            case CLAN_GUEST_MESSAGE:
            case CLAN_GIM_CHAT:
            case CLAN_GUEST_CHAT:
                chatPanelSidebar.addClanChatMessage(event.getName(), event.getMessage());
                break;
            case PRIVATECHATOUT:
                chatPanelSidebar.addPrivateChatMessage("You", event.getMessage());
                break;
            case GAMEMESSAGE:
            case ENGINE:
            case NPC_EXAMINE:
            case SPAM:
            case ITEM_EXAMINE:
            case OBJECT_EXAMINE:
            case WELCOME:
                chatPanelSidebar.addGameChatMessage(event.getMessage());
                break;
        }
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
