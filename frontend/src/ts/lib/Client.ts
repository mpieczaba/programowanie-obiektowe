import BoardResponse from "./model/BoardResponse.js";
import GameInput from "./model/GameInput.js";
import GameResponse from "./model/GameResponse.js";
import ResponseError from "./model/ResponseError.js";
import UnitInput from "./model/UnitInput.js";

export default class Client {
  private ws?: WebSocket;

  public connect = (gameId: string | null) => {
    this.ws = new WebSocket(`ws://localhost:8080/games/${gameId}`);

    this.ws.onopen = (e) => {
      console.log(e);
    };

    this.ws.onmessage = (e) => {
      console.log(e.data);
    };
  };

  public createNewGame = async (game: GameInput): Promise<GameResponse> => {
    const res = await fetch("http://localhost:8080/games", {
      method: "POST",
      body: JSON.stringify(game),
    });

    if (res.status == 201) {
      return res.json();
    }

    const data: ResponseError = await res.json();
    throw new Error(data.REQUEST_BODY[0].message);
  };

  public getGame = async (id: string): Promise<GameResponse> => {
    const res = await fetch(`http://localhost:8080/games/${id}`);

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
    });

    if (res.status == 201) {
      return res.json();
    }

    const data: ResponseError = await res.json();
    throw new Error(data.REQUEST_BODY[0].message);
  };
}
