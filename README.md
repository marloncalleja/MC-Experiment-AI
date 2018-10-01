# MC-Experiment-AI

With modern video games becoming increasingly dependent on the behavioural nature of non-player characters (NPCs), the need for smarter AI agents to take the next best action in a complex environment is pressing.
The study led to the exploration of existing methods, employing similar techniques to goal oriented action planning (GOAP), to craft an AI agent within a sandbox game environment. In this case Minecraft was chosen for its open-world setting, allowing for free form experimentation. An action pool was employed to instruct the AI agent's behaviour, implementing logic in populating decision trees with available actions in the direction of reaching a set goal. Several tree traversal algorithms were tested in finding a solution from the generated decision tree, taking world-state data into account.
A-Star (A*) search, being a pathfinding algorithm, its application onto a decision tree has allowed the assignment of costs to each step in the action sequence, being performed by the AI agent. The study concluded that the application of a pathfinding algorithm is still worth employing in the context of building an AI agent, even though it takes longer to compute than Breadth-First Search (BFS) and Depth-First Search (DFS) algorithms in the majority of cases, for having to calculate action cost.
Retrieval of the best solution from the tree is always guaranteed, obtaining an action sequence with the least number of steps required to reach a set goal. Turn-based games or any setting which dedicates time for the NPC to 'think' and decide about a solution is the ideal instance to employ A-Star search. Real-time games involving fast paced combat hinders the algorithm's efficiency.


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

Further project details can be found inside the attached write-up (PDF file).

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



