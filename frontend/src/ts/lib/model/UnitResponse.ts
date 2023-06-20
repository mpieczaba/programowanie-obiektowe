import EntityResponse from "./EntityResponse.js";
import PlayerResponse from "./PlayerResponse.js";
import Position from "./Position.js";

export default class UnitResponse extends EntityResponse {
  public range: number;
  public damage: number;

  constructor(
    id: string,
    range: number,
    hp: number,
    damage: number,
    position: Position,
    owner: PlayerResponse
  ) {
    super(id, position, hp, owner);

    this.range = range;
    this.damage = damage;
  }
}
