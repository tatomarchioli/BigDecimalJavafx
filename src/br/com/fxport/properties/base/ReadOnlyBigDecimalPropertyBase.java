package br.com.fxport.properties.base;

import java.math.BigDecimal;

import com.sun.javafx.binding.ExpressionHelper;

import br.com.fxport.properties.ReadOnlyBigDecimalProperty;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;

public abstract class ReadOnlyBigDecimalPropertyBase extends ReadOnlyBigDecimalProperty {

    ExpressionHelper<BigDecimal> helper;

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
     * This method needs to be called, if the value of this property changes.
     */
    protected void fireValueChangedEvent() {
        ExpressionHelper.fireValueChangedEvent(helper);
    }

}

