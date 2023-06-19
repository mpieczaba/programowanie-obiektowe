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

  public canvas = document.getElementById("canvas") as HTMLCanvasElement;

  private buffer = document
    .createElement("canvas")
    .getContext("2d") as CanvasRenderingContext2D;

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

        if (i == 4 && (j == 0 || j == 11)) tile.innerText = "X";

        this.map.appendChild(tile);
      }
    }
  }

  public resizeCanvas = (ctx: CanvasRenderingContext2D) => {
    const newWidth = this.canvas.width - (this.canvas.width % 16);
    const newHeight = this.canvas.height - (this.canvas.height % 16);
    this.buffer.canvas.width = newWidth;
    this.buffer.canvas.height = newHeight;
    ctx.canvas.width = newWidth;
    ctx.canvas.height = newHeight;
  };
}
