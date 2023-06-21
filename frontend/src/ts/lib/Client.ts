import UI from "../UI.js";
import BoardResponse from "./model/BoardResponse.js";
import GameInput from "./model/GameInput.js";
import GameResponse from "./model/GameResponse.js";
import ResponseError from "./model/ResponseError.js";
import UnitInput from "./model/UnitInput.js";
import UnitResponse from "./model/UnitResponse.js";
import WsResponse from "./model/WsResponse.js";

export default class Client {
  private ui: UI;
  private ws?: WebSocket;

  constructor(ui: UI) {
    this.ui = ui;
  }

  public connect = (gameId: string | null) => {
    this.ws = new WebSocket(`ws://localhost:8080/games/${gameId}`);

    this.ws.onopen = (e) => {
      console.log(e);
    };

    this.ws.onmessage = (e) => {
      const res: WsResponse<any> = JSON.parse(e.data);
      console.log(res.event);

      switch (res.event) {
        case "user_joined":
          const data: UnitResponse = res.data;
          this.ui.drawUnit(data);
          break;

        default:
          break;
      }
    };
  };

  public createNewGame = async (game: GameInput): Promise<GameResponse> => {
    const res = await fetch("http://localhost:8080/games", {
      method: "POST",
      body: JSON.stringify(game),
      headers: {
        Accept: "application/json",
      },
    });

    if (res.status == 201) {
      return res.json();
    }

    const data: ResponseError = await res.json();
    throw new Error(data.REQUEST_BODY[0].message);
  };

  public getGame = async (id: string): Promise<GameResponse> => {
    const res = await fetch(`http://localhost:8080/games/${id}`, {
      headers: {
        Accept: "application/json",
      },
    });

    if (res.status == 200) {
      return res.json();
    }

    const data: ResponseError = await res.json();
    throw new Error(data.REQUEST_BODY[0].message);
  };

  public placeUnitOnTheMap = async (
    id: string,
    unit: UnitInput
  ): Promise<BoardResponse> => {
    const res = await fetch(`http://localhost:8080/games/${id}/units/place`, {
      method: "POST",
      body: JSON.stringify(unit),
      headers: {
        Accept: "application/json",
      },
    });

    if (res.status == 201) {
      return res.json();
    }

    const data: { title: string } = await res.json();
    throw new Error(data.title);
  };
}
