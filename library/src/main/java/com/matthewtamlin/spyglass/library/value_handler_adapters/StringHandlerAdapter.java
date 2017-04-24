package com.matthewtamlin.spyglass.library.value_handler_adapters;

import android.content.res.TypedArray;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.value_handler_annotations.StringHandler;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

/**
 * Adapter for interfacing with StringHandler annotations.
 */
@Tested(testMethod = "automated")
public class StringHandlerAdapter implements ValueHandlerAdapter<String, StringHandler> {
	@Override
	public TypedArrayAccessor<String> getAccessor(final StringHandler annotation) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return new TypedArrayAccessor<String>() {
			@Override
			public boolean valueExistsInArray(final TypedArray array) {
				checkNotNull(array, "Argument \'array\' cannot be null.");

				return array.hasValue(annotation.attributeId());
			}

			@Override
			public String getValueFromArray(final TypedArray array) {
				checkNotNull(array, "Argument \'array\' cannot be null.");

				if (valueExistsInArray(array)) {
					return array.getString(annotation.attributeId());
				} else {
					throw new RuntimeException("No attribute found for attribute ID " +
							annotation.attributeId());
				}
			}
		};
	}
}