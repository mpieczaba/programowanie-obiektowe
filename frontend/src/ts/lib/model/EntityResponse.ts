import PlayerResponse from "./PlayerResponse.js";
import Position from "./Position.js";

export default abstract class EntityResponse {
  public id: string;

  public position: Position;

  public hp: number;

  public owner: PlayerResponse;

  constructor(
    id: string,
    position: Position,
    hp: number,
    owner: PlayerResponse
  ) {
    this.id = id;
    this.position = position;
    this.hp = hp;
    this.owner = owner;
  }
}
