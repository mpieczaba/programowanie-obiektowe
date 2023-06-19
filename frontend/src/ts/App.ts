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

    const entities = document.querySelectorAll(
      ".entity"
    ) as NodeListOf<HTMLDivElement>;
    entities.forEach((entity: HTMLDivElement) => {
      entity.addEventListener("dragstart", (e: DragEvent) => {
        e.dataTransfer?.setData("text", entity.id);
      });
      entity.addEventListener("dragend", (e) => console.log(e));
    });

    const tiles = document.querySelectorAll(
      ".tile"
    ) as NodeListOf<HTMLDivElement>;
    tiles.forEach((tile: HTMLDivElement) => {
      tile.addEventListener("drop", (e: DragEvent) => {
        e.preventDefault();

        const data = e.dataTransfer?.getData("text") || "";
        tile.appendChild(document.getElementById(data)!);
      });
      tile.addEventListener("dragover", (e) => e.preventDefault());
    });
  }
}
