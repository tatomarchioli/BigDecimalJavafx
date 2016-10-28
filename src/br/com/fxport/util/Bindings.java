package br.com.fxport.util;

/*
 * Copyright (c) 2010, 2014, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

import java.math.BigDecimal;
import java.text.Format;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import javafx.beans.Observable;
import javafx.beans.property.Property;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableFloatValue;
import javafx.beans.value.ObservableLongValue;
import javafx.beans.value.ObservableNumberValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
import javafx.util.StringConverter;
import com.sun.javafx.binding.BidirectionalBinding;
import com.sun.javafx.binding.BidirectionalContentBinding;
import com.sun.javafx.binding.ContentBinding;
import com.sun.javafx.binding.DoubleConstant;
import com.sun.javafx.binding.FloatConstant;
import com.sun.javafx.binding.IntegerConstant;
import com.sun.javafx.binding.Logging;
import com.sun.javafx.binding.LongConstant;
import com.sun.javafx.binding.SelectBinding;
import com.sun.javafx.binding.StringFormatter;
import javafx.beans.binding.*;
import com.sun.javafx.collections.ImmutableObservableList;

import br.com.fxport.interfaces.ObservableBigDecimalValue;
import br.com.fxport.properties.BigDecimalBinding;
import br.com.fxport.properties.base.BigDecimalConstant;

/**
 * 
 * Modified by Otavio Marchioli. 
 * <p>This class is originally on JavaFX package. This is a fork modified 
 * to work with BigDecimal properties.<br>
 * ===============================================================================
 * <br>
 * 
 * 
 * Bindings is a helper class with a lot of utility functions to create simple
 * bindings.
 * <p>
 * Usually there are two possibilities to define the same operation: the Fluent
 * API and the the factory methods in this class. This allows a developer to
 * define complex expression in a way that is most easy to understand. For
 * instance the expression {@code result = a*b + c*d} can be defined using only
 * the Fluent API:
 * <p>
 * {@code DoubleBinding result = a.multiply(b).add(c.multiply(d));}
 * <p>
 * Or using only factory methods in Bindings:
 * <p>
 * {@code NumberBinding result = add (multiply(a, b), multiply(c,d));}
 * <p>
 * Or mixing both possibilities:
 * <p>
 * {@code NumberBinding result = add (a.multiply(b), c.multiply(d));}
 * <p>
 * The main difference between using the Fluent API and using the factory
 * methods in this class is that the Fluent API requires that at least one of
 * the operands is an Expression (see {@link javafx.beans.binding}). (Every
 * Expression contains a static method that generates an Expression from an
 * {@link javafx.beans.value.ObservableValue}.)
 * <p>
 * Also if you watched closely, you might have noticed that the return type of
 * the Fluent API is different in the examples above. In a lot of cases the
 * Fluent API allows to be more specific about the returned type (see
 * {@link javafx.beans.binding.NumberExpression} for more details about implicit
 * casting.
 *
 * @see Binding
 * @see NumberBinding
 *
 *
 * @since JavaFX 2.0
 */
public final class Bindings {

	private Bindings() {
	
	}

	/**
	 * Creates a binding used to get a member, such as {@code a.b.c}. The value
	 * of the binding will be {@code c}, or {@code null} if {@code c} could not
	 * be reached (due to {@code b} not having a {@code c} property,
	 * {@code b} being {@code null}, or {@code c} not being the right type etc.).
	 * <p>
	 * All classes and properties used in a select-binding have to be public.
	 *
	 * Note: since 8.0, JavaBeans properties are supported and might be in the chain.
	 *
	 * @param root
	 *            The root {@link javafx.beans.value.ObservableValue}
	 * @param steps
	 *            The property names to reach the final property
	 * @return the created {@link ObjectBinding}
	 */
	public static <T> ObjectBinding<T> select(ObservableValue<?> root, String... steps) {
		return new SelectBinding.AsObject<T>(root, steps);
	}

	/**
	 * Creates a binding used to get a member, such as {@code a.b.c}. The value
	 * of the binding will be {@code c}, or {@code 0.0} if {@code c} could not
	 * be reached (due to {@code b} not having a {@code c} property,
	 * {@code b} being {@code null}, or {@code c} not being a {@code Number} etc.).
	 * <p>
	 * All classes and properties used in a select-binding have to be public.
	 *
	 * Note: since 8.0, JavaBeans properties are supported and might be in the chain.
	 *
	 * @param root
	 *            The root {@link javafx.beans.value.ObservableValue}
	 * @param steps
	 *            The property names to reach the final property
	 * @return the created {@link DoubleBinding}
	 */
	public static DoubleBinding selectDouble(ObservableValue<?> root, String... steps) {
		return new SelectBinding.AsDouble(root, steps);
	}


	// =================================================================================================================
	// Bidirectional Bindings

	/**
	 * Generates a bidirectional binding (or "bind with inverse") between two
	 * instances of {@link javafx.beans.property.Property}.
	 * <p>
	 * A bidirectional binding is a binding that works in both directions. If
	 * two properties {@code a} and {@code b} are linked with a bidirectional
	 * binding and the value of {@code a} changes, {@code b} is set to the same
	 * value automatically. And vice versa, if {@code b} changes, {@code a} is
	 * set to the same value.
	 * <p>
	 * A bidirectional binding can be removed with
	 * {@link #unbindBidirectional(Property, Property)}.
	 * <p>
	 * Note: this implementation of a bidirectional binding behaves differently
	 * from all other bindings here in two important aspects. A property that is
	 * linked to another property with a bidirectional binding can still be set
	 * (usually bindings would throw an exception). Secondly bidirectional
	 * bindings are calculated eagerly, i.e. a bound property is updated
	 * immediately.
	 *
	 * @param <T>
	 *            the types of the properties
	 * @param property1
	 *            the first {@code Property<T>}
	 * @param property2
	 *            the second {@code Property<T>}
	 * @throws NullPointerException
	 *            if one of the properties is {@code null}
	 * @throws IllegalArgumentException
	 *            if both properties are equal
	 */
	public static <T> void bindBidirectional(Property<T> property1, Property<T> property2) {
		BidirectionalBinding.bind(property1, property2);
	}

	/**
	 * Delete a bidirectional binding that was previously defined with
	 * {@link #bindBidirectional(Property, Property)}.
	 *
	 * @param <T>
	 *            the types of the properties
	 * @param property1
	 *            the first {@code Property<T>}
	 * @param property2
	 *            the second {@code Property<T>}
	 * @throws NullPointerException
	 *            if one of the properties is {@code null}
	 * @throws IllegalArgumentException
	 *            if both properties are equal
	 */
	public static <T> void unbindBidirectional(Property<T> property1, Property<T> property2) {
		BidirectionalBinding.unbind(property1, property2);
	}

	/**
	 * Delete a bidirectional binding that was previously defined with
	 * {@link #bindBidirectional(Property, Property)} or
	 * {@link #bindBidirectional(javafx.beans.property.Property, javafx.beans.property.Property, java.text.Format)}.
	 *
	 * @param property1
	 *            the first {@code Property<T>}
	 * @param property2
	 *            the second {@code Property<T>}
	 * @throws NullPointerException
	 *            if one of the properties is {@code null}
	 * @throws IllegalArgumentException
	 *            if both properties are equal
	 * @since JavaFX 2.1
	 */
	public static void unbindBidirectional(Object property1, Object property2) {
		BidirectionalBinding.unbind(property1, property2);
	}

