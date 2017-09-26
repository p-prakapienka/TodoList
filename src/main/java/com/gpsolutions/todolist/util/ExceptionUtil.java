package com.gpsolutions.todolist.util;

public class ExceptionUtil {

    public static <T> void checkNotNull(int id, T t, Class<T> clazz) {
        if (t == null) {
            throw getNotFoundException(id, clazz);
        }
    }

    public static <T> NotFoundException getNotFoundException(int id, Class<T> clazz) {
        return new NotFoundException(String.format("%s %d not found.",
            clazz.getSimpleName(), id));
    }

}
