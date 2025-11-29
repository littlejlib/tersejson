package terse;

import org.junit.jupiter.api.Test;
import static terse.Json.*;
import static org.junit.jupiter.api.Assertions.*;

class JsonTest {

    @Test
    void testSimpleObject() {
        String json = obj("name", "Alice", "age", 30).toString();
        assertTrue(json.contains("\"name\":\"Alice\""));
        assertTrue(json.contains("\"age\":30"));
    }

    @Test
    void testNestedObject() {
        String json = obj(
            "user", obj("name", "Bob"),
            "tags", arr("admin", "verified")
        ).toString();
        assertTrue(json.contains("\"user\""));
        assertTrue(json.contains("\"tags\""));
    }

    @Test
    void testMapper() {
        assertNotNull(Json.mapper());
    }

    @Test
    void testArray() {
        String json = arr("one", "two", "three").toString();
        assertTrue(json.contains("\"one\""));
        assertTrue(json.contains("\"two\""));
        assertTrue(json.contains("\"three\""));
    }

    @Test
    void testComplexNesting() {
        String json = obj(
            "name", "Alice",
            "age", 30,
            "address", obj(
                "city", "NYC",
                "zip", "10001"
            ),
            "tags", arr("admin", "verified")
        ).toString();

        assertTrue(json.contains("\"name\":\"Alice\""));
        assertTrue(json.contains("\"age\":30"));
        assertTrue(json.contains("\"city\":\"NYC\""));
        assertTrue(json.contains("\"zip\":\"10001\""));
    }

    @Test
    void testOddLengthArgsShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> {
            obj("key1", "value1", "key2");
        });
    }
}
