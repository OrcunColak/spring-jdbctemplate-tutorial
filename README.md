# Read Me First
Original idea is from  
https://medium.com/code-with-farhan/spring-boot-database-connection-jdbc-vs-jpa-hibernate-edc9708966fc
 See also  
https://www.sivalabs.in/spring-boot-jdbctemplate-tutorial/

# Stream From Repository
See  
https://medium.com/@eremeykin/how-to-deal-with-hikaricp-connection-leaks-part-2-847a9629627f

# Swagger UI
http://localhost:8080/swagger-ui/index.html

# SimpleJdbcInsert
A SimpleJdbcInsert is a multi-threaded, reusable object providing easy (batch) insert capabilities for a table. It provides meta-data processing to simplify the code to construct a basic insert statement. All you need to provide is the name of the table and a Map containing the column names and the column values.

```java
@Repository
public class BookRepository {

    private final SimpleJdbcInsert simpleJdbcInsert;

    public BookRepository(DataSource dataSource) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("book")
                .usingGeneratedKeyColumns("id");
    }

    public Long insertBook(Book book) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("title", book.title());
        parameters.addValue("isbn", book.isbn());
        parameters.addValue("description", book.description());
        parameters.addValue("page", book.page());
        parameters.addValue("price", book.price());

        // Execute the insert and return the generated key
        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }
}
```

# SimpleJdbcCall
A SimpleJdbcCall is a multithreaded, reusable object representing a call to a stored procedure or a stored function. It provides meta-data processing to simplify the code needed to access basic stored procedures/functions.
```java
@Autowired
private SimpleJdbcCall simpleJdbcCall;

public Book getBookById(Long bookId) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("bookId", bookId);
        
        simpleJdbcCall.withProcedureName("getBookById");
        Map<String, Object> result = simpleJdbcCall.execute(parameters);
        return ((List<Book>) result.get("result")).get(0);
}
```
