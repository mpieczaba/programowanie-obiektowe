import Position from "./Position.js";
import { UnitType } from "./UnitResponse";

export default class UnitInput {
  public position: Position;
  public ownerId: string;
  public type: UnitType;

  constructor(position: Position, ownerId: string, type: UnitType) {
    this.position = position;
    this.ownerId = ownerId;
    this.type = type;
  }
}
