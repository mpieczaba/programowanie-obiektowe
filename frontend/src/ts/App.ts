import Controller from "./Controller.js";
import UI from "./UI.js";

export default class App {
  private ui = new UI();
  public controller = new Controller(this.ui);

  constructor() {}

  public handleEvents() {
    this.ui.newGameForm.addEventListener(
      "submit",
      this.controller.newGameFormSubmit
    );
  }
}
