package com.labs.reflection.injector;

import com.labs.reflection.annotations.AutoInjectable;
import com.labs.reflection.interfaces.SomeInterface;
import com.labs.reflection.interfaces.SomeOtherInterface;
import com.labs.reflection.impl.SomeImpl;
import com.labs.reflection.impl.SODoer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class InjectorTest {

    private Injector injector;

    @BeforeEach
    void setUp() {
        injector = new Injector();
    }

    @Test
    void shouldLoadConfigSuccessfully() throws Exception {
        // Arrange
        String configContent = "com.labs.reflection.interfaces.SomeInterface=com.labs.reflection.impl.SomeImpl\n" +
                "com.labs.reflection.interfaces.SomeOtherInterface=com.labs.reflection.impl.SODoer";
        InputStream inputStream = new ByteArrayInputStream(configContent.getBytes(StandardCharsets.UTF_8));

        // Создаем временный файл с содержимым configContent
        Path tempFile = Files.createTempFile("config", ".properties");
        Files.write(tempFile, configContent.getBytes(StandardCharsets.UTF_8));

        // Передаем путь к временному файлу в метод loadConfig
        injector.loadConfig(tempFile.toString());

        // Assert
        assertEquals("com.labs.reflection.impl.SomeImpl",
                injector.properties.getProperty("com.labs.reflection.interfaces.SomeInterface"));
        assertEquals("com.labs.reflection.impl.SODoer",
                injector.properties.getProperty("com.labs.reflection.interfaces.SomeOtherInterface"));
    }

    @Test
    void shouldInjectDependenciesCorrectly() throws Exception {
        // Arrange
        String configContent = "com.labs.reflection.interfaces.SomeInterface=com.labs.reflection.impl.SomeImpl\n" +
                "com.labs.reflection.interfaces.SomeOtherInterface=com.labs.reflection.impl.SODoer";
        InputStream inputStream = new ByteArrayInputStream(configContent.getBytes(StandardCharsets.UTF_8));

        // Создаем временный файл с содержимым configContent
        Path tempFile = Files.createTempFile("config", ".properties");
        Files.write(tempFile, configContent.getBytes(StandardCharsets.UTF_8));

        // Передаем путь к временному файлу в метод loadConfig
        injector.loadConfig(tempFile.toString());

        TestBean testBean = new TestBean();

        // Act
        TestBean injectedBean = injector.inject(testBean);

        // Assert
        assertNotNull(injectedBean.someInterface);
        assertNotNull(injectedBean.someOtherInterface);
        assertEquals(SomeImpl.class, injectedBean.someInterface.getClass());
        assertEquals(SODoer.class, injectedBean.someOtherInterface.getClass());
    }

    private static class TestBean {
        @AutoInjectable
        private SomeInterface someInterface;
        @AutoInjectable
        private SomeOtherInterface someOtherInterface;
    }
}