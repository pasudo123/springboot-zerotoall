package com.example.springboottestcodebasis.annotation;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExtensionContext;

class EnableIfMethodCondition extends AbstractMethodReferenceEvaluatingCondition {

    private static final Log logger = LogFactory.getLog(EnableIfMethodCondition.class);

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        return evaluationAnnotation(EnabledIfMethodReference::methodReferenceName, context);
    }
}
