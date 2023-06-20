import EntityResponse from "./EntityResponse.js";
import Position from "./Position.js";

export default class UnitResponse extends EntityResponse {
  public range: number;
  public damage: number;

  constructor(
    id: string,
    range: number,
    hp: number,
    damage: number,
    position: Position
  ) {
    super(id, position, hp);

    this.range = range;
    this.damage = damage;
  }
}
