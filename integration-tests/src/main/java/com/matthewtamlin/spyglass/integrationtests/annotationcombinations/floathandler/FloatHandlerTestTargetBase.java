/*
 * Copyright 2017 Matthew David Tamlin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.matthewtamlin.spyglass.integrationtests.annotationcombinations.floathandler;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import com.matthewtamlin.spyglass.integrationtests.framework.ReceivedValue;

public class FloatHandlerTestTargetBase extends View {
  private ReceivedValue<Float> receivedValue = ReceivedValue.none();
  
  public FloatHandlerTestTargetBase(final Context context) {
    super(context);
  }
  
  public FloatHandlerTestTargetBase(final Context context, final AttributeSet attrs) {
    super(context, attrs);
  }
  
  public FloatHandlerTestTargetBase(
      final Context context,
      final AttributeSet attrs,
      final int defStyleAttr) {
    
    super(context, attrs, defStyleAttr);
  }
  
  @TargetApi(21)
  @RequiresApi(21)
  public FloatHandlerTestTargetBase(
      final Context context,
      final AttributeSet attrs,
      final int defStyleAttr,
      final int defStyleRes) {
    
    super(context, attrs, defStyleAttr, defStyleRes);
  }
  
  public ReceivedValue<Float> getReceivedValue() {
    return receivedValue;
  }
  
  protected void setReceivedValue(final ReceivedValue<Float> receivedValue) {
    this.receivedValue = receivedValue;
  }
}