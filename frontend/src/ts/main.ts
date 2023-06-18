import Display from "./display.js";
import Engine from "./engine.js";
import Game from "./game.js";

const canvas = document.getElementById("canvas") as HTMLCanvasElement;
const ctx = canvas.getContext("2d");

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
