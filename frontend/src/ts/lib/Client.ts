import UI from "../UI.js";
import BoardResponse from "./model/BoardResponse.js";
import GameInput from "./model/GameInput.js";
import GameResponse from "./model/GameResponse.js";
import PlayerInput from "./model/PlayerInput.js";
import ResponseError from "./model/ResponseError.js";
import UnitInput from "./model/UnitInput.js";
import UnitResponse, { UnitType } from "./model/UnitResponse.js";
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

      let tick = 0;

      switch (res.event) {
        case "user_joined":
          this.ui.drawUnit(res.data, this.handleUnitClick);
          break;

        case "game_started":
          setInterval(() => {
            this.ui.turnCounter.innerText = (16 - (tick % 16)).toString();
            tick++;
          }, 1000);
          break;

        case "unit_placed":
          this.ui.drawUnit(res.data, this.handleUnitClick);
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

          this.ui.boosting.hidden = true;
          this.ui.deck.hidden = false;
          break;

        case "session_tick":
          (res.data as BoardResponse).units.forEach((u) =>
            this.ui.drawUnit(u, this.handleUnitClick)
          );
          break;

        case "unit_removed":
          const u = res.data as UnitResponse;
          const prev = document.getElementById(u.id) as HTMLDivElement;

          if (u.type == UnitType.CASTLE) {
            this.ui.endText.innerText = `Player ${
              u.owner.nickname
            } lost in ${this.ui.turn.innerText.slice(-1)} ${
              this.ui.turn.innerText.slice(-1) == "1" ? "turn" : "turns"
            }.`;
            this.ui.game.hidden = true;
            this.ui.end.hidden = false;
          }

          if (prev) prev.parentNode?.removeChild(prev);
          break;

        default:
          break;
      }
    };
  };

  public handleUnitClick = async (id: string) => {
    const res = await fetch(
      `http://localhost:8080/games/${this.ui.gameId}/units/${id}`,
      {
        headers: {
          Accept: "application/json",
        },
      }
    );

    if (res.status == 200) {
      res.json().then((data: UnitResponse) => {
        this.ui.unitId.value = data.id;
        this.ui.unitHpNumber.innerText = data.hp.toString();
        this.ui.unitHpProgress.value = data.hp;
        this.ui.unitDamageCount.innerText = data.damage.toString();
        this.ui.unitAttackSpeedCount.innerText = data.attackSpeed.toString();
        this.ui.unitMovementSpeedCount.innerText =
          data.movementSpeed.toString();

        switch (data.type) {
          case UnitType.WARRIOR:
            this.ui.unitImage.src = "img/WarriorV1.png";
            break;

          case UnitType.ARCHER:
            this.ui.unitImage.src = "img/archer.png";
            break;

          case UnitType.BARBARIAN:
            u.innerHTML += `<img src="img/barbarianV1.png" />`;
            break;

          default:
            break;
        }
      });
    }

    this.ui.boosting.hidden = false;
    this.ui.deck.hidden = true;
  };

  public startGame = async (id: string): Promise<boolean> => {
    const res = await fetch(`http://localhost:8080/games/${id}/start`, {
      method: "POST",
      headers: {
        Accept: "application/json",
      },
    });

    if (res.status == 200) return true;

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

    if (res.status == 201) return res.json();

    const data: ResponseError = await res.json();
    throw new Error(data.REQUEST_BODY[0].message);
  };

  public getGame = async (id: string): Promise<GameResponse> => {
    const res = await fetch(`http://localhost:8080/games/${id}`, {
      headers: {
        Accept: "application/json",
      },
    });

    if (res.status == 200) return res.json();

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

    if (res.status == 201) return res.json();

    const data: { title: string } = await res.json();
    throw new Error(data.title);
  };

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

    if (res.status == 201) return true;

    const data: { title: string } = await res.json();
    throw new Error(data.title);
  };

  public boostUnitDamage = async (
    id: string,
    unitId: string
  ): Promise<UnitResponse> => {
    const res = await fetch(
      `http://localhost:8080/games/${id}/units/${unitId}/boost/damage`,
      {
        method: "POST",
        headers: {
          Accept: "application/json",
        },
      }
    );

    if (res.status == 200) return res.json();

    const data: { title: string } = await res.json();
    throw new Error(data.title);
  };

  public boostUnitAttackSpeed = async (
    id: string,
    unitId: string
  ): Promise<UnitResponse> => {
    const res = await fetch(
      `http://localhost:8080/games/${id}/units/${unitId}/boost/attack_speed`,
      {
        method: "POST",
        headers: {
          Accept: "application/json",
        },
      }
    );

    if (res.status == 200) return res.json();

    const data: { title: string } = await res.json();
    throw new Error(data.title);
  };

  public boostUnitMovementSpeed = async (
    id: string,
    unitId: string
  ): Promise<UnitResponse> => {
    const res = await fetch(
      `http://localhost:8080/games/${id}/units/${unitId}/boost/movement_speed`,
      {
        method: "POST",
        headers: {
          Accept: "application/json",
        },
      }
    );

    if (res.status == 200) return res.json();

    const data: { title: string } = await res.json();
    throw new Error(data.title);
  };
}
