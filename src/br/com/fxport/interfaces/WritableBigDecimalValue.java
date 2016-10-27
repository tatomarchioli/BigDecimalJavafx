package br.com.fxport.interfaces;

import java.math.BigDecimal;

import javafx.beans.value.WritableValue;

public interface WritableBigDecimalValue extends WritableValue<BigDecimal> {
	 /**
     * Get the wrapped value.
     * Unlike {@link #getValue()},
     * this method returns primitive double.
     * Needs to be identical to {@link #getValue()}.
     *
     * @return The current value
     */
    BigDecimal get();

    /**
     * Set the wrapped value.
     * Unlike {@link #setValue(java.lang.Number) },
     * this method uses primitive double.
     *
     * @param value
     *            The new value
     */
    void set(BigDecimal value);

    /**
     * Set the wrapped value.
     * <p>
     * Note: this method should accept null without throwing an exception,
     * setting "0.0" instead.
     *
     * @param value
     *            The new value
     */
    @Override
    void setValue(BigDecimal value);
}
