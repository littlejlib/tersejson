package terse;

import org.junit.jupiter.api.Test;
import static terse.Json.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests to demonstrate that JsonObj and JsonArr already have useful methods
 * because they extend HashMap and ArrayList respectively.
 */
class FluentApiTest {

    @Test
    void testJsonObjHasPutMethod() {
        // JsonObj extends HashMap, so it has put(), get(), containsKey(), etc.
        JsonObj user = obj("name", "Alice");

        // Use HashMap's put method
        user.put("age", 30);
        user.put("email", "alice@example.com");

        String json = user.toString();
        System.out.println("After using put(): " + json);

        assertTrue(json.contains("\"name\":\"Alice\""));
        assertTrue(json.contains("\"age\":30"));
        assertTrue(json.contains("\"email\":\"alice@example.com\""));
    }

    @Test
    void testJsonObjHashMapMethods() {
        JsonObj user = obj("name", "Alice", "age", 30);

        // All HashMap methods available
        assertEquals("Alice", user.get("name"));
        assertEquals(30, user.get("age"));
        assertTrue(user.containsKey("name"));
        assertFalse(user.containsKey("email"));
        assertEquals(2, user.size());

        user.remove("age");
        assertFalse(user.containsKey("age"));
    }

    @Test
    void testJsonArrHasAddMethod() {
        // JsonArr extends ArrayList, so it has add(), get(), size(), etc.
        JsonArr tags = arr("admin");

        // Use ArrayList's add method
        tags.add("verified");
        tags.add("premium");

        String json = tags.toString();
        System.out.println("After using add(): " + json);

        assertTrue(json.contains("\"admin\""));
        assertTrue(json.contains("\"verified\""));
        assertTrue(json.contains("\"premium\""));
    }

    @Test
    void testJsonArrArrayListMethods() {
        JsonArr numbers = arr(1, 2, 3);

        // All ArrayList methods available
        assertEquals(1, numbers.get(0));
        assertEquals(2, numbers.get(1));
        assertEquals(3, numbers.size());

        numbers.add(4);
        numbers.add(0, 0);  // Insert at beginning

        assertEquals(5, numbers.size());
        assertEquals(0, numbers.get(0));
        assertEquals(4, numbers.get(4));
    }

    @Test
    void testFluentPutChaining() {
        // Now put() returns 'this' for fluent chaining!
        JsonObj user = obj("name", "Alice")
            .__("age", 30)
            .__("email", "alice@example.com")
            .__("verified", true);

        String json = user.toString();
        System.out.println("Fluent put() chaining: " + json);

        assertTrue(json.contains("\"name\":\"Alice\""));
        assertTrue(json.contains("\"age\":30"));
        assertTrue(json.contains("\"email\":\"alice@example.com\""));
        assertTrue(json.contains("\"verified\":true"));
    }

    @Test
    void testFluentAddChaining() {
        // Now add() returns 'this' for fluent chaining!
        JsonArr tags = arr("admin")
            .__("verified")
            .__("premium")
            .__("beta");

        String json = tags.toString();
        System.out.println("Fluent add() chaining: " + json);

        assertEquals(4, tags.size());
        assertTrue(json.contains("\"admin\""));
        assertTrue(json.contains("\"beta\""));
    }

    @Test
    void testFluentMixedBuilding() {
        // Build complex structure using fluent API
        String json = obj("name", "Alice")
            .__("age", 30)
            .__("address", obj("city", "NYC")
                .__("zip", "10001")
                .__("country", "USA"))
            .__("tags", arr("admin")
                .__("verified")
                .__("premium"))
            .toString();

        System.out.println("Fluent mixed building: " + json);

        assertTrue(json.contains("\"city\":\"NYC\""));
        assertTrue(json.contains("\"verified\""));
    }

    @Test
    void testBuildingComplexStructureMutably() {
        // Start with base object
        JsonObj user = obj("name", "Alice");

        // Add fields mutably
        user.put("age", 30);
        user.put("email", "alice@example.com");

        // Add nested object
        JsonObj address = obj("city", "NYC");
        address.put("zip", "10001");
        user.put("address", address);

        // Add array
        JsonArr tags = arr("admin");
        tags.add("verified");
        user.put("tags", tags);

        String json = user.toString();
        System.out.println("Complex structure built mutably: " + json);

        assertTrue(json.contains("\"name\":\"Alice\""));
        assertTrue(json.contains("\"city\":\"NYC\""));
        assertTrue(json.contains("\"verified\""));
    }
}
