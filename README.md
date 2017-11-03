# MC-Experiment-AI

--- Workspace Setup ---

Use the following guide: http://techwiseacademy.com/minecraft-modding-setting-up-your-environment/

1) Set Java environment variables

2) Install Eclipse

3) Configure Forge

Un-zip Forge in a separte folder.
Once extracted, in the same directory location hold down shift, and right click, then select “Open command window here”.

Type the following command in the cmd window: 

*gradlew setupDecompWorkspace --refresh-dependencies*

Next type the following command:

*gradlew eclipse*

Inside the 'src' folder (Found at extraction location), download 'main' and replace with existing content.

___________________________________________

Test bot commands

Enter the following commands in the game chat window:

/minebot getfood

/minebot getwool

/minebot getfuel

Framework used to build prototype: https://github.com/michaelzangl/minebot/

________________________________________

--- Author Notes ---

Limited RAM with Forge extraction

Increase virtual memory space - 'Computer performance options'

Run with command: *gradlew -Dorg.gradle.jvmargs =-Xmx10240m setupDecompWorkspace*

Edit gradlew file:
*DEFAULT_JVM_OPTS = "Xmx2048m"

________________________________________

Prototype demonstration:

Vid No.1   https://youtu.be/nof9Si2_FH4

Vid No.2   https://youtu.be/OfVk3JW8-ZI



