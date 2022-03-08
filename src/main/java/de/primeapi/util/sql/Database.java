package de.primeapi.util.sql;

import de.primeapi.util.sql.queries.SelectStatement;
import de.primeapi.util.sql.queries.UpdateStatement;
import lombok.Getter;
import lombok.NonNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Lukas S. PrimeAPI
 * created on 08.03.2022
 * crated for SQLAdapter
 */
@Getter
public class Database {

	private static Database instance;

	@NonNull
	private final Connection connection;

	/**
	 * Initializies the Database.
	 * @param connection The SQL-Connection the statements shall be sent to
	 */
	public Database(@NonNull Connection connection) {
		this.connection = connection;
	}

	/**
	 * Initializes the Database by creating a jdbc connection.
	 * @param host The Hostname (including the port)
	 * @param database The name of the database
	 * @param username The username of the sql-user
	 * @param password The password for the user (may be null)
	 * @throws SQLException if a database access error occurs
	 */
	public Database(String host, String database, String username, String password) throws SQLException {
		this.connection = DriverManager.getConnection(
				String.format("jdbc:mysql://%s/%s?autoReconnect=true", host, database),
				username,
				password)
		;
	}

	/**
	 * Used for select statements
	 * @param query The Query which shall be executed
	 * @return a {@link SelectStatement} for building an {@link de.primeapi.util.sql.queries.Collector}
	 */
	public SelectStatement select(String query){
		return new SelectStatement(this, query);
	}

	/**
	 * Used fpr update statements (UPDATE, INSERT, CREATE, DROP, DELETE)
	 * @param query The Query which shall be executed
	 * @return a {@link UpdateStatement} for building an {@link de.primeapi.util.sql.queries.Collector}
	 */
	public UpdateStatement update(String query){
		return new UpdateStatement(this, query);
	}




}
