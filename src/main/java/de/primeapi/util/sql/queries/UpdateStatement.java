package de.primeapi.util.sql.queries;

import de.primeapi.util.sql.Database;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Lukas S. PrimeAPI
 * created on 08.03.2022
 * crated for SQLAdapter
 */
@RequiredArgsConstructor
public class UpdateStatement {


	@NonNull
	private Database database;
	@NonNull
	private String query;


	private Set<Object> parameters = new HashSet<>();

	public UpdateStatement parameters(Object... parameters) {
		this.parameters = Arrays.stream(parameters).collect(Collectors.toSet());
		return this;
	}

	public UpdateStatement parameter(Object parameter) {
		parameters.add(parameter);
		return this;
	}


	@SneakyThrows
	public void execute() {
		PreparedStatement st = database.getConnection().prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
		                                                                 ResultSet.CONCUR_UPDATABLE
		                                                                );
		int i = 1;
		for (Object parameter : parameters) {
			st.setObject(i, parameter);
			i++;
		}
		st.executeUpdate();
	}

	@SneakyThrows
	public <T> Collector<T> returnGeneratedKeys(Class<T> type){

		PreparedStatement st = database.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		int i = 1;
		for (Object parameter : parameters) {
			st.setObject(i, parameter);
			i++;
		}
		st.executeUpdate();
		return new Collector<>(st.getGeneratedKeys(), database, type);
	}


}
