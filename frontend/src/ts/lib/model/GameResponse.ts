import BoardResponse from "./BoardResponse.js";

export default class GameResponse {
  public id: string;

  public board: BoardResponse;

  constructor(id: string) {
    this.id = id;
    this.board = new BoardResponse();
  }
}
