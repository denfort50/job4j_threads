package ru.job4j.storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UserStorage {

    @GuardedBy("this")
    private final Map<Integer, User> users = new ConcurrentHashMap<>();

    public synchronized boolean add(User user) {
        users.putIfAbsent(user.getId(), user);
        return users.get(user.getId()) != null;
    }

    public synchronized boolean update(User user) {
        return users.replace(user.getId(), users.getOrDefault(user.getId(), new User(0, 0)), user);
    }

    public synchronized boolean delete(User user) {
        return users.remove(user.getId(), user);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        User userFrom = users.get(fromId);
        User userTo = users.get(toId);
        boolean usersAndAmountAreValid = userFrom != null && userFrom.getAmount() - amount >= 0 && userTo != null;
        if (usersAndAmountAreValid) {
            userFrom.setAmount(userFrom.getAmount() - amount);
            userTo.setAmount(userTo.getAmount() + amount);
        }
        return usersAndAmountAreValid;
    }

    public synchronized User findById(int id) {
        return users.get(id);
    }
}
