package com.chatpanel;

import com.google.inject.Provides;
import net.runelite.api.*;
import net.runelite.api.events.ActorDeath;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.HitsplatApplied;
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
        description = "Displays chat messages in a pop out window or the side panel",
        tags = {"chat", "panel", "window", "messages", "font", "private", "accessibility", "copy", "pop out", "custom"}
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
    }

    @Subscribe
    public void onChatMessage(ChatMessage event) {
        String cleanedName = event.getType() == ChatMessageType.PRIVATECHATOUT ? "To " + cleanString(event.getName()) : event.getType() == ChatMessageType.PRIVATECHAT || event.getType() == ChatMessageType.MODPRIVATECHAT? "From " + cleanString(event.getName()) : cleanString(event.getName());
        String cleanedMessage = event.getType() == ChatMessageType.DIALOG ? cleanDialogMessage(event.getMessage()) : cleanString(event.getMessage());
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
                    chatPanelSidebar.addPrivateChatMessage(timestamp, event.getName(), cleanedMessage);}
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
                    chatPanelSidebar.addPrivateChatMessage(timestamp, cleanedName, cleanedMessage);}
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
                    chatPanelSidebar.addGameChatMessage(timestamp, cleanedName, cleanedMessage);}
                break;
            case UNKNOWN:
        }
        if (config.showAllChat()) {
            chatPanelSidebar.addAllChatMessage(timestamp, cleanedName, cleanedMessage);
        }
        if (config.showCustomChat() || config.showCustom2Chat() || config.showCustom3Chat()) {
            onCustomChatMessage(event, cleanedName, cleanedMessage, timestamp);
        }
    }

    private void onCustomChatMessage(ChatMessage event, String cleanedName, String cleanedMessage, String timestamp) {
        String identifier = getIdentifier(cleanedName, event);
        switch (event.getType()) {
            case PUBLICCHAT:
                if (config.showCustomChat() && config.CustomPublicChatEnabled()) {
                    chatPanelSidebar.addCustomChatMessage(timestamp, config.identifier1() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom2Chat() && config.Custom2PublicChatEnabled()) {
                    chatPanelSidebar.addCustom2ChatMessage(timestamp, config.identifier2() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom3Chat() && config.Custom3PublicChatEnabled()) {
                    chatPanelSidebar.addCustom3ChatMessage(timestamp, config.identifier3() ? identifier : cleanedName, cleanedMessage);
                }
                break;
            case MODCHAT:
                if (config.showCustomChat() && config.CustomModChatEnabled()) {
                    chatPanelSidebar.addCustomChatMessage(timestamp, config.identifier1() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom2Chat() && config.Custom2ModChatEnabled()) {
                    chatPanelSidebar.addCustom2ChatMessage(timestamp, config.identifier2() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom3Chat() && config.Custom3ModChatEnabled()) {
                    chatPanelSidebar.addCustom3ChatMessage(timestamp, config.identifier3() ? identifier : cleanedName, cleanedMessage);
                }
                break;
            case PRIVATECHAT:
                if (config.showCustomChat() && config.CustomPrivateChatEnabled()) {
                    chatPanelSidebar.addCustomChatMessage(timestamp, config.identifier1() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom2Chat() && config.Custom2PrivateChatEnabled()) {
                    chatPanelSidebar.addCustom2ChatMessage(timestamp, config.identifier2() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom3Chat() && config.Custom3PrivateChatEnabled()) {
                    chatPanelSidebar.addCustom3ChatMessage(timestamp, config.identifier3() ? identifier : cleanedName, cleanedMessage);
                }
                break;
            case MODPRIVATECHAT:
                if (config.showCustomChat() && config.CustomModPrivateChatEnabled()) {
                    chatPanelSidebar.addCustomChatMessage(timestamp, config.identifier1() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom2Chat() && config.Custom2ModPrivateChatEnabled()) {
                    chatPanelSidebar.addCustom2ChatMessage(timestamp, config.identifier2() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom3Chat() && config.Custom3ModPrivateChatEnabled()) {
                    chatPanelSidebar.addCustom3ChatMessage(timestamp, config.identifier3() ? identifier : cleanedName, cleanedMessage);
                }
                break;
            case CLAN_CHAT:
                if (config.showCustomChat() && config.CustomClanChatEnabled()) {
                    chatPanelSidebar.addCustomChatMessage(timestamp, config.identifier1() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom2Chat() && config.Custom2ClanChatEnabled()) {
                    chatPanelSidebar.addCustom2ChatMessage(timestamp, config.identifier2() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom3Chat() && config.Custom3ClanChatEnabled()) {
                    chatPanelSidebar.addCustom3ChatMessage(timestamp, config.identifier3() ? identifier : cleanedName, cleanedMessage);
                }
                break;
            case CLAN_MESSAGE:
                if (config.showCustomChat() && config.CustomClanMessageEnabled()) {
                    chatPanelSidebar.addCustomChatMessage(timestamp, config.identifier1() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom2Chat() && config.Custom2ClanMessageEnabled()) {
                    chatPanelSidebar.addCustom2ChatMessage(timestamp, config.identifier2() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom3Chat() && config.Custom3ClanMessageEnabled()) {
                    chatPanelSidebar.addCustom3ChatMessage(timestamp, config.identifier3() ? identifier : cleanedName, cleanedMessage);
                }
                break;
            case CLAN_GUEST_MESSAGE:
                if (config.showCustomChat() && config.CustomClanGuestMessageEnabled()) {
                    chatPanelSidebar.addCustomChatMessage(timestamp, config.identifier1() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom2Chat() && config.Custom2ClanGuestMessageEnabled()) {
                    chatPanelSidebar.addCustom2ChatMessage(timestamp, config.identifier2() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom3Chat() && config.Custom3ClanGuestMessageEnabled()) {
                    chatPanelSidebar.addCustom3ChatMessage(timestamp, config.identifier3() ? identifier : cleanedName, cleanedMessage);
                }
                break;
            case CLAN_GIM_CHAT:
                if (config.showCustomChat() && config.CustomClanGimChatEnabled()) {
                    chatPanelSidebar.addCustomChatMessage(timestamp, config.identifier1() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom2Chat() && config.Custom2ClanGimChatEnabled()) {
                    chatPanelSidebar.addCustom2ChatMessage(timestamp, config.identifier2() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom3Chat() && config.Custom3ClanGimChatEnabled()) {
                    chatPanelSidebar.addCustom3ChatMessage(timestamp, config.identifier3() ? identifier : cleanedName, cleanedMessage);
                }
                break;
            case CLAN_GIM_MESSAGE:
                if (config.showCustomChat() && config.CustomClanGimMessageEnabled()) {
                    chatPanelSidebar.addCustomChatMessage(timestamp, config.identifier1() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom2Chat() && config.Custom2ClanGimMessageEnabled()) {
                    chatPanelSidebar.addCustom2ChatMessage(timestamp, config.identifier2() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom3Chat() && config.Custom3ClanGimMessageEnabled()) {
                    chatPanelSidebar.addCustom3ChatMessage(timestamp, config.identifier3() ? identifier : cleanedName, cleanedMessage);
                }
                break;
            case CLAN_GUEST_CHAT:
                if (config.showCustomChat() && config.CustomClanGuestChatEnabled()) {
                    chatPanelSidebar.addCustomChatMessage(timestamp, config.identifier1() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom2Chat() && config.Custom2ClanGuestChatEnabled()) {
                    chatPanelSidebar.addCustom2ChatMessage(timestamp, config.identifier2() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom3Chat() && config.Custom3ClanGuestChatEnabled()) {
                    chatPanelSidebar.addCustom3ChatMessage(timestamp, config.identifier3() ? identifier : cleanedName, cleanedMessage);
                }
                break;
            case CHALREQ_CLANCHAT:
                if (config.showCustomChat() && config.CustomChalreqClanChatEnabled()) {
                    chatPanelSidebar.addCustomChatMessage(timestamp, config.identifier1() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom2Chat() && config.Custom2ChalreqClanChatEnabled()) {
                    chatPanelSidebar.addCustom2ChatMessage(timestamp, config.identifier2() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom3Chat() && config.Custom3ChalreqClanChatEnabled()) {
                    chatPanelSidebar.addCustom3ChatMessage(timestamp, config.identifier3() ? identifier : cleanedName, cleanedMessage);
                }
                break;
            case PRIVATECHATOUT:
                if (config.showCustomChat() && config.CustomPrivateChatoutEnabled()) {
                    chatPanelSidebar.addCustomChatMessage(timestamp, config.identifier1() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom2Chat() && config.Custom2PrivateChatoutEnabled()) {
                    chatPanelSidebar.addCustom2ChatMessage(timestamp, config.identifier2() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom3Chat() && config.Custom3PrivateChatoutEnabled()) {
                    chatPanelSidebar.addCustom3ChatMessage(timestamp, config.identifier3() ? identifier : cleanedName, cleanedMessage);
                }
                break;
            case FRIENDSCHAT:
                if (config.showCustomChat() && config.CustomFriendsChatEnabled()) {
                    chatPanelSidebar.addCustomChatMessage(timestamp, config.identifier1() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom2Chat() && config.Custom2FriendsChatEnabled()) {
                    chatPanelSidebar.addCustom2ChatMessage(timestamp, config.identifier2() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom3Chat() && config.Custom3FriendsChatEnabled()) {
                    chatPanelSidebar.addCustom3ChatMessage(timestamp, config.identifier3() ? identifier : cleanedName, cleanedMessage);
                }
                break;
            case CHALREQ_FRIENDSCHAT:
                if (config.showCustomChat() && config.CustomChalreqFriendsChatEnabled()) {
                    chatPanelSidebar.addCustomChatMessage(timestamp, config.identifier1() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom2Chat() && config.Custom2ChalreqFriendsChatEnabled()) {
                    chatPanelSidebar.addCustom2ChatMessage(timestamp, config.identifier2() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom3Chat() && config.Custom3ChalreqFriendsChatEnabled()) {
                    chatPanelSidebar.addCustom3ChatMessage(timestamp, config.identifier3() ? identifier : cleanedName, cleanedMessage);
                }
                break;
            case FRIENDSCHATNOTIFICATION:
                if (config.showCustomChat() && config.CustomFriendsChatNotificationEnabled()) {
                    chatPanelSidebar.addCustomChatMessage(timestamp, config.identifier1() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom2Chat() && config.Custom2FriendsChatNotificationEnabled()) {
                    chatPanelSidebar.addCustom2ChatMessage(timestamp, config.identifier2() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom3Chat() && config.Custom3FriendsChatNotificationEnabled()) {
                    chatPanelSidebar.addCustom3ChatMessage(timestamp, config.identifier3() ? identifier : cleanedName, cleanedMessage);
                }
                break;
            case BROADCAST:
                if (config.showCustomChat() && config.CustomBroadcastEnabled()) {
                    chatPanelSidebar.addCustomChatMessage(timestamp, config.identifier1() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom2Chat() && config.Custom2BroadcastEnabled()) {
                    chatPanelSidebar.addCustom2ChatMessage(timestamp, config.identifier2() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom3Chat() && config.Custom3BroadcastEnabled()) {
                    chatPanelSidebar.addCustom3ChatMessage(timestamp, config.identifier3() ? identifier : cleanedName, cleanedMessage);
                }
                break;
            case GAMEMESSAGE:
                if (config.showCustomChat() && config.CustomGameMessageEnabled()) {
                    chatPanelSidebar.addCustomChatMessage(timestamp, config.identifier1() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom2Chat() && config.Custom2GameMessageEnabled()) {
                    chatPanelSidebar.addCustom2ChatMessage(timestamp, config.identifier2() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom3Chat() && config.Custom3GameMessageEnabled()) {
                    chatPanelSidebar.addCustom3ChatMessage(timestamp, config.identifier3() ? identifier : cleanedName, cleanedMessage);
                }
                break;
            case ENGINE:
                if (config.showCustomChat() && config.CustomEngineEnabled()) {
                    chatPanelSidebar.addCustomChatMessage(timestamp, config.identifier1() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom2Chat() && config.Custom2EngineEnabled()) {
                    chatPanelSidebar.addCustom2ChatMessage(timestamp, config.identifier2() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom3Chat() && config.Custom3EngineEnabled()) {
                    chatPanelSidebar.addCustom3ChatMessage(timestamp, config.identifier3() ? identifier : cleanedName, cleanedMessage);
                }
                break;
            case NPC_EXAMINE:
                if (config.showCustomChat() && config.CustomNpcExamineEnabled()) {
                    chatPanelSidebar.addCustomChatMessage(timestamp, config.identifier1() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom2Chat() && config.Custom2NpcExamineEnabled()) {
                    chatPanelSidebar.addCustom2ChatMessage(timestamp, config.identifier2() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom3Chat() && config.Custom3NpcExamineEnabled()) {
                    chatPanelSidebar.addCustom3ChatMessage(timestamp, config.identifier3() ? identifier : cleanedName, cleanedMessage);
                }
                break;
            case SPAM:
                if (config.showCustomChat() && config.CustomSpamEnabled()) {
                    chatPanelSidebar.addCustomChatMessage(timestamp, config.identifier1() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom2Chat() && config.Custom2SpamEnabled()) {
                    chatPanelSidebar.addCustom2ChatMessage(timestamp, config.identifier2() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom3Chat() && config.Custom3SpamEnabled()) {
                    chatPanelSidebar.addCustom3ChatMessage(timestamp, config.identifier3() ? identifier : cleanedName, cleanedMessage);
                }
                break;
            case DIALOG:
                if (config.showCustomChat() && config.CustomDialogEnabled()) {
                    chatPanelSidebar.addCustomChatMessage(timestamp, config.identifier1() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom2Chat() && config.Custom2DialogEnabled()) {
                    chatPanelSidebar.addCustom2ChatMessage(timestamp, config.identifier2() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom3Chat() && config.Custom3DialogEnabled()) {
                    chatPanelSidebar.addCustom3ChatMessage(timestamp, config.identifier3() ? identifier : cleanedName, cleanedMessage);
                }
                break;
            case ITEM_EXAMINE:
                if (config.showCustomChat() && config.CustomItemExamineEnabled()) {
                    chatPanelSidebar.addCustomChatMessage(timestamp, config.identifier1() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom2Chat() && config.Custom2ItemExamineEnabled()) {
                    chatPanelSidebar.addCustom2ChatMessage(timestamp, config.identifier2() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom3Chat() && config.Custom3ItemExamineEnabled()) {
                    chatPanelSidebar.addCustom3ChatMessage(timestamp, config.identifier3() ? identifier : cleanedName, cleanedMessage);
                }
                break;
            case OBJECT_EXAMINE:
                if (config.showCustomChat() && config.CustomObjectExamineEnabled()) {
                    chatPanelSidebar.addCustomChatMessage(timestamp, config.identifier1() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom2Chat() && config.Custom2ObjectExamineEnabled()) {
                    chatPanelSidebar.addCustom2ChatMessage(timestamp, config.identifier2() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom3Chat() && config.Custom3ObjectExamineEnabled()) {
                    chatPanelSidebar.addCustom3ChatMessage(timestamp, config.identifier3() ? identifier : cleanedName, cleanedMessage);
                }
                break;
            case WELCOME:
                if (config.showCustomChat() && config.CustomWelcomeEnabled()) {
                    chatPanelSidebar.addCustomChatMessage(timestamp, config.identifier1() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom2Chat() && config.Custom2WelcomeEnabled()) {
                    chatPanelSidebar.addCustom2ChatMessage(timestamp, config.identifier2() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom3Chat() && config.Custom3WelcomeEnabled()) {
                    chatPanelSidebar.addCustom3ChatMessage(timestamp, config.identifier3() ? identifier : cleanedName, cleanedMessage);
                }
                break;
            case TRADE:
                if (config.showCustomChat() && config.CustomTradeEnabled()) {
                    chatPanelSidebar.addCustomChatMessage(timestamp, config.identifier1() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom2Chat() && config.Custom2TradeEnabled()) {
                    chatPanelSidebar.addCustom2ChatMessage(timestamp, config.identifier2() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom3Chat() && config.Custom3TradeEnabled()) {
                    chatPanelSidebar.addCustom3ChatMessage(timestamp, config.identifier3() ? identifier : cleanedName, cleanedMessage);
                }
                break;
            case TRADE_SENT:
                if (config.showCustomChat() && config.CustomTradeSentEnabled()) {
                    chatPanelSidebar.addCustomChatMessage(timestamp, config.identifier1() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom2Chat() && config.Custom2TradeSentEnabled()) {
                    chatPanelSidebar.addCustom2ChatMessage(timestamp, config.identifier2() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom3Chat() && config.Custom3TradeSentEnabled()) {
                    chatPanelSidebar.addCustom3ChatMessage(timestamp, config.identifier3() ? identifier : cleanedName, cleanedMessage);
                }
                break;
            case TRADEREQ:
                if (config.showCustomChat() && config.CustomTradeReqEnabled()) {
                    chatPanelSidebar.addCustomChatMessage(timestamp, config.identifier1() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom2Chat() && config.Custom2TradeReqEnabled()) {
                    chatPanelSidebar.addCustom2ChatMessage(timestamp, config.identifier2() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom3Chat() && config.Custom3TradeReqEnabled()) {
                    chatPanelSidebar.addCustom3ChatMessage(timestamp, config.identifier3() ? identifier : cleanedName, cleanedMessage);
                }
                break;
            case CONSOLE:
                if (config.showCustomChat() && config.CustomConsoleEnabled()) {
                    chatPanelSidebar.addCustomChatMessage(timestamp, config.identifier1() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom2Chat() && config.Custom2ConsoleEnabled()) {
                    chatPanelSidebar.addCustom2ChatMessage(timestamp, config.identifier2() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom3Chat() && config.Custom3ConsoleEnabled()) {
                    chatPanelSidebar.addCustom3ChatMessage(timestamp, config.identifier3() ? identifier : cleanedName, cleanedMessage);
                }
                break;
            case MODAUTOTYPER:
                if (config.showCustomChat() && config.CustomModAutoTyperEnabled()) {
                    chatPanelSidebar.addCustomChatMessage(timestamp, config.identifier1() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom2Chat() && config.Custom2ModAutoTyperEnabled()) {
                    chatPanelSidebar.addCustom2ChatMessage(timestamp, config.identifier2() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom3Chat() && config.Custom3ModAutoTyperEnabled()) {
                    chatPanelSidebar.addCustom3ChatMessage(timestamp, config.identifier3() ? identifier : cleanedName, cleanedMessage);
                }
                break;
            case CHALREQ_TRADE:
                if (config.showCustomChat() && config.CustomChalreqTradeEnabled()) {
                    chatPanelSidebar.addCustomChatMessage(timestamp, config.identifier1() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom2Chat() && config.Custom2ChalreqTradeEnabled()) {
                    chatPanelSidebar.addCustom2ChatMessage(timestamp, config.identifier2() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom3Chat() && config.Custom3ChalreqTradeEnabled()) {
                    chatPanelSidebar.addCustom3ChatMessage(timestamp, config.identifier3() ? identifier : cleanedName, cleanedMessage);
                }
                break;
            case IGNORENOTIFICATION:
                if (config.showCustomChat() && config.CustomIgnoreNotificationEnabled()) {
                    chatPanelSidebar.addCustomChatMessage(timestamp, config.identifier1() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom2Chat() && config.Custom2IgnoreNotificationEnabled()) {
                    chatPanelSidebar.addCustom2ChatMessage(timestamp, config.identifier2() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom3Chat() && config.Custom3IgnoreNotificationEnabled()) {
                    chatPanelSidebar.addCustom3ChatMessage(timestamp, config.identifier3() ? identifier : cleanedName, cleanedMessage);
                }
                break;
            case FRIENDNOTIFICATION:
                if (config.showCustomChat() && config.CustomFriendNotificationEnabled()) {
                    chatPanelSidebar.addCustomChatMessage(timestamp, config.identifier1() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom2Chat() && config.Custom2FriendNotificationEnabled()) {
                    chatPanelSidebar.addCustom2ChatMessage(timestamp, config.identifier2() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom3Chat() && config.Custom3FriendNotificationEnabled()) {
                    chatPanelSidebar.addCustom3ChatMessage(timestamp, config.identifier3() ? identifier : cleanedName, cleanedMessage);
                }
                break;
            case UNKNOWN:
                if (config.showCustomChat() && config.CustomUnknownEnabled()) {
                    chatPanelSidebar.addCustomChatMessage(timestamp, config.identifier1() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom2Chat() && config.Custom2UnknownEnabled()) {
                    chatPanelSidebar.addCustom2ChatMessage(timestamp, config.identifier2() ? identifier : cleanedName, cleanedMessage);
                }
                if (config.showCustom3Chat() && config.Custom3UnknownEnabled()) {
                    chatPanelSidebar.addCustom3ChatMessage(timestamp, config.identifier3() ? identifier : cleanedName, cleanedMessage);
                }
                break;
        }
    }

    @Subscribe
    public void onHitsplatApplied(HitsplatApplied hitsplatApplied) {
        if (config.showCombatTab() || config.CustomCombatEnabled() || config.Custom2CombatEnabled() || config.Custom3CombatEnabled())
        {
            if (hitsplatApplied.getHitsplat().isMine() || !config.onlyshowMyHitsplats())
            {
            Actor attacker = hitsplatApplied.getActor();
            Actor defender = attacker.getInteracting();
            String defenderName = attacker.getName();
            String attackerName = (defender != null) ? defender.getName() : attacker.getName();
            Hitsplat hitsplat = hitsplatApplied.getHitsplat();
            if (hitsplat.getAmount() == 0 && config.hidezerodamageHitsplats()) {
                return;
            }
            int damageAmount = hitsplat.getAmount();
            String identifier = "Combat";
            String timestamp = getCurrentTimestamp();
            String combatMessage = (defender == null) ? defenderName + " was hit for: " + damageAmount
                    : attackerName + " hit " + defenderName + " for: " + damageAmount;
            if (config.showCombatTab()) {
            chatPanelSidebar.addCombatMessage(timestamp, (config.identifierC()) ? "Combat" : "", combatMessage);
            }
            if (config.showCustomChat() && config.CustomCombatEnabled()) {
                chatPanelSidebar.addCustomChatMessage(timestamp, config.identifier1() ? identifier : "", combatMessage);
            }
            if (config.showCustom2Chat() && config.Custom2CombatEnabled()) {
                chatPanelSidebar.addCustom2ChatMessage(timestamp, config.identifier2() ? identifier : "", combatMessage);
            }
            if (config.showCustom3Chat() && config.Custom3CombatEnabled()) {
                chatPanelSidebar.addCustom3ChatMessage(timestamp, config.identifier3() ? identifier : "", combatMessage);
            }
        }}
    }
    @Subscribe
    public void onActorDeath(ActorDeath actorDeath) {
        Actor actor = actorDeath.getActor();
        String actorName = actor.getName();

        String identifier = "Death";
        String timestamp = getCurrentTimestamp();
        String deathMessage = actorName + " died.";

        if (config.showCombatTab() && config.displayDeaths()) {
            chatPanelSidebar.addCombatMessage(timestamp, (config.identifierC()) ? "Death" : "", deathMessage);
        }
        if (config.showCustomChat() && config.CustomCombatEnabled() && config.displayDeaths()) {
            chatPanelSidebar.addCustomChatMessage(timestamp, config.identifier1() ? identifier : "", deathMessage);
        }
        if (config.showCustom2Chat() && config.Custom2CombatEnabled() && config.displayDeaths()) {
            chatPanelSidebar.addCustom2ChatMessage(timestamp, config.identifier2() ? identifier : "", deathMessage);
        }
        if (config.showCustom3Chat() && config.Custom3CombatEnabled() && config.displayDeaths()) {
            chatPanelSidebar.addCustom3ChatMessage(timestamp, config.identifier3() ? identifier : "", deathMessage);
        }
    }

    private String getIdentifier(String cleanedName, ChatMessage event) {
        switch (event.getType()) {
            case PUBLICCHAT:
                return "Public - " + cleanedName;
            case MODCHAT:
                return "ModChat - " + cleanedName;
            case PRIVATECHAT:
                return "Private - " + cleanedName;
            case MODPRIVATECHAT:
                return "ModPrivate - " + cleanedName;
            case CLAN_CHAT:
                return "Clan - " + cleanedName;
            case CLAN_MESSAGE:
                return "Clan Msg" + cleanedName;
            case CLAN_GUEST_MESSAGE:
                return "ClanGuest Msg" + cleanedName;
            case CLAN_GIM_CHAT:
                return "ClanGIM - " + cleanedName;
            case CLAN_GIM_MESSAGE:
                return "ClanGIM Msg" + cleanedName;
            case CLAN_GUEST_CHAT:
                return "ClanGuest - " + cleanedName;
            case CHALREQ_CLANCHAT:
                return "Chalreq Clan" + cleanedName;
            case PRIVATECHATOUT:
                return "Private Out - " + cleanedName;
            case FRIENDSCHAT:
                return "Friends Chat - " + cleanedName;
            case CHALREQ_FRIENDSCHAT:
                return "Chalreq Friends" + cleanedName;
            case FRIENDSCHATNOTIFICATION:
                return "FriendsChatNotify" + cleanedName;
            case BROADCAST:
                return "Broadcast" + cleanedName;
            case GAMEMESSAGE:
                return "Game Message" + cleanedName;
            case ENGINE:
                return "Engine" + cleanedName;
            case NPC_EXAMINE:
                return "NPC Examine" + cleanedName;
            case SPAM:
                return "Spam" + cleanedName;
            case DIALOG:
                return "Dialog" + cleanedName;
            case ITEM_EXAMINE:
                return "Item Examine" + cleanedName;
            case OBJECT_EXAMINE:
                return "Object Examine" + cleanedName;
            case WELCOME:
                return "Welcome" + cleanedName;
            case TRADE:
                return "Trade" + cleanedName;
            case TRADE_SENT:
                return "Trade Sent" + cleanedName;
            case TRADEREQ:
                return "Trade Req" + cleanedName;
            case CONSOLE:
                return "Console" + cleanedName;
            case MODAUTOTYPER:
                return "ModAutoTyper - " + cleanedName;
            case CHALREQ_TRADE:
                return "Chalreq Trade" + cleanedName;
            case IGNORENOTIFICATION:
                return "Ignore Notification" + cleanedName;
            case FRIENDNOTIFICATION:
                return "Friend Notification" + cleanedName;
            case UNKNOWN:
                return "Unknown" + cleanedName;
            default:
                return cleanedName;
        }
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

    private String cleanDialogMessage(String message)
    {
        return message.replace("|", ": ");
    }

    @Subscribe
    public void onConfigChanged(ConfigChanged event) {
        if ("chatpanel".equals(event.getGroup())) {
            if (event.getKey().startsWith("show")) {
                chatPanelSidebar.reloadPlugin();
            }
            if (event.getKey().startsWith("font") || event.getKey().endsWith("FontSize")) {
                chatPanelSidebar.updateFonts();
            }
            if (event.getKey().equals("fontFamily") && (config.fontFamily().equals(ChatPanelConfig.FontFamily.CUSTOM_FONT))) {
                chatPanelSidebar.fontLoadErrorShown = false;
                chatPanelSidebar.updateFonts();
                chatPanelSidebar.fontLoadErrorShown = true;
            } else {
                chatPanelSidebar.updateChatStyles();
                if (chatPanelSidebar.isPopout()) {
                    chatPanelSidebar.setCactus(config.popoutOpacity());
                }
            }
            if (event.getKey().startsWith("hideSidebar")) {
                if (config.hideSidebarIcon()) {
                    String message = "<html>Hide Sidebar Icon enabled.<br> Turn plugin off/on with Auto-pop out window enabled to spawn a Chat Panel.</html>";
                    JOptionPane.showMessageDialog(null, message, "Notice", JOptionPane.WARNING_MESSAGE);
                }
            }
            }
            if (config.hideSidebarIcon() && !config.AutoPop()) {
                String message = "<html>Warning: Hide Sidebar Icon is enabled but Auto-pop out window is not.<br>Enable Auto-pop out or disable Hide Sidebar Icon to access to Chat Panel.</html>";
                JOptionPane.showMessageDialog(null, message, "Configuration Issue", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
