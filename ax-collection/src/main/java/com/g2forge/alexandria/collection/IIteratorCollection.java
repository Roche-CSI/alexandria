package com.g2forge.alexandria.collection;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@FunctionalInterface
public interface IIteratorCollection<T> extends ICollection<T> {
	public default Stream<T> stream() {
		return StreamSupport.stream(spliterator(), false);
	}

	public default Collection<T> toCollection() {
		return stream().collect(Collectors.toList());
	}
}
