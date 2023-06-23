package backend.model.pair;

import org.javatuples.Pair;

public class PairResponse {
    public int x;
    public int y;

    public PairResponse(Pair<Integer, Integer> pair) {
        this.x = pair.getValue0();
        this.y = pair.getValue1();
    }
}
