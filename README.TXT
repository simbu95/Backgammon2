This is a java project I was working on earlier that has most of the functionality of Backgammon implemented. 

It is in java, and so to run the .jar file (basically the same as a .exe) you will need to have java installed on your computer.

The .java files are the source code, you may browse and edit/recompile as you wish, though they are very messy. I removed a couple features from my old work that wasn't needed and then submitted. I will probably update the files for readabilty later, but for now this is it.

If you run the .jar, 2 UIs will pop up, one to play the game and one to view past board states. 

I have configured the "AI" to play O and the player to play X. This can be edited on lines 30-31 of Backgammon2.java. 
For the AI to move, you must press the move button, this allows you to see which dice the AI recieved before it moving.

The doubling feature is barely implemented, that is it will ask the player to double or not, but the opposing player always accepts, 
and nothing really happens. This can be edited in lines 247 of Board.java and lines 169 of Backgammon2.java. Both will need to be edited In order to have full functionality.

The AI is mostly handled in lines 207 of Backgammon2.java and the current reward function is defined at lines 319 of Board.java.
Editing either will change the AI.

Remember, for any edits to take effect, you must recompile into a new .jar file however you see fit. This can be done through the
command line with javac Backgammon2.java, or you can use eclipse, netbeans or any editor of your choice. 
