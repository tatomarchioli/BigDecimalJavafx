package br.com.fxport.interfaces;

import java.util.Locale;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.StringBinding;

public interface InterfaceBigDecimalExpression extends ObservableBigDecimalValue {

    // ===============================================================
    // Negation

    /**
     * Creates a new {@link br.com.fxport.interfaces.InterfaceBigDecimalBinding} that calculates
     * the negation of {@code NumberExpression}.
     *
     * @return the new {@code BigDecimalBinding}
     */
    InterfaceBigDecimalBinding negate();

    // ===============================================================
    // Plus

    /**
     * Creates a new {@link br.com.fxport.interfaces.InterfaceBigDecimalBinding} that calculates
     * the sum of this {@code NumberExpression} and another
     * {@link javafx.beans.value.ObservableBigDecimalValue}.
     *
     * @param other
     *            the second {@code ObservableBigDecimalValue}
     * @return the new {@code BigDecimalBinding}
     * @throws NullPointerException
     *             if the other {@code ObservableBigDecimalValue} is {@code null}
     */
    InterfaceBigDecimalBinding add(final ObservableBigDecimalValue other);

    /**
     * Creates a new {@link br.com.fxport.interfaces.InterfaceBigDecimalBinding} that calculates
     * the sum of this {@code NumberExpression} and a constant value.
     *
     * @param other
     *            the constant value
     * @return the new {@code BigDecimalBinding}
     */
    InterfaceBigDecimalBinding add(final double other);

    /**
     * Creates a new {@link br.com.fxport.interfaces.InterfaceBigDecimalBinding} that calculates
     * the sum of this {@code NumberExpression} and a constant value.
     *
     * @param other
     *            the constant value
     * @return the new {@code BigDecimalBinding}
     */
    InterfaceBigDecimalBinding add(final float other);

    /**
     * Creates a new {@link br.com.fxport.interfaces.InterfaceBigDecimalBinding} that calculates
     * the sum of this {@code NumberExpression} and a constant value.
     *
     * @param other
     *            the constant value
     * @return the new {@code BigDecimalBinding}
     */
    InterfaceBigDecimalBinding add(final long other);

    /**
     * Creates a new {@link br.com.fxport.interfaces.InterfaceBigDecimalBinding} that calculates
     * the sum of this {@code NumberExpression} and a constant value.
     *
     * @param other
     *            the constant value
     * @return the new {@code BigDecimalBinding}
     */
    InterfaceBigDecimalBinding add(final int other);

    // ===============================================================
    // Minus

    /**
     * Creates a new {@link br.com.fxport.interfaces.InterfaceBigDecimalBinding} that calculates
     * the difference of this {@code NumberExpression} and another
     * {@link javafx.beans.value.ObservableBigDecimalValue}.
     *
     * @param other
     *            the second {@code ObservableBigDecimalValue}
     * @return the new {@code BigDecimalBinding}
     * @throws NullPointerException
     *             if the other {@code ObservableBigDecimalValue} is {@code null}
     */
    InterfaceBigDecimalBinding subtract(final ObservableBigDecimalValue other);

    /**
     * Creates a new {@link br.com.fxport.interfaces.InterfaceBigDecimalBinding} that calculates
     * the difference of this {@code NumberExpression} and a constant value.
     *
     * @param other
     *            the constant value
     * @return the new {@code BigDecimalBinding}
     */
    InterfaceBigDecimalBinding subtract(final double other);

    /**
     * Creates a new {@link br.com.fxport.interfaces.InterfaceBigDecimalBinding} that calculates
     * the difference of this {@code NumberExpression} and a constant value.
     *
     * @param other
     *            the constant value
     * @return the new {@code BigDecimalBinding}
     */
    InterfaceBigDecimalBinding subtract(final float other);

    /**
     * Creates a new {@link br.com.fxport.interfaces.InterfaceBigDecimalBinding} that calculates
     * the difference of this {@code NumberExpression} and a constant value.
     *
     * @param other
     *            the constant value
     * @return the new {@code BigDecimalBinding}
     */
    InterfaceBigDecimalBinding subtract(final long other);

