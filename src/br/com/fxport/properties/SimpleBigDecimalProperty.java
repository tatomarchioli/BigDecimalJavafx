package br.com.fxport.properties;

import java.math.BigDecimal;

import br.com.fxport.properties.base.BigDecimalPropertyBase;

public class SimpleBigDecimalProperty extends BigDecimalPropertyBase {

    private static final Object DEFAULT_BEAN = null;
    private static final String DEFAULT_NAME = "";

    private final Object bean;
    private final String name;

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getBean() {
        return bean;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * The constructor of {@code DoubleProperty}
     */
    public SimpleBigDecimalProperty() {
        this(DEFAULT_BEAN, DEFAULT_NAME);
    }

    /**
     * The constructor of {@code DoubleProperty}
     *
     * @param initialValue
     *            the initial value of the wrapped value
     */
    public SimpleBigDecimalProperty(BigDecimal initialValue) {
        this(DEFAULT_BEAN, DEFAULT_NAME, initialValue);
    }

    /**
     * The constructor of {@code DoubleProperty}
     *
     * @param bean
     *            the bean of this {@code DoubleProperty}
     * @param name
     *            the name of this {@code DoubleProperty}
     */
    public SimpleBigDecimalProperty(Object bean, String name) {
        this.bean = bean;
        this.name = (name == null) ? DEFAULT_NAME : name;
    }

    /**
     * The constructor of {@code DoubleProperty}
     *
     * @param bean
     *            the bean of this {@code DoubleProperty}
     * @param name
     *            the name of this {@code DoubleProperty}
     * @param initialValue
     *            the initial value of the wrapped value
     */
    public SimpleBigDecimalProperty(Object bean, String name, BigDecimal initialValue) {
        super(initialValue);
        this.bean = bean;
        this.name = (name == null) ? DEFAULT_NAME : name;
    }

}

