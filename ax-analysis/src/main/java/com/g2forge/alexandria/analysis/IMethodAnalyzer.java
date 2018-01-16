package com.g2forge.alexandria.analysis;

import java.lang.reflect.Method;

public interface IMethodAnalyzer {
	public org.apache.bcel.classfile.Method getBCEL();

	public Method getMethod();

	public String getPath();
}
