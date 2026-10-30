package terse;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

    @Test
    void testAsNodeForObject() {
        ObjectNode node = obj("name", "Alice", "age", 30).asNode();

        assertNotNull(node);
        assertEquals("Alice", node.get("name").asText());
        assertEquals(30, node.get("age").asInt());
    }

    @Test
    void testAsNodeForArray() {
        ArrayNode node = arr("one", "two", "three").asNode();

        assertNotNull(node);
        assertEquals(3, node.size());
        assertEquals("one", node.get(0).asText());
        assertEquals("two", node.get(1).asText());
        assertEquals("three", node.get(2).asText());
    }

    @Test
    void testAsNodeWithNestedStructures() {
        ObjectNode node = obj(
            "user", obj("name", "Bob", "age", 25),
            "tags", arr("admin", "verified")
        ).asNode();

        assertNotNull(node);
        assertTrue(node.has("user"));
        assertTrue(node.has("tags"));

        ObjectNode userNode = (ObjectNode) node.get("user");
        assertEquals("Bob", userNode.get("name").asText());
        assertEquals(25, userNode.get("age").asInt());

        ArrayNode tagsNode = (ArrayNode) node.get("tags");
        assertEquals(2, tagsNode.size());
        assertEquals("admin", tagsNode.get(0).asText());
    }
}
