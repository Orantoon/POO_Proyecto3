# Model-View-Controller (MVC) Mini Virtual Console (Tetris and Pacman)

![Pacman](https://github.com/Orantoon/POO_Proyecto3/assets/62961473/68f89769-8978-4430-9700-007db47eea5a)
![Tetris](https://github.com/Orantoon/POO_Proyecto3/assets/62961473/0617f6d9-794f-4b1f-9b51-ac7c418c9a62)

## Objectives
* Implement the MVC design pattern.
* Learn about using sockets in Java for application communication (JSON).

## Description
This project implements a mini virtual console that includes two games: Pacman and Tetris. The games are controlled using the WASD keys and the spacebar. The console consists of three applications: the game itself (PacMan.java or Tetris.java), the screen display (Screen2.java), and the controller (Controller.java). The applications communicate with each other through sockets.

To run the project, follow these steps:

1. Add the JAR file to the project configuration.
2. Start the game first (PacMan.java or Tetris.java).
3. Run Screen2.java.
4. Run Controller.java.

## Requirements
Java 8 or higher.

## How to Play
Must have the Controller window selected.

* Pacman: Use the WASD keys to move Pacman through the maze and eat the dots. Avoid the ghosts. The game ends when all dots are eaten or Pacman is caught by a ghost.
* Tetris: Use the WASD keys to move and rotate the falling blocks. Arrange the blocks to form complete horizontal lines. The game ends when the blocks reach the top of the screen.