	/**
	 * Generates a bidirectional binding (or "bind with inverse") between a
	 * {@code String}-{@link javafx.beans.property.Property} and another {@code Property}
	 * using the specified {@code Format} for conversion.
	 * <p>
	 * A bidirectional binding is a binding that works in both directions. If
	 * two properties {@code a} and {@code b} are linked with a bidirectional
	 * binding and the value of {@code a} changes, {@code b} is set to the same
	 * value automatically. And vice versa, if {@code b} changes, {@code a} is
	 * set to the same value.
	 * <p>
	 * A bidirectional binding can be removed with
	 * {@link #unbindBidirectional(Object, Object)}.
	 * <p>
	 * Note: this implementation of a bidirectional binding behaves differently
	 * from all other bindings here in two important aspects. A property that is
	 * linked to another property with a bidirectional binding can still be set
	 * (usually bindings would throw an exception). Secondly bidirectional
	 * bindings are calculated eagerly, i.e. a bound property is updated
	 * immediately.
	 *
	 * @param stringProperty
	 *            the {@code String} {@code Property}
	 * @param otherProperty
	 *            the other (non-{@code String}) {@code Property}
	 * @param format
	 *            the {@code Format} used to convert between the properties
	 * @throws NullPointerException
	 *            if one of the properties or the {@code format} is {@code null}
	 * @throws IllegalArgumentException
	 *            if both properties are equal
	 * @since JavaFX 2.1
	 */
	public  static void bindBidirectional(Property<String> stringProperty, Property<?> otherProperty, Format format) {
		BidirectionalBinding.bind(stringProperty, otherProperty, format);
	}

	/**
	 * Generates a bidirectional binding (or "bind with inverse") between a
	 * {@code String}-{@link javafx.beans.property.Property} and another {@code Property}
	 * using the specified {@link javafx.util.StringConverter} for conversion.
	 * <p>
	 * A bidirectional binding is a binding that works in both directions. If
	 * two properties {@code a} and {@code b} are linked with a bidirectional
	 * binding and the value of {@code a} changes, {@code b} is set to the same
	 * value automatically. And vice versa, if {@code b} changes, {@code a} is
	 * set to the same value.
	 * <p>
	 * A bidirectional binding can be removed with
	 * {@link #unbindBidirectional(Object, Object)}.
	 * <p>
	 * Note: this implementation of a bidirectional binding behaves differently
	 * from all other bindings here in two important aspects. A property that is
	 * linked to another property with a bidirectional binding can still be set
	 * (usually bindings would throw an exception). Secondly bidirectional
	 * bindings are calculated eagerly, i.e. a bound property is updated
	 * immediately.
	 *
	 * @param stringProperty
	 *            the {@code String} {@code Property}
	 * @param otherProperty
	 *            the other (non-{@code String}) {@code Property}
	 * @param converter
	 *            the {@code StringConverter} used to convert between the properties
	 * @throws NullPointerException
	 *            if one of the properties or the {@code converter} is {@code null}
	 * @throws IllegalArgumentException
	 *            if both properties are equal
	 * @since JavaFX 2.1
	 */
	public static <T> void bindBidirectional(Property<String> stringProperty, Property<T> otherProperty, StringConverter<T> converter) {
		BidirectionalBinding.bind(stringProperty, otherProperty, converter);
	}

	/**
	 * Generates a bidirectional binding (or "bind with inverse") between two
	 * instances of {@link javafx.collections.ObservableList}.
	 * <p>
	 * A bidirectional binding is a binding that works in both directions. If
	 * two properties {@code a} and {@code b} are linked with a bidirectional
	 * binding and the value of {@code a} changes, {@code b} is set to the same
	 * value automatically. And vice versa, if {@code b} changes, {@code a} is
	 * set to the same value.
	 * <p>
	 * Only the content of the two lists is synchronized, which means that
	 * both lists are different, but they contain the same elements.
	 * <p>
	 * A bidirectional content-binding can be removed with
	 * {@link #unbindContentBidirectional(Object, Object)}.
	 * <p>
	 * Note: this implementation of a bidirectional binding behaves differently
	 * from all other bindings here in two important aspects. A property that is
	 * linked to another property with a bidirectional binding can still be set
	 * (usually bindings would throw an exception). Secondly bidirectional
	 * bindings are calculated eagerly, i.e. a bound property is updated
	 * immediately.
	 *
	 * @param <E>
	 *            the type of the list elements
	 * @param list1
	 *            the first {@code ObservableList<E>}
	 * @param list2
	 *            the second {@code ObservableList<E>}
	 * @throws NullPointerException
	 *            if one of the lists is {@code null}
	 * @throws IllegalArgumentException
	 *            if {@code list1} == {@code list2}
	 * @since JavaFX 2.1
	 */
	public static <E> void bindContentBidirectional(ObservableList<E> list1, ObservableList<E> list2) {
		BidirectionalContentBinding.bind(list1, list2);
	}

	/**
	 * Generates a bidirectional binding (or "bind with inverse") between two
	 * instances of {@link javafx.collections.ObservableSet}.
	 * <p>
	 * A bidirectional binding is a binding that works in both directions. If
	 * two properties {@code a} and {@code b} are linked with a bidirectional
	 * binding and the value of {@code a} changes, {@code b} is set to the same
	 * value automatically. And vice versa, if {@code b} changes, {@code a} is
	 * set to the same value.
	 * <p>
	 * Only the content of the two sets is synchronized, which means that
	 * both sets are different, but they contain the same elements.
	 * <p>
	 * A bidirectional content-binding can be removed with
	 * {@link #unbindContentBidirectional(Object, Object)}.
	 * <p>
	 * Note: this implementation of a bidirectional binding behaves differently
	 * from all other bindings here in two important aspects. A property that is
	 * linked to another property with a bidirectional binding can still be set
	 * (usually bindings would throw an exception). Secondly bidirectional
	 * bindings are calculated eagerly, i.e. a bound property is updated
	 * immediately.
	 *
	 * @param <E>
	 *            the type of the set elements
	 * @param set1
	 *            the first {@code ObservableSet<E>}
	 * @param set2
	 *            the second {@code ObservableSet<E>}
	 * @throws NullPointerException
	 *            if one of the sets is {@code null}
	 * @throws IllegalArgumentException
	 *            if {@code set1} == {@code set2}
	 * @since JavaFX 2.1
	 */
	public static <E> void bindContentBidirectional(ObservableSet<E> set1, ObservableSet<E> set2) {
		BidirectionalContentBinding.bind(set1, set2);
	}

	/**
	 * Generates a bidirectional binding (or "bind with inverse") between two
	 * instances of {@link javafx.collections.ObservableMap}.
	 * <p>
	 * A bidirectional binding is a binding that works in both directions. If
	 * two properties {@code a} and {@code b} are linked with a bidirectional
	 * binding and the value of {@code a} changes, {@code b} is set to the same
	 * value automatically. And vice versa, if {@code b} changes, {@code a} is
	 * set to the same value.
	 * <p>
	 * Only the content of the two maps is synchronized, which means that
	 * both maps are different, but they contain the same elements.
	 * <p>
	 * A bidirectional content-binding can be removed with
	 * {@link #unbindContentBidirectional(Object, Object)}.
	 * <p>
	 * Note: this implementation of a bidirectional binding behaves differently
	 * from all other bindings here in two important aspects. A property that is
	 * linked to another property with a bidirectional binding can still be set
	 * (usually bindings would throw an exception). Secondly bidirectional
	 * bindings are calculated eagerly, i.e. a bound property is updated
	 * immediately.
	 *
	 * @param <K>
	 *            the type of the key elements
	 * @param <V>
	 *            the type of the value elements
	 * @param map1
	 *            the first {@code ObservableMap<K, V>}
	 * @param map2
	 *            the second {@code ObservableMap<K, V>}
	 * @since JavaFX 2.1
	 */
	public static <K, V> void bindContentBidirectional(ObservableMap<K, V> map1, ObservableMap<K, V> map2) {
		BidirectionalContentBinding.bind(map1, map2);
	}

	/**
	 * Remove a bidirectional content binding.
	 *
	 * @param obj1
	 *            the first {@code Object}
	 * @param obj2
	 *            the second {@code Object}
	 * @since JavaFX 2.1
	 */
	public static void unbindContentBidirectional(Object obj1, Object obj2) {
		BidirectionalContentBinding.unbind(obj1, obj2);
	}

