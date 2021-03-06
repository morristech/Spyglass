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

package com.matthewtamlin.spyglass.processor.validation;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Result {
  public abstract boolean isSuccessful();
  
  public abstract String getDescription();
  
  public static Result createSuccessful() {
    return new AutoValue_Result(true, "Validation successful.");
  }
  
  public static Result createFailure(final String description, final Object... args) {
    return new AutoValue_Result(false, String.format(description, args));
  }
}