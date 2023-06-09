import Position from "./lib/model/Position.js";
import UnitResponse, { UnitType } from "./lib/model/UnitResponse.js";

export default class UI {
  public gameId = new URLSearchParams(window.location.search).get("g");

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

  public deck = document.getElementById("deck") as HTMLDivElement;

  public deckSelectors = document.querySelectorAll(
    ".deck-selector"
  ) as NodeListOf<HTMLDivElement>;

  public boosting = document.getElementById("boosting") as HTMLDivElement;

  public unitId = document.getElementById("unit-id") as HTMLInputElement;

  public unitDamageCount = document.getElementById(
    "unit-damage-count"
  ) as HTMLDivElement;

  public unitAttackSpeedCount = document.getElementById(
    "unit-attack-speed-count"
  ) as HTMLDivElement;

  public unitMovementSpeedCount = document.getElementById(
    "unit-movement-speed-count"
  ) as HTMLDivElement;

  public unitDamageButton = document.getElementById(
    "unit-damage-button"
  ) as HTMLButtonElement;

  public unitAttackSpeedButton = document.getElementById(
    "unit-attack-speed-button"
  ) as HTMLButtonElement;

  public unitMovementSpeedButton = document.getElementById(
    "unit-movement-speed-button"
  ) as HTMLButtonElement;

  public playButton = document.getElementById("play-button") as HTMLDivElement;

  public playButtonText = document.getElementById(
    "play-button-text"
  ) as HTMLDivElement;

  public playButtonIcon = document.getElementById(
    "play-button-icon"
  ) as HTMLDivElement;

  public turn = document.getElementById("turn") as HTMLDivElement;

  public turnCounter = document.getElementById(
    "turn-counter"
  ) as HTMLDivElement;

  public unitImage = document.getElementById("unit-image") as HTMLImageElement;

  public closeButton = document.getElementById(
    "close-button"
  ) as HTMLDivElement;

  public getTiles = (): NodeListOf<HTMLDivElement> => {
    return document.querySelectorAll(".tile") as NodeListOf<HTMLDivElement>;
  };

  public getSelectors = (): NodeListOf<HTMLDivElement> => {
    return document.querySelectorAll(
      ".deck-selector"
    ) as NodeListOf<HTMLDivElement>;
  };

  public castleHpNumber = document.getElementById(
    "castle-hp-number"
  ) as HTMLDivElement;

  public castleHpProgress = document.getElementById(
    "castle-hp-progress"
  ) as HTMLProgressElement;

  public unitHpNumber = document.getElementById(
    "unit-hp-number"
  ) as HTMLDivElement;

  public unitHpProgress = document.getElementById(
    "unit-hp-progress"
  ) as HTMLProgressElement;

  public end = document.getElementById("end") as HTMLDivElement;

  public endText = document.getElementById("end-text") as HTMLHeadingElement;

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

  public drawUnit(unit: UnitResponse, click: (id: string) => void) {
    const tile = this.getTileByPosition(unit.position);

    const prev = document.getElementById(unit.id) as HTMLDivElement;
    if (prev) prev.parentNode?.removeChild(prev);

    switch (unit.type) {
      case UnitType.CASTLE:
        const c = document.createElement("div");
        c.id = unit.id;
        c.classList.add("unit");
        c.classList.add("castle");

        if (unit.owner.id == sessionStorage.getItem("playerId")) {
          c.innerHTML = `<img src="img/CastleRed.png" />`;
          this.castleHpNumber.innerText = unit.hp.toString();
          this.castleHpProgress.value = unit.hp;
        } else {
          c.innerHTML = `<img src="img/Castle.png" />`;
        }

        tile.appendChild(c);

        break;

      case UnitType.WARRIOR:
      default:
        const progress = document.createElement("progress");
        progress.max = 100;
        progress.value = unit.hp;
        progress.classList.add("hp");
        progress.classList.add("nes-progress");
        progress.classList.add(
          unit.owner.id == sessionStorage.getItem("playerId")
            ? "is-error"
            : "is-primary"
        );

        const u = document.createElement("div");
        u.id = unit.id;
        u.draggable = false;
        u.classList.add("unit");
        u.classList.add("nes-pointer");

        if (unit.owner.id != sessionStorage.getItem("playerId"))
          u.classList.add("opponent");
        else u.addEventListener("click", () => click(u.id));

        if (sessionStorage.getItem("isOpponent") == "true")
          u.classList.add("flip");

        u.appendChild(progress);

        switch (unit.type) {
          case UnitType.WARRIOR:
            u.innerHTML += `<img src="img/WarriorV1.png" />`;
            break;

          case UnitType.ARCHER:
            u.innerHTML += `<img src="img/archer.png" />`;
            break;

          case UnitType.BARBARIAN:
            u.innerHTML += `<img src="img/barbarianV1.png" />`;
            break;

          default:
            break;
        }

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
        tile.draggable = false;
        this.map.appendChild(tile);
      }
    }
  }
}
