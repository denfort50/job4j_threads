package ru.job4j.storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UserStorage {

    @GuardedBy("this")
    private final Map<Integer, User> users = new ConcurrentHashMap<>();

    public synchronized User add(User user) {
        return users.putIfAbsent(user.getId(), user);
    }

    public synchronized User update(User user) {
        return users.replace(user.getId(), user);
    }

    public synchronized boolean delete(User user) {
        return users.remove(user.getId(), user);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean idIsValid = validateId(fromId) && validateId(toId);
        boolean amountIsValid = validateAmount(fromId, amount);
        if (idIsValid && amountIsValid) {
            User userFrom = users.get(fromId);
            User userTo = users.get(toId);
            userFrom.setAmount(userFrom.getAmount() - amount);
            userTo.setAmount(userTo.getAmount() + amount);
        }
        return idIsValid && amountIsValid;
    }

    /**
     * Метод проверяет, существует ли в коллекции пользователь с указанным id
     * @param id id пользователя
     * @return true, если пользователь с таким id существует, иначе false
     */
    public synchronized boolean validateId(int id) {
        return users.get(id) != null;
    }

    /**
     * Метод проверяет, что у пользователя-отправителя есть сумма, которую нужно перевести
     * @param id id пользователя
     * @param amount сумма денег
     * @return true, если пользователь располагает указанной суммой
     */
    public synchronized boolean validateAmount(int id, int amount) {
        return users.get(id).getAmount() >= amount;
    }

    public synchronized User findById(int id) {
        return users.get(id);
    }
}
