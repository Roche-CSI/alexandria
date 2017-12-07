package com.g2forge.alexandria.collection;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * Simple interface for a collection of elements which can be examined. This interface may need to be renamed later given the number of things called "collections".
 * 
 * @author greg
 *
 * @param <T>
 * @link IIteratorCollection
 * @link ICollectionCollection
 * @link IStreamCollection
 */
public interface ICollection<T> extends Iterable<T> {
	public Stream<T> stream();

	public Collection<T> toCollection();
}
