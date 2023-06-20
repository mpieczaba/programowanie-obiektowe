import BoardResponse from "./BoardResponse.js";
import PlayerResponse from "./PlayerResponse.js";

export default class GameResponse {
  public id: string;

  public board: BoardResponse;

  public host: PlayerResponse;

  public opponent?: PlayerResponse;

  constructor(id: string, host: PlayerResponse, opponent?: PlayerResponse) {
    this.id = id;
    this.board = new BoardResponse();
    this.host = host;
    this.opponent = opponent;
  }
}
