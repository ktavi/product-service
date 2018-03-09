package com.example.product.data;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionException;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.math.BigDecimal;
import java.util.function.Function;


/**
 * Boolean function defined on a set of any ClientInfo objects represented by SpEL expression.
 */
class SpELClientInfoPredicate implements Function<ClientInfo, Boolean> {

    /**
     * We need any ClientInfo instance to validate SpEL expression against target context.
     */
    private static final ClientInfo STUB_INFO = new ClientInfo(BigDecimal.ZERO, BigDecimal.ZERO, Boolean.FALSE);
    private static final SpelExpressionParser SPEL_PARSER = new SpelExpressionParser();

    private final Expression expression;

    /**
     * @param expression SpEL expression that calculates a boolean value over ClientInfo fields.
     * @throws IllegalArgumentException if passed SpEL expression is not valid.
     */
    public SpELClientInfoPredicate(String expression) {
        this.expression = parseAndValidate(expression);
    }

    private Expression parseAndValidate(String rawExpression) {
        try {
            Expression expression = SPEL_PARSER.parseExpression(rawExpression);
            expression.getValue(STUB_INFO, Boolean.class);
            return expression;
        } catch (ExpressionException e) {
            throw new IllegalArgumentException("Provided SpEL expression is incorrect", e);
        }
    }

    @Override
    public Boolean apply(ClientInfo info) {
        return expression.getValue(info, Boolean.class);
    }
}
