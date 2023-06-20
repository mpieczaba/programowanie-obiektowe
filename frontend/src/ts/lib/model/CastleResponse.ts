import EntityResponse from "./EntityResponse.js";
import Position from "./Position.js";

export default class CastleResponse extends EntityResponse {
  constructor(id: string, position: Position, hp: number) {
    super(id, position, hp);
  }
}
