# Codecool Quest

This is a simple tile-based RPG game with some very dangerous monsters:goberserk:. You can enjoy our game through 3 levels.  
Who does not want to fight monsters, open all the doors, or just loot all the gold from the dungeon:moneybag::moneybag:. 

It was developed by the Guan Xi Gang (Roland Csősz, Botond Donáth, Máté Rajnai) in the OOP module at Codecool.  
During this project we learned and practised the OOP concepts. **There was a skeleton code provided by [pwmarcz](https://github.com/pwmarcz) from Codecool**.  

#### Well, lets flex with some features:
  - Monsters can move and attack the player  
  - Monsters are moving constantly  
  - Player have inventory  
  - Player can pick up items  
  - Multiple levels  
  - Boss fight  
  - "Camera" follows the player  
  - Player's damage get modified after pick up a weapon  
#### Also have a partly implemented feature too  
  - Monsters can auto-attack the player(it means they goes for the player in a certain range)  
#### Some plans about the project  
  - Add multiple levels
  - Add more items
  - Add more monsters
  - Vision range for player
  - Add some friendly NPCs
  - Add more detailed quest(s)  
## Controls  
  - **UP ARROW** up
  - **DOWN ARROW** down
  - **LEFT ARROW** left
  - **RIGHT ARROW** right
  
## Prerequisites  
  - Java 11  
  - Maven 3.6.0  

## Used technologies  
  - Openjfx - Javafx controls - version: 12.0.2  
  - Junit 5 - Junit jupiter api - version: 5.3.2  

## Opening the project

Open the project in IntelliJ IDEA. This is a Maven project, so you will need to open `pom.xml`.

The project is using JavaFX.  Use the `javafx` maven plugin to build and run the program.

Build:

```bash
mvn javafx:compile
```

Run:

```bash
mvn javafx:run
```

## Architecture

The project is meant to teach the concept of **layer separation**. All of the game logic (that is, player movement, game rules, and so on), is in the `logic` package, completely independent of user interface code. In principle, you could implement a completely different interface (terminal, web, Virtual Reality...) for the same logic code.

## Product Backlog

[Codecool Quest Product Backlog](https://docs.google.com/spreadsheets/d/1CvVh2s6obWEh4eQxu8w4f3jBLhz208bG-1FybWGc1sA/edit#gid=0)

## Graphics

The tiles used in the game are from [1-Bit Pack by Kenney](https://kenney.nl/assets/bit-pack), shared on [CC0 1.0 Universal license](https://creativecommons.org/publicdomain/zero/1.0/).

![tiles](src/main/resources/tiles.png)

## Copyright and license
Code and documentation copyright 2020 the Guan Xi Gang (Roland Csősz, Botond Donáth, Máté Rajnai). Code released under the Creative Commons (CC0 1.0 Universal) License.
