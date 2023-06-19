import Controller from "./Controller.js";
import UI from "./UI.js";

import Client from "./lib/Client.js";

export default class App {
  private ui = new UI();

  private client = new Client();

  private controller = new Controller(this.ui, this.client);

  public handleEvents() {
    this.ui.newGameForm.addEventListener(
      "submit",
      this.controller.newGameFormSubmit
    );

    window.addEventListener("load", this.controller.windowLoad);
  }
}
