# Word-Letter_Game-TCP-
Simple network game that uses TCP as socket protocol.

A simple network game that uses TCP as socket protocol. The game can be played by
two hosts.

• One of the players starts the game and writes a word and sends it to the other player.
The other player sends a new word starting with the last 2 letters of the word.
• If the player cannot respond with a new word within a certain period of time, the player
loses the game. The system automatically sends a message to the other player stating
that the game is over.
• The word sent by the player must not have been sent before. Therefore, the system
should check the word and send it later. Otherwise, the user should be asked to write a
new word.
