# Chat Panel Plugin


This plugin displays in-game chat in a seperate window or the side panel.

The Chat Panel window can be moved freely like a normal windowed application, including to different monitors.



![image](https://github.com/Yenof/chat-panel/assets/122739279/93b9e17f-f326-4a2e-a8ba-d4a0b977fd0a)![image](https://github.com/Yenof/chat-panel/assets/122739279/f7df8bd3-9ae9-4473-b032-b4375893397f)















## Configuration
There are many config groups that contain the plugin's settings.<br/>
They can be expanded by clicking on the group name or little arrows.

![Screenshot from 2024-06-18 19-56-35](https://github.com/Yenof/chat-panel/assets/122739279/60825c4e-da16-4536-9456-7a81ee3a8fe4) ![image](https://github.com/Yenof/chat-panel/assets/122739279/b561e3ab-7a41-4c49-a90b-ae1fb9552dcc) 


Configurable text, background, and name colors per tab.

Adjustable Font sizes per tab. (Min size 5, max 50)

![Screenshot from 2024-06-18 19-57-54](https://github.com/Yenof/chat-panel/assets/122739279/0d9b9a98-1594-43c2-8cb8-210dcefd71c7)

"Odd Row Shading" tints alternating lines of chat for visability. Negative entries darken, positive lighten. 

Line spacing adds space in between messages. (Max 10)

Options for Bold, Italic, and Plain font styles.

Timestamps can be set using SimpleDateFormat patterns. (HH:mm, yyyy.MM.dd, and more)

![Screenshot from 2024-06-18 19-59-28](https://github.com/Yenof/chat-panel/assets/122739279/54917cf5-f0fc-4d50-b472-4cf0cbd6f9d6) ![Screenshot from 2024-06-18 19-59-14](https://github.com/Yenof/chat-panel/assets/122739279/94dac02c-0e24-4628-9a56-5aaf022212cd)



The window size and transparency (opacity) of the pop out window can be configured.

The pop out window can be set to "Always on Top", keeping it on top of other windows and programs.

"Auto-Pop Out Window" allows the pop out window to spawn when RuneLite is started with the plugin on, or when the plugin is turned on.


![image](https://github.com/Yenof/chat-panel/assets/122739279/da8a596f-0d14-420e-b677-1ea28b52f9f6) ![Screenshot from 2024-06-18 20-00-05](https://github.com/Yenof/chat-panel/assets/122739279/b8f441e8-6378-446f-84cd-08ee0991a74b)




Can choose between many tabs to display: Public, Private, Clan, Friends Chat, Game, All, Combat, and Custom (1, 2, 3).

For the Combat Tab, there are options to only show combat events related to you, hide zero damage events, and show deaths in chat.

The height of the Chat Area in the side panel can be adjusted, recommended to be less than the height of your client.

The sidebar icon position can be adjusted or the icon hidden.

Can hide the Pop out and Pop in buttons, except when the side panel is empty.

The Timestamp Color can be overridden by enabling "Custom Timestamp Color", this will change the color of the Timestamp in all tabs to the color selected.

Chosen words can be highlighted with the "Highlight Words" option. "Partial Word Highlighting" allows highlight words like "Sell" to highlight part of "Selling".



![Screenshot from 2024-06-18 20-00-37](https://github.com/Yenof/chat-panel/assets/122739279/0d66776d-ed88-4985-8d40-2f40f9ca5508) ![image](https://github.com/Yenof/chat-panel/assets/122739279/a2d9f62d-a996-4bc3-b3d5-234f43c524ba)






## Usage:

Right clicking on a tab shows additional options like "Pop Out", "Reset History", and "Export Log".

Clicking a tab with middle mouse button can also pop the tab out into it's own window.

Scroll and click to lock position while reading, return to the bottom to resume snapping to most recent message.

Text can be copied with Ctrl+C.

Custom tabs start empty, and you must add desired chat channels through the Custom Chat configurations.

If you have "Hide Sidebar Icon" enabled and close the pop out window, you will need to toggle the plugin on/off with Auto-Pop enabled to have a new Chat Panel window created.


 ![image](https://github.com/Yenof/chat-panel/assets/122739279/fdc47a4a-f70b-44fd-b4bb-4d56ba30458a) ![Screenshot from 2024-02-12 17-22-18](https://github.com/Yenof/chat-panel/assets/122739279/d0c3e199-f9a9-4e3d-b199-5d1acc1a6d96) 





## Notes:
Max lines of chat is 10,000 by default.

Some config options apply retroactively when changed, but not all.

When toggling the plugin on/off it does not remember message history (including closing and reopening the client).

Really long NPC Dialog messages get cut off and don't show the whole text, there are other plugins that handle this better.

Combat Tab provides very basic combat logging, there are other plugins for more advanced logging.

The Combat tab relies on what the player is targetting, so if you are hit and you have no target it will just say "PlayerName was hit for: 1".

The appearance of Chat Panel's titlebar is mostly dependent on your operating system's settings, some systems allow user customization of titlebars.<br/>
For now, Chat Panel does not inherit RuneLite's Custom Chrome on all OSs, some work though.<br/>
Screenshots are taken on X11/GNOME/22.04 with RuneLite's Custom Chrome enabled.


<br/>
<br/>
<br/>
I love feedback, please feel free to reach out with any comments, concerns, or questions to @Yenofthunder on Discord. :D<br/>
or<br/>
Issues can be posted to Github Issues.<br/>
Suggestions can be posted to Github Discussions.
