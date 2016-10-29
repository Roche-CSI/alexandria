package com.g2forge.alexandria.java.reflect;

import java.lang.reflect.Type;

import com.g2forge.alexandria.java.core.helpers.HArray;
import com.g2forge.alexandria.java.core.helpers.HString;

public interface IJavaAccessorMethod {
	public enum Accessor {
		GET {
			@Override
			public boolean isMatchingParameterTypes(Type[] types) {
				return types.length == 0;
			}

			@Override
			public boolean isMatchingReturnType(Type type) {
				return !Void.TYPE.equals(type);
			}
		},
		IS {
			@Override
			public boolean isMatchingParameterTypes(Type[] types) {
				return types.length == 0;
			}

			@Override
			public boolean isMatchingReturnType(Type type) {
				return Boolean.TYPE.equals(type) || Boolean.class.equals(type);
			}
		},
		SET {
			@Override
			public boolean isMatchingParameterTypes(Type[] types) {
				return types.length == 1;
			}

			@Override
			public boolean isMatchingReturnType(Type type) {
				return true;
			}
		};

		public static final String[] PREFIXES = HArray.map(String.class, Accessor::getPrefix, Accessor.values());

		public static String getFieldName(String methodName) {
			final String stripped = HString.stripPrefix(methodName, Accessor.PREFIXES);
			if (stripped == methodName) return null;
			return HString.lowercase(stripped);
		}

		public String getPrefix() {
			return name().toLowerCase();
		}

		public abstract boolean isMatchingParameterTypes(Type[] types);

		public abstract boolean isMatchingReturnType(Type type);
	}

	public default Accessor getAccessorType() {
		final String name = getName();
		final Type[] parameterTypes = getParameterTypes();
		final Type returnType = getReturnType();
		for (Accessor type : Accessor.values()) {
			if (name.startsWith(type.getPrefix()) && type.isMatchingReturnType(returnType) && type.isMatchingParameterTypes(parameterTypes)) return type;
		}
		return null;
	}

	public default String getFieldName() {
		final String name = getName();
		final String stripped = HString.stripPrefix(name, Accessor.PREFIXES);
		if (stripped == name) return null;
		return HString.lowercase(stripped);
	}

	public String getName();

	public Type[] getParameterTypes();

	public Type getReturnType();
}
