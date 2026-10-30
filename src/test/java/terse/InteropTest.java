package terse;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.junit.jupiter.api.Test;
import static terse.Json.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests interoperability between JsonObj/JsonArr and ObjectNode/ArrayNode
 */
class InteropTest {

    @Test
    void testNestObjectNodeInsideJsonObj() {
        // Can we nest an ObjectNode inside a JsonObj?
        ObjectNode innerNode = mapper().createObjectNode()
            .put("city", "NYC")
            .put("zip", "10001");

        JsonObj outer = obj(
            "name", "Alice",
            "address", innerNode  // Nesting ObjectNode inside JsonObj
        );

        String json = outer.toString();
        System.out.println("JsonObj with ObjectNode nested: " + json);

        assertTrue(json.contains("\"name\":\"Alice\""));
        assertTrue(json.contains("\"city\":\"NYC\""));
        assertTrue(json.contains("\"zip\":\"10001\""));
    }

    @Test
    void testNestJsonObjInsideObjectNode() {
        // Can we nest a JsonObj inside an ObjectNode?
        JsonObj innerObj = obj("city", "NYC", "zip", "10001");

        ObjectNode outer = mapper().createObjectNode()
            .put("name", "Alice");
        outer.set("address", mapper().valueToTree(innerObj));  // Need to convert

        String json = outer.toString();
        System.out.println("ObjectNode with JsonObj nested: " + json);

        assertTrue(json.contains("\"name\":\"Alice\""));
        assertTrue(json.contains("\"city\":\"NYC\""));
    }

    @Test
    void testMixedNesting() {
        // Mix JsonObj and ObjectNode freely
        ObjectNode jacksonNode = mapper().createObjectNode().put("type", "jackson");

        JsonObj result = obj(
            "user", obj("name", "Bob"),           // JsonObj inside JsonObj
            "metadata", jacksonNode,               // ObjectNode inside JsonObj
            "tags", arr("admin", "verified")       // JsonArr inside JsonObj
        );

        String json = result.toString();
        System.out.println("Mixed nesting: " + json);

        assertTrue(json.contains("\"name\":\"Bob\""));
        assertTrue(json.contains("\"type\":\"jackson\""));
        assertTrue(json.contains("\"tags\""));
    }

    @Test
    void testConvertBackAndForth() {
        // Start with JsonObj, convert to ObjectNode, modify, convert back
        JsonObj original = obj("name", "Alice", "age", 30);

        ObjectNode asNode = original.asNode();
        asNode.put("email", "alice@example.com");  // Modify using Jackson API

        // Can we use the modified ObjectNode in a new JsonObj?
        JsonObj wrapper = obj("user", asNode);

        String json = wrapper.toString();
        System.out.println("After conversion and modification: " + json);

        assertTrue(json.contains("\"name\":\"Alice\""));
        assertTrue(json.contains("\"email\":\"alice@example.com\""));
    }
}
