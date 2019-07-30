package ru.io.aimc;

import java.util.*;

public class Storage {
    private volatile Map<String, Set<String>> storage = new HashMap<>();

    public synchronized boolean set(String key, String value) {
        Set<String> set = storage.get(key);

        if (set == null) {
            set = new HashSet<>();
            storage.put(key, set);
        }

        return set.add(value);
    }
}
