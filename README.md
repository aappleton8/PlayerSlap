# PlayerSlap
## Description: 
This plugin gives server administrators the ability to 'slap' players as a form of punishment. The action of 'slapping' can involve removing health, spawning mobs, hitting the player with lightning, producing smoke and broadcasting a message. Different types of slaps can be defined in the config file. 

## Versions: 
This project is currently in a development build. 

## License: 
This plugin and its source code are released under a MIT license (see the LICENSE file for full details). This plugin is copyright (c) aappleton3/aappleton8, 2018. 

## Build: 
This plugin is built using Eclipse. To build the plugin, the .classpath file firstly needs to be recreated using Eclipse, and the craftbukkit-1.12.2.jar file needs to be added as an external build dependency. 

## Commands: 
This plugin has the following commands: 
 - */slap [&lt;player&gt;] [&lt;type&gt;] [&lt;worth&gt;]* - Slap a player 
 - */slapall [&lt;type&gt;] [&lt;worth&gt;]* - Slap every player except those who are protected 
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
 - playerslap.unslap.mustaccept.decrement - Quietly remove a player's need to accept a slap whilst decrementing its slap times counter by 1 (also gives playerslap.unslap.mustaccept) 
 - playerslap.unslap.mustaccept - Quietly remove a player's need to accept a slap whithout decrementing its slap times counter  
 - playerslap.unslap.ispermanent.decrement - Quietly release a player from a permanent slap whilst decrementing its slap times counter by 1 (also gives playerslap.unslap.ispermanent) 
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
 - playerslap.config.setplayer.times - Let a player set the amount of times a player has been slapped (also gives playerslap.config.setplayer.times.reset)
 - playerslap.config.setplayer.times.reset - Let a player set the amount of times a player has been slapped back to 0 
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
 
 The 'config.yml' file contains basic plugin settings as well as all the defined slap types. The 'players.yml' file contains data stored about each player, such as how many times the player has been slapped and the player's current slapped state. 

### The 'config.yml' File: 
An example 'config.yml' file is given below: 

```YAML
slapself: true
slapdefault: '1'
incrementonslapall: true
messages:
  accept: __Default
  releasebroadcast: __Default
  releasepersonal: __Default
  noreleasepersonal: _
  noreleasesender: __Default
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
      slapbroadcast: __Giver slapped __Slapped. 
      slappersonal: __Giver slapped you. 
      death: __Default
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
      broadcast: __Default
      personal: __Default
      death: __Default
    mobs:
      BLAZE: 10
      GIANT: 1
      VILLAGER: 100
```

The string values of subfields of each configuration section called 'messages' can contain some of the following words which will be substituted for another value when the message is sent: 
- \_\_Default gets substituted for the default message. 
- \_\_Unknown gets substituted for 'unknown' 
- \_\_None get substituted for nothing 
- \_\_Slapped gets substituted for the name of the person who has been slapped 
- \_\_Giver gets substituted for the name of the person who gave the slap or the name of the person trying to remove a permanent slap (as appropriate) 
- \_\_Type becomes the slap type 
- \_\_Permanent becomes 'permanent' if the slap is permanent and nothing otherwise 
The following restrictions apply to using the substitutions defined in the above list: 
- The 'accept' message cannot substitute \_\_Type 
- The 'releasebroadcast' message cannot substitute \_\_Type 
- The 'releasepersonal' message cannot substitute \_\_Type 
- The 'noreleasepersonal' message cannot substitute \_\_Type
- The 'noreleasesender' message cannot substitute \_\_Type
- The 'slapbroadcast' message has no restrictions 
- The 'slappersonal' message has no restrictions 
- The 'death' message has no restrictions 

The below list explains the top-level fields and the fields immediately inside the top-level 'messages' section in the above example 'config.yml' file: 
- The 'slapself' field is a boolean that determines whether a player can slap itself or not. 
- The 'slapdefault' field contain the id of the default slap type. 
- The 'incrementonslapall' field is a boolean that determines whether a player's slap counter should be incremented on a slap as a result of the '/slapall' command. 
- The 'accept' message is the message broadcast when a player accepts a slap. 
- The 'releasebroadcast' message is the message broadcast when a player is released from a slap. 
- The 'releasepersonal' message is the message sent to a player when the player is released from a slap. 
- The 'noreleasepersonal' message is the message sent to a player when it tries to accept a slap from which it has not been released. 
- The 'noreleasesender' message is the message sent to a player who tries to accept a slap for a player but cannot as it is a permanent slap from which the player has not been released. 
- The 'slaptypes' section contains the name of each slap type. 

The below list explains the fields within each section about a specific slap: 
- The 'health' field is an integer that defines how much health to remove from a player. 
- The 'worth' field is an integer that indicates by how much each players' field recording the amount of times it has been slapped will be incremented. 
- The 'mustaccept' field is a boolean that indicates whether the player has to accept the slap or not. 
- The 'permanent' field is a boolean that indicates whether the slap lasts until someone with the appropriate permission releases the player from it or not. 
- The 'lightning' field is a boolean that indicates whether the player is stuck by lightning or not. 
- The 'smoke' field is an integer that defines the radius of smoke produced by the slap ('0' disables smoke). 
- The 'broadcast' message is the message broadcast when a player is slapped. 
- The 'personal' message is the message sent to the player when the player is slapped. 
- The 'death' message is the message broadcast if the slap kills the player. 
- The 'mobs' field contains the case-sensitive name (as defined on this web-page: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/entity/EntityType.html) and amount to spawn of each mob, in the form 'name: amount'. 

### The 'players.yml' File: 
An example 'players.yml' file is given below: 

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

The only top-level field of the 'players.yml' is the 'players' field, which contains the UUID of each player. The UUID of each player forms a section containig the data about each player. 

The below list explains the fields within the each UUID section: 
- The 'username' field contains the username of the player. 
- The 'exempt' field is a boolean that states whether the player is exempt from being slapped or not (this is a stronger form of protection than the *playerslap.noslap* permission). 
- The 'times' field is an integer stating the cumulative value of the 'worth' field of each slap that the player has been slapped with. 
- The 'mustaccept' field states whether the player needs to accept a slap or not. 
- The 'permanent' field states whether the player is currently under a permanent slap or not. 

