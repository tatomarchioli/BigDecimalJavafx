package br.com.fxport.properties.base;

import java.math.BigDecimal;

import br.com.fxport.interfaces.ObservableBigDecimalValue;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;

public class BigDecimalConstant implements ObservableBigDecimalValue{
	private final BigDecimal value;

    private BigDecimalConstant(BigDecimal value) {
        this.value = value;
    }

    public static BigDecimalConstant valueOf(BigDecimal value) {
        return new BigDecimalConstant(value);
    }

    @Override
    public BigDecimal get() {
        return value;
    }

    @Override
    public BigDecimal getValue() {
        return value;
    }

    @Override
    public void addListener(InvalidationListener observer) {
        // no-op
    }

    @Override
    public void addListener(ChangeListener<? super BigDecimal> listener) {
        // no-op
    }

    @Override
    public void removeListener(InvalidationListener observer) {
        // no-op
    }

    @Override
    public void removeListener(ChangeListener<? super BigDecimal> listener) {
        // no-op
    }

    @Override
    public int intValue() {
        return value.intValue();
    }

    @Override
    public long longValue() {
        return value.longValue();
    }

    @Override
    public float floatValue() {
        return value.floatValue();
    }

    @Override
    public double doubleValue() {
        return value.doubleValue();
    }

	public static BigDecimalConstant valueOf(double op2) {
		return new BigDecimalConstant(BigDecimal.valueOf(op2));
	}
	
	public static BigDecimalConstant valueOf(int op2) {
		return new BigDecimalConstant(BigDecimal.valueOf(op2));
	}
	
	public static BigDecimalConstant valueOf(long op2) {
		return new BigDecimalConstant(BigDecimal.valueOf(op2));
	}
	
	public static BigDecimalConstant valueOf(float op2) {
		return new BigDecimalConstant(BigDecimal.valueOf(op2));
	}
}
