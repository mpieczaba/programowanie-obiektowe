export default class WsResponse<T> {
  public event: string;
  public data: T;

  constructor(event: string, data: T) {
    this.event = event;
    this.data = data;
  }
}
