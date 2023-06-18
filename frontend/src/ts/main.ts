import Client from "./lib/client.js";
import Display from "./display.js";
import Engine from "./engine.js";
import Game from "./game.js";

const gameId = new URLSearchParams(window.location.search).get("g");

document.getElementById("nick")?.addEventListener("submit", (e) => {
  const nick = document.getElementById("nick-input") as HTMLInputElement;

  // Easter egg
  const easterEgg = document.getElementById("easter-egg") as HTMLDivElement;
  easterEgg.hidden = true;

  if (
    nick.value.toLocaleLowerCase() == "jan paweł ii" ||
    nick.value.toLocaleLowerCase() == "jan paweł 2"
  ) {
    return (easterEgg.hidden = false);
  }

  alert(nick.value);
});

const newGame = document.getElementById("new-game") as HTMLDivElement;

const canvas = document.getElementById("canvas") as HTMLCanvasElement;
const ctx = canvas.getContext("2d");

const client = new Client();
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
