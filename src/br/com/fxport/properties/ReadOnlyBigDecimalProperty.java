package br.com.fxport.properties;

import java.math.BigDecimal;

import br.com.fxport.properties.base.ReadOnlyBigDecimalPropertyBase;
import javafx.beans.InvalidationListener;
import javafx.beans.WeakInvalidationListener;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectPropertyBase;
import javafx.beans.property.ReadOnlyProperty;

public abstract class ReadOnlyBigDecimalProperty extends BigDecimalExpression
 implements ReadOnlyProperty<BigDecimal> {
	 /**
     * The constructor of {@code ReadOnlyBigDecimalProperty}.
     */
    public ReadOnlyBigDecimalProperty() {
    }

    /**
     * Returns a string representation of this {@code ReadOnlyBigDecimalProperty} object.
     * @return a string representation of this {@code ReadOnlyBigDecimalProperty} object.
     */
    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder(
                "ReadOnlyBigDecimalProperty [");
        if (bean != null) {
            result.append("bean: ").append(bean).append(", ");
        }
        if ((name != null) && !name.equals("")) {
            result.append("name: ").append(name).append(", ");
        }
        result.append("value: ").append(get()).append("]");
        return result.toString();
    }

    /**
     * Returns a {@code ReadOnlyBigDecimalProperty} that wraps a
     * {@link javafx.beans.property.ReadOnlyProperty}. If the
     * {@code ReadOnlyProperty} is already a {@code ReadOnlyBigDecimalProperty}, it
     * will be returned. Otherwise a new
     * {@code ReadOnlyBigDecimalProperty} is created that is bound to
     * the {@code ReadOnlyProperty}.
     *
     * Note: null values will be interpreted as 0.0
     *
     * @param property
     *            The source {@code ReadOnlyProperty}
     * @return A {@code ReadOnlyBigDecimalProperty} that wraps the
     *         {@code ReadOnlyProperty} if necessary
     * @throws NullPointerException
     *             if {@code property} is {@code null}
     * @since JavaFX 8.0
     */
    public static <T extends BigDecimal> ReadOnlyBigDecimalProperty readOnlyBigDecimalProperty(final ReadOnlyProperty<T> property) {
        if (property == null) {
            throw new NullPointerException("Property cannot be null");
        }

        return property instanceof ReadOnlyBigDecimalProperty ? (ReadOnlyBigDecimalProperty) property:
           new ReadOnlyBigDecimalPropertyBase() {
            private boolean valid = true;
            private final InvalidationListener listener = observable -> {
                if (valid) {
                    valid = false;
                    fireValueChangedEvent();
                }
            };

            {
                property.addListener(new WeakInvalidationListener(listener));
            }

            @Override
            public BigDecimal get() {
                valid = true;
                final T value = property.getValue();
                return value == null ? BigDecimal.ZERO : value;
            }

            @Override
            public Object getBean() {
                return null; // Virtual property, no bean
            }

            @Override
            public String getName() {
                return property.getName();
            }
        };
    }

    /**
     * Creates a {@link javafx.beans.property.ReadOnlyObjectProperty} that holds the value
     * of this {@code ReadOnlyBigDecimalProperty}. If the
     * value of this {@code ReadOnlyBigDecimalProperty} changes, the value of the
     * {@code ReadOnlyObjectProperty} will be updated automatically.
     *
     * @return the new {@code ReadOnlyObjectProperty}
     * @since JavaFX 8.0
     */
    @Override
    public ReadOnlyObjectProperty<BigDecimal> asObject() {
        return new ReadOnlyObjectPropertyBase<BigDecimal>() {

            private boolean valid = true;
            private final InvalidationListener listener = observable -> {
                if (valid) {
                    valid = false;
                    fireValueChangedEvent();
                }
            };

            {
                ReadOnlyBigDecimalProperty.this.addListener(new WeakInvalidationListener(listener));
            }

            @Override
            public Object getBean() {
                return null; // Virtual property, does not exist on a bean
            }

            @Override
            public String getName() {
                return ReadOnlyBigDecimalProperty.this.getName();
            }

            @Override
            public BigDecimal get() {
                valid = true;
                return ReadOnlyBigDecimalProperty.this.getValue();
            }
        };
    };



}
