package de.primeapi.util.sql.queries;

import de.primeapi.util.sql.Database;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * @author Lukas S. PrimeAPI
 * created on 08.03.2022
 * crated for SQLAdapter
 */
@AllArgsConstructor
public class Collector<T> {

	@NonNull
	private ResultSet resultSet;
	@NonNull
	private Database database;
	private Class<T> type;

	/**
	 * @return The Set of the first column of all rows
	 */
	@SneakyThrows
	public Set<T> getAsSet() {
		Set<T> set = new HashSet<>();
		resultSet.beforeFirst();
		while (resultSet.next()) {
			set.add(resultSet.getObject(1, type));
		}
		return set;
	}

	public CompletableFuture<Set<T>> getAsSetAsync() {
		return CompletableFuture.supplyAsync(this::getAsSet);
	}

	/**
	 *
	 * @return The first column of the first row
	 */
	@SneakyThrows
	public T get() {
		if (resultSet.isBeforeFirst()) resultSet.next();
		return resultSet.getObject(1, type);
	}

	public CompletableFuture<T> getAsync() {
		return CompletableFuture.supplyAsync(this::get);
	}

	/**
	 *
	 * @param index The index of the row
	 * @return Returns the row indicated the index of the first row
	 */
	@SneakyThrows
	public T get(int index) {
		if (resultSet.isBeforeFirst()) resultSet.next();
		return resultSet.getObject(index, type);
	}
	public CompletableFuture<T> getAsync(int index) {
		return CompletableFuture.supplyAsync(() -> get(index));
	}


	/**
	 *
	 * @param column The name of the column
	 * @return The value if the column given by {@code column} of the first row
	 */
	@SneakyThrows
	public T get(String column) {
		if (resultSet.isBeforeFirst()) resultSet.next();
		return resultSet.getObject(column, type);
	}

	public CompletableFuture<T> getAsync(String column) {
		return CompletableFuture.supplyAsync(() -> get(column));
	}


	/**
	 *
	 * @return Whether or not there is a next row
	 */
	@SneakyThrows
	public boolean isAny() {
		return resultSet.next();
	}

	public CompletableFuture<Boolean> isAnyAsync(){
		return CompletableFuture.supplyAsync(this::isAny);
	}

	/**
	 *
	 * @return All columns values in a row in an array
	 */
	@SneakyThrows
	public Object[] getRowData() {
		if (resultSet.isBeforeFirst()) resultSet.next();
		Object[] array = new Object[resultSet.getMetaData().getColumnCount()];
		for (int i = 0; i < array.length; i++) {
			array[i] = resultSet.getObject(i + 1);
		}
		return array;
	}

	public CompletableFuture<Object[]> getRowDataAsync(){
		return CompletableFuture.supplyAsync(this::getRowData);
	}

	/**
	 * @return A Set of all rows' columns values in a row in an array
	 */
	@SneakyThrows
	public Set<Object[]> getAllRowData() {
		Set<Object[]> set = new HashSet<>();
		resultSet.beforeFirst();
		while (resultSet.next()) {
			set.add(getRowData());
		}
		return set;
	}

	public CompletableFuture<Set<Object[]>> getAllRowDataAsync(){
		return CompletableFuture.supplyAsync(this::getAllRowData);
	}


}
