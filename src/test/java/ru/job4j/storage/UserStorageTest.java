package ru.job4j.storage;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class UserStorageTest {

    @Test
    public void whenTwoThreadsCreate4UsersAndThirdThreadTransfersMoneyThenSuccess() throws InterruptedException {
        UserStorage userStorage = new UserStorage();
        Thread first = new Thread(
                () -> {
                    userStorage.add(new User(1, 1000));
                    userStorage.add(new User(2, 2000));
                }
        );
        Thread second = new Thread(
                () -> {
                    userStorage.add(new User(3, 3000));
                    userStorage.add(new User(4, 4000));
                }
        );
        Thread third = new Thread(
                () -> {
                    userStorage.transfer(1, 4, 500);
                    userStorage.transfer(4, 2, 4500);
                }
        );
        first.start();
        second.start();
        first.join();
        second.join();
        third.start();
        third.join();
        assertThat(userStorage.findById(4).getAmount(), is(0));
    }

    @Test
    public void whenTwoThreadsCreate4UsersAndThirdThreadTransfersMoneyThenFail() throws InterruptedException {
        UserStorage userStorage = new UserStorage();
        Thread first = new Thread(
                () -> {
                    userStorage.add(new User(1, 1000));
                    userStorage.add(new User(2, 2000));
                }
        );
        Thread second = new Thread(
                () -> {
                    userStorage.add(new User(3, 3000));
                    userStorage.add(new User(4, 4000));
                }
        );
        Thread third = new Thread(
                () -> {
                    userStorage.transfer(1, 4, 500);
                    userStorage.transfer(4, 2, 5000);
                }
        );
        first.start();
        second.start();
        first.join();
        second.join();
        third.start();
        third.join();
        assertThat(userStorage.findById(4).getAmount(), is(4500));
    }
}