	/**
	 * Generates a content binding between an {@link javafx.collections.ObservableList} and a {@link java.util.List}.
	 * <p>
	 * A content binding ensures that the {@code List} contains the same elements as the {@code ObservableList}.
	 * If the content of the {@code ObservableList} changes, the {@code List} will be updated automatically.
	 * <p>
	 * Once a {@code List} is bound to an {@code ObservableList}, the {@code List} must not be changed directly
	 * anymore. Doing so would lead to unexpected results.
	 * <p>
	 * A content-binding can be removed with {@link #unbindContent(Object, Object)}.
	 *
	 * @param <E>
	 *            the type of the {@code List} elements
	 * @param list1
	 *            the {@code List}
	 * @param list2
	 *            the {@code ObservableList}
	 * @since JavaFX 2.1
	 */
	public static <E> void bindContent(List<E> list1, ObservableList<? extends E> list2) {
		ContentBinding.bind(list1, list2);
	}

	/**
	 * Generates a content binding between an {@link javafx.collections.ObservableSet} and a {@link java.util.Set}.
	 * <p>
	 * A content binding ensures that the {@code Set} contains the same elements as the {@code ObservableSet}.
	 * If the content of the {@code ObservableSet} changes, the {@code Set} will be updated automatically.
	 * <p>
	 * Once a {@code Set} is bound to an {@code ObservableSet}, the {@code Set} must not be changed directly
	 * anymore. Doing so would lead to unexpected results.
	 * <p>
	 * A content-binding can be removed with {@link #unbindContent(Object, Object)}.
	 *
	 * @param <E>
	 *            the type of the {@code Set} elements
	 * @param set1
	 *            the {@code Set}
	 * @param set2
	 *            the {@code ObservableSet}
	 * @throws NullPointerException
	 *            if one of the sets is {@code null}
	 * @throws IllegalArgumentException
	 *            if {@code set1} == {@code set2}
	 * @since JavaFX 2.1
	 */
	public static <E> void bindContent(Set<E> set1, ObservableSet<? extends E> set2) {
		ContentBinding.bind(set1, set2);
	}

	/**
	 * Generates a content binding between an {@link javafx.collections.ObservableMap} and a {@link java.util.Map}.
	 * <p>
	 * A content binding ensures that the {@code Map} contains the same elements as the {@code ObservableMap}.
	 * If the content of the {@code ObservableMap} changes, the {@code Map} will be updated automatically.
	 * <p>
	 * Once a {@code Map} is bound to an {@code ObservableMap}, the {@code Map} must not be changed directly
	 * anymore. Doing so would lead to unexpected results.
	 * <p>
	 * A content-binding can be removed with {@link #unbindContent(Object, Object)}.
	 *
	 * @param <K>
	 *            the type of the key elements of the {@code Map}
	 * @param <V>
	 *            the type of the value elements of the {@code Map}
	 * @param map1
	 *            the {@code Map}
	 * @param map2
	 *            the {@code ObservableMap}
	 * @throws NullPointerException
	 *            if one of the maps is {@code null}
	 * @throws IllegalArgumentException
	 *            if {@code map1} == {@code map2}
	 * @since JavaFX 2.1
	 */
	public static <K, V> void bindContent(Map<K, V> map1, ObservableMap<? extends K, ? extends V> map2) {
		ContentBinding.bind(map1, map2);
	}

	/**
	 * Remove a content binding.
	 *
	 * @param obj1
	 *            the first {@code Object}
	 * @param obj2
	 *            the second {@code Object}
	 * @throws NullPointerException
	 *            if one of the {@code Objects} is {@code null}
	 * @throws IllegalArgumentException
	 *            if {@code obj1} == {@code obj2}
	 * @since JavaFX 2.1
	 */
	public static void unbindContent(Object obj1, Object obj2) {
		ContentBinding.unbind(obj1, obj2);
	}

	private static NumberBinding min(final ObservableNumberValue op1, final ObservableNumberValue op2, final Observable... dependencies) {
		if ((op1 == null) || (op2 == null)) {
			throw new NullPointerException("Operands cannot be null.");
		}
		assert (dependencies != null) && (dependencies.length > 0);

		if ((op1 instanceof ObservableDoubleValue) || (op2 instanceof ObservableDoubleValue)) {
			return new DoubleBinding() {
				{
					super.bind(dependencies);
				}

				@Override
				public void dispose() {
					super.unbind(dependencies);
				}

				@Override
				protected double computeValue() {
					return Math.min(op1.doubleValue(), op2.doubleValue());
				}

				@Override

				public ObservableList<?> getDependencies() {
					return (dependencies.length == 1)?
							FXCollections.singletonObservableList(dependencies[0])
							: new ImmutableObservableList<Observable>(dependencies);
				}
			};
		} else if ((op1 instanceof ObservableFloatValue) || (op2 instanceof ObservableFloatValue)) {
			return new FloatBinding() {
				{
					super.bind(dependencies);
				}

				@Override
				public void dispose() {
					super.unbind(dependencies);
				}

				@Override
				protected float computeValue() {
					return Math.min(op1.floatValue(), op2.floatValue());
				}

				@Override

				public ObservableList<?> getDependencies() {
					return (dependencies.length == 1)?
							FXCollections.singletonObservableList(dependencies[0])
							: new ImmutableObservableList<Observable>(dependencies);
				}
			};
		} else if ((op1 instanceof ObservableLongValue) || (op2 instanceof ObservableLongValue)) {
			return new LongBinding() {
				{
					super.bind(dependencies);
				}

				@Override
				public void dispose() {
					super.unbind(dependencies);
				}

				@Override
				protected long computeValue() {
					return Math.min(op1.longValue(), op2.longValue());
				}

				@Override

				public ObservableList<?> getDependencies() {
					return (dependencies.length == 1)?
							FXCollections.singletonObservableList(dependencies[0])
							: new ImmutableObservableList<Observable>(dependencies);
				}
			};
		} else {
			return new IntegerBinding() {
				{
					super.bind(dependencies);
				}

				@Override
				public void dispose() {
					super.unbind(dependencies);
				}

				@Override
				protected int computeValue() {
					return Math.min(op1.intValue(), op2.intValue());
				}

				@Override

				public ObservableList<?> getDependencies() {
					return (dependencies.length == 1)?
							FXCollections.singletonObservableList(dependencies[0])
							: new ImmutableObservableList<Observable>(dependencies);
				}
			};
		}
	}

	/**
	 * Creates a new {@link javafx.beans.binding.NumberBinding} that calculates
	 * the minimum of the values of two instances of
	 * {@link javafx.beans.value.ObservableNumberValue}.
	 *
	 * @param op1
	 *            the first operand
	 * @param op2
	 *            the second operand
	 * @return the new {@code NumberBinding}
	 * @throws NullPointerException
	 *             if one of the operands is {@code null}
	 */
	public static NumberBinding min(final ObservableNumberValue op1, final ObservableNumberValue op2) {
		return min(op1, op2, op1, op2);
	}

	/**
	 * Creates a new {@link javafx.beans.binding.DoubleBinding} that calculates
	 * the minimum of the value of a
	 * {@link javafx.beans.value.ObservableNumberValue} and a constant value.
	 *
	 * @param op1
	 *            the {@code ObservableNumberValue}
	 * @param op2
	 *            the constant value
	 * @return the new {@code DoubleBinding}
	 * @throws NullPointerException
	 *             if the {@code ObservableNumberValue} is {@code null}
	 */
	public static DoubleBinding min(final ObservableNumberValue op1, final double op2) {
		return (DoubleBinding) min(op1, DoubleConstant.valueOf(op2), op1);
	}

