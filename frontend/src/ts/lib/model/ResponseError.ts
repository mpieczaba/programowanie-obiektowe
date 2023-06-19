export default class ResponseError {
  public REQUEST_BODY: RequestBody[] = new Array();
}

class RequestBody {
  public message: string;

  constructor(message: string) {
    this.message = message;
  }
}
