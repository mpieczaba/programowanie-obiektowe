import UI from "./UI.js";
import Client from "./lib/Client.js";

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
            .createNewGame(nick)
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
    const gameId = new URLSearchParams(window.location.search).get("g");
    if (!gameId) return;

    this.client.connect(gameId);

    this.client
      .getGame(gameId)
      .then((res) => {
        res.board.units.forEach((unit) => this.ui.drawUnit(unit));
      })
      .catch((e) => console.log(e));
  };

  public handleSelectorDrag = (e: DragEvent, id: string) =>
    e.dataTransfer?.setData("text", id);

  public handleTileDrop = (e: DragEvent, id: string) => {
    e.preventDefault();

    const type = e.dataTransfer?.getData("text") || "";

    console.log(`Add ${type} on ${id}`);
  };
}
