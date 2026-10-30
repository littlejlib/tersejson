# TerseJson

[![Maven Central](https://img.shields.io/maven-central/v/io.github.xyz-jphil/tersejson.svg)](https://central.sonatype.com/artifact/io.github.xyz-jphil/tersejson)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

Minimal-ceremony JSON object builder for Java. Build JSON with the terseness of JavaScript, the type-safety of Java collections, and full Jackson integration.

## Quick Start

```java
import static terse.Json.*;

String json = obj(
    "name", "Alice",
    "age", 30,
    "address", obj(
        "city", "NYC",
        "zip", "10001"
    ),
    "tags", arr("admin", "verified")
).toString();

// Output: {"name":"Alice","age":30,"address":{"city":"NYC","zip":"10001"},"tags":["admin","verified"]}
```

## Installation

```xml
<dependency>
    <groupId>io.github.xyz-jphil</groupId>
    <artifactId>tersejson</artifactId>
    <version>1.0</version>
</dependency>
```

## Requirements

- Java 21+
- Jackson 2.18.2+

## Why TerseJson?

**Java JSON building is verbose:**
```java
Map<String, Object> user = new HashMap<>();
user.put("name", "Alice");
user.put("age", 30);
Map<String, Object> address = new HashMap<>();
address.put("city", "NYC");
user.put("address", address);
String json = new ObjectMapper().writeValueAsString(user);
```

**TerseJson makes it terse:**
```java
String json = obj(
    "name", "Alice",
    "age", 30,
    "address", obj("city", "NYC")
).toString();
```

## Features

- **Terse syntax**: Build JSON with minimal ceremony using `obj()` and `arr()` helpers
- **Fluent API**: Chain mutations with `.__()` method for incremental building
- **Type-safe**: Extends standard Java collections (HashMap, ArrayList)
- **Nested structures**: Support for arbitrarily nested objects and arrays
- **Jackson integration**: Direct conversion to ObjectNode/ArrayNode with `.asNode()`
- **Lightweight**: Only dependency is Jackson (no HTTP client bloat)
- **Interoperable**: Mix with Jackson ObjectNode/ArrayNode freely

## API Reference

### Creating JSON Objects

```java
JsonObj user = obj(
    "id", 123,
    "name", "Alice",
    "active", true
);
```

### Creating JSON Arrays

```java
JsonArr numbers = arr(1, 2, 3, 4, 5);
JsonArr names = arr("Alice", "Bob", "Charlie");
```

### Nested Structures

```java
JsonObj complex = obj(
    "user", obj(
        "name", "Alice",
        "email", "alice@example.com"
    ),
    "roles", arr("admin", "user"),
    "metadata", obj(
        "created", "2025-01-01",
        "active", true
    )
);
```

### Fluent API with `__()` Method

Build JSON incrementally with method chaining:

```java
// Fluent object building
JsonObj user = obj("name", "Alice")
    .__("age", 30)
    .__("email", "alice@example.com")
    .__("verified", true);

// Fluent array building
JsonArr tags = arr("admin")
    .__("verified")
    .__("premium");

// Complex nested structures
String json = obj("name", "Alice")
    .__("age", 30)
    .__("address", obj("city", "NYC")
        .__("zip", "10001")
        .__("country", "USA"))
    .__("tags", arr("admin")
        .__("verified")
        .__("premium"))
    .toString();
```

**Note:** Use `__()` for fluent chaining. The inherited `put()` and `add()` methods are still available but don't return `this`.

### Converting to Jackson JsonNode

```java
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

// Convert JsonObj to ObjectNode
ObjectNode objNode = obj("name", "Alice", "age", 30).asNode();

// Convert JsonArr to ArrayNode
ArrayNode arrNode = arr("one", "two", "three").asNode();

// Useful for working with Jackson's tree model APIs
ObjectNode complexNode = obj(
    "user", obj("name", "Bob"),
    "tags", arr("admin", "verified")
).asNode();
```

### Accessing the ObjectMapper

```java
ObjectMapper mapper = Json.mapper();
// Customize mapper if needed
mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
```

## Use Cases

TerseJson is perfect for:

1. **Building REST API request bodies** - Pair with any HTTP client (OkHttp, Java HttpClient, etc.)
   ```java
   String payload = obj("query", "search term", "limit", 10).toString();
   ```

2. **Testing** - Quickly create JSON payloads for unit/integration tests
   ```java
   @Test
   void testUserCreation() {
       String json = obj("name", "Alice", "email", "alice@test.com").toString();
       // Use in your test...
   }
   ```

3. **Configuration** - Generate JSON configuration files
   ```java
   String config = obj(
       "database", obj("host", "localhost", "port", 5432),
       "cache", obj("enabled", true, "ttl", 3600)
   ).toString();
   ```

4. **Working with Jackson APIs** - Convert to ObjectNode when needed
   ```java
   ObjectNode node = obj("key", "value").asNode();
   node.put("addedLater", "newValue");  // Use Jackson's API
   ```

5. **API prototyping** - Rapidly build and iterate on JSON structures
   ```java
   String mockResponse = obj(
       "status", "success",
       "data", arr(obj("id", 1), obj("id", 2))
   ).toString();
   ```

## Interoperability

Mix TerseJson and Jackson freely:

```java
// Nest Jackson ObjectNode inside JsonObj
ObjectNode jacksonNode = mapper().createObjectNode().put("type", "jackson");
JsonObj result = obj("data", jacksonNode, "count", 42);

// Nest JsonObj inside Jackson ObjectNode
JsonObj terseObj = obj("name", "Alice");
ObjectNode jacksonRoot = mapper().createObjectNode();
jacksonRoot.set("user", terseObj.asNode());
```

## Comparison

| Feature | TerseJson | Jackson Only | Gson |
|---------|-----------|--------------|------|
| Terse syntax | ✅ | ❌ | ❌ |
| Fluent chaining | ✅ | ✅ | ❌ |
| Jackson integration | ✅ | ✅ | ❌ |
| Extends Java Collections | ✅ | ❌ | ❌ |
| Zero additional dependencies | ✅ | N/A | ✅ |

## Related Projects

- [TerseRest](https://github.com/xyz-jphil/terserest) - Minimal-ceremony REST API client (uses TerseJson for JSON building)

## Contributing

Issues and pull requests welcome at [GitHub](https://github.com/xyz-jphil/tersejson).

## License

MIT License - see [LICENSE](LICENSE) file for details
