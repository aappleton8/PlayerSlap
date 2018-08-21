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
 - */slapinfo slaplist|{slap &lt;type&gt;}|{slapmessages &lt;type&gt;}|{slapmobs &lt;type&gt;}|{player &lt;player&gt;}|{times &lt;player&gt;}|general* - Get information about the available slaps, a specific slap, messages sent by a specific slap, mobs spawned by a specific slap, a player, the amount of times a player has been slapped or general configuration settings. 
 - */playerslap help|version|{config save|reload|{set &lt;property&gt; &lt;value&gt;}}|{slap {set &lt;type&gt; &lt;property&gt; [sub-property] &lt;value&gt;}|{new &lt;type&gt;}|{remove &lt;type&gt;}}|{player {set &lt;player&gt; &lt;property&gt; &lt;value&gt; [&lt;modifier&gt;]}|{remove &lt;player&gt;}}* - The main playerslap command that gives command help, the plugin version and the ability to set configuration and player properties. 

## Permissions: 
All permissions default to being op only. 
 - playerslap.* - The root permission for this plugin 
 - playerslap.slap.all - Slap every players 
 - playerslap.slap.others - Slap other players 
 - playerslap.slap - Slap oneself 
 - playerslap.accept.others - Accept a slap on behalf of another player 
 - playerslap.accept - Accept a slap 
 - playerslap.release - Release a player from a permanent slap 
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
 - playerslap.see.accept - Let a player see when another player accepts a slap 
 - playerslap.see.release - Let a player see when another player has been released from a permanent slap 
 - playerslap.see.config - Let a player see config save, set and reload messages 
 - playerslap.unslap.* - Quietly unslap a player 
 - playerslap.unslap.mustaccept.decrement - Quietly remove a player's need to accept a slap whilst decrementing its slap times counter by 1 
 - playerslap.unslap.mustaccept - Quietly remove a player's need to accept a slap whithout decrementing its slap times counter  
 - playerslap.unslap.ispermanent.decrement - Quietly release a player from a permanent slap whilst decrementing its slap times counter by 1 
 - playerslap.unslap.ispermanent - Quietly release a player from a permanent slap whithout decrementing its slap times counter 
 - playerslap.config.* - Let a player use all config commands 
 - playerslap.config.save - Let a player save the config 
 - playerslap.config.reload - Let a player reload the config 
 - playerslap.config.newslap - Let a player create a new slap type 
 - playerslap.config.removeslap - Let a player remove a slap type 
 - playerslap.config.removeplayer - Let a player remove another player's information 
 - playerslap.config.setconfig.* - Let a player set basic config file options 
 - playerslap.config.setconfig.slapself - Let a player determine whether players can slap themselves or not 
 - playerslap.config.setconfig.slapdefault - Let a player determine the default slap 
 - playerslap.config.setconfig.incrementonslapall - Let a player determine whether a player's slap count is incremented when the */slapall* command is used 
 - playerslap.config.setslap.* - Let a player set basic config file options 
 - playerslap.config.setslap.health - Let a player set the amount of health a slap removes 
 - playerslap.config.setslap.worth - Let a player set the worth of a slap 
 - playerslap.config.setslap.mustaccept - Let a player choose whether a slap requires a player to accept it or not 
 - playerslap.config.setslap.permanent - Let a player choose whether a slap is permanent or not 
 - playerslap.config.setslap.lightning - Let a player set whether a slap produces lightning or not 
 - playerslap.config.setslap.smoke - Let a player set the radius of smoke produced by a slap 
 - playerslap.config.setslap.mob - Let a player set what mobs a slap produces 
 - playerslap.config.setplayer.* - Let a player set basic player properties (also gives playerslap.unslap.*) 
 - playerslap.config.setplayer.times - Let a player set the amount of times a player has been slapped 
 - playerslap.config.setplayer.exempt - Let a player set whether another player is exempt from being slapped or not 
 - playerslap.info.* - Let a player get information about slap types, players and general config information 
 - playerslap.info.slap - Let a player get information about a slap type 
 - playerslap.info.slapmessages - Let a player get the messages produced by a slap 
 - playerslap.info.slapmohs - Let a player get the mobs spawned by a slap 
 - playerslap.info.slaplist - Let a player list the available slap types 
 - playerslap.info.player - Let a player get information about a player 
 - playerslap.info.general - Let a player get general config file settings 
 - playerslap.type.* - Let a player use all slap types 
 - playerslap.type.defaultworth.* - Let a player use all slap types with their specified worths 
 - playerslap.type.defaultworth.&lt;slap&gt; - Let a player use the specified slap with its default worth 
 - playerslap.type.noworth.* - Let a player use all slap types with no worth 
 - playerslap.type.noworth.&lt;slap&gt; - Let a player use the specified slap type with no worth 
 - playerslap.type.anyworth.* - Let a player use all slap type with any worth 
 - playerslap.type.anyworth.&lt;slap&gt; - Let a player use the specified slap type with any worth 

## Configuration Files: 
### General Information: 
This plugin uses the following config files: 
 - config.yml 
 - players.yml
 
 The config.yml file contains basic plugin settings as well as all the defined slap types. The players.yml file contains data stored about each player, such as how many times the player has been slapped and the player's current slapped state. 

### The 'config.yml' File: 
An example config.yml file is given below: 

```YAML
slapself: true
slapdefault: '1'
incrementonslapall: true
messages:
  accept: $Default
  releasebroadcast: $Default
  releasepersonal: $Default
  noreleasepersonal: $Default
slaptypes:
  '1':
    health: 3
    worth: 1
    followup:
      mustaccept: true
      permanent: false
    lightning: true
    smoke: 30
    messages:
      slapbroadcast: $Giver slapped $Slapped. 
      slappersonal: $Giver slapped you. 
      death: $Default
    mobs:
      ZOMBIE: 5
      SKELETON: 1
  '2':
    health: 5
    worth: 3
    followup:
      mustaccept: false
      permanent: false
    lightning: true
    smoke: 50
    messages:
      broadcast: $Default
      personal: $Default
      death: $Default
    mobs:
      BLAZE: 10
      GIANT: 1
      VILLAGER: 100
```

The string values of subfields of each configuration section called 'messages' can contain the following words which will be substituted for another value when the message is sent: 
- $Default gets substituted for a default message. 
- $Unknown gets substituted for 'unknown' 
- $None get substituted for nothing 
- $Slapped gets substituted for the name of the person who has been slapped 
- $Giver gets substituted for the name of the person who gave the slap 

The below list explains the fields in the example file: 
- The 'slapself' field is a boolean that determines whether a player can slap itself or not. 
- The 'slapdefault' field contain the id of the default slap type. 
- The 'incrementonslapall' field is a boolean that determines whether a player's slap counter should be incremented on a slap as a result of the '/slapall' command. 
- The accept message is the mesage broadcast when a player accepts a slap. 

### The 'players.yml' File: 
An example players.yml file is given below: 

```YAML
players:
  UUID-here:
    username: genericusername
    exempt: false
    times: 5
    currentslap:
      mustaccept: false
      permanent: false
```



