package ru.job4j.storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UserStorage {

    @GuardedBy("this")
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();

    public synchronized boolean add(User user) {
        users.put(user.getId(), user);
        return true;
    }

    public synchronized boolean update(User user) {
        boolean idIsValid = validateId(user.getId());
        if (idIsValid) {
            users.put(user.getId(), user);
        }
        return idIsValid;
    }

    public synchronized boolean delete(User user) {
        boolean idIsValid = validateId(user.getId());
        if (idIsValid) {
            users.remove(user.getId());
        }
        return idIsValid;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean idIsValid = validateId(fromId) && validateId(toId);
        boolean amountIsValid = validateAmount(fromId, amount);
        if (idIsValid && amountIsValid) {
            User userFrom = users.get(fromId);
            User userTo = users.get(toId);
            userFrom.setAmount(userFrom.getAmount() - amount);
            userTo.setAmount(userTo.getAmount() + amount);
            update(userFrom);
            update(userTo);
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

    public static void main(String[] args) throws InterruptedException {
        UserStorage userStorage = new UserStorage();
        Thread first = new Thread(
                () -> {
                    System.out.println("Added 1st: " + userStorage.add(new User(1, 1000)));
                    System.out.println("Added 2nd: " + userStorage.add(new User(2, 2000)));
                }
        );
        Thread second = new Thread(
                () -> {
                    System.out.println("Added 3rd: " + userStorage.add(new User(3, 3000)));
                    System.out.println("Added 4th: " + userStorage.add(new User(4, 4000)));
                }
        );
        first.start();
        second.start();
        first.join();
        System.out.println("Transferred 1st to 4th: " + userStorage.transfer(1, 4, 500));
        second.join();
        System.out.println("Transferred 4th to 2nd: " + userStorage.transfer(4, 2, 4500));
        System.out.println("Account of 1: " + userStorage.findById(1).getAmount());
        System.out.println("Account of 2: " + userStorage.findById(2).getAmount());
        System.out.println("Account of 3: " + userStorage.findById(3).getAmount());
        System.out.println("Account of 4: " + userStorage.findById(4).getAmount());
    }
}
