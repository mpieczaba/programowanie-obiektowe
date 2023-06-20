import EntityResponse from "./EntityResponse.js";
import PlayerResponse from "./PlayerResponse.js";
import Position from "./Position.js";

export default class CastleResponse extends EntityResponse {
  constructor(
    id: string,
    position: Position,
    hp: number,
    owner: PlayerResponse
  ) {
    super(id, position, hp, owner);
  }
}
