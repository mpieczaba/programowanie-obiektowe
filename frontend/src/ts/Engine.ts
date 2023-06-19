export default class Engine {
  private static FPS = 100 / 30;

  private accumulatedTime = 0;
  private animationFrameRequest = 0;
  private time = 0;

  private updated = false;

  private render: (timeStamp: number) => void;
  private update: (timeStamp: number) => void;

  constructor(
    render: (timeStamp: number) => void,
    update: (timeStamp: number) => void
  ) {
    this.render = render;
    this.update = update;
  }

  private run = (timeStamp: number) => {
    this.accumulatedTime += timeStamp - this.time;
    this.time = timeStamp;

    if (this.accumulatedTime >= Engine.FPS) {
      this.accumulatedTime = Engine.FPS;
    }

    while (this.accumulatedTime >= Engine.FPS) {
      this.accumulatedTime -= Engine.FPS;

      this.update(timeStamp);

      if (this.updated) {
        this.updated = false;
        this.render(timeStamp);
      }

      this.updated = true;
    }

    this.animationFrameRequest = window.requestAnimationFrame(this.run);
  };

  start = () => {
    this.accumulatedTime = Engine.FPS;
    this.time = window.performance.now();
    this.animationFrameRequest = window.requestAnimationFrame(this.run);
  };

  stop = () => window.cancelAnimationFrame(this.animationFrameRequest);
}
