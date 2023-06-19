import Position from "./Position.js";

export default class UnitResponse {
  public id: string;
  public hp: number;
  public damage: number;
  public position: Position;

  constructor(id: string, hp: number, damage: number, position: Position) {
    this.id = id;
    this.hp = hp;
    this.damage = damage;
    this.position = position;
  }
}
