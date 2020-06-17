# SlimTrade
An overlay for Path of Exile that creates macro popups for trade messages. Also includes a chat scanner, trade history, item ignore feature, color themes, update checker, and more! POE must be run in 'Windowed Fullscreen' or 'Windowed' mode.<br><br>
[**Donate with PayPal**](https://www.paypal.me/zmilla93)<br>
[**Download Latest Version**](https://github.com/zmilla93/SlimTrade/releases/latest)<br>

## Vulkan Renderer
- Currently POE's new Vulkan renderer only works with 3rd party tools while in windowed mode. However, you can fake windowed borderless using an AutoHotkey script.
1. Download & Run "[poe_borderless.ahk](https://cdn.discordapp.com/attachments/721037187361144882/722805074606162050/poe_borderless.ahk)" (Requires AutoHotkey to be installed)
1. Run POE with Vulkan enabled in windowed mode
1. Focus POE and press **ctrl+ alt + w** to toggle window full screen
- Hotkey can be modified by editing the script.

## Incoming & Outgoing Trades
Popups are automatically created when trade messages are sent or received.<br>
Incoming trades are green, outgoing trades are red.<br>
![](/src/main/resources/images/incoming-trade.png)<br>
![](/src/main/resources/images/outgoing-trade.png)<br>

There are multiple color themes, as well as a colorblind option to swap green and red for blue and pink respectively.<br>
![](/src/main/resources/images/incoming-trade-dark-cb.png)<br>
![](/src/main/resources/images/outgoing-trade-dark-cb.png)<br>

Incoming trades highlight the buyer's name when joining your hideout, and create an info window with item and stash names.<br>
This info popup can be hovered to highlight the item, or clicked to search the name in the stash.<br>
Stash color coding can be added in the options.<br>
![](/src/main/resources/images/stash.png)<br>

## Ignore Items
Ignore specific item names or generic terms.<br>
![](/src/main/resources/images/ignore.png)<br>

## Customization
Additional color theme examples shown here.<br>
There are inbuilt macros for preset commands, and custom macros for everything else!<br>
![](/src/main/resources/images/macro-customizer.png)<br>
Theme: Stormy<br>

## History
Easily restore any recent trade message with the history panel.<br>
![](/src/main/resources/images/history.png)<br>
Theme: Monokai<br>

## Chat Scanner
The chat scanner allows you to search for custom phrases. These popups appear orange.<br>
![](/src/main/resources/images/scanner-message.png)<br>

You can create multiple search presets, ignore terms, and add custom responses.<br>
![](/src/main/resources/images/scanner-full.png)<br>
Theme : Solarized Dark<br>
