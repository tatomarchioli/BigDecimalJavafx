package br.com.fxport.properties;

import java.math.BigDecimal;

import br.com.fxport.interfaces.ObservableBigDecimalValue;
import br.com.fxport.properties.base.BigDecimalExpressionBase;
import br.com.fxport.util.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.binding.ObjectExpression;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class BigDecimalExpression extends BigDecimalExpressionBase implements ObservableBigDecimalValue{

    @Override
    public int intValue() {
        return get().intValue();
    }

    @Override
    public long longValue() {
        return get().longValue();
    }

    @Override
    public float floatValue() {
        return get().floatValue();
    }

    @Override
    public double doubleValue() {
        return get().doubleValue();
    }

    @Override
    public BigDecimal getValue() {
        return get();
    }

    /**
     * Returns a {@code BigDecimalExpression} that wraps a
     * {@link javafx.beans.value.ObservableDoubleValue}. If the
     * {@code ObservableDoubleValue} is already a {@code BigDecimalExpression}, it
     * will be returned. Otherwise a new
     * {@link br.com.fxport.properties.BigDecimalBinding} is created that is bound to
     * the {@code ObservableDoubleValue}.
     *
     * @param value
     *            The source {@code ObservableDoubleValue}
     * @return A {@code BigDecimalExpression} that wraps the
     *         {@code ObservableDoubleValue} if necessary
     * @throws NullPointerException
     *             if {@code value} is {@code null}
     */
    public static BigDecimalExpression bigDecimalExpression(
            final ObservableBigDecimalValue value) {
        if (value == null) {
            throw new NullPointerException("Value must be specified.");
        }
        return (value instanceof BigDecimalExpression) ? (BigDecimalExpression) value
                : new BigDecimalBinding() {
                    {
                        super.bind(value);
                    }

                    @Override
                    public void dispose() {
                        super.unbind(value);
                    }

                    @Override
                    protected BigDecimal computeValue() {
                        return value.get();
                    }

                    @Override
                    public ObservableList<ObservableBigDecimalValue> getDependencies() {
                        return FXCollections.singletonObservableList(value);
                    }

                };
    }

    /**
     * Returns a {@code BigDecimalExpression} that wraps an
     * {@link javafx.beans.value.ObservableValue}. If the
     * {@code ObservableValue} is already a {@code BigDecimalExpression}, it
     * will be returned. Otherwise a new
     * {@link br.com.fxport.properties.BigDecimalBinding} is created that is bound to
     * the {@code ObservableValue}.
     *
     * <p>
     * Note: this method can be used to convert an {@link ObjectExpression} or
     * {@link javafx.beans.property.ObjectProperty} of specific number type to BigDecimalExpression, which
     * is essentially an {@code ObservableValue<Number>}. See sample below.
     *
     * <blockquote><pre>
     *   DoubleProperty doubleProperty = new SimpleDoubleProperty(1.0);
     *   ObjectProperty&lt;Double&gt; objectProperty = new SimpleObjectProperty&lt;&gt;(2.0);
     *   BooleanBinding binding = doubleProperty.greaterThan(BigDecimalExpression.BigDecimalExpression(objectProperty));
     * </pre></blockquote>
     *
     * Note: null values will be interpreted as 0.0
     *
     * @param value
     *            The source {@code ObservableValue}
     * @return A {@code BigDecimalExpression} that wraps the
     *         {@code ObservableValue} if necessary
     * @throws NullPointerException
     *             if {@code value} is {@code null}
     * @since JavaFX 8.0
     */
    public static <T extends BigDecimal> BigDecimalExpression bigDecimalExpression(final ObservableValue<T> value) {
        if (value == null) {
            throw new NullPointerException("Value must be specified.");
        }
        return (value instanceof BigDecimalExpression) ? (BigDecimalExpression) value
                : new BigDecimalBinding() {
            {
                super.bind(value);
            }

            @Override
            public void dispose() {
                super.unbind(value);
            }

            @Override
            protected BigDecimal computeValue() {
                final T val = value.getValue();
                return val == null ? BigDecimal.ZERO : val;
            }

            @Override
            public ObservableList<ObservableValue<T>> getDependencies() {
                return FXCollections.singletonObservableList(value);
            }
        };
    }

    @Override
    public BigDecimalBinding negate() {
        return (BigDecimalBinding) Bindings.negate(this);
    }

    @Override
    public BigDecimalBinding add(final ObservableBigDecimalValue other) {
        return (BigDecimalBinding) Bindings.add(this, other);
    }

    @Override
    public BigDecimalBinding add(final double other) {
        return Bindings.add(this, other);
    }

    @Override
    public BigDecimalBinding add(final float other) {
        return (BigDecimalBinding) Bindings.add(this, other);
    }

    @Override
    public BigDecimalBinding add(final long other) {
        return (BigDecimalBinding) Bindings.add(this, other);
    }

    @Override
    public BigDecimalBinding add(final int other) {
        return (BigDecimalBinding) Bindings.add(this, other);
    }

    @Override
    public BigDecimalBinding subtract(final ObservableBigDecimalValue other) {
        return (BigDecimalBinding) Bindings.subtract(this, other);
    }

    @Override
    public BigDecimalBinding subtract(final double other) {
        return Bindings.subtract(this, other);
    }

    @Override
    public BigDecimalBinding subtract(final float other) {
        return (BigDecimalBinding) Bindings.subtract(this, other);
    }

    @Override
    public BigDecimalBinding subtract(final long other) {
        return (BigDecimalBinding) Bindings.subtract(this, other);
    }

    @Override
    public BigDecimalBinding subtract(final int other) {
        return (BigDecimalBinding) Bindings.subtract(this, other);
    }

    @Override
    public BigDecimalBinding multiply(final ObservableBigDecimalValue other) {
        return (BigDecimalBinding) Bindings.multiply(this, other);
    }

    @Override
    public BigDecimalBinding multiply(final double other) {
        return Bindings.multiply(this, other);
    }

    @Override
    public BigDecimalBinding multiply(final float other) {
        return (BigDecimalBinding) Bindings.multiply(this, other);
    }

    @Override
    public BigDecimalBinding multiply(final long other) {
        return (BigDecimalBinding) Bindings.multiply(this, other);
    }

    @Override
    public BigDecimalBinding multiply(final int other) {
        return (BigDecimalBinding) Bindings.multiply(this, other);
    }

    @Override
    public BigDecimalBinding divide(final ObservableBigDecimalValue other) {
        return (BigDecimalBinding) Bindings.divide(this, other);
    }

    @Override
    public BigDecimalBinding divide(final double other) {
        return Bindings.divide(this, other);
    }

    @Override
    public BigDecimalBinding divide(final float other) {
        return (BigDecimalBinding) Bindings.divide(this, other);
    }

    @Override
    public BigDecimalBinding divide(final long other) {
        return (BigDecimalBinding) Bindings.divide(this, other);
    }

    @Override
    public BigDecimalBinding divide(final int other) {
        return (BigDecimalBinding) Bindings.divide(this, other);
    }

    /**
     * Creates an {@link javafx.beans.binding.ObjectExpression} that holds the value
     * of this {@code BigDecimalExpression}. If the
     * value of this {@code BigDecimalExpression} changes, the value of the
     * {@code ObjectExpression} will be updated automatically.
     *
     * @return the new {@code ObjectExpression}
     * @since JavaFX 8.0
     */
    public ObjectExpression<BigDecimal> asObject() {
        return new ObjectBinding<BigDecimal>() {
            {
                bind(BigDecimalExpression.this);
            }

            @Override
            public void dispose() {
                unbind(BigDecimalExpression.this);
            }

            @Override
            protected BigDecimal computeValue() {
                return BigDecimalExpression.this.getValue();
            }
        };
    }}
