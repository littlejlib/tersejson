# TerseJson

Minimal-ceremony JSON object builder for Java.

## Usage

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

## Features

- **Terse syntax**: Build JSON with minimal ceremony using `obj()` and `arr()` helpers
- **Type-safe**: Extends standard Java collections (HashMap, ArrayList)
- **Nested structures**: Support for arbitrarily nested objects and arrays
- **Jackson integration**: Uses Jackson ObjectMapper for serialization
- **Lightweight**: Only dependency is Jackson

## API

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

### Accessing the ObjectMapper

```java
ObjectMapper mapper = Json.mapper();
// Customize mapper if needed
mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
```

## Use Cases

TerseJson is perfect for:

1. **Building REST API request bodies** - Pair with any HTTP client (OkHttp, Java HttpClient, etc.)
2. **Testing** - Quickly create JSON payloads for unit/integration tests
3. **Configuration** - Generate JSON configuration files
4. **Data transformation** - Convert Java objects to JSON strings
5. **API prototyping** - Rapidly build JSON payloads for API exploration

## Related Projects

- [TerseRest](https://github.com/xyz-jphil/terserest) - Minimal-ceremony REST API client (uses TerseJson for JSON building)

## License

MIT License - see LICENSE file for details
