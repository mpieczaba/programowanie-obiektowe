import UnitResponse from "./lib/model/UnitResponse.js";

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

  public drawUnit(unit: UnitResponse) {
    const tile = document.getElementById(
      `tile-${unit.position.x}-${unit.position.y}`
    ) as HTMLDivElement;

    const progress = document.createElement("progress");
    progress.max = 100;
    progress.classList.add("hp");
    progress.classList.add("nes-progress");
    progress.classList.add("is-error");

    const u = document.createElement("div");
    u.id = unit.id;
    u.draggable = true;
    u.classList.add("unit");
    u.appendChild(progress);
    u.innerHTML += `<img src="img/barbarianV1.png" />`;

    tile.appendChild(u);
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
