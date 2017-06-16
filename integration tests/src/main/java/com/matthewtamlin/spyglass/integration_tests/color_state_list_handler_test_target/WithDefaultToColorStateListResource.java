package com.matthewtamlin.spyglass.integration_tests.color_state_list_handler_test_target;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToColorStateListResource;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.ColorStateListHandler;
import com.matthewtamlin.spyglass.consumer.Spyglass;
import com.matthewtamlin.spyglass.integration_tests.R;
import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;

public class WithDefaultToColorStateListResource extends ColorStateListHandlerTestTargetBase {
	public WithDefaultToColorStateListResource(final Context context) {
		super(context);
		init(null, 0, 0);
	}

	public WithDefaultToColorStateListResource(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0, 0);
	}

	public WithDefaultToColorStateListResource(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr) {

		super(context, attrs, defStyleAttr);
		init(attrs, defStyleAttr, 0);
	}

	@TargetApi(21)
	@RequiresApi(21)
	public WithDefaultToColorStateListResource(
			final Context context,
			final AttributeSet attrs,
			final int defStyleAttr,
			final int defStyleRes) {

		super(context, attrs, defStyleAttr, defStyleRes);
		init(attrs, defStyleAttr, defStyleRes);
	}

	@ColorStateListHandler(attributeId = R.styleable.ColorStateListHandlerTestTargetBase_colorStateListAttr)
	@DefaultToColorStateListResource(resId = R.color.default_color_state_list_for_testing)
	public void handlerMethod(final ColorStateList csl) {
		setReceivedValue(ReceivedValue.of(csl));
	}

	private void init(final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
		Spyglass.builder()
				.withTarget(this)
				.withAnnotationSource(WithDefaultToColorStateListResource.class)
				.withContext(getContext())
				.withAttributeSet(attrs)
				.withDefStyleAttr(defStyleAttr)
				.withDefStyleRes(defStyleRes)
				.withStyleableResource(R.styleable.ColorStateListHandlerTestTargetBase)
				.build()
				.passDataToMethods();
	}
}