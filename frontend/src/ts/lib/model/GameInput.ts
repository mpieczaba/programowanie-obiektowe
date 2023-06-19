import BoardResponse from "./BoardResponse.js";
import PlayerInput from "./PlayerInput.js";

export default class GameInput {
  public host: PlayerInput;

  constructor(hostNickname: string) {
    this.host = new PlayerInput(hostNickname);
  }
}