	/**
	 * Creates a new {@link javafx.beans.binding.DoubleBinding} that calculates
	 * the minimum of the value of a
	 * {@link javafx.beans.value.ObservableNumberValue} and a constant value.
	 *
	 * @param op1
	 *            the constant value
	 * @param op2
	 *            the {@code ObservableNumberValue}
	 * @return the new {@code DoubleBinding}
	 * @throws NullPointerException
	 *             if the {@code ObservableNumberValue} is {@code null}
	 */
	public static DoubleBinding min(final double op1, final ObservableNumberValue op2) {
		return (DoubleBinding) min(DoubleConstant.valueOf(op1), op2, op2);
	}

	/**
	 * Creates a new {@link javafx.beans.binding.NumberBinding} that calculates
	 * the minimum of the value of a
	 * {@link javafx.beans.value.ObservableNumberValue} and a constant value.
	 *
	 * @param op1
	 *            the {@code ObservableNumberValue}
	 * @param op2
	 *            the constant value
	 * @return the new {@code NumberBinding}
	 * @throws NullPointerException
	 *             if the {@code ObservableNumberValue} is {@code null}
	 */
	public static NumberBinding min(final ObservableNumberValue op1, final float op2) {
		return min(op1, FloatConstant.valueOf(op2), op1);
	}

	/**
	 * Creates a new {@link javafx.beans.binding.NumberBinding} that calculates
	 * the minimum of the value of a
	 * {@link javafx.beans.value.ObservableNumberValue} and a constant value.
	 *
	 * @param op1
	 *            the constant value
	 * @param op2
	 *            the {@code ObservableNumberValue}
	 * @return the new {@code NumberBinding}
	 * @throws NullPointerException
	 *             if the {@code ObservableNumberValue} is {@code null}
	 */
	public static NumberBinding min(final float op1, final ObservableNumberValue op2) {
		return min(FloatConstant.valueOf(op1), op2, op2);
	}

	/**
	 * Creates a new {@link javafx.beans.binding.NumberBinding} that calculates
	 * the minimum of the value of a
	 * {@link javafx.beans.value.ObservableNumberValue} and a constant value.
	 *
	 * @param op1
	 *            the {@code ObservableNumberValue}
	 * @param op2
	 *            the constant value
	 * @return the new {@code NumberBinding}
	 * @throws NullPointerException
	 *             if the {@code ObservableNumberValue} is {@code null}
	 */
	public static NumberBinding min(final ObservableNumberValue op1, final long op2) {
		return min(op1, LongConstant.valueOf(op2), op1);
	}

	/**
	 * Creates a new {@link javafx.beans.binding.NumberBinding} that calculates
	 * the minimum of the value of a
	 * {@link javafx.beans.value.ObservableNumberValue} and a constant value.
	 *
	 * @param op1
	 *            the constant value
	 * @param op2
	 *            the {@code ObservableNumberValue}
	 * @return the new {@code NumberBinding}
	 * @throws NullPointerException
	 *             if the {@code ObservableNumberValue} is {@code null}
	 */
	public static NumberBinding min(final long op1, final ObservableNumberValue op2) {
		return min(LongConstant.valueOf(op1), op2, op2);
	}

	/**
	 * Creates a new {@link javafx.beans.binding.NumberBinding} that calculates
	 * the minimum of the value of a
	 * {@link javafx.beans.value.ObservableNumberValue} and a constant value.
	 *
	 * @param op1
	 *            the {@code ObservableNumberValue}
	 * @param op2
	 *            the constant value
	 * @return the new {@code NumberBinding}
	 * @throws NullPointerException
	 *             if the {@code ObservableNumberValue} is {@code null}
	 */
	public static NumberBinding min(final ObservableNumberValue op1, final int op2) {
		return min(op1, IntegerConstant.valueOf(op2), op1);
	}

	/**
	 * Creates a new {@link javafx.beans.binding.NumberBinding} that calculates
	 * the minimum of the value of a
	 * {@link javafx.beans.value.ObservableNumberValue} and a constant value.
	 *
	 * @param op1
	 *            the constant value
	 * @param op2
	 *            the {@code ObservableNumberValue}
	 * @return the new {@code NumberBinding}
	 * @throws NullPointerException
	 *             if the {@code ObservableNumberValue} is {@code null}
	 */
	public static NumberBinding min(final int op1, final ObservableNumberValue op2) {
		return min(IntegerConstant.valueOf(op1), op2, op2);
	}

	// =================================================================================================================
	// Maximum

	private static NumberBinding max(final ObservableNumberValue op1, final ObservableNumberValue op2, final Observable... dependencies) {
		if ((op1 == null) || (op2 == null)) {
			throw new NullPointerException("Operands cannot be null.");
		}
		assert (dependencies != null) && (dependencies.length > 0);

		if ((op1 instanceof ObservableDoubleValue) || (op2 instanceof ObservableDoubleValue)) {
			return new DoubleBinding() {
				{
					super.bind(dependencies);
				}

				@Override
				public void dispose() {
					super.unbind(dependencies);
				}

				@Override
				protected double computeValue() {
					return Math.max(op1.doubleValue(), op2.doubleValue());
				}

				@Override

				public ObservableList<?> getDependencies() {
					return (dependencies.length == 1)?
							FXCollections.singletonObservableList(dependencies[0])
							: new ImmutableObservableList<Observable>(dependencies);
				}
			};
		} else if ((op1 instanceof ObservableFloatValue) || (op2 instanceof ObservableFloatValue)) {
			return new FloatBinding() {
				{
					super.bind(dependencies);
				}

				@Override
				public void dispose() {
					super.unbind(dependencies);
				}

				@Override
				protected float computeValue() {
					return Math.max(op1.floatValue(), op2.floatValue());
				}

				@Override

				public ObservableList<?> getDependencies() {
					return (dependencies.length == 1)?
							FXCollections.singletonObservableList(dependencies[0])
							: new ImmutableObservableList<Observable>(dependencies);
				}
			};
		} else if ((op1 instanceof ObservableLongValue) || (op2 instanceof ObservableLongValue)) {
			return new LongBinding() {
				{
					super.bind(dependencies);
				}

				@Override
				public void dispose() {
					super.unbind(dependencies);
				}

				@Override
				protected long computeValue() {
					return Math.max(op1.longValue(), op2.longValue());
				}

				@Override

				public ObservableList<?> getDependencies() {
					return (dependencies.length == 1)?
							FXCollections.singletonObservableList(dependencies[0])
							: new ImmutableObservableList<Observable>(dependencies);
				}
			};
		} else {
			return new IntegerBinding() {
				{
					super.bind(dependencies);
				}

				@Override
				public void dispose() {
					super.unbind(dependencies);
				}

				@Override
				protected int computeValue() {
					return Math.max(op1.intValue(), op2.intValue());
				}

				@Override

				public ObservableList<?> getDependencies() {
					return (dependencies.length == 1)?
							FXCollections.singletonObservableList(dependencies[0])
							: new ImmutableObservableList<Observable>(dependencies);
				}
			};
		}
	}

	/**
	 * Creates a new {@link javafx.beans.binding.NumberBinding} that calculates
	 * the maximum of the values of two instances of
	 * {@link javafx.beans.value.ObservableNumberValue}.
	 *
	 * @param op1
	 *            the first operand
	 * @param op2
	 *            the second operand
	 * @return the new {@code NumberBinding}
	 * @throws NullPointerException
	 *             if one of the operands is {@code null}
	 */
	public static NumberBinding max(final ObservableNumberValue op1, final ObservableNumberValue op2) {
		return max(op1, op2, op1, op2);
	}

	/**
	 * Creates a new {@link javafx.beans.binding.DoubleBinding} that calculates
	 * the maximum of the value of a
	 * {@link javafx.beans.value.ObservableNumberValue} and a constant value.
	 *
	 * @param op1
	 *            the {@code ObservableNumberValue}
	 * @param op2
	 *            the constant value
	 * @return the new {@code DoubleBinding}
	 * @throws NullPointerException
	 *             if the {@code ObservableNumberValue} is {@code null}
	 */
	public static DoubleBinding max(final ObservableNumberValue op1, final double op2) {
		return (DoubleBinding) max(op1, DoubleConstant.valueOf(op2), op1);
	}

