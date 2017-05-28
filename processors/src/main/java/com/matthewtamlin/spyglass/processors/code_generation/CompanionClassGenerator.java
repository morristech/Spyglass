package com.matthewtamlin.spyglass.processors.code_generation;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.Set;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static com.matthewtamlin.spyglass.processors.annotation_utils.CallHandlerAnnotationUtil.hasCallHandlerAnnotation;
import static com.matthewtamlin.spyglass.processors.annotation_utils.DefaultAnnotationUtil.hasDefaultAnnotation;
import static com.matthewtamlin.spyglass.processors.annotation_utils.UseAnnotationUtil.hasUseAnnotation;
import static com.matthewtamlin.spyglass.processors.annotation_utils.ValueHandlerAnnotationUtil.hasValueHandlerAnnotation;
import static javax.lang.model.element.Modifier.PUBLIC;

public class CompanionClassGenerator {
	private final ClassName targetClass;

	private final Elements elementUtil;

	private CompanionClassGenerator(final Builder builder) {
		checkNotNull(builder, "Argument \'builder\' cannot be null.");

		this.targetClass = checkNotNull(builder.targetClass, "Builder target class cannot be null at instantiation.");
		this.elementUtil = checkNotNull(builder.elementUtil, "Builder element util cannot be null at instantiation.");
	}

	public JavaFile generateCompanionFromElements(final Set<ExecutableElement> methods) {
		final Set<TypeSpec> callerSpecs = generateCallerSpecs(methods);

		return null; //TODO
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private ClassName targetClass;

		private Elements elementUtil;

		private Builder() {}

		public Builder withTargetClass(final String packageName, final String simpleClassName) {
			targetClass = ClassName.get(packageName, simpleClassName);
			return this;
		}

		public Builder withElementUtil(final Elements elementUtil) {
			this.elementUtil = elementUtil;
			return this;
		}

		public CompanionClassGenerator build() {
			return new CompanionClassGenerator(this);
		}
	}

	private TypeSpec generateCallerSpec(final ExecutableElement method) {
		if (hasCallHandlerAnnotation(method)) {
			return generateCallerForCallHandlerCase(method);

		} else if (hasValueHandlerAnnotation(method)) {
			return hasDefaultAnnotation(method) ?
					generateCallerForValueHandlerWithDefaultCase(method) :
					generateCallerForValueHandlerWithoutDefaultCase(method);

		} else {
			throw new IllegalArgumentException("Argument \'method\' has neither a value handler annotation nor a call" +
					" handler annotation.");
		}
	}

	private TypeSpec generateCallerForCallHandlerCase(final ExecutableElement e) {
		/* General anonymous caller structure

		new Caller {
			public void callMethod(T target, Context context, TypedArray attrs) {
				if (shouldCallMethod(attrs) {
					// Variable implementation, generated by InvocationLiteralGenerator
					target.<generated method call>
				}
			}

			public boolean shouldCallMethod(TypedArray attrs) {
				// Variable implementation, generated by CallerComponentGenerator class
				return <generated boolean>
			}
		}
		 */

		final MethodSpec shouldCallMethod = buildShouldCallMethodSpecFor(getCallHandlerAnnotation(e));

		final MethodSpec callMethod = getCallMethodPartialSpec()
				.addCode(CodeBlock
						.builder()
						.addStatement("if ($N(attrs))", shouldCallMethod)
						.addStatement("$L.$L", "target", buildInvocationLiteralFor(e))
						.endControlFlow()
						.build())
				.build();

		return getCallerPartialSpec()
				.addMethod(callMethod)
				.addMethod(shouldCallMethod)
				.build();
	}

