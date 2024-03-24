package org.example;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Manages the associations between addresses and their respective receivers.
 * Utilizes thread-safe collections to ensure consistency in a concurrent environment.
 */
public class AddressManager {
    // Mapping of address to a set of receiver IDs. Thread-safe to support concurrent access.
    private static final Map<String, Set<Long>> addressToReceivers;

    static {
        addressToReceivers = new ConcurrentHashMap<>();
    }

    /**
     * Retrieves a set of receiver IDs associated with a given address.
     * If no receivers are associated, returns an empty set.
     *
     * @param address The address for which receivers are queried.
     * @return A set of receiver IDs or an empty set if none are associated.
     */
    public static Set<Long> getReceivers(String address) {
        Set<Long> receivers = addressToReceivers.get(address);
        return receivers == null ? Set.of() : receivers;
    }

    /**
     * Associates a new receiver ID with a given address.
     * If the address does not already have associated receivers, initializes a new set.
     *
     * @param address  The address to associate with the receiver.
     * @param receiver The receiver ID to be associated with the address.
     */
    public static void addReceiver(String address, long receiver) {
        addressToReceivers.computeIfAbsent(address, k -> new CopyOnWriteArraySet<>()).add(receiver);
    }

    /**
     * Removes a receiver ID association from a given address.
     * If removing the receiver results in an empty set of receivers for the address, the address entry is removed.
     *
     * @param address  The address from which the receiver ID is to be disassociated.
     * @param receiver The receiver ID to be disassociated from the address.
     */
    public static void removeReceiver(String address, long receiver) {
        if (addressToReceivers.containsKey(address)) {
            addressToReceivers.get(address).remove(receiver);

            // If no receivers are left for this address, remove the address entry
            if (addressToReceivers.get(address).isEmpty()) {
                addressToReceivers.remove(address);
            }
        }
    }
}
