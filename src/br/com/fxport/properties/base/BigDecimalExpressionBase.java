package br.com.fxport.properties.base;

import java.math.BigDecimal;
import java.util.Locale;

import com.sun.javafx.binding.StringFormatter;

import br.com.fxport.interfaces.InterfaceBigDecimalBinding;
import br.com.fxport.interfaces.InterfaceBigDecimalExpression;
import br.com.fxport.interfaces.ObservableBigDecimalValue;
import br.com.fxport.util.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.StringBinding;

public abstract class BigDecimalExpressionBase implements InterfaceBigDecimalExpression {

    /**
     * Returns an {@code BigDecimalExpressionBase} that wraps a
     * {@link javafx.beans.value.ObservableBigDecimalValue}. If the
     * {@code ObservableBigDecimalValue} is already an instance of
     * {@code BigDecimalExpressionBase}, it will be returned. Otherwise a new
     * {@link br.com.fxport.interfaces.InterfaceBigDecimalBinding} is created that is bound to
     * the {@code ObservableBigDecimalValue}.
     *
     * @param value
     *            The source {@code ObservableBigDecimalValue}
     * @return An {@code BigDecimalExpressionBase} that wraps the
     *         {@code ObservableBigDecimalValue} if necessary
     * @throws NullPointerException
     *             if {@code value} is {@code null}
     */
    public static <S extends BigDecimal> BigDecimalExpressionBase bigDecimalExpression(
            final ObservableBigDecimalValue value) {
        if (value == null) {
            throw new NullPointerException("Value must be specified.");
        }
        BigDecimalExpressionBase result = (BigDecimalExpressionBase) 
        		((value instanceof BigDecimalExpressionBase) ? value : null);
        if (result != null) {
            return result;
        } else {
            throw new IllegalArgumentException("Unsupported Type");
        }
    }

    @Override
    public InterfaceBigDecimalBinding add(final ObservableBigDecimalValue other) {
        return Bindings.add(this, other);
    }

    @Override
    public InterfaceBigDecimalBinding subtract(final ObservableBigDecimalValue other) {
        return Bindings.subtract(this, other);
    }

    @Override
    public InterfaceBigDecimalBinding multiply(final ObservableBigDecimalValue other) {
        return Bindings.multiply(this, other);
    }

    @Override
    public InterfaceBigDecimalBinding divide(final ObservableBigDecimalValue other) {
        return Bindings.divide(this, other);
    }

    // ===============================================================
    // IsEqualTo

    @Override
    public BooleanBinding isEqualTo(final ObservableBigDecimalValue other) {
        return Bindings.equal(this, other);
    }

    @Override
    public BooleanBinding isEqualTo(final double other) {
        return Bindings.equal(this, other);
    }

    @Override
    public BooleanBinding isEqualTo(final float other) {
        return Bindings.equal(this, other);
    }

    @Override
    public BooleanBinding isEqualTo(final long other) {
        return Bindings.equal(this, other);
    }

    @Override
    public BooleanBinding isEqualTo(final int other) {
        return Bindings.equal(this, other);
    }

    // ===============================================================
    // IsNotEqualTo

    @Override
    public BooleanBinding isNotEqualTo(final ObservableBigDecimalValue other) {
        return Bindings.notEqual(this, other);
    }

    @Override
    public BooleanBinding isNotEqualTo(final double other) {
        return Bindings.notEqual(this, other);
    }

    @Override
    public BooleanBinding isNotEqualTo(final float other) {
        return Bindings.notEqual(this, other);
    }

    @Override
    public BooleanBinding isNotEqualTo(final long other) {
        return Bindings.notEqual(this, other);
    }

    @Override
    public BooleanBinding isNotEqualTo(final int other) {
        return Bindings.notEqual(this, other);
    }

    

    // ===============================================================
    // IsGreaterThan

    @Override
    public BooleanBinding greaterThan(final ObservableBigDecimalValue other) {
        return Bindings.greaterThan(this, other);
    }

    @Override
    public BooleanBinding greaterThan(final double other) {
        return Bindings.greaterThan(this, other);
    }

    @Override
    public BooleanBinding greaterThan(final float other) {
        return Bindings.greaterThan(this, other);
    }

    @Override
    public BooleanBinding greaterThan(final long other) {
        return Bindings.greaterThan(this, other);
    }

    @Override
    public BooleanBinding greaterThan(final int other) {
        return Bindings.greaterThan(this, other);
    }

    // ===============================================================
    // IsLesserThan

    @Override
    public BooleanBinding lessThan(final ObservableBigDecimalValue other) {
        return Bindings.lessThan(this, other);
    }

    @Override
    public BooleanBinding lessThan(final double other) {
        return Bindings.lessThan(this, other);
    }

    @Override
    public BooleanBinding lessThan(final float other) {
        return Bindings.lessThan(this, other);
    }

    @Override
    public BooleanBinding lessThan(final long other) {
        return Bindings.lessThan(this, other);
    }

    @Override
    public BooleanBinding lessThan(final int other) {
        return Bindings.lessThan(this, other);
    }

    // ===============================================================
    // IsGreaterThanOrEqualTo

    @Override
    public BooleanBinding greaterThanOrEqualTo(final ObservableBigDecimalValue other) {
        return Bindings.greaterThanOrEqual(this, other);
    }

    @Override
    public BooleanBinding greaterThanOrEqualTo(final double other) {
        return Bindings.greaterThanOrEqual(this, other);
    }

    @Override
    public BooleanBinding greaterThanOrEqualTo(final float other) {
        return Bindings.greaterThanOrEqual(this, other);
    }

    @Override
    public BooleanBinding greaterThanOrEqualTo(final long other) {
        return Bindings.greaterThanOrEqual(this, other);
    }

    @Override
    public BooleanBinding greaterThanOrEqualTo(final int other) {
        return Bindings.greaterThanOrEqual(this, other);
    }

    // ===============================================================
    // IsLessThanOrEqualTo

    @Override
    public BooleanBinding lessThanOrEqualTo(final ObservableBigDecimalValue other) {
        return Bindings.lessThanOrEqual(this, other);
    }

    @Override
    public BooleanBinding lessThanOrEqualTo(final double other) {
        return Bindings.lessThanOrEqual(this, other);
    }

    @Override
    public BooleanBinding lessThanOrEqualTo(final float other) {
        return Bindings.lessThanOrEqual(this, other);
    }

    @Override
    public BooleanBinding lessThanOrEqualTo(final long other) {
        return Bindings.lessThanOrEqual(this, other);
    }

    @Override
    public BooleanBinding lessThanOrEqualTo(final int other) {
        return Bindings.lessThanOrEqual(this, other);
    }

    // ===============================================================
    // String conversions

    @Override
    public StringBinding asString() {
        return (StringBinding) StringFormatter.convert(this);
    }

    @Override
    public StringBinding asString(String format) {
        return (StringBinding) Bindings.format(format, this);
    }

    @Override
    public StringBinding asString(Locale locale, String format) {
        return (StringBinding) Bindings.format(locale, format, this);
    }
    
    
}