	private TypeSpec generateCallerForValueHandlerWithoutDefaultCase(final ExecutableElement e) {
		/* General caller structure without default

		new Caller {
			public void callMethod(T target, Context context, TypedArray attrs) {
				if (valueIsAvailable(attrs) {
					V value = getValue(attrs);
					target.someMethod(use annotation values);
				}
			}

			public void valueIsAvailable(TypedArray attrs) {
				// Variable implementation, generated by CallerComponentGenerator class
			}

			public V getValue(TypedArray attrs) {

			}
		}

		 */

		final MethodSpec valueIsAvailable = buildValueIsAvailableSpecFor(getValueHandlerAnnotation(e));

		final TypeName nonUseParamType = getTypeNameOfNonUseParameter(e);

		final MethodSpec getValue = buildGetValueSpecFor(getValueHandlerAnnotation(e));

		final MethodSpec callMethod = getCallMethodPartialSpec()
				.addCode(CodeBlock
						.builder()
						.addStatement("if ($N(attrs))", valueIsAvailable)
						.addStatement("$T value = ($T) $N(attrs)", nonUseParamType, nonUseParamType, getValue)
						.addStatement("$L.$L", "target", buildInvocationLiteralFor(e, "value"))
						.endControlFlow()
						.build())
				.build();

		return getCallerPartialSpec()
				.addMethod(callMethod)
				.addMethod(valueIsAvailable)
				.addMethod(getValue)
				.build();
	}

	private TypeSpec generateCallerForValueHandlerWithDefaultCase(final ExecutableElement e) {
		/* General caller structure with default

		new Caller {
			public void callMethod(T target, Context context, TypedArray attrs) {
				if (valueIsAvailable(attrs) {
					final V value = getValue(attrs);
					target.someMethod('value' and use annotation literals);
				} else {
					final V value = getDefault(attrs);
					target.someMethod('value' and use annotation literals);
				}
			}

			public void valueIsAvailable(TypedArray attrs) {
				// Variable implementation, generated by CallerComponentGenerator class
			}

			public V getValue(TypedArray attrs) {

			}

			public V getDefault(Context context, TypedArray attrs) {

			}
		}

		 */

		final MethodSpec valueIsAvailable = buildValueIsAvailableSpecFor(getValueHandlerAnnotation(e));

		final TypeName nonUseParamType = getTypeNameOfNonUseParameter(e);

		final MethodSpec getValue = buildGetValueSpecFor(getValueHandlerAnnotation(e));
		final MethodSpec getDefault = buildGetDefaultValueSpecFor(getDefaultAnnotation(e));

		final MethodSpec callMethod = getCallMethodPartialSpec()
				.addCode(CodeBlock
						.builder()
						.addStatement("if ($N(attrs))", valueIsAvailable)
						.addStatement("$T value = ($T) $N(attrs)", nonUseParamType, nonUseParamType, getValue)
						.addStatement("$L.$L", "target", buildInvocationLiteralFor(e, "value"))
						.nextControlFlow("else")
						.addStatement("$T value = ($T) $N(attrs, context)",
								nonUseParamType,
								nonUseParamType,
								getDefault)
						.addStatement("$L.$L", "target", buildInvocationLiteralFor(e, "value"))
						.build())
				.build();

		return getCallerPartialSpec()
				.addMethod(callMethod)
				.addMethod(valueIsAvailable)
				.addMethod(getValue)
				.addMethod(getDefault)
				.build();
	}

	private TypeSpec.Builder getCallerPartialSpec() {
		final ClassName genericCaller = ClassName.get(CallerDef.PACKAGE, CallerDef.INTERFACE_NAME);
		final TypeName specificCaller = ParameterizedTypeName.get(genericCaller, targetClass);

		return TypeSpec
				.anonymousClassBuilder("")
				.addSuperinterface(specificCaller);
	}

	private MethodSpec.Builder getCallMethodPartialSpec() {
		return MethodSpec
				.methodBuilder(CallerDef.METHOD_NAME)
				.returns(void.class)
				.addModifiers(PUBLIC)
				.addParameter(targetClass, "target")
				.addParameter(AndroidClassNames.CONTEXT, "context")
				.addParameter(AndroidClassNames.TYPED_ARRAY, "attrs");
	}

	private TypeName getTypeNameOfNonUseParameter(final ExecutableElement e) {
		for (final VariableElement parameter : e.getParameters()) {
			if (!hasUseAnnotation(parameter)) {
				return ClassName.get(parameter.asType());
			}
		}

		throw new RuntimeException("No non-use argument found.");
	}
}