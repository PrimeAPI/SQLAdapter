package de.primeapi.util.sql.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Lukas S. PrimeAPI
 * created on 08.03.2022
 * crated for SQLAdapter
 */
@Getter
@AllArgsConstructor
public class AsyncContainer<T> {

	private final Supplier<T> supplier;


	/**
	 * Runs the consumer async as soon as the Database returns a value
	 * @param consumer The consumer which should be run
	 */
	public void submit(Consumer<T> consumer){
		CompletableFuture.supplyAsync(supplier).handle((unused, throwable) -> {
			if (throwable != null) {
				throwable.printStackTrace();
				return null;
			}
			return unused;
		}).thenAcceptAsync(consumer);
	}


	/**
	 * Used to handle the SQL request synchronised<br>
	 * Use this only if needed! This slows down the hole runtime!
	 * @return The Object which is returned by the Database
	 */
	public T complete(){
		return supplier.get();
	}



	@Override
	public String toString() {
		return complete().toString();
	}
}
