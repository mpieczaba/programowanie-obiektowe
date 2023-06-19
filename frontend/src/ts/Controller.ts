import UI from "./UI.js";
import Client from "./lib/Client.js";

export default class Controller {
  private client = new Client();
  private ui: UI;

  constructor(ui: UI) {
    this.ui = ui;
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

              window.location.search = urlSearchParams.toString();
            })
            .catch((e: Error) => this.ui.updateBalloon(e.message));
      }
    }
  };
}
