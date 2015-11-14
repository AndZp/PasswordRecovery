package ua.com.ukrelektro.passwordrec.model;

import java.util.HashMap;
import java.util.Map;

public enum State {
    NOT_CHECK(0),
    FAIL(1),
    PASS(2);

    private int status;

    private static Map<Integer, State> map = new HashMap<>();

    static {
        for (State status : State.values()) {
            map.put(status.status, status);
        }
    }

    State(final int leg) {
        status = leg;
    }

    public static State valueOf(int status) {
        return map.get(status);
    }


}
