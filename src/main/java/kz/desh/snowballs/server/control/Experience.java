package kz.desh.snowballs.server.control;

import java.util.HashMap;
import java.util.Map;

public final class Experience {
    public static final Map<Integer, Integer> EXPERIENCE_FOR_NEX_LEVEL = new HashMap<>();

    static {
        EXPERIENCE_FOR_NEX_LEVEL.put(1, 20);
        EXPERIENCE_FOR_NEX_LEVEL.put(2, 40);
    }

    private Experience() {
    }
}