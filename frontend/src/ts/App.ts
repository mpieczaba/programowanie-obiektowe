import Controller from "./Controller.js";
import UI from "./UI.js";

import Client from "./lib/Client.js";

export default class App {
  private ui = new UI();

  private client = new Client(this.ui);

  private controller = new Controller(this.ui, this.client);

  public handleEvents() {
    window.addEventListener("load", this.controller.windowLoad);

    this.ui.newGameForm.addEventListener(
      "submit",
      this.controller.newGameFormSubmit
    );

    this.ui.getTiles().forEach((t) => {
      {
        t.addEventListener("drop", (e) =>
          this.controller.handleTileDrop(e, t.id)
        );
        t.addEventListener("dragover", (e) => e.preventDefault());
      }
    });

    this.ui.getSelectors().forEach((s) => {
      s.addEventListener("dragstart", (e) =>
        this.controller.handleSelectorDrag(e, s.id)
      );
    });
  }
}