    /**
     * Creates a new {@link br.com.fxport.interfaces.InterfaceBigDecimalBinding} that calculates
     * the difference of this {@code NumberExpression} and a constant value.
     *
     * @param other
     *            the constant value
     * @return the new {@code BigDecimalBinding}
     */
    InterfaceBigDecimalBinding subtract(final int other);

    // ===============================================================
    // Times

    /**
     * Creates a new {@link br.com.fxport.interfaces.InterfaceBigDecimalBinding} that calculates
     * the product of this {@code NumberExpression} and another
     * {@link javafx.beans.value.ObservableBigDecimalValue}.
     *
     * @param other
     *            the second {@code ObservableBigDecimalValue}
     * @return the new {@code BigDecimalBinding}
     * @throws NullPointerException
     *             if the other {@code ObservableBigDecimalValue} is {@code null}
     */
    InterfaceBigDecimalBinding multiply(final ObservableBigDecimalValue other);

    /**
     * Creates a new {@link br.com.fxport.interfaces.InterfaceBigDecimalBinding} that calculates
     * the product of this {@code NumberExpression} and a constant value.
     *
     * @param other
     *            the constant value
     * @return the new {@code BigDecimalBinding}
     */
    InterfaceBigDecimalBinding multiply(final double other);

    /**
     * Creates a new {@link br.com.fxport.interfaces.InterfaceBigDecimalBinding} that calculates
     * the product of this {@code NumberExpression} and a constant value.
     *
     * @param other
     *            the constant value
     * @return the new {@code BigDecimalBinding}
     */
    InterfaceBigDecimalBinding multiply(final float other);

    /**
     * Creates a new {@link br.com.fxport.interfaces.InterfaceBigDecimalBinding} that calculates
     * the product of this {@code NumberExpression} and a constant value.
     *
     * @param other
     *            the constant value
     * @return the new {@code BigDecimalBinding}
     */
    InterfaceBigDecimalBinding multiply(final long other);

    /**
     * Creates a new {@link br.com.fxport.interfaces.InterfaceBigDecimalBinding} that calculates
     * the product of this {@code NumberExpression} and a constant value.
     *
     * @param other
     *            the constant value
     * @return the new {@code BigDecimalBinding}
     */
    InterfaceBigDecimalBinding multiply(final int other);

    // ===============================================================
    // DividedBy

    /**
     * Creates a new {@link br.com.fxport.interfaces.InterfaceBigDecimalBinding} that calculates
     * the division of this {@code NumberExpression} and another
     * {@link javafx.beans.value.ObservableBigDecimalValue}.
     *
     * @param other
     *            the second {@code ObservableBigDecimalValue}
     * @return the new {@code BigDecimalBinding}
     * @throws NullPointerException
     *             if the other {@code ObservableBigDecimalValue} is {@code null}
     */
    InterfaceBigDecimalBinding divide(final ObservableBigDecimalValue other);

    /**
     * Creates a new {@link br.com.fxport.interfaces.InterfaceBigDecimalBinding} that calculates
     * the division of this {@code NumberExpression} and a constant value.
     *
     * @param other
     *            the constant value
     * @return the new {@code BigDecimalBinding}
     */
    InterfaceBigDecimalBinding divide(final double other);

    /**
     * Creates a new {@link br.com.fxport.interfaces.InterfaceBigDecimalBinding} that calculates
     * the division of this {@code NumberExpression} and a constant value.
     *
     * @param other
     *            the constant value
     * @return the new {@code BigDecimalBinding}
     */
    InterfaceBigDecimalBinding divide(final float other);

    /**
     * Creates a new {@link br.com.fxport.interfaces.InterfaceBigDecimalBinding} that calculates
     * the division of this {@code NumberExpression} and a constant value.
     *
     * @param other
     *            the constant value
     * @return the new {@code BigDecimalBinding}
     */
    InterfaceBigDecimalBinding divide(final long other);

