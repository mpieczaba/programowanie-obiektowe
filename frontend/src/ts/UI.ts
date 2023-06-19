export default class UI {
  public balloon = document.getElementById("balloon") as HTMLDivElement;
  private balloonText = document.getElementById(
    "balloon-text"
  ) as HTMLParagraphElement;

  public newGameForm = document.getElementById(
    "new-game-form"
  ) as HTMLFormElement;

  public hideBalloon() {
    this.balloon.hidden = true;
  }

  public updateBalloon(message: string) {
    this.balloon.hidden = false;
    this.balloonText.innerText = message;
  }
}