	/**
	 * Creates a new {@link javafx.beans.binding.DoubleBinding} that calculates
	 * the maximum of the value of a
	 * {@link javafx.beans.value.ObservableNumberValue} and a constant value.
	 *
	 * @param op1
	 *            the constant value
	 * @param op2
	 *            the {@code ObservableNumberValue}
	 * @return the new {@code DoubleBinding}
	 * @throws NullPointerException
	 *             if the {@code ObservableNumberValue} is {@code null}
	 */
	public static DoubleBinding max(final double op1, final ObservableNumberValue op2) {
		return (DoubleBinding) max(DoubleConstant.valueOf(op1), op2, op2);
	}

	/**
	 * Creates a new {@link javafx.beans.binding.NumberBinding} that calculates
	 * the maximum of the value of a
	 * {@link javafx.beans.value.ObservableNumberValue} and a constant value.
	 *
	 * @param op1
	 *            the {@code ObservableNumberValue}
	 * @param op2
	 *            the constant value
	 * @return the new {@code NumberBinding}
	 * @throws NullPointerException
	 *             if the {@code ObservableNumberValue} is {@code null}
	 */
	public static NumberBinding max(final ObservableNumberValue op1, final float op2) {
		return max(op1, FloatConstant.valueOf(op2), op1);
	}

	/**
	 * Creates a new {@link javafx.beans.binding.NumberBinding} that calculates
	 * the maximum of the value of a
	 * {@link javafx.beans.value.ObservableNumberValue} and a constant value.
	 *
	 * @param op1
	 *            the constant value
	 * @param op2
	 *            the {@code ObservableNumberValue}
	 * @return the new {@code NumberBinding}
	 * @throws NullPointerException
	 *             if the {@code ObservableNumberValue} is {@code null}
	 */
	public static NumberBinding max(final float op1, final ObservableNumberValue op2) {
		return max(FloatConstant.valueOf(op1), op2, op2);
	}

	/**
	 * Creates a new {@link javafx.beans.binding.NumberBinding} that calculates
	 * the maximum of the value of a
	 * {@link javafx.beans.value.ObservableNumberValue} and a constant value.
	 *
	 * @param op1
	 *            the {@code ObservableNumberValue}
	 * @param op2
	 *            the constant value
	 * @return the new {@code NumberBinding}
	 * @throws NullPointerException
	 *             if the {@code ObservableNumberValue} is {@code null}
	 */
	public static NumberBinding max(final ObservableNumberValue op1, final long op2) {
		return max(op1, LongConstant.valueOf(op2), op1);
	}

	/**
	 * Creates a new {@link javafx.beans.binding.NumberBinding} that calculates
	 * the maximum of the value of a
	 * {@link javafx.beans.value.ObservableNumberValue} and a constant value.
	 *
	 * @param op1
	 *            the constant value
	 * @param op2
	 *            the {@code ObservableNumberValue}
	 * @return the new {@code NumberBinding}
	 * @throws NullPointerException
	 *             if the {@code ObservableNumberValue} is {@code null}
	 */
	public static NumberBinding max(final long op1, final ObservableNumberValue op2) {
		return max(LongConstant.valueOf(op1), op2, op2);
	}

	/**
	 * Creates a new {@link javafx.beans.binding.NumberBinding} that calculates
	 * the maximum of the value of a
	 * {@link javafx.beans.value.ObservableNumberValue} and a constant value.
	 *
	 * @param op1
	 *            the {@code ObservableNumberValue}
	 * @param op2
	 *            the constant value
	 * @return the new {@code NumberBinding}
	 * @throws NullPointerException
	 *             if the {@code ObservableNumberValue} is {@code null}
	 */
	public static NumberBinding max(final ObservableNumberValue op1, final int op2) {
		return max(op1, IntegerConstant.valueOf(op2), op1);
	}

	/**
	 * Creates a new {@link javafx.beans.binding.NumberBinding} that calculates
	 * the maximum of the value of a
	 * {@link javafx.beans.value.ObservableNumberValue} and a constant value.
	 *
	 * @param op1
	 *            the constant value
	 * @param op2
	 *            the {@code ObservableNumberValue}
	 * @return the new {@code NumberBinding}
	 * @throws NullPointerException
	 *             if the {@code ObservableNumberValue} is {@code null}
	 */
	public static NumberBinding max(final int op1, final ObservableNumberValue op2) {
		return max(IntegerConstant.valueOf(op1), op2, op2);
	}


	public static BigDecimalBinding createBigDecimalBinding(final Callable<BigDecimal> func, final Observable... dependencies) {
		return new BigDecimalBinding() {
			{
				bind(dependencies);
			}
	
			@Override
			protected BigDecimal computeValue() {
				try {
					return func.call();
				} catch (Exception e) {
					Logging.getLogger().warning("Exception while evaluating binding", e);
					return BigDecimal.ZERO;
				}
			}
	
			@Override
			public void dispose() {
				super.unbind(dependencies);
			}
	
			@Override
	
			public ObservableList<?> getDependencies() {
				return  ((dependencies == null) || (dependencies.length == 0))?
						FXCollections.emptyObservableList()
						: (dependencies.length == 1)?
								FXCollections.singletonObservableList(dependencies[0])
								: new ImmutableObservableList<Observable>(dependencies);
			}
		};
	}
	
	public static BigDecimalBinding negate(final ObservableBigDecimalValue value) {
		if (value == null) {
			throw new NullPointerException("Operand cannot be null.");
		}

			return new BigDecimalBinding() {
				{
					super.bind(value);
				}

				@Override
				public void dispose() {
					super.unbind(value);
				}

				@Override
				protected BigDecimal computeValue() {
					return  value.get().negate();
				}

				@Override

				public ObservableList<?> getDependencies() {
					return FXCollections.singletonObservableList(value);
				}
			};
	}
	

	// =================================================================================================================
	// Add
	
	private static BigDecimalBinding add(final ObservableBigDecimalValue op1, final ObservableBigDecimalValue op2,
			final Observable... dependencies) {
		if ((op1 == null) || (op2 == null)) {
			throw new NullPointerException("Operands cannot be null.");
		}
		assert (dependencies != null) && (dependencies.length > 0);

		return new BigDecimalBinding() {
			{
				super.bind(dependencies);
			}

			@Override
			public void dispose() {
				super.unbind(dependencies);
			}

			@Override
			protected BigDecimal computeValue() {
				return op1.get().add(op2.get());
			}

			@Override

			public ObservableList<?> getDependencies() {
				return (dependencies.length == 1)?
						FXCollections.singletonObservableList(dependencies[0])
						: new ImmutableObservableList<Observable>(dependencies);
			}
		};
	}

	public static BigDecimalBinding add(final ObservableBigDecimalValue op1, final ObservableBigDecimalValue op2) {
		return Bindings.add(op1, op2, op1, op2);
	}
	
	public static BigDecimalBinding add(final ObservableBigDecimalValue op1, double op2) {
		return (BigDecimalBinding) Bindings.add(op1, BigDecimalConstant.valueOf(op2), op1);
	}

	public static BigDecimalBinding add(final ObservableBigDecimalValue op1, int op2) {
		return (BigDecimalBinding) Bindings.add(op1, BigDecimalConstant.valueOf(op2), op1);
	}

	public static BigDecimalBinding add(final ObservableBigDecimalValue op1, long op2) {
		return (BigDecimalBinding) Bindings.add(op1, BigDecimalConstant.valueOf(op2), op1);
	}
	
	public static BigDecimalBinding add(double op1, final ObservableBigDecimalValue op2) {
		return (BigDecimalBinding) Bindings.add(BigDecimalConstant.valueOf(op1), op2, op2);
	}

	public static BigDecimalBinding add(int op1, final ObservableBigDecimalValue op2) {
		return (BigDecimalBinding) Bindings.add(BigDecimalConstant.valueOf(op1), op2, op2);
	}

