import UI from "./UI.js";
import Client from "./lib/Client.js";
import GameInput from "./lib/model/GameInput.js";
import PlayerInput from "./lib/model/PlayerInput.js";
import Position from "./lib/model/Position.js";
import UnitInput from "./lib/model/UnitInput.js";
import UnitResponse, { UnitType } from "./lib/model/UnitResponse.js";

export default class Controller {
  private client;
  private ui: UI;

  constructor(ui: UI, client: Client) {
    this.ui = ui;
    this.client = client;
  }

  public newGameFormSubmit = (e: SubmitEvent) => {
    e.preventDefault();

    const data = new FormData(this.ui.newGameForm);

    if (data.has("nick") && data.get("nick")!.toString() == "") return;

    const nick = data.get("nick")!.toString();

    switch (nick.toLowerCase()) {
      // Easter egg
      case "jan paweł 2":
      case "jan paweł ii":
        this.ui.updateBalloon(
          "Prosze podać inny nik niż Jan Paweł 2.\nTen nik może (i czyni to) obraża osoby wierzące."
        );
        break;
    }

    const urlSearchParams = new URLSearchParams(document.location.search);

    if (data.has("game-id") && data.get("game-id")!.toString() != "") {
      const gameId = data.get("game-id")!.toString();

      this.client
        .joinGame(gameId, new PlayerInput(nick))
        .then((res) => {
          urlSearchParams.append("g", res.id);
          window.sessionStorage.setItem("playerId", res.opponent!.id);
          window.sessionStorage.setItem("isOpponent", "true");
          window.location.search = urlSearchParams.toString();
          return;
        })
        .catch((e: Error) => {
          this.ui.updateBalloon(e.message);
          return;
        });
    } else {
      this.client
        .createNewGame(new GameInput(nick))
        .then((res) => {
          urlSearchParams.append("g", res.id);
          window.sessionStorage.setItem("playerId", res.host.id);
          window.location.search = urlSearchParams.toString();
        })
        .catch((e: Error) => this.ui.updateBalloon(e.message));
    }
  };

  public handlePlay = () => {
    this.client.startGame(this.ui.gameId!).catch((e) => console.log(e));

    this.ui.playButtonIcon.classList.remove("play");
    this.ui.playButtonIcon.classList.add("pause");
    this.ui.playButtonText.innerText = "Pause";
  };

  public handleClose = () => {
    this.ui.deck.hidden = false;
    this.ui.boosting.hidden = true;
  };

  public windowLoad = () => {
    if (!this.ui.gameId) return;

    this.client.connect(this.ui.gameId);

    this.client
      .getGame(this.ui.gameId)
      .then((res) => {
        res.board.units.forEach((unit) =>
          this.ui.drawUnit(unit, this.client.handleUnitClick)
        );
      })
      .catch((e) => console.log(e));
  };

  public handleSelectorDrag = (e: DragEvent, id: string) =>
    e.dataTransfer?.setData("text", id);

  public handleTileDrop = (e: DragEvent, id: string) => {
    e.preventDefault();

    const text = e.dataTransfer?.getData("text");
    if (!text?.endsWith("-selector")) return;

    const counter = document.getElementById(
      `${text}-counter`
    ) as HTMLDivElement;
    const count = parseInt(counter.innerText[1]);

    if (count < 1) return;

    const type: UnitType =
      UnitType[text.split("-")[0].toUpperCase() as keyof typeof UnitType];

    this.client
      .placeUnitOnTheMap(
        this.ui.gameId!,
        new UnitInput(
          new Position(parseInt(id.split("-")[1]), parseInt(id.split("-")[2])),
          sessionStorage.getItem("playerId")!,
          type
        )
      )
      .then((res) => {
        if (!res) return;

        counter.innerText = `x${count - 1}`;
        if (count - 1 == 0) {
          const selector = document.getElementById(text) as HTMLDivElement;
          selector.classList.add("locked");
          selector.classList.add("empty");
        }
      })
      .catch((e) => console.log(e));
  };

  public boostDamage = () => {
    this.client
      .boostUnitDamage(this.ui.gameId!, this.ui.unitId.value)
      .then((res: UnitResponse) => {
        this.ui.unitDamageCount.innerText = res.damage.toString();
      })
      .catch((e) => console.log(e));
  };

  public boostAttackSpeed = () => {
    this.client
      .boostUnitAttackSpeed(this.ui.gameId!, this.ui.unitId.value)
      .then((res: UnitResponse) => {
        this.ui.unitAttackSpeedCount.innerText = res.attackSpeed.toString();
      })
      .catch((e) => console.log(e));
  };

  public boostMovementSpeed = () => {
    this.client
      .boostUnitMovementSpeed(this.ui.gameId!, this.ui.unitId.value)
      .then((res: UnitResponse) => {
        this.ui.unitMovementSpeedCount.innerText = res.movementSpeed.toString();
      })
      .catch((e) => console.log(e));
  };
}
