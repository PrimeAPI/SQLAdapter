package de.primeapi.util.sql.queries;

import de.primeapi.util.sql.Database;
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
	private Set<T> getAsSetRaw() {
		Set<T> set = new HashSet<>();
		resultSet.beforeFirst();
		while (resultSet.next()) {
			set.add(resultSet.getObject(1, type));
		}
		return set;
	}

	public Retriever<Set<T>> getAsSet() {
		return new Retriever<>(this::getAsSetRaw);
	}

	/**
	 *
	 * @return The first column of the first row
	 */
	@SneakyThrows
	private T getRaw() {
		if (resultSet.isBeforeFirst()) resultSet.next();
		return resultSet.getObject(1, type);
	}

	public Retriever<T> get() {
		return new Retriever<>(this::getRaw);
	}

	/**
	 *
	 * @param index The index of the row
	 * @return Returns the row indicated the index of the first row
	 */
	@SneakyThrows
	private T getRaw(int index) {
		if (resultSet.isBeforeFirst()) resultSet.next();
		return resultSet.getObject(index, type);
	}
	public Retriever<T> get(int index) {
		return new Retriever<>(() -> getRaw(index));
	}


	/**
	 *
	 * @param column The name of the column
	 * @return The value if the column given by {@code column} of the first row
	 */
	@SneakyThrows
	private T getRaw(String column) {
		if (resultSet.isBeforeFirst()) resultSet.next();
		return resultSet.getObject(column, type);
	}

	public Retriever<T> get(String column) {
		return new Retriever<>(() -> getRaw(column));
	}


	/**
	 *
	 * @return Whether or not there is a next row
	 */
	@SneakyThrows
	private boolean isAnyRaw() {
		return resultSet.next();
	}

	public Retriever<Boolean> isAny(){
		return new Retriever<>(this::isAnyRaw);
	}

	/**
	 *
	 * @return All columns values in a row in an array
	 */
	@SneakyThrows
	private Object[] getRowDataRaw() {
		if (resultSet.isBeforeFirst()) resultSet.next();
		Object[] array = new Object[resultSet.getMetaData().getColumnCount()];
		for (int i = 0; i < array.length; i++) {
			array[i] = resultSet.getObject(i + 1);
		}
		return array;
	}

	public Retriever<Object[]> getRowData(){
		return new Retriever<>(this::getRowDataRaw);
	}

	/**
	 * @return A Set of all rows' columns values in a row in an array
	 */
	@SneakyThrows
	private Set<Object[]> getAllRowDataRaw() {
		Set<Object[]> set = new HashSet<>();
		resultSet.beforeFirst();
		while (resultSet.next()) {
			set.add(getRowDataRaw());
		}
		return set;
	}

	public Retriever<Set<Object[]>> getAllRowDataAsync(){
		return new Retriever<>(this::getAllRowDataRaw);
	}


}