	public static BigDecimalBinding add(long op1,final ObservableBigDecimalValue op2) {
		return (BigDecimalBinding) Bindings.add(BigDecimalConstant.valueOf(op1), op2, op2);
	}


	// =================================================================================================================
	// Subtract
	
	private static BigDecimalBinding subtract(final ObservableBigDecimalValue op1, final ObservableBigDecimalValue op2,
			final Observable... dependencies) {
		if ((op1 == null) || (op2 == null)) {
			throw new NullPointerException("Operands cannot be null.");
		}
		assert (dependencies != null) && (dependencies.length > 0);
	
		return new BigDecimalBinding() {
			{
				super.bind(dependencies);
			}
	
			@Override
			public void dispose() {
				super.unbind(dependencies);
			}
	
			@Override
			protected BigDecimal computeValue() {
				return op1.get().subtract(op2.get());
			}
	
			@Override
	
			public ObservableList<?> getDependencies() {
				return (dependencies.length == 1)?
						FXCollections.singletonObservableList(dependencies[0])
						: new ImmutableObservableList<Observable>(dependencies);
			}
		};
	}

	public static BigDecimalBinding subtract(final ObservableBigDecimalValue op1, final ObservableBigDecimalValue op2) {
		return Bindings.subtract(op1, op2, op1, op2);
	}

	public static BigDecimalBinding subtract(final ObservableBigDecimalValue op1, double op2) {
		return (BigDecimalBinding) Bindings.subtract(op1, BigDecimalConstant.valueOf(op2), op1);
	}

	public static BigDecimalBinding subtract(final ObservableBigDecimalValue op1, int op2) {
		return (BigDecimalBinding) Bindings.subtract(op1, BigDecimalConstant.valueOf(op2), op1);
	}

	public static BigDecimalBinding subtract(final ObservableBigDecimalValue op1, long op2) {
		return (BigDecimalBinding) Bindings.subtract(op1, BigDecimalConstant.valueOf(op2), op1);
	}
	
	public static BigDecimalBinding subtract(double op1, final ObservableBigDecimalValue op2) {
		return (BigDecimalBinding) Bindings.subtract(BigDecimalConstant.valueOf(op1), op2, op2);
	}

	public static BigDecimalBinding subtract(int op1, final ObservableBigDecimalValue op2) {
		return (BigDecimalBinding) Bindings.subtract(BigDecimalConstant.valueOf(op1), op2, op2);
	}

	public static BigDecimalBinding subtract(long op1,final ObservableBigDecimalValue op2) {
		return (BigDecimalBinding) Bindings.subtract(BigDecimalConstant.valueOf(op1), op2, op2);
	}

	// =================================================================================================================
	// Multiply
	

	private static BigDecimalBinding multiply(final ObservableBigDecimalValue op1, final ObservableBigDecimalValue op2,
			final Observable... dependencies) {
		if ((op1 == null) || (op2 == null)) {
			throw new NullPointerException("Operands cannot be null.");
		}
		assert (dependencies != null) && (dependencies.length > 0);
	
		return new BigDecimalBinding() {
			{
				super.bind(dependencies);
			}
	
			@Override
			public void dispose() {
				super.unbind(dependencies);
			}
	
			@Override
			protected BigDecimal computeValue() {
				return op1.get().multiply(op2.get());
			}
	
			@Override
	
			public ObservableList<?> getDependencies() {
				return (dependencies.length == 1)?
						FXCollections.singletonObservableList(dependencies[0])
						: new ImmutableObservableList<Observable>(dependencies);
			}
		};
	}

	public static BigDecimalBinding multiply(final ObservableBigDecimalValue op1, final ObservableBigDecimalValue op2) {
		return Bindings.multiply(op1, op2, op1, op2);
	}

	public static BigDecimalBinding multiply(final ObservableBigDecimalValue op1, double op2) {
		return (BigDecimalBinding) Bindings.multiply(op1, BigDecimalConstant.valueOf(op2), op1);
	}

	public static BigDecimalBinding multiply(final ObservableBigDecimalValue op1, int op2) {
		return (BigDecimalBinding) Bindings.multiply(op1, BigDecimalConstant.valueOf(op2), op1);
	}

	public static BigDecimalBinding multiply(final ObservableBigDecimalValue op1, long op2) {
		return (BigDecimalBinding) Bindings.multiply(op1, BigDecimalConstant.valueOf(op2), op1);
	}

	public static BigDecimalBinding multiply(double op1, final ObservableBigDecimalValue op2) {
		return (BigDecimalBinding) Bindings.multiply(BigDecimalConstant.valueOf(op1), op2, op2);
	}

	public static BigDecimalBinding multiply(int op1, final ObservableBigDecimalValue op2) {
		return (BigDecimalBinding) Bindings.multiply(BigDecimalConstant.valueOf(op1), op2, op2);
	}

	public static BigDecimalBinding multiply(long op1,final ObservableBigDecimalValue op2) {
		return (BigDecimalBinding) Bindings.multiply(BigDecimalConstant.valueOf(op1), op2, op2);
	}
	// =================================================================================================================
	// Multiply
	
	private static BigDecimalBinding divide(final ObservableBigDecimalValue op1, final ObservableBigDecimalValue op2,
			final Observable... dependencies) {
		if ((op1 == null) || (op2 == null)) {
			throw new NullPointerException("Operands cannot be null.");
		}
		assert (dependencies != null) && (dependencies.length > 0);
	
		return new BigDecimalBinding() {
			{
				super.bind(dependencies);
			}
	
			@Override
			public void dispose() {
				super.unbind(dependencies);
			}
	
			@Override
			protected BigDecimal computeValue() {
				return op1.get().divide(op2.get());
			}
	
			@Override
	
			public ObservableList<?> getDependencies() {
				return (dependencies.length == 1)?
						FXCollections.singletonObservableList(dependencies[0])
						: new ImmutableObservableList<Observable>(dependencies);
			}
		};
	}

	public static BigDecimalBinding divide(final ObservableBigDecimalValue op1, final ObservableBigDecimalValue op2) {
		return Bindings.divide(op1, op2, op1, op2);
	}

	public static BigDecimalBinding divide(final ObservableBigDecimalValue op1, double op2) {
		return (BigDecimalBinding) Bindings.divide(op1, BigDecimalConstant.valueOf(op2), op1);
	}

	public static BigDecimalBinding divide(final ObservableBigDecimalValue op1, int op2) {
		return (BigDecimalBinding) Bindings.divide(op1, BigDecimalConstant.valueOf(op2), op1);
	}

	public static BigDecimalBinding divide(final ObservableBigDecimalValue op1, long op2) {
		return (BigDecimalBinding) Bindings.divide(op1, BigDecimalConstant.valueOf(op2), op1);
	}

	public static BigDecimalBinding divide(double op1, final ObservableBigDecimalValue op2) {
		return (BigDecimalBinding) Bindings.divide(BigDecimalConstant.valueOf(op1), op2, op2);
	}

	public static BigDecimalBinding divide(int op1, final ObservableBigDecimalValue op2) {
		return (BigDecimalBinding) Bindings.divide(BigDecimalConstant.valueOf(op1), op2, op2);
	}

	public static BigDecimalBinding divide(long op1,final ObservableBigDecimalValue op2) {
		return (BigDecimalBinding) Bindings.divide(BigDecimalConstant.valueOf(op1), op2, op2);
	}
	// =================================================================================================================
	// Equals
	
	private static BooleanBinding equal(final ObservableBigDecimalValue op1, final ObservableBigDecimalValue op2, final Observable... dependencies) {
		if ((op1 == null) || (op2 == null)) {
			throw new NullPointerException("Operands cannot be null.");
		}
		assert (dependencies != null) && (dependencies.length > 0);
	
			return new BooleanBinding() {
				{
					super.bind(dependencies);
				}
	
				@Override
				public void dispose() {
					super.unbind(dependencies);
				}
	
				@Override
				protected boolean computeValue() {
					return op1.get().compareTo(op2.get()) == 0;
				}
	
				@Override
	
				public ObservableList<?> getDependencies() {
					return (dependencies.length == 1)?
							FXCollections.singletonObservableList(dependencies[0])
							: new ImmutableObservableList<Observable>(dependencies);
				}
			};
	}

