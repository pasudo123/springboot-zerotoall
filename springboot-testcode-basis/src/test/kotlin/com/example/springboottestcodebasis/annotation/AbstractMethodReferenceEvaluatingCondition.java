package com.example.springboottestcodebasis.annotation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.function.Function;

abstract class AbstractMethodReferenceEvaluatingCondition implements ExecutionCondition {

    private static final Log logger = LogFactory.getLog(AbstractMethodReferenceEvaluatingCondition.class);

    protected <A extends Annotation> ConditionEvaluationResult evaluationAnnotation(
            Function<A, String> methodReferenceNameExtractor,
            ExtensionContext context
    ) {

        Assert.state(context.getElement().isPresent(), "No AnnotatedElement");

        final AnnotatedElement element = context.getElement().get();
        final Annotation annotation = element.getAnnotation((Class<A>) EnabledIfMethodReference.class);
        final String methodReferenceName = methodReferenceNameExtractor.apply((A) annotation);
        final Class clazz = context.getRequiredTestClass();

        try {
            final Method method = clazz.getDeclaredMethod(methodReferenceName);
            final Object instance = clazz.getDeclaredConstructor().newInstance();
            final Boolean executePossible = (Boolean) method.invoke(instance);

            if (executePossible) {
                return ConditionEvaluationResult.enabled("수행한다.");
            }

        } catch(Exception e) {
            return ConditionEvaluationResult.disabled("error : " + e.getMessage());
        }

        return ConditionEvaluationResult.disabled("수행하지 않는다.");
    }
}
