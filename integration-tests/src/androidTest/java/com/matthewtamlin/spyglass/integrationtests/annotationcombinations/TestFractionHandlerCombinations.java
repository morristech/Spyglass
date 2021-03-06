/*
 * Copyright 2017-2018 Matthew David Tamlin
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

package com.matthewtamlin.spyglass.integrationtests.annotationcombinations;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.AttributeSet;
import com.matthewtamlin.spyglass.integrationtests.R;
import com.matthewtamlin.spyglass.integrationtests.annotationcombinations.fractionhandler.FractionHandlerTestTargetBase;
import com.matthewtamlin.spyglass.integrationtests.annotationcombinations.fractionhandler.withdefault.WithDefaultToFractionUsingBaseFractionAndBaseMultiplier;
import com.matthewtamlin.spyglass.integrationtests.annotationcombinations.fractionhandler.withdefault.WithDefaultToFractionUsingBaseFractionAndNoMultiplier;
import com.matthewtamlin.spyglass.integrationtests.annotationcombinations.fractionhandler.withdefault.WithDefaultToFractionUsingBaseFractionAndParentMultiplier;
import com.matthewtamlin.spyglass.integrationtests.annotationcombinations.fractionhandler.withdefault.WithDefaultToFractionUsingParentFractionAndBaseMultiplier;
import com.matthewtamlin.spyglass.integrationtests.annotationcombinations.fractionhandler.withdefault.WithDefaultToFractionUsingParentFractionAndNoMultiplier;
import com.matthewtamlin.spyglass.integrationtests.annotationcombinations.fractionhandler.withdefault.WithDefaultToFractionUsingParentFractionAndParentMultiplier;
import com.matthewtamlin.spyglass.integrationtests.annotationcombinations.fractionhandler.withdefault.WithDefaultToNull;
import com.matthewtamlin.spyglass.integrationtests.annotationcombinations.fractionhandler.withoutdefault.HandlerUsingBaseMultiplier;
import com.matthewtamlin.spyglass.integrationtests.annotationcombinations.fractionhandler.withoutdefault.HandlerUsingNoMultiplier;
import com.matthewtamlin.spyglass.integrationtests.annotationcombinations.fractionhandler.withoutdefault.HandlerUsingParentMultiplier;
import com.matthewtamlin.spyglass.integrationtests.framework.AttributeSetSupplier;
import com.matthewtamlin.spyglass.integrationtests.framework.ReceivedValue;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.matthewtamlin.spyglass.integrationtests.framework.AttributeSetSupplier.fromXml;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(AndroidJUnit4.class)
public class TestFractionHandlerCombinations {
  @Rule
  public final UiThreadTestRule uiThreadTestRule = new UiThreadTestRule();
  
  private Context context;
  
  @Before
  public void setup() {
    context = InstrumentationRegistry.getTargetContext();
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassPassesCorrectData_attributePresent_attributeIsBaseFraction_handlerHasBaseMultiplier() {
    final AttributeSet attrs = fromXml(context, R.xml.fraction_handler_with_attr_equals_base_frac);
    
    final FractionHandlerTestTargetBase target = new HandlerUsingBaseMultiplier(context, attrs);
    
    final float expectedValue = getBaseFractionMultipliedBy(HandlerUsingBaseMultiplier.MULTIPLIER);
    assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassPassesCorrectData_attributePresent_attributeIsBaseFraction_handlerHasNoMultiplier() {
    final AttributeSet attrs = fromXml(context, R.xml.fraction_handler_with_attr_equals_base_frac);
    
    final FractionHandlerTestTargetBase target = new HandlerUsingNoMultiplier(context, attrs);
    
    final float expectedValue = getBaseFractionMultipliedBy(1);
    assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassPassesCorrectData_attributePresent_attributeIsBaseFraction_handlerHasPrntMultiplier() {
    final AttributeSet attrs = fromXml(context, R.xml.fraction_handler_with_attr_equals_base_frac);
    
    final FractionHandlerTestTargetBase target = new HandlerUsingParentMultiplier(context, attrs);
    
    final float expectedValue = getBaseFractionMultipliedBy(1);
    assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassPassesCorrectData_attributePresent_attributeIsPrntFraction_handlerHasBaseMultiplier() {
    final AttributeSet attrs = fromXml(context, R.xml.fraction_handler_with_attr_equals_parent_frac);
    
    final FractionHandlerTestTargetBase target = new HandlerUsingBaseMultiplier(context, attrs);
    
    final float expectedValue = getParentFractionMultipliedBy(1);
    assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassPassesCorrectData_attributePresent_attributeIsPrntFraction_handlerHasNoMultiplier() {
    final AttributeSet attrs = fromXml(context, R.xml.fraction_handler_with_attr_equals_parent_frac);
    
    final FractionHandlerTestTargetBase target = new HandlerUsingNoMultiplier(context, attrs);
    
    final float expectedValue = getParentFractionMultipliedBy(1);
    assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassPassesCorrectData_attributePresent_attributeIsPrntFraction_handlerHasPrntMultiplier() {
    final AttributeSet attrs = fromXml(context, R.xml.fraction_handler_with_attr_equals_parent_frac);
    
    final FractionHandlerTestTargetBase target = new HandlerUsingParentMultiplier(context, attrs);
    
    final float expectedValue = getParentFractionMultipliedBy(HandlerUsingParentMultiplier.MULTIPLIER);
    assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassNeverCallsMethod_attributeMissing_noDefaultPresent() {
    final AttributeSet attrs = fromXml(context, R.xml.fraction_handler_without_attr);
  
    final FractionHandlerTestTargetBase target = new HandlerUsingNoMultiplier(context, attrs);
  
    assertThat(target.getReceivedValue(), is(ReceivedValue.none()));
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassPassesCorrectData_attributeMissing_defaultToFractionUsingBaseFractionAndBaseMultiplier() {
    final AttributeSet attrs = fromXml(context, R.xml.fraction_handler_without_attr);
    
    final FractionHandlerTestTargetBase target = new WithDefaultToFractionUsingBaseFractionAndBaseMultiplier(
        context,
        attrs);
    
    final int multiplier = WithDefaultToFractionUsingBaseFractionAndBaseMultiplier.MULTIPLIER;
    final float expectedValue = getBaseFractionMultipliedBy(multiplier);
    assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassPassesCorrectData_attributeMissing_defaultToFractionUsingBaseFractionAndPrntMultiplier() {
    final AttributeSet attrs = fromXml(context, R.xml.fraction_handler_without_attr);
    
    final FractionHandlerTestTargetBase target = new WithDefaultToFractionUsingBaseFractionAndParentMultiplier(
        context,
        attrs);
    
    final float expectedValue = getBaseFractionMultipliedBy(1);
    assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassPassesCorrectData_attributeMissing_defaultToFractionUsingBaseFractionAndNoMultiplier() {
    final AttributeSet attrs = fromXml(context, R.xml.fraction_handler_without_attr);
    
    final FractionHandlerTestTargetBase target = new WithDefaultToFractionUsingBaseFractionAndNoMultiplier(
        context,
        attrs);
    
    final float expectedValue = getBaseFractionMultipliedBy(1);
    assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassPassesCorrectData_attributeMissing_defaultToFractionUsingPrntFractionAndBaseMultiplier() {
    final AttributeSet attrs = fromXml(context, R.xml.fraction_handler_without_attr);
    
    final FractionHandlerTestTargetBase target = new WithDefaultToFractionUsingParentFractionAndBaseMultiplier(
        context,
        attrs);
    
    final float expectedValue = getParentFractionMultipliedBy(1);
    assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassPassesCorrectData_attributeMissing_defaultToFractionUsingPrntFractionAndPrntMultiplier() {
    final AttributeSet attrs = fromXml(context, R.xml.fraction_handler_without_attr);
    
    final FractionHandlerTestTargetBase target = new WithDefaultToFractionUsingParentFractionAndParentMultiplier(
        context,
        attrs);
    
    final int multiplier = WithDefaultToFractionUsingParentFractionAndParentMultiplier.MULTIPLIER;
    final float expectedValue = getParentFractionMultipliedBy(multiplier);
    assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassPassesCorrectData_attributeMissing_defaultToFractionUsingPrntFractionAndNoMultiplier() {
    final AttributeSet attrs = fromXml(context, R.xml.fraction_handler_without_attr);
    
    final FractionHandlerTestTargetBase target = new WithDefaultToFractionUsingParentFractionAndNoMultiplier(
        context,
        attrs);
    
    final float expectedValue = getParentFractionMultipliedBy(1);
    assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassCallsMethod_attributeMissing_defaultToNullPresent() {
    final AttributeSet attrs = AttributeSetSupplier.fromXml(context, R.xml.fraction_handler_without_attr);
    
    final FractionHandlerTestTargetBase target = new WithDefaultToNull(context, attrs);
    
    assertThat(target.getReceivedValue(), is(ReceivedValue.<Float>of(null)));
  }
  
  private float getBaseFractionMultipliedBy(final int i) {
    return context.getResources().getFraction(R.fraction.base_fraction_for_testing, i, 0);
  }
  
  private float getParentFractionMultipliedBy(final int i) {
    return context.getResources().getFraction(R.fraction.parent_fraction_for_testing, 0, i);
  }
}