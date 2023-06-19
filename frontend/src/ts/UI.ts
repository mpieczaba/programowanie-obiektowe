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

  public drawMap() {
    for (let i = 0; i < 9; i++) {
      for (let j = 0; j < 12; j++) {
        const tile = document.createElement("div");
        tile.classList.add("tile");

        if (i == 4 && (j == 0 || j == 11)) {
          const progress = document.createElement("progress");
          progress.max = 100;
          progress.classList.add("hp");
          progress.classList.add("nes-progress");
          progress.classList.add("is-error");

          const castle = document.createElement("div");
          castle.id = `castle-${i}-${j}`;
          castle.draggable = true;
          castle.classList.add("entity");
          castle.appendChild(progress);
          castle.innerHTML += `<img src="img/druidV1.png" />`;

          castle.classList.add("castle");
          tile.appendChild(castle);
        }

        this.map.appendChild(tile);
      }
    }
  }
}
