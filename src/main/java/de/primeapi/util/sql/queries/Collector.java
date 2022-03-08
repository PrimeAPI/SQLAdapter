package de.primeapi.util.sql.queries;

import de.primeapi.util.sql.Database;
import de.primeapi.util.sql.util.AsyncContainer;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

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

	public AsyncContainer<Set<T>> getAsSetAsync() {
		return new AsyncContainer<>(this::getAsSet);
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

	public AsyncContainer<T> getAsync() {
		return new AsyncContainer<>(this::get);
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
	public AsyncContainer<T> getAsync(int index) {
		return new AsyncContainer<>(() -> get(index));
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

	public AsyncContainer<T> getAsync(String column) {
		return new AsyncContainer<>(() -> get(column));
	}


	/**
	 *
	 * @return Whether or not there is a next row
	 */
	@SneakyThrows
	public boolean isAny() {
		return resultSet.next();
	}

	public AsyncContainer<Boolean> isAnyAsync(){
		return new AsyncContainer<>(this::isAny);
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

	public AsyncContainer<Object[]> getRowDataAsync(){
		return new AsyncContainer<>(this::getRowData);
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

	public AsyncContainer<Set<Object[]>> getAllRowDataAsync(){
		return new AsyncContainer<>(this::getAllRowData);
	}


}
