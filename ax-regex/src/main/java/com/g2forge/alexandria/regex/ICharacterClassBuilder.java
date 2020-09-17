package com.g2forge.alexandria.regex;

import com.g2forge.alexandria.java.function.builder.IBuilder;

public interface ICharacterClassBuilder<Result> extends IBuilder<Result> {
	public ICharacterClassBuilder<Result> character(char character);

	public ICharacterClassBuilder<Result> range(char start, char end);
	
	public ICharacterClassBuilder<Result> named(NamedCharacterClass named);
}
