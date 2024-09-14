package br.com.schumaker.octopus.framework.model;

import br.com.schumaker.octopus.framework.exception.OctopusException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MapperTest {

    @Test
    public void testMap() {
        // Arrange
        Source source = new Source();
        source.setId(100L);
        source.setName("John Doe");
        source.setAge(30);

        // Act
        Mapper<Source, Target> mapper = new Mapper<>();
        Target target = mapper.map(source, Target.class);

        // Assert
        assertEquals(source.getName(), target.getName());
        assertEquals(source.getAge(), target.getAge());
    }

    @Test
    public void testMapWithException() {
        // Arrange
        Source source = new Source();
        Mapper<Source, Target> mapper = new Mapper<>();

        // Act & Assert
        assertThrows(OctopusException.class, () -> {
            mapper.map(source, null);
        });
    }

    public static class Source {
        private Long id;
        private String name;
        private int age;

        // Getters and setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    public static class Target {
        private String name;
        private int age;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}