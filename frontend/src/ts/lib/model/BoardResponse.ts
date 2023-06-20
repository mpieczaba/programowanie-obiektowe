import CastleResponse from "./CastleResponse.js";
import UnitResponse from "./UnitResponse.js";

export default class BoardResponse {
  public units: UnitResponse[] = new Array();
  public castles: CastleResponse[] = new Array();
}
