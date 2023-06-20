export default class PlayerResponse {
  public id: string;
  public nickname: string;

  constructor(id: string, nickname: string) {
    this.id = id;
    this.nickname = nickname;
  }
}
