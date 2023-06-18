export default class Client {
  private ws?: WebSocket;

  public connect = (gameId: String | null) => {
    this.ws = new WebSocket(`ws://localhost:8080/games/${gameId}`);

    this.ws.onopen = (e) => {
      console.log(e);
    };

    this.ws.onmessage = (e) => {
      console.log(e.data);
    };
  };
}
