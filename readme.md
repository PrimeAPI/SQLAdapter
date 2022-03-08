# SQLAdapter

## Maven

````
<repository>
    <id>PrimeAPI</id>
    <url>https://repo.primeapi.de</url>
</repository>

<dependency>
   <groupId>de.primeapi.util.sql</groupId>
   <artifactId>SQLAdapter</artifactId>
   <version>1.0.0</version>
</dependency>
````

## Usage

### Select Queries

````java
int salary = database.select("SELECT salary FROM employees WHERE name = ?")
		.parameters("John")
		.execute(Integer.class)
		.get();
````

#### Async

````java
        database.select("SELECT salary FROM employees WHERE name = ?")
		.parameters("John")
		.execute(Integer.class)
		.getAsync()
		.thenAcceptAsync(salary->{
		System.out.println(String.format("John's salery is: %s",salary))
		});
````

### Update Queries (UPDATE, INSERT, DELETE, CREATE, DROP)
````java
int id = database.update("INSERT INTO employees VALUES (id, ?, ?)")
		                .parameters("Dieter", 500000)
		                .returnGeneratedKeys(Integer.class)
		                .get();
````