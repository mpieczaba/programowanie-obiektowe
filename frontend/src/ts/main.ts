import Client from "./lib/Client.js";
import Display from "./Display.js";
import Engine from "./Engine.js";
import Game from "./Game.js";
import App from "./App.js";

const app = new App();
app.handleEvents();

const client = new Client();

const gameId = new URLSearchParams(window.location.search).get("g");

const newGame = document.getElementById("new-game") as HTMLDivElement;

const canvas = document.getElementById("canvas") as HTMLCanvasElement;
const ctx = canvas.getContext("2d");

client.connect(gameId);

if (gameId != null) {
  canvas.hidden = false;
  newGame.hidden = true;
}

if (ctx) {
  const display = new Display(ctx);

  const game = new Game();

  const engine = new Engine(
    () => {
      display.renderColor(game.color);
      display.render();
    },
    () => game.update()
  );

  window.addEventListener("resize", display.resize);
  // window.addEventListener("keydown", controller.keyDownUp);
  // window.addEventListener("keyup", controller.keyDownUp);
  display.resize();
  engine.start();
}
