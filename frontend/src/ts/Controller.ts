import UI from "./UI.js";
import Client from "./lib/Client.js";
import GameInput from "./lib/model/GameInput.js";
import Position from "./lib/model/Position.js";
import UnitInput from "./lib/model/UnitInput.js";
import { UnitType } from "./lib/model/UnitResponse.js";

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
    if (data.has("nick")) {
      const nick = data.get("nick")?.toString();

      if (!nick) return;

      switch (nick.toLowerCase()) {
        case "jan paweł 2":
        case "jan paweł ii":
          this.ui.updateBalloon(
            "Prosze podać inny nik niż Jan Paweł 2.\nTen nik może (i czyni to) obraża osoby wierzące."
          );
          break;

        default:
          this.client
            .createNewGame(new GameInput(nick))
            .then((res) => {
              const urlSearchParams = new URLSearchParams(
                document.location.search
              );
              urlSearchParams.append("g", res.id);

              window.localStorage.setItem("playerId", res.host.id);

              window.location.search = urlSearchParams.toString();
            })
            .catch((e: Error) => this.ui.updateBalloon(e.message));
      }
    }
  };

  public windowLoad = () => {
    if (!this.ui.gameId) return;

    this.client.connect(this.ui.gameId);

    this.client
      .getGame(this.ui.gameId)
      .then((res) => {
        res.board.units.forEach((unit) => this.ui.drawUnit(unit));
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
          localStorage.getItem("playerId")!,
          type
        )
      )
      .then((res) => {
        res.units.forEach((u) => this.ui.drawUnit(u));

        counter.innerText = `x${count - 1}`;
        if (count - 1 == 0) {
          const selector = document.getElementById(text) as HTMLDivElement;
          selector.classList.add("no-more-units");
        }
      })
      .catch((e) => console.log(e));
  };
}
