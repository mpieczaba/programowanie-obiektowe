# Dokumentacja

## Architektura

Projekt został podzielony na dwie części:

- **back-end** – część odpowiadającą za logikę gry, przeprowadzenie symulacji oraz komunikację z klientami gry.
- **front-end** – warstwę interaktywną projektu, która udostępnia graficzny interfejs do manipulacji obiektami oraz przedstawia wizualną reprezentację symulacji.

```mermaid
---
title: Schemat architektury projektu
---

flowchart LR
    subgraph backend[Back-end]
        direction LR

        server(Serwer WebSocket)
        simEngine(Silnik symulacji)
    end

    subgraph frontend[Front-end]
        direction LR

        client(Klient WebSocket)
        gui(Interfejs graficzny)
    end

    server <-- WebSocket --> client
```

### Backend

```mermaid
---
title: Schemat klas back-endu
---

classDiagram
    class App {
        -IO ws
        -Handler handler
        -init()
        +run()
        +main([]String args)$
    }

    %% Obsługa zdarzeń CRUD %%
    class Handler {
        -Socket socket

        +Handler(Socket socket) : Handler
        %% game:create %%
        -createGame()
        %% game:pause %%
        -pauseGame()
        %% game:end %%
        -endGame()
        %% game:join %%
        -joinGame()
        %% entity:update %%
        -updateEntity()
        +handleEvents()
    }

    App <|-- Handler
```

### Silnik symulacji

Klasy w silniku symulacji:

- **Game** – instancje tej klasy reprezentują każdą utworzoną przez użytkowników grę
- **World** – klasa przechowująca obecny stan planszy

```mermaid
---
title: Schemat klas silnika symulacji
---

classDiagram
    class Game {
        +String id
        -World world
        -ArrayList~Player~ players

        +Game() : Game
    }

    class Simulation {
        +update()
    }

    class Turn {

    }

    class World {
        -ArrayList~Castle~ castles
    }

    class Player {
        +String nickname
        -Castle castle
        -ArrayList~Entity~ entities

        +Player(String nickname) : Player
    }

    class GameMaster {
        +startGame()
        +pauseGame()
        +endGame()
    }

    class Entity {
        <<Abstract>>

        +int x
        +int y
    }

    class Warrior {

    }

    class Wizard {

    }

    class Castle {
        +Player owner
        +int x
        +int y
        +int hp
    }

    Game <.. Player
    Game <.. World
    Entity <|-- Warrior
    Entity <|-- Wizard
    Player <|-- GameMaster
```
