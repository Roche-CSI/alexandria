package com.g2forge.alexandria.java.tuple.implementations;

import java.util.Objects;

import com.g2forge.alexandria.java.tuple.ITuple2GS;

public class Tuple2GSO<T0, T1> extends Tuple1GSO<T0> implements ITuple2GS<T0, T1> {
	protected T1 value1;

	public Tuple2GSO() {
		this(null, null);
	}

	/**
	 * @param value1
	 */
	public Tuple2GSO(final T0 value0, final T1 value1) {
		super(value0);
		this.value1 = value1;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!super.equals(obj)) return false;
		if (getClass() != obj.getClass()) return false;

		final Tuple2G_O<?, ?> that = (Tuple2G_O<?, ?>) obj;
		return Objects.equals(get0(), that.get0()) && Objects.equals(get1(), that.get1());
	}

	@Override
	public T1 get1() {
		return value1;
	}

	@Override
	public int hashCode() {
		return Objects.hash(get0(), get1());
	}

	@Override
	public ITuple2GS<T0, T1> set0(final T0 value) {
		value0 = value;
		return this;
	}

	@Override
	public ITuple2GS<T0, T1> set1(final T1 value) {
		this.value1 = value;
		return this;
	}

	@Override
	public T1 swap1(final T1 value) {
		final T1 retVal = get1();
		set1(value);
		return retVal;
	}
}
