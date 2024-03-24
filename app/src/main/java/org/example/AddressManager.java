package org.example;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class AddressManager {
    private static final Map<String, Set<Long>> addressToReceivers;

    static {
        addressToReceivers = new ConcurrentHashMap<>();
    }

    public static Set<Long> getReceivers(String address) {
        Set<Long> receivers = addressToReceivers.get(address);
        return receivers == null ? Set.of() : receivers;
    }

    public static void addReceiver(String address, long receiver) {
        addressToReceivers.computeIfAbsent(address, k -> new CopyOnWriteArraySet<>()).add(receiver);
    }

    public static void removeReceiver(String address, long receiver) {
        if (addressToReceivers.containsKey(address)) {
            addressToReceivers.get(address).remove(receiver);

            if (addressToReceivers.get(address).isEmpty()) {
                addressToReceivers.remove(address);
            }
        }
    }
}
