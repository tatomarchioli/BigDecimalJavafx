package br.com.fxport.tests;

import java.math.BigDecimal;

import br.com.fxport.properties.BigDecimalProperty;
import br.com.fxport.properties.SimpleBigDecimalProperty;


public class MainTest {

	public static void main(String[] args) {

		BigDecimalProperty p = new SimpleBigDecimalProperty(new BigDecimal("0.1"));
		BigDecimalProperty b = new SimpleBigDecimalProperty(new BigDecimal("1"));
		BigDecimalProperty c = new SimpleBigDecimalProperty(BigDecimal.ZERO);
		c.bind(p.add(b).add(0.002).divide(2).multiply(0.5));
		c.addListener((obs, oldv, newv)->{
			System.out.println(newv);
		});
		p.set(BigDecimal.valueOf(10));
		b.set(BigDecimal.valueOf(25.668));
	}

}
