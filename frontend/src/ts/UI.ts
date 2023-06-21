import Position from "./lib/model/Position.js";
import UnitResponse, { UnitType } from "./lib/model/UnitResponse.js";

export default class UI {
  public balloon = document.getElementById("balloon") as HTMLDivElement;
  private balloonText = document.getElementById(
    "balloon-text"
  ) as HTMLParagraphElement;

  private newGame = document.getElementById("new-game") as HTMLDivElement;

  public newGameForm = document.getElementById(
    "new-game-form"
  ) as HTMLFormElement;

  public game = document.getElementById("game") as HTMLDivElement;

  public board = document.getElementById("board") as HTMLDivElement;

  public map = document.getElementById("map") as HTMLDivElement;

  public getTiles = (): NodeListOf<HTMLDivElement> => {
    return document.querySelectorAll(".tile") as NodeListOf<HTMLDivElement>;
  };

  public getSelectors = (): NodeListOf<HTMLDivElement> => {
    return document.querySelectorAll(
      ".deck-selector"
    ) as NodeListOf<HTMLDivElement>;
  };

  constructor() {
    const gameId = new URLSearchParams(window.location.search).get("g");

    this.newGame.hidden = gameId != null;
    this.game.hidden = gameId == null;

    this.drawMap();
  }

  public hideBalloon() {
    this.balloon.hidden = true;
  }

  public updateBalloon(message: string) {
    this.balloon.hidden = false;
    this.balloonText.innerText = message;
  }

  private getTileByPosition(position: Position): HTMLDivElement {
    return document.getElementById(
      `tile-${position.x}-${position.y}`
    ) as HTMLDivElement;
  }

  public drawUnit(unit: UnitResponse) {
    const tile = this.getTileByPosition(unit.position);

    console.log(UnitType.CASTLE);

    switch (unit.type) {
      case UnitType.CASTLE:
        const c = document.createElement("div");
        c.id = unit.id;
        c.classList.add("entity");
        c.classList.add("castle");
        c.innerHTML +=
          unit.owner.id == localStorage.getItem("playerId")
            ? `<img src="img/CastleRed.png" />`
            : `<img src="img/Castle.png" />`;
        tile.appendChild(c);

        break;

      case UnitType.WARRIOR:
      default:
        const progress = document.createElement("progress");
        progress.max = 100;
        progress.classList.add("hp");
        progress.classList.add("nes-progress");
        progress.classList.add("is-error");

        const u = document.createElement("div");
        u.id = unit.id;
        u.draggable = true;
        u.classList.add("entity");
        u.classList.add("unit");
        u.classList.add("nes-pointer");
        u.appendChild(progress);
        u.innerHTML += `<img src="img/WarriorV1.png" />`;

        tile.appendChild(u);

        break;
    }
  }

  public drawMap() {
    for (let x = 0; x < 9; x++) {
      for (let y = 0; y < 12; y++) {
        const tile = document.createElement("div");
        tile.id = `tile-${x}-${y}`;
        tile.classList.add("tile");
        this.map.appendChild(tile);
      }
    }
  }
}