	public static BooleanBinding equal(final ObservableBigDecimalValue op1, final ObservableBigDecimalValue op2) {
		return Bindings.equal(op1, op2, op1, op2);
	}

	public static BooleanBinding equal(final ObservableBigDecimalValue op1, double op2) {
		return (BooleanBinding) Bindings.equal(op1, BigDecimalConstant.valueOf(op2), op1);
	}

	public static BooleanBinding equal(final ObservableBigDecimalValue op1, int op2) {
		return (BooleanBinding) Bindings.equal(op1, BigDecimalConstant.valueOf(op2), op1);
	}

	public static BooleanBinding equal(final ObservableBigDecimalValue op1, long op2) {
		return (BooleanBinding) Bindings.equal(op1, BigDecimalConstant.valueOf(op2), op1);
	}

	public static BooleanBinding equal(double op1, final ObservableBigDecimalValue op2) {
		return (BooleanBinding) Bindings.equal(BigDecimalConstant.valueOf(op1), op2, op2);
	}

	public static BooleanBinding equal(int op1, final ObservableBigDecimalValue op2) {
		return (BooleanBinding) Bindings.equal(BigDecimalConstant.valueOf(op1), op2, op2);
	}

	public static BooleanBinding equal(long op1,final ObservableBigDecimalValue op2) {
		return (BooleanBinding) Bindings.equal(BigDecimalConstant.valueOf(op1), op2, op2);
	}

	// =================================================================================================================
	// Not Equals
	
	private static BooleanBinding notEqual(final ObservableBigDecimalValue op1, final ObservableBigDecimalValue op2, final Observable... dependencies) {
		if ((op1 == null) || (op2 == null)) {
			throw new NullPointerException("Operands cannot be null.");
		}
		assert (dependencies != null) && (dependencies.length > 0);
	
			return new BooleanBinding() {
				{
					super.bind(dependencies);
				}
	
				@Override
				public void dispose() {
					super.unbind(dependencies);
				}
	
				@Override
				protected boolean computeValue() {
					return op1.get().compareTo(op2.get()) != 0;
				}
	
				@Override
	
				public ObservableList<?> getDependencies() {
					return (dependencies.length == 1)?
							FXCollections.singletonObservableList(dependencies[0])
							: new ImmutableObservableList<Observable>(dependencies);
				}
			};
	}

	public static BooleanBinding notEqual(final ObservableBigDecimalValue op1, final ObservableBigDecimalValue op2) {
		return Bindings.notEqual(op1, op2, op1, op2);
	}

	public static BooleanBinding notEqual(final ObservableBigDecimalValue op1, double op2) {
		return (BooleanBinding) Bindings.notEqual(op1, BigDecimalConstant.valueOf(op2), op1);
	}

	public static BooleanBinding notEqual(final ObservableBigDecimalValue op1, int op2) {
		return (BooleanBinding) Bindings.notEqual(op1, BigDecimalConstant.valueOf(op2), op1);
	}

	public static BooleanBinding notEqual(final ObservableBigDecimalValue op1, long op2) {
		return (BooleanBinding) Bindings.notEqual(op1, BigDecimalConstant.valueOf(op2), op1);
	}
	
	public static BooleanBinding notEqual(double op1, final ObservableBigDecimalValue op2) {
		return (BooleanBinding) Bindings.notEqual(BigDecimalConstant.valueOf(op1), op2, op2);
	}

	public static BooleanBinding notEqual(int op1, final ObservableBigDecimalValue op2) {
		return (BooleanBinding) Bindings.notEqual(BigDecimalConstant.valueOf(op1), op2, op2);
	}

	public static BooleanBinding notEqual(long op1,final ObservableBigDecimalValue op2) {
		return (BooleanBinding) Bindings.notEqual(BigDecimalConstant.valueOf(op1), op2, op2);
	}

	// =================================================================================================================
	// greaterThan
	
	private static BooleanBinding greaterThan(final ObservableBigDecimalValue op1, final ObservableBigDecimalValue op2, final Observable... dependencies) {
		if ((op1 == null) || (op2 == null)) {
			throw new NullPointerException("Operands cannot be null.");
		}
		assert (dependencies != null) && (dependencies.length > 0);
	
			return new BooleanBinding() {
				{
					super.bind(dependencies);
				}
	
				@Override
				public void dispose() {
					super.unbind(dependencies);
				}
	
				@Override
				protected boolean computeValue() {
					return op1.get().compareTo(op2.get()) > 0;
				}
	
				@Override
	
				public ObservableList<?> getDependencies() {
					return (dependencies.length == 1)?
							FXCollections.singletonObservableList(dependencies[0])
							: new ImmutableObservableList<Observable>(dependencies);
				}
			};
	}

	public static BooleanBinding greaterThan(final ObservableBigDecimalValue op1, final ObservableBigDecimalValue op2) {
		return Bindings.greaterThan(op1, op2, op1, op2);
	}

	public static BooleanBinding greaterThan(final ObservableBigDecimalValue op1, double op2) {
		return (BooleanBinding) Bindings.greaterThan(op1, BigDecimalConstant.valueOf(op2), op1);
	}

	public static BooleanBinding greaterThan(final ObservableBigDecimalValue op1, int op2) {
		return (BooleanBinding) Bindings.greaterThan(op1, BigDecimalConstant.valueOf(op2), op1);
	}

	public static BooleanBinding greaterThan(final ObservableBigDecimalValue op1, long op2) {
		return (BooleanBinding) Bindings.greaterThan(op1, BigDecimalConstant.valueOf(op2), op1);
	}
	
	public static BooleanBinding greaterThan(double op1, final ObservableBigDecimalValue op2) {
		return (BooleanBinding) Bindings.greaterThan(BigDecimalConstant.valueOf(op1), op2, op2);
	}

	public static BooleanBinding greaterThan(int op1, final ObservableBigDecimalValue op2) {
		return (BooleanBinding) Bindings.greaterThan(BigDecimalConstant.valueOf(op1), op2, op2);
	}

	public static BooleanBinding greaterThan(long op1,final ObservableBigDecimalValue op2) {
		return (BooleanBinding) Bindings.greaterThan(BigDecimalConstant.valueOf(op1), op2, op2);
	}
	// =================================================================================================================
	// greaterThan
	
	private static BooleanBinding lessThan(final ObservableBigDecimalValue op1, final ObservableBigDecimalValue op2, final Observable... dependencies) {
		if ((op1 == null) || (op2 == null)) {
			throw new NullPointerException("Operands cannot be null.");
		}
		assert (dependencies != null) && (dependencies.length > 0);
	
			return new BooleanBinding() {
				{
					super.bind(dependencies);
				}
	
				@Override
				public void dispose() {
					super.unbind(dependencies);
				}
	
				@Override
				protected boolean computeValue() {
					return op1.get().compareTo(op2.get()) < 0;
				}
	
				@Override
	
				public ObservableList<?> getDependencies() {
					return (dependencies.length == 1)?
							FXCollections.singletonObservableList(dependencies[0])
							: new ImmutableObservableList<Observable>(dependencies);
				}
			};
	}

	public static BooleanBinding lessThan(final ObservableBigDecimalValue op1, final ObservableBigDecimalValue op2) {
		return Bindings.lessThan(op1, op2, op1, op2);
	}

	public static BooleanBinding lessThan(final ObservableBigDecimalValue op1, double op2) {
		return (BooleanBinding) Bindings.lessThan(op1, BigDecimalConstant.valueOf(op2), op1);
	}

