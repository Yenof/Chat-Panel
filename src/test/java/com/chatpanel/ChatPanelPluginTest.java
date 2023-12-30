package com.chatpanel;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class ChatPanelPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(ChatPanelPlugin.class);
		RuneLite.main(args);
	}
}