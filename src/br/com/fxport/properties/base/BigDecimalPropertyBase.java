package br.com.fxport.properties.base;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;

import com.sun.javafx.binding.ExpressionHelper;

import br.com.fxport.interfaces.ObservableBigDecimalValue;
import br.com.fxport.properties.BigDecimalBinding;
import br.com.fxport.properties.BigDecimalProperty;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public abstract class BigDecimalPropertyBase extends BigDecimalProperty{

    private BigDecimal value;
    private ObservableBigDecimalValue observable = null;
    private InvalidationListener listener = null;
    private boolean valid = true;
    private ExpressionHelper<BigDecimal> helper = null;

    /**
     * The constructor of the {@code BigDecimalPropertyBase}.
     */
    public BigDecimalPropertyBase() {
    }

    /**
     * The constructor of the {@code BigDecimalPropertyBase}.
     *
     * @param initialValue
     *            the initial value of the wrapped value
     */
    public BigDecimalPropertyBase(BigDecimal initialValue) {
        this.value = initialValue;
    }

    @Override
    public void addListener(InvalidationListener listener) {
        helper = ExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        helper = ExpressionHelper.removeListener(helper, listener);
    }

    @Override
    public void addListener(ChangeListener<? super BigDecimal> listener) {
        helper = ExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(ChangeListener<? super BigDecimal> listener) {
        helper = ExpressionHelper.removeListener(helper, listener);
    }

    /**
     * Sends notifications to all attached
     * {@link javafx.beans.InvalidationListener InvalidationListeners} and
     * {@link javafx.beans.value.ChangeListener ChangeListeners}.
     *
     * This method is called when the value is changed, either manually by
     * calling {@link #set(BigDecimal)} or in case of a bound property, if the
     * binding becomes invalid.
     */
    protected void fireValueChangedEvent() {
        ExpressionHelper.fireValueChangedEvent(helper);
    }

    private void markInvalid() {
        if (valid) {
            valid = false;
            invalidated();
            fireValueChangedEvent();
        }
    }

    /**
     * The method {@code invalidated()} can be overridden to receive
     * invalidation notifications. This is the preferred option in
     * {@code Objects} defining the property, because it requires less memory.
     *
     * The default implementation is empty.
     */
    protected void invalidated() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal get() {
        valid = true;
        return observable == null ? value : observable.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(BigDecimal newValue) {
        if (isBound()) {
            throw new java.lang.RuntimeException((getBean() != null && getName() != null ?
                    getBean().getClass().getSimpleName() + "." + getName() + " : ": "") + "A bound value cannot be set.");
        }
        if (value != newValue) {
            value = newValue;
            markInvalid();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBound() {
        return observable != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bind(final ObservableValue<? extends BigDecimal> rawObservable) {
        if (rawObservable == null) {
            throw new NullPointerException("Cannot bind to null");
        }

        ObservableBigDecimalValue newObservable;
        if (rawObservable instanceof ObservableBigDecimalValue) {
            final ObservableBigDecimalValue numberValue = (ObservableBigDecimalValue)rawObservable;
            newObservable = new BigDecimalBinding() {
                {
                    super.bind(rawObservable);
                }

                @Override
                protected BigDecimal computeValue() {
                    return numberValue.get();
                }
            };
        } else {
            newObservable = new BigDecimalBinding() {
                {
                    super.bind(rawObservable);
                }

                @Override
                protected BigDecimal computeValue() {
                    final BigDecimal value = rawObservable.getValue();
                    return (value == null)? BigDecimal.ZERO : value;
                }
            };
        }

        if (!newObservable.equals(observable)) {
            unbind();
            observable = newObservable;
            if (listener == null) {
                listener = new Listener(this);
            }
            observable.addListener(listener);
            markInvalid();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unbind() {
        if (observable != null) {
            value = observable.get();
            observable.removeListener(listener);
            observable = null;
        }
    }

    /**
     * Returns a string representation of this {@code BigDecimalPropertyBase} object.
     * @return a string representation of this {@code BigDecimalPropertyBase} object.
     */
    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder("BigDecimalProperty [");
        if (bean != null) {
            result.append("bean: ").append(bean).append(", ");
        }
        if ((name != null) && (!name.equals(""))) {
            result.append("name: ").append(name).append(", ");
        }
        if (isBound()) {
            result.append("bound, ");
            if (valid) {
                result.append("value: ").append(get());
            } else {
                result.append("invalid");
            }
        } else {
            result.append("value: ").append(get());
        }
        result.append("]");
        return result.toString();
    }

    private static class Listener implements InvalidationListener {

        private final WeakReference<BigDecimalPropertyBase> wref;

        public Listener(BigDecimalPropertyBase ref) {
            this.wref = new WeakReference<>(ref);
        }

        @Override
        public void invalidated(Observable observable) {
            BigDecimalPropertyBase ref = wref.get();
            if (ref == null) {
                observable.removeListener(this);
            } else {
                ref.markInvalid();
            }
        }
    }
}
