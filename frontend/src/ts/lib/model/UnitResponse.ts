import Position from "./Position.js";

export default class UnitResponse {
  public id: string;
  public range: number;
  public hp: number;
  public damage: number;
  public position: Position;

  constructor(
    id: string,
    range: number,
    hp: number,
    damage: number,
    position: Position
  ) {
    this.id = id;
    this.range = range;
    this.hp = hp;
    this.damage = damage;
    this.position = position;
  }
}
