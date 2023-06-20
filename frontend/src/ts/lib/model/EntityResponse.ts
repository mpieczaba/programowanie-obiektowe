import Position from "./Position.js";

export default abstract class EntityResponse {
  public id: string;

  public position: Position;

  public hp: number;

  constructor(id: string, position: Position, hp: number) {
    this.id = id;
    this.position = position;
    this.hp = hp;
  }
}
