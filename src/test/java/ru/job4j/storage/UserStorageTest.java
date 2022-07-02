package ru.job4j.storage;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class UserStorageTest {

    @Test
    public void whenCreate4UsersAndTransferMoneyThenSuccess() throws InterruptedException {
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
    public void whenCreate4UsersAndTransferMoneyThenFail() throws InterruptedException {
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

    @Test
    public void whenCreate4UsersAndUpdateUserThenSuccess() throws InterruptedException {
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
                    userStorage.update(new User(2, 7000));
                }
        );
        first.start();
        second.start();
        first.join();
        second.join();
        third.start();
        third.join();
        assertThat(userStorage.findById(2).getAmount(), is(7000));
    }

    @Test
    public void whenCreate4UsersAndUpdateUserThenFail() throws InterruptedException {
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
                    userStorage.update(new User(5, 5000));
                }
        );
        first.start();
        second.start();
        first.join();
        second.join();
        third.start();
        third.join();
        assertThat(userStorage.findById(5), is(nullValue()));
    }

    @Test
    public void whenCreate4UsersAndDeleteUserThenSuccess() throws InterruptedException {
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
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(userStorage.delete(new User(3, 3000)), is(true));
    }

    @Test
    public void whenCreate4UsersAndDeleteUserThenFail() throws InterruptedException {
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
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(userStorage.delete(new User(3, 5000)), is(false));
    }
}