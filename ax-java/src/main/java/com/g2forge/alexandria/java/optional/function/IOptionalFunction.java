package com.g2forge.alexandria.java.optional.function;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.g2forge.alexandria.java.core.error.UnreachableCodeError;
import com.g2forge.alexandria.java.core.helpers.CollectionHelpers;
import com.g2forge.alexandria.java.core.helpers.ObjectHelpers;
import com.g2forge.alexandria.java.function.LiteralSupplier;
import com.g2forge.alexandria.java.optional.IOptional;
import com.g2forge.alexandria.java.optional.factory.IOptionalFactory;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@FunctionalInterface
public interface IOptionalFunction<I, O> extends Function<I, IOptional<? extends O>>, IOptionalFunctional<I, O, IOptionalFunction<? super I, ? extends O>> {
	@Data
	@RequiredArgsConstructor
	public static class Overridden<I, O> implements IOptionalFunction<I, O> {
		protected final List<IOptionalFunction<? super I, ? extends O>> functions;

		@SafeVarargs
		public Overridden(IOptionalFunction<? super I, ? extends O>... functions) {
			this(CollectionHelpers.asList(functions));
		}

		@Override
		public IOptional<? extends O> apply(I i) {
			for (IOptionalFunction<? super I, ? extends O> function : getFunctions()) {
				final IOptional<? extends O> o = function.apply(i);
				if (o.isPresent()) return o;
			}
			throw new UnreachableCodeError();
		}

		@Override
		public IOptionalFunction<I, O> override(IOptionalFunction<? super I, ? extends O> override) {
			final List<IOptionalFunction<? super I, ? extends O>> functions = new ArrayList<>(getFunctions());
			functions.add(override);
			return new Overridden<I, O>(functions);
		}
	}

	@Data
	@RequiredArgsConstructor
	public static class Restricted<I, O> implements IOptionalFunction<I, O> {
		protected final IOptionalFunction<? super I, ? extends O> function;

		protected final Predicate<? super I> predicate;

		protected final IOptional<O> empty;

		@Override
		public IOptional<? extends O> apply(I i) {
			if (!getPredicate().test(i)) return getEmpty();
			return getFunction().apply(i);
		}
	}

	public static <I, O> IOptionalFunction<I, O> empty(IOptionalFactory factory) {
		return new IOptionalFunction<I, O>() {
			@Override
			public IOptional<? extends O> apply(I i) {
				return factory.empty();
			}

			@Override
			public String toString() {
				return ObjectHelpers.toString(this, "empty");
			}
		};
	}

	public static <I, O> IOptionalFunction<I, O> of(IOptionalFactory factory, Map<? super I, ? extends O> map) {
		return new IOptionalFunction<I, O>() {
			@Override
			public IOptional<? extends O> apply(I i) {
				return map.containsKey(i) ? factory.of(map.get(i)) : factory.empty();
			}

			@Override
			public String toString() {
				return ObjectHelpers.toString(this, map);
			}
		};
	}

	public static <I, O> IOptionalFunction<I, O> of(IOptionalFactory factory, Object input, O output) {
		return of(factory, input, new LiteralSupplier<>(output));
	}

	public static <I, O> IOptionalFunction<I, O> of(IOptionalFactory factory, Object input, Supplier<? extends O> output) {
		return new IOptionalFunction<I, O>() {
			@Override
			public IOptional<? extends O> apply(I i) {
				return input.equals(i) ? factory.of(output.get()) : factory.empty();
			}

			@Override
			public String toString() {
				return ObjectHelpers.toString(this, b -> b.append(input).append('=').append(output));
			}
		};
	}

	@Override
	public default Function<I, O> fallback(Function<? super I, ? extends O> fallback) {
		return i -> {
			final IOptional<? extends O> o = apply(i);
			return o.isPresent() ? o.get() : fallback.apply(i);
		};
	}

	@Override
	public default IOptionalFunction<I, O> override(IOptionalFunction<? super I, ? extends O> override) {
		return new Overridden<I, O>(this, override);
	}

	@Override
	public default IOptionalFunction<I, O> restrict(Predicate<? super I> predicate, IOptionalFactory factory) {
		return new Restricted<>(this, predicate, factory.empty());
	}
}
