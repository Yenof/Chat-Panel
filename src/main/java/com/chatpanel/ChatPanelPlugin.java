package com.chatpanel;

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
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;

@PluginDescriptor(
        name = "Chat Panel",
        description = "Displays chat messages in a side panel or pop out window",
        tags = {"chat", "panel", "window", "messages", "font style", "private", "accessibility", "copy"}
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
        if (!config.hideSidebarIcon()) {
            final BufferedImage icon = ImageUtil.loadImageResource(getClass(), "/ChatPanelimg.png");

            navButton = NavigationButton.builder()
                    .tooltip("Chat Panel")
                    .icon(icon)
                    .priority(config.iconPosition())
                    .panel(chatPanelSidebar)
                    .build();

            clientToolbar.addNavigation(navButton);
        }
    }

    @Override
    protected void shutDown() throws Exception
    {
        if (navButton != null){
        clientToolbar.removeNavigation(navButton);}
        chatPanelSidebar.closePopout();
       // chatPanelSidebar.closePopoutTab();
    }

    @Subscribe
    public void onChatMessage(ChatMessage event) {
        String cleanedName = cleanString(event.getName());
        String cleanedMessage = cleanString(event.getMessage());
        String timestamp = getCurrentTimestamp();

        switch (event.getType()) {
            case PUBLICCHAT:
            case MODCHAT:
                if (config.showPublicChat()) {
                    chatPanelSidebar.addPublicChatMessage(timestamp, cleanedName, cleanedMessage);}
                break;
            case PRIVATECHAT:
            case MODPRIVATECHAT:
                if (config.showPrivateChat()) {
                    chatPanelSidebar.addPrivateChatMessage(timestamp, cleanedName, cleanedMessage);}
                break;
            case CLAN_CHAT:
            case CLAN_MESSAGE:
            case CLAN_GUEST_MESSAGE:
            case CLAN_GIM_CHAT:
            case CLAN_GIM_MESSAGE:
            case CLAN_GUEST_CHAT:
            case CHALREQ_CLANCHAT:
                if (config.showClanChat()) {
                    chatPanelSidebar.addClanChatMessage(timestamp, cleanedName, cleanedMessage);}
                break;
            case PRIVATECHATOUT:
                if (config.showPrivateChat()) {
                    chatPanelSidebar.addPrivateChatMessage(timestamp, "You", cleanedMessage);}
                break;
            case FRIENDSCHAT:
            case CHALREQ_FRIENDSCHAT:
            case FRIENDSCHATNOTIFICATION:
                if (config.showFriendsChat()) {
                    chatPanelSidebar.addFriendsChatMessage(timestamp, cleanedName, cleanedMessage);}
                break;
            case BROADCAST:
            case GAMEMESSAGE:
            case ENGINE:
            case NPC_EXAMINE:
            case SPAM:
            case DIALOG:
            case ITEM_EXAMINE:
            case OBJECT_EXAMINE:
            case WELCOME:
            case TRADE:
            case TRADE_SENT:
            case TRADEREQ:
            case CONSOLE:
            case MODAUTOTYPER:
            case CHALREQ_TRADE:
            case IGNORENOTIFICATION:
            case FRIENDNOTIFICATION:
                if (config.showGameChat()) {
                    chatPanelSidebar.addGameChatMessage(timestamp, cleanedMessage);}
                break;
            case UNKNOWN:
        }

        if (config.showAllChat()) {
            chatPanelSidebar.addAllChatMessage(timestamp, cleanedName, cleanedMessage);}
    }
    private String getCurrentTimestamp() {
        String customFormat = config.TimestampFormat();
        if (!customFormat.isEmpty()) {
              SimpleDateFormat dateFormat;
           try
           {
               dateFormat = new SimpleDateFormat(customFormat);
           }
           catch (IllegalArgumentException e) {
               dateFormat = new SimpleDateFormat("HH:mm");
            }
         return dateFormat.format(new Date());}
         return customFormat;
    }
    private String cleanString(String message)
    {
        return message.replaceAll("<img=[0-9]+>", "").replace("<lt>", "<").replace("<gt>", ">");
    }

    @Subscribe
    public void onConfigChanged(ConfigChanged event) {
        if ("chatpanel".equals(event.getGroup())) {
            if (event.getKey().startsWith("show")) {
                chatPanelSidebar.reloadPlugin();
            } else {
                chatPanelSidebar.updateChatStyles();
                if (chatPanelSidebar.isPopout()) {
                    chatPanelSidebar.setCactus(config.popoutOpacity());
                }
            }
            if (config.AutoPop() && config.DisablePopout()) {
                String message;
                if (config.hideSidebarIcon() && config.DisablePopout()) {
                    message = "<html>Warning: Disable Pop Out and Hide Sidebar Icon are both enabled.<br>This leaves no way to access Chat Panel.</html>";
                } else {
                    message = "<html>Notice: Auto-pop out window and Disable Pop Out are both enabled.<br>Pop out window will be unable to open.</html>";
                }
                JOptionPane.showMessageDialog(null, message, "Configuration Issue", JOptionPane.WARNING_MESSAGE);
            }
            if (config.hideSidebarIcon() && !config.AutoPop()) {
                String message = "<html>Warning: Hide Sidebar Icon is enabled but Auto-pop out window is not.<br>Enable Auto-pop out or disable Hide Sidebar Icon to access to Chat Panel.</html>";
                JOptionPane.showMessageDialog(null, message, "Configuration Issue", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

}