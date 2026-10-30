package terse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.*;

/**
 * Terse JSON object builder utility.
 *
 * Usage:
 * <pre>
 * import static terse.Json.*;
 *
 * String json = obj(
 *     "name", "Alice",
 *     "age", 30,
 *     "address", obj(
 *         "city", "NYC",
 *         "zip", "10001"
 *     ),
 *     "tags", arr("admin", "verified")
 * ).toString();
 * </pre>
 */
public class Json {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Returns the shared ObjectMapper instance.
     * Useful when you need to customize configuration or parse JSON.
     */
    public static ObjectMapper mapper() {
        return MAPPER;
    }

    /**
     * Creates a JSON object from varargs key-value pairs.
     * @param vs alternating keys (String) and values (Object)
     * @return JsonObj instance
     */
    public static JsonObj obj(Object... vs) {
        return new JsonObj(vs);
    }

    /**
     * Creates a JSON array from varargs elements.
     * @param vs array elements
     * @return JsonArr instance
     */
    public static JsonArr arr(Object... vs) {
        return new JsonArr(vs);
    }

    /**
     * Fluent JSON object builder extending HashMap.
     * Call toString() to serialize to JSON string.
     */
    public static class JsonObj extends HashMap<String, Object> {

        /**
         * Creates a JSON object from varargs key-value pairs.
         * @param vs alternating keys and values (must be even length)
         */
        public JsonObj(Object... vs) {
            if (vs.length % 2 != 0) {
                throw new IllegalArgumentException("Arguments must be key-value pairs (even length)");
            }
            for (int i = 0; i < vs.length; i += 2) {
                put((String) vs[i], vs[i + 1]);
            }
        }
        
        /**
         * Fluent put() that returns this for method chaining.
         * Note: This overrides HashMap's put() which normally returns the old value.
         * @param key the key
         * @param value the value
         * @return this JsonObj for chaining
         */
        public JsonObj __(String key, Object value) {
            super.put(key, value);
            return this;
        }

        /**
         * Converts this JsonObj to Jackson's ObjectNode.
         * Useful when you need to work with Jackson's tree model.
         * @return ObjectNode representation
         */
        public ObjectNode asNode(){
            return MAPPER.valueToTree(this);
        }

        @Override
        public String toString() {
            try {
                return MAPPER.writeValueAsString(this);
            } catch (Exception e) {
                throw new RuntimeException("Failed to serialize JSON object", e);
            }
        }
    }

    /**
     * Fluent JSON array builder extending ArrayList.
     * Call toString() to serialize to JSON string.
     */
    public static class JsonArr extends ArrayList<Object> {

        /**
         * Creates a JSON array from varargs elements.
         * @param vs array elements
         */
        public JsonArr(Object... vs) {
            addAll(Arrays.asList(vs));
        }

        /**
         * Fluent add() that returns this for method chaining.
         * Note: This overrides ArrayList's add() which normally returns boolean.
         * @param element the element to add
         * @return this JsonArr for chaining
         */
        public JsonArr __(Object element) {
            super.add(element);
            return this;
        }

        /**
         * Converts this JsonArr to Jackson's ArrayNode.
         * Useful when you need to work with Jackson's tree model.
         * @return ArrayNode representation
         */
        public ArrayNode asNode(){
            return MAPPER.valueToTree(this);
        }

        @Override
        public String toString() {
            try {
                return MAPPER.writeValueAsString(this);
            } catch (Exception e) {
                throw new RuntimeException("Failed to serialize JSON array", e);
            }
        }
    }
}
