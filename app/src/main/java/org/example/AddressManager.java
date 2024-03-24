package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class AddressManager {
    private static final Map<String, List<Long>> addressToReceivers;

    static {
        addressToReceivers = new HashMap<>();
    }

    public static List<Long> getReceivers(String address) {
        return addressToReceivers.get(address);
    }

    public static void addReceiver(String address, long receiver) {
        addressToReceivers.computeIfAbsent(address, k -> new CopyOnWriteArrayList<>()).add(receiver);
    }
}