	public static BooleanBinding lessThan(final ObservableBigDecimalValue op1, int op2) {
		return (BooleanBinding) Bindings.lessThan(op1, BigDecimalConstant.valueOf(op2), op1);
	}

	public static BooleanBinding lessThan(final ObservableBigDecimalValue op1, long op2) {
		return (BooleanBinding) Bindings.lessThan(op1, BigDecimalConstant.valueOf(op2), op1);
	}
	
	public static BooleanBinding lessThan(double op1, final ObservableBigDecimalValue op2) {
		return (BooleanBinding) Bindings.lessThan(BigDecimalConstant.valueOf(op1), op2, op2);
	}

	public static BooleanBinding lessThan(int op1, final ObservableBigDecimalValue op2) {
		return (BooleanBinding) Bindings.lessThan(BigDecimalConstant.valueOf(op1), op2, op2);
	}

	public static BooleanBinding lessThan(long op1,final ObservableBigDecimalValue op2) {
		return (BooleanBinding) Bindings.lessThan(BigDecimalConstant.valueOf(op1), op2, op2);
	}
	// =================================================================================================================
	// greaterThan
	
	private static BooleanBinding greaterThanOrEqual(final ObservableBigDecimalValue op1, final ObservableBigDecimalValue op2, final Observable... dependencies) {
		if ((op1 == null) || (op2 == null)) {
			throw new NullPointerException("Operands cannot be null.");
		}
		assert (dependencies != null) && (dependencies.length > 0);
	
			return new BooleanBinding() {
				{
					super.bind(dependencies);
				}
	
				@Override
				public void dispose() {
					super.unbind(dependencies);
				}
	
				@Override
				protected boolean computeValue() {
					return op1.get().compareTo(op2.get()) >= 0;
				}
	
				@Override
	
				public ObservableList<?> getDependencies() {
					return (dependencies.length == 1)?
							FXCollections.singletonObservableList(dependencies[0])
							: new ImmutableObservableList<Observable>(dependencies);
				}
			};
	}

	public static BooleanBinding greaterThanOrEqual(final ObservableBigDecimalValue op1, final ObservableBigDecimalValue op2) {
		return Bindings.greaterThanOrEqual(op1, op2, op1, op2);
	}

	public static BooleanBinding greaterThanOrEqual(final ObservableBigDecimalValue op1, double op2) {
		return (BooleanBinding) Bindings.greaterThanOrEqual(op1, BigDecimalConstant.valueOf(op2), op1);
	}

	public static BooleanBinding greaterThanOrEqual(final ObservableBigDecimalValue op1, int op2) {
		return (BooleanBinding) Bindings.greaterThanOrEqual(op1, BigDecimalConstant.valueOf(op2), op1);
	}

	public static BooleanBinding greaterThanOrEqual(final ObservableBigDecimalValue op1, long op2) {
		return (BooleanBinding) Bindings.greaterThanOrEqual(op1, BigDecimalConstant.valueOf(op2), op1);
	}
	
	public static BooleanBinding greaterThanOrEqual(double op1, final ObservableBigDecimalValue op2) {
		return (BooleanBinding) Bindings.greaterThanOrEqual(BigDecimalConstant.valueOf(op1), op2, op2);
	}

	public static BooleanBinding greaterThanOrEqual(int op1, final ObservableBigDecimalValue op2) {
		return (BooleanBinding) Bindings.greaterThanOrEqual(BigDecimalConstant.valueOf(op1), op2, op2);
	}

	public static BooleanBinding greaterThanOrEqual(long op1,final ObservableBigDecimalValue op2) {
		return (BooleanBinding) Bindings.greaterThanOrEqual(BigDecimalConstant.valueOf(op1), op2, op2);
	}
	// =================================================================================================================
	// greaterThan
	
	private static BooleanBinding lessThanOrEqual(final ObservableBigDecimalValue op1, final ObservableBigDecimalValue op2, final Observable... dependencies) {
		if ((op1 == null) || (op2 == null)) {
			throw new NullPointerException("Operands cannot be null.");
		}
		assert (dependencies != null) && (dependencies.length > 0);
	
			return new BooleanBinding() {
				{
					super.bind(dependencies);
				}
	
				@Override
				public void dispose() {
					super.unbind(dependencies);
				}
	
				@Override
				protected boolean computeValue() {
					return op1.get().compareTo(op2.get()) <= 0;
				}
	
				@Override
	
				public ObservableList<?> getDependencies() {
					return (dependencies.length == 1)?
							FXCollections.singletonObservableList(dependencies[0])
							: new ImmutableObservableList<Observable>(dependencies);
				}
			};
	}

	public static BooleanBinding lessThanOrEqual(final ObservableBigDecimalValue op1, final ObservableBigDecimalValue op2) {
		return Bindings.lessThanOrEqual(op1, op2, op1, op2);
	}

	public static BooleanBinding lessThanOrEqual(final ObservableBigDecimalValue op1, double op2) {
		return (BooleanBinding) Bindings.lessThanOrEqual(op1, BigDecimalConstant.valueOf(op2), op1);
	}

	public static BooleanBinding lessThanOrEqual(final ObservableBigDecimalValue op1, int op2) {
		return (BooleanBinding) Bindings.lessThanOrEqual(op1, BigDecimalConstant.valueOf(op2), op1);
	}

	public static BooleanBinding lessThanOrEqual(final ObservableBigDecimalValue op1, long op2) {
		return (BooleanBinding) Bindings.lessThanOrEqual(op1, BigDecimalConstant.valueOf(op2), op1);
	}
	
	public static BooleanBinding lessThanOrEqual(double op1, final ObservableBigDecimalValue op2) {
		return (BooleanBinding) Bindings.lessThanOrEqual(BigDecimalConstant.valueOf(op1), op2, op2);
	}

	public static BooleanBinding lessThanOrEqual(int op1, final ObservableBigDecimalValue op2) {
		return (BooleanBinding) Bindings.lessThanOrEqual(BigDecimalConstant.valueOf(op1), op2, op2);
	}

	public static BooleanBinding lessThanOrEqual(long op1,final ObservableBigDecimalValue op2) {
		return (BooleanBinding) Bindings.lessThanOrEqual(BigDecimalConstant.valueOf(op1), op2, op2);
	}
	
	
	
	//Adicionado para cumprir contrato com bindings original do javafx.
	
	
    /**
     * Creates a {@link javafx.beans.binding.StringExpression} that holds the
     * value of multiple {@code Objects} formatted according to a format
     * {@code String}.
     * <p>
     * If one of the arguments implements
     * {@link javafx.beans.value.ObservableValue} and the value of this
     * {@code ObservableValue} changes, the change is automatically reflected in
     * the {@code StringExpression}.
     * <p>
     * See {@code java.util.Formatter} for formatting rules.
     *
     * @param format
     *            the formatting {@code String}
     * @param args
     *            the {@code Objects} that should be inserted in the formatting
     *            {@code String}
     * @return the new {@code StringExpression}
     */
    public static StringExpression format(String format, Object... args) {
        return StringFormatter.format(format, args);
    }

    /**
     * Creates a {@link javafx.beans.binding.StringExpression} that holds the
     * value of multiple {@code Objects} formatted according to a format
     * {@code String} and a specified {@code Locale}
     * <p>
     * If one of the arguments implements
     * {@link javafx.beans.value.ObservableValue} and the value of this
     * {@code ObservableValue} changes, the change is automatically reflected in
     * the {@code StringExpression}.
     * <p>
     * See {@code java.util.Formatter} for formatting rules. See
     * {@code java.util.Locale} for details on {@code Locale}.
     *
     * @param locale
     *            the {@code Locale} to use during formatting
     * @param format
     *            the formatting {@code String}
     * @param args
     *            the {@code Objects} that should be inserted in the formatting
     *            {@code String}
     * @return the new {@code StringExpression}
     */
    public static StringExpression format(Locale locale, String format,
            Object... args) {
        return StringFormatter.format(locale, format, args);
    }

}
