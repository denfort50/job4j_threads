package ru.job4j.cache;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class CacheTest {

    @Test
    public void whenAddThenSuccess() {
        Cache cache = new Cache();
        Base model = new Base(1, 1);
        model.setName("model");
        cache.add(model);
        assertThat(cache.getById(1).getName(), is("model"));
    }

    @Test
    public void whenUpdateThenSuccess() {
        Cache cache = new Cache();
        Base model = new Base(1, 1);
        model.setName("model");
        cache.add(model);
        Base newModel = new Base(1, 1);
        newModel.setName("newModel");
        cache.update(newModel);
        assertThat(cache.getById(1).getName(), is("newModel"));
        assertThat(cache.getById(1).getVersion(), is(2));
    }

    @Test
    public void whenDeleteThenSuccess() {
        Cache cache = new Cache();
        Base model = new Base(1, 1);
        model.setName("model");
        cache.add(model);
        cache.delete(model);
        assertThat(cache.getById(1), is(nullValue()));
    }

    @Test(expected = OptimisticException.class)
    public void whenUpdateThenException() {
        Cache cache = new Cache();
        Base model = new Base(1, 1);
        model.setName("model");
        cache.add(model);
        Base newModel = new Base(1, 2);
        newModel.setName("newModel");
        cache.update(newModel);
    }
}