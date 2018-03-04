package com.matthewtamlin.spyglass.integrationtests.reactivex.flowable;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import com.matthewtamlin.spyglass.integrationtests.R;
import com.matthewtamlin.spyglass.integrationtests.framework.ReceivedValue;
import com.matthewtamlin.spyglass.markers.annotations.defaults.DefaultToBoolean;
import com.matthewtamlin.spyglass.markers.annotations.unconditionalhandlers.BooleanHandler;
import io.reactivex.Flowable;

public class WithUnconditionalHandlerAndDefault extends FlowableTestTargetBase {
  public WithUnconditionalHandlerAndDefault(final Context context) {
    super(context);
    init(null, 0, 0);
  }
  
  public WithUnconditionalHandlerAndDefault(final Context context, @Nullable final AttributeSet attrs) {
    super(context, attrs);
    init(attrs, 0, 0);
  }
  
  public WithUnconditionalHandlerAndDefault(final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(attrs, defStyleAttr, 0);
  }
  
  @RequiresApi(21)
  @TargetApi(21)
  public WithUnconditionalHandlerAndDefault(
      final Context context,
      @Nullable final AttributeSet attrs,
      final int defStyleAttr,
      final int defStyleRes) {
    
    super(context, attrs, defStyleAttr, defStyleRes);
    init(attrs, defStyleAttr, defStyleRes);
  }
  
  @BooleanHandler(attributeId = R.styleable.FlowableTestTargetBase_flowableAttr)
  @DefaultToBoolean(false)
  public Flowable handlerMethod(final boolean value) {
    return Flowable.fromCallable(() -> {
      setReceivedValue(ReceivedValue.of(value));
      
      return 0;
    });
  }
  
  private void init(final AttributeSet attributeSet, final int defStyleAttr, final int defStyleRes) {
    WithUnconditionalHandlerAndDefault_SpyglassCompanion
        .builder()
        .withTarget(this)
        .withContext(getContext())
        .withStyleableResource(R.styleable.FlowableTestTargetBase)
        .withAttributeSet(attributeSet)
        .withDefaultStyleAttribute(defStyleAttr)
        .withDefaultStyleResource(defStyleRes)
        .build()
        .callTargetMethods()
        .blockingAwait();
  }
}