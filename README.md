# PlayerSlap
## Description: 
This plugin gives server administrators the ability to 'slap' players as a form of punishment. The action of 'slapping' can involve removing health, spawning mobs, hitting the player with lightning, producing smoke and broadcasting a message. Different types of slaps can be defined in the config file. 

## Versions: 
This project is currenty in an early development build. 

## License: 
This plugin and its source code are released under a MIT license (see the LICENSE file for full details). This plugin is copyright (c) aappleton3/aappleton8, 2018. 

## Build: 
This plugin is built using Eclipse. To build the plugin, the .classpath file firstly needs to be recreated using Eclipse, and the craftbukkit-1.12.2.jar file needs to be added as an external build dependency. 

## Commands: 
This plugin has the following commands: 
 - */slap [&lt;player&gt;] [&lt;type&gt;] [&lt;worth&gt;]* - Slap a player 
 - */slapall [&lt;type&gt;] [&lt;worth&gt;]* - Slap every player 
 - */forceslap &lt;player&gt; [&lt;type&gt;] [&lt;worth&gt;]* - Slap a player, even if it has the *playerslap.noslap* permission 
 - */slapaccept [&lt;player&gt;]* - Accept the slap 
 - */slaprelease &lt;player&gt;* - Release the player from a permanent slap 
 - */playerslap help|version|{times &lt;player&gt;}|{config save|reload|{set &lt;options&gt;}}* - The main playerslap command 

## Permissions: 
 - playerslap.* - The root permission for this plugin 
 - playerslap.slap.all - Slap every players 
 - playerslap.slap.others - Slap other players 
 - playerslap.slap - Slap oneself 
 - playerslap.times.* - See and reset the amount of times a player has been slapped 
 - playerslap.times.reset - Reset the amount of times a player has been slapped 
 - playerslap.times.others - See the amount of times another player has been slapped 
 - playerslap.times - See the amount of times the command sender has been slapped 
 - playerslap.force - Use the */forceslap* command 
 - playerslap.noslap.protect - Prevent this player from being slapped and slap the slapping player 
 - playerslap.noslap - Prevent this player from being slapped 
 - playerslap.see.* - See all plugin messages 
 - playerslap.see.slap - See player slap messages 
 - playerslap.see.noslap - Let a player see whan a player tried to slap a player with the *playerslap.noslap* command 
 - playerslap.see.config - Let a player see config save, set and reload messages 
 - playerslap.config.* - Let a player use all config commands 
 - playerslap.config.save - Let a player save the config 
 - playerslap.config.reload - Let a player reload the config 
 - playerslap.config.set.* - Let a player set basic config file options 
 - playerslap.type.* - Let a player use all slap types 
 - playerslap.type.defaultworth.* - Let a player use all slap types with their specified worths 
 - playerslap.type.noworth.* - Let a player use all slap types with no worth 
 - playerslap.type.anyworth.* - Let a player use all slap types with any positive worth 

## Configs: 
This plugin uses the following config files: 
 - config.yml 
 - players.yml
