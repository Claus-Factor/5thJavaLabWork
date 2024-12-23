package com.labs.reflection.injector;

import com.labs.reflection.annotations.AutoInjectable;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

public class Injector {

    private final Properties properties = new Properties();

    // Метод для чтения файла свойств
    public void loadConfig(String filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
        }
    }

    // Параметризированный метод inject
    public <T> T inject(T object) throws Exception {
        Class<?> clazz = object.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(AutoInjectable.class)) {
                String interfaceName = field.getType().getCanonicalName(); // Получаем имя интерфейса
                String implementationName = properties.getProperty(interfaceName); // Находим реализацию

                if (implementationName != null && !implementationName.isEmpty()) {
                    Class<?> implClass = Class.forName(implementationName); // Загружаем класс реализации

                    Object instance = implClass.newInstance(); // Создаем экземпляр реализации
                    field.setAccessible(true); // Делаем поле доступным для записи
                    field.set(object, instance); // Заполняем поле объектом реализации
                }
            }
        }

        return object;
    }
}