    /**
     * Creates a new {@link br.com.fxport.interfaces.InterfaceBigDecimalBinding} that calculates
     * the division of this {@code NumberExpression} and a constant value.
     *
     * @param other
     *            the constant value
     * @return the new {@code BigDecimalBinding}
     */
    InterfaceBigDecimalBinding divide(final int other);

    // ===============================================================
    // IsEqualTo

    /**
     * Creates a new {@link javafx.beans.binding.BooleanBinding} that holds {@code true}
     * if this and another {@link javafx.beans.value.ObservableBigDecimalValue} are
     * equal.
     * <p>
     * When comparing floating-point numbers it is recommended to use the
     * {@link #isEqualTo(ObservableBigDecimalValue, double) isEqualTo()} method that
     * allows a small tolerance.
     *
     * @param other
     *            the second {@code ObservableBigDecimalValue}
     * @return the new {@code BooleanBinding}
     * @throws NullPointerException
     *             if the other {@code ObservableBigDecimalValue} is {@code null}
     */
    BooleanBinding isEqualTo(final ObservableBigDecimalValue other);

    /**
     * Creates a new {@link javafx.beans.binding.BooleanBinding} that holds {@code true}
     * if this {@code NumberExpression} is equal to a constant value (with a
     * tolerance).
     * <p>
     * Two operands {@code a} and {@code b} are considered equal if
     * {@code Math.abs(a-b) <= epsilon}.
     * <p>
     * Allowing a small tolerance is recommended when comparing floating-point
     * numbers because of rounding-errors.
     *
     * @param other
     *            the constant value
     * @param epsilon
     *            the permitted tolerance
     * @return the new {@code BooleanBinding}
     */
    BooleanBinding isEqualTo(final double other);

    /**
     * Creates a new {@link javafx.beans.binding.BooleanBinding} that holds {@code true}
     * if this {@code NumberExpression} is equal to a constant value (with a
     * tolerance).
     * <p>
     * Two operands {@code a} and {@code b} are considered equal if
     * {@code Math.abs(a-b) <= epsilon}.
     * <p>
     * Allowing a small tolerance is recommended when comparing floating-point
     * numbers because of rounding-errors.
     *
     * @param other
     *            the constant value
     * @param epsilon
     *            the permitted tolerance
     * @return the new {@code BooleanBinding}
     */
    BooleanBinding isEqualTo(final float other);

    /**
     * Creates a new {@link javafx.beans.binding.BooleanBinding} that holds {@code true}
     * if this {@code NumberExpression} is equal to a constant value.
     * <p>
     * When comparing floating-point numbers it is recommended to use the
     * {@link #isEqualTo(long, double) isEqualTo()} method that allows a small
     * tolerance.
     *
     * @param other
     *            the constant value
     * @return the new {@code BooleanBinding}
     */
    BooleanBinding isEqualTo(final long other);

    /**
     * Creates a new {@link javafx.beans.binding.BooleanBinding} that holds {@code true}
     * if this {@code NumberExpression} is equal to a constant value.
     * <p>
     * When comparing floating-point numbers it is recommended to use the
     * {@link #isEqualTo(int, double) isEqualTo()} method that allows a small
     * tolerance.
     *
     * @param other
     *            the constant value
     * @return the new {@code BooleanBinding}
     */
    BooleanBinding isEqualTo(final int other);

    

    // ===============================================================
    // IsNotEqualTo

    /**
     * Creates a new {@link javafx.beans.binding.BooleanBinding} that holds {@code true}
     * if this and another {@link javafx.beans.value.ObservableBigDecimalValue} are
     * not equal.
     * <p>
     * When comparing floating-point numbers it is recommended to use the
     * {@link #isNotEqualTo(ObservableBigDecimalValue, double) isNotEqualTo()}
     * method that allows a small tolerance.
     *
     * @param other
     *            the second {@code ObservableBigDecimalValue}
     * @return the new {@code BooleanBinding}
     * @throws NullPointerException
     *             if the other {@code ObservableBigDecimalValue} is {@code null}
     */
    BooleanBinding isNotEqualTo(final ObservableBigDecimalValue other);

    /**
     * Creates a new {@link javafx.beans.binding.BooleanBinding} that holds {@code true}
     * if this {@code NumberExpression} is not equal to a constant value (with a
     * tolerance).
     * <p>
     * Two operands {@code a} and {@code b} are considered not equal if
     * {@code Math.abs(a-b) > epsilon}.
     * <p>
     * Allowing a small tolerance is recommended when comparing floating-point
     * numbers.
     *
     * @param other
     *            the constant value
     * @param epsilon
     *            the permitted tolerance
     * @return the new {@code BooleanBinding}
     */
    BooleanBinding isNotEqualTo(final double other);

    /**
     * Creates a new {@link javafx.beans.binding.BooleanBinding} that holds {@code true}
     * if this {@code NumberExpression} is not equal to a constant value (with a
     * tolerance).
     * <p>
     * Two operands {@code a} and {@code b} are considered not equal if
     * {@code Math.abs(a-b) > epsilon}.
     * <p>
     * Allowing a small tolerance is recommended when comparing floating-point
     * numbers.
     *
     * @param other
     *            the constant value
     * @param epsilon
     *            the permitted tolerance
     * @return the new {@code BooleanBinding}
     */
    BooleanBinding isNotEqualTo(final float other);

    /**
     * Creates a new {@link javafx.beans.binding.BooleanBinding} that holds {@code true}
     * if this {@code NumberExpression} is not equal to a constant value.
     * <p>
     * When comparing floating-point numbers it is recommended to use the
     * {@link #isNotEqualTo(long, double) isNotEqualTo()} method that allows a
     * small tolerance.
     *
     * @param other
     *            the constant value
     * @return the new {@code BooleanBinding}
     */
    BooleanBinding isNotEqualTo(final long other);

    /**
     * Creates a new {@link javafx.beans.binding.BooleanBinding} that holds {@code true}
     * if this {@code NumberExpression} is not equal to a constant value.
     * <p>
     * When comparing floating-point numbers it is recommended to use the
     * {@link #isNotEqualTo(int, double) isNotEqualTo()} method that allows a
     * small tolerance.
     *
     * @param other
     *            the constant value
     * @return the new {@code BooleanBinding}
     */
    BooleanBinding isNotEqualTo(final int other);

    

    // ===============================================================
    // IsGreaterThan

    /**
     * Creates a new {@link javafx.beans.binding.BooleanBinding} that holds {@code true}
     * if this {@code NumberExpression} is greater than another
     * {@link javafx.beans.value.ObservableBigDecimalValue}.
     *
     * @param other
     *            the second {@code ObservableBigDecimalValue}
     * @return the new {@code BooleanBinding}
     * @throws NullPointerException
     *             if the other {@code ObservableBigDecimalValue} is {@code null}
     */
    BooleanBinding greaterThan(final ObservableBigDecimalValue other);

    /**
     * Creates a new {@link javafx.beans.binding.BooleanBinding} that holds {@code true}
     * if this {@code NumberExpression} is greater than a constant value.
     *
     * @param other
     *            the constant value
     * @return the new {@code BooleanBinding}
     */
    BooleanBinding greaterThan(final double other);

    /**
     * Creates a new {@link javafx.beans.binding.BooleanBinding} that holds {@code true}
     * if this {@code NumberExpression} is greater than a constant value.
     *
     * @param other
     *            the constant value
     * @return the new {@code BooleanBinding}
     */
    BooleanBinding greaterThan(final float other);

    /**
     * Creates a new {@link javafx.beans.binding.BooleanBinding} that holds {@code true}
     * if this {@code NumberExpression} is greater than a constant value.
     *
     * @param other
     *            the constant value
     * @return the new {@code BooleanBinding}
     */
    BooleanBinding greaterThan(final long other);

    /**
     * Creates a new {@link javafx.beans.binding.BooleanBinding} that holds {@code true}
     * if this {@code NumberExpression} is greater than a constant value.
     *
     * @param other
     *            the constant value
     * @return the new {@code BooleanBinding}
     */
    BooleanBinding greaterThan(final int other);

    // ===============================================================
    // IsLesserThan

    /**
     * Creates a new {@link javafx.beans.binding.BooleanBinding} that holds {@code true}
     * if this {@code NumberExpression} is lesser than another
     * {@link javafx.beans.value.ObservableBigDecimalValue}.
     *
     * @param other
     *            the second {@code ObservableBigDecimalValue}
     * @return the new {@code BooleanBinding}
     * @throws NullPointerException
     *             if the other {@code ObservableBigDecimalValue} is {@code null}
     */
    BooleanBinding lessThan(final ObservableBigDecimalValue other);

    /**
     * Creates a new {@link javafx.beans.binding.BooleanBinding} that holds {@code true}
     * if this {@code NumberExpression} is lesser than a constant value.
     *
     * @param other
     *            the constant value
     * @return the new {@code BooleanBinding}
     */
    BooleanBinding lessThan(final double other);

    /**
     * Creates a new {@link javafx.beans.binding.BooleanBinding} that holds {@code true}
     * if this {@code NumberExpression} is lesser than a constant value.
     *
     * @param other
     *            the constant value
     * @return the new {@code BooleanBinding}
     */
    BooleanBinding lessThan(final float other);

    /**
     * Creates a new {@link javafx.beans.binding.BooleanBinding} that holds {@code true}
     * if this {@code NumberExpression} is lesser than a constant value.
     *
     * @param other
     *            the constant value
     * @return the new {@code BooleanBinding}
     */
    BooleanBinding lessThan(final long other);

    /**
     * Creates a new {@link javafx.beans.binding.BooleanBinding} that holds {@code true}
     * if this {@code NumberExpression} is lesser than a constant value.
     *
     * @param other
     *            the constant value
     * @return the new {@code BooleanBinding}
     */
    BooleanBinding lessThan(final int other);

    // ===============================================================
    // IsGreaterThanOrEqualTo

    /**
     * Creates a new {@link javafx.beans.binding.BooleanBinding} that holds {@code true}
     * if this {@code NumberExpression} is greater than or equal to another
     * {@link javafx.beans.value.ObservableBigDecimalValue}.
     *
     * @param other
     *            the second {@code ObservableBigDecimalValue}
     * @return the new {@code BooleanBinding}
     * @throws NullPointerException
     *             if the other {@code ObservableBigDecimalValue} is {@code null}
     */
    BooleanBinding greaterThanOrEqualTo(final ObservableBigDecimalValue other);

    /**
     * Creates a new {@link javafx.beans.binding.BooleanBinding} that holds {@code true}
     * if this {@code NumberExpression} is greater than or equal to a constant
     * value.
     *
     * @param other
     *            the constant value
     * @return the new {@code BooleanBinding}
     */
    BooleanBinding greaterThanOrEqualTo(final double other);

    /**
     * Creates a new {@link javafx.beans.binding.BooleanBinding} that holds {@code true}
     * if this {@code NumberExpression} is greater than or equal to a constant
     * value.
     *
     * @param other
     *            the constant value
     * @return the new {@code BooleanBinding}
     */
    BooleanBinding greaterThanOrEqualTo(final float other);

    /**
     * Creates a new {@link javafx.beans.binding.BooleanBinding} that holds {@code true}
     * if this {@code NumberExpression} is greater than or equal to a constant
     * value.
     *
     * @param other
     *            the constant value
     * @return the new {@code BooleanBinding}
     */
    BooleanBinding greaterThanOrEqualTo(final long other);

    /**
     * Creates a new {@link javafx.beans.binding.BooleanBinding} that holds {@code true}
     * if this {@code NumberExpression} is greater than or equal to a constant
     * value.
     *
     * @param other
     *            the constant value
     * @return the new {@code BooleanBinding}
     */
    BooleanBinding greaterThanOrEqualTo(final int other);

    // ===============================================================
    // IsLessThanOrEqualTo

    /**
     * Creates a new {@link javafx.beans.binding.BooleanBinding} that holds {@code true}
     * if this {@code NumberExpression} is less than or equal to another
     * {@link javafx.beans.value.ObservableBigDecimalValue}.
     *
     * @param other
     *            the second {@code ObservableBigDecimalValue}
     * @return the new {@code BooleanBinding}
     * @throws NullPointerException
     *             if the other {@code ObservableBigDecimalValue} is {@code null}
     */
    BooleanBinding lessThanOrEqualTo(final ObservableBigDecimalValue other);

    /**
     * Creates a new {@link javafx.beans.binding.BooleanBinding} that holds {@code true}
     * if this {@code NumberExpression} is less than or equal to a constant
     * value.
     *
     * @param other
     *            the constant value
     * @return the new {@code BooleanBinding}
     */
    BooleanBinding lessThanOrEqualTo(final double other);

    /**
     * Creates a new {@link javafx.beans.binding.BooleanBinding} that holds {@code true}
     * if this {@code NumberExpression} is less than or equal to a constant
     * value.
     *
     * @param other
     *            the constant value
     * @return the new {@code BooleanBinding}
     */
    BooleanBinding lessThanOrEqualTo(final float other);

    /**
     * Creates a new {@link javafx.beans.binding.BooleanBinding} that holds {@code true}
     * if this {@code NumberExpression} is less than or equal to a constant
     * value.
     *
     * @param other
     *            the constant value
     * @return the new {@code BooleanBinding}
     */
    BooleanBinding lessThanOrEqualTo(final long other);

    /**
     * Creates a new {@link javafx.beans.binding.BooleanBinding} that holds {@code true}
     * if this {@code NumberExpression} is less than or equal to a constant
     * value.
     *
     * @param other
     *            the constant value
     * @return the new {@code BooleanBinding}
     */
    BooleanBinding lessThanOrEqualTo(final int other);

    // ===============================================================
    // String conversions

    /**
     * Creates a {@link javafx.beans.binding.StringBinding} that holds the value
     * of the {@code NumberExpression} turned into a {@code String}. If the
     * value of this {@code NumberExpression} changes, the value of the
     * {@code StringBinding} will be updated automatically.
     * <p>
     * The conversion is done without any formatting applied.
     *
     * @return the new {@code StringBinding}
     */
    StringBinding asString();

    /**
     * Creates a {@link javafx.beans.binding.StringBinding} that holds the value
     * of the {@code NumberExpression} turned into a {@code String}. If the
     * value of this {@code NumberExpression} changes, the value of the
     * {@code StringBinding} will be updated automatically.
     * <p>
     * The result is formatted according to the formatting {@code String}. See
     * {@code java.util.Formatter} for formatting rules.
     *
     * @param format
     *            the formatting {@code String}
     * @return the new {@code StringBinding}
     */
    StringBinding asString(String format);

    /**
     * Creates a {@link javafx.beans.binding.StringBinding} that holds the value
     * of the {@code NumberExpression} turned into a {@code String}. If the
     * value of this {@code NumberExpression} changes, the value of the
     * {@code StringBinding} will be updated automatically.
     * <p>
     * The result is formatted according to the formatting {@code String} and
     * the passed in {@code Locale}. See {@code java.util.Formatter} for
     * formatting rules. See {@code java.util.Locale} for details on
     * {@code Locale}.
     *
     * @param format
     *            the formatting {@code String}
     * @return the new {@code StringBinding}
     */
    StringBinding asString(Locale locale, String format);
}
