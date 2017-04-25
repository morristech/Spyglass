package com.matthewtamlin.spyglass.annotations.use_annotations;

import com.matthewtamlin.spyglass.library.use_adapters.UseByteAdapter;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.annotation.Annotation;

@RunWith(JUnit4.class)
public class TestUseByte extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return UseByte.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return UseByteAdapter.class;
	}
}