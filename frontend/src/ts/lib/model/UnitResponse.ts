import PlayerResponse from "./PlayerResponse.js";
import Position from "./Position.js";

export enum UnitType {
  CASTLE = "CASTLE",
  WARRIOR = "WARRIOR",
}

export default class UnitResponse {
  public id: string;
  public position: Position;
  public hp: number;
  public owner: PlayerResponse;
  public range: number;
  public damage: number;
  public type: UnitType;

  constructor(
    id: string,
    range: number,
    hp: number,
    damage: number,
    position: Position,
    owner: PlayerResponse,
    type: UnitType
  ) {
    this.id = id;
    this.hp = hp;
    this.position = position;
    this.owner = owner;
    this.range = range;
    this.damage = damage;
    this.type = type;
  }
}
