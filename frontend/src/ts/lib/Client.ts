import UI from "../UI.js";
import BoardResponse from "./model/BoardResponse.js";
import GameInput from "./model/GameInput.js";
import GameResponse from "./model/GameResponse.js";
import PlayerInput from "./model/PlayerInput.js";
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

      let tick = 0;

      switch (res.event) {
        case "user_joined":
          this.ui.drawUnit(res.data);
          break;

        case "game_started":
          setInterval(() => {
            this.ui.turnCounter.innerText = (16 - (tick % 16)).toString();
            tick++;
          }, 1000);
          break;

        case "unit_placed":
          this.ui.drawUnit(res.data);
          break;

        case "boosting":
          this.ui.deckSelectors.forEach((s) => {
            if (!s.classList.contains("empty")) s.classList.remove("locked");
          });

          this.ui.turn.innerText =
            "Turn: " +
            (parseInt(this.ui.turn.innerText.slice(-1)) + 1).toString();
          break;

        case "simulation":
          this.ui.deckSelectors.forEach((s) => s.classList.add("locked"));
          break;

        case "session_tick":
          (res.data as BoardResponse).units.forEach((u) => this.ui.drawUnit(u));
          break;

        default:
          break;
      }
    };
  };

  public startGame = async (id: string): Promise<boolean> => {
    const res = await fetch(`http://localhost:8080/games/${id}/start`, {
      method: "POST",
      headers: {
        Accept: "application/json",
      },
    });

    if (res.status == 200) {
      return true;
    }

    const data: { title: string } = await res.json();
    throw new Error(data.title);
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

  public joinGame = async (
    id: string,
    player: PlayerInput
  ): Promise<GameResponse> => {
    const res = await fetch(`http://localhost:8080/games/${id}/join`, {
      method: "POST",
      body: JSON.stringify(player),
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

  public getUnit = async (id: string, unitId: string) => {};

  public placeUnitOnTheMap = async (
    id: string,
    unit: UnitInput
  ): Promise<boolean> => {
    const res = await fetch(`http://localhost:8080/games/${id}/units/place`, {
      method: "POST",
      body: JSON.stringify(unit),
      headers: {
        Accept: "application/json",
      },
    });

    if (res.status == 201) {
      return true;
    }

    const data: { title: string } = await res.json();
    throw new Error(data.title);
  };
}
