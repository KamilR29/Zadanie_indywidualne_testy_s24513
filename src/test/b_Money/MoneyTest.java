package test.b_Money;

import static org.junit.Assert.*;

import b_Money.Currency;
import b_Money.Money;
import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;

	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {

		assertEquals(20.00, EUR20.getAmount(), 0.01);
		assertEquals(0.00, SEK0.getAmount(), 0.01);
		assertEquals(100.00, SEK100.getAmount(), 0.01);
		assertEquals(10.00, EUR10.getAmount(), 0.01);
		assertEquals(0.00, EUR0.getAmount(), 0.01);
		assertEquals(-100.00, SEKn100.getAmount(), 0.01);
		assertEquals(200.00, SEK200.getAmount(), 0.01);

	}

	@Test
	public void testGetCurrency() {

		assertEquals(SEK, SEK100.getCurrency());
		assertEquals(EUR, EUR10.getCurrency());
	}

	@Test
	public void testToString() {

		assertEquals("100.0 SEK", SEK100.toString());
		assertEquals("10.0 EUR", EUR10.toString());
		assertEquals("20.0 EUR", EUR20.toString());
		assertEquals("0.0 SEK", SEK0.toString());
		assertEquals("0.0 EUR", EUR0.toString());
		assertEquals("200.0 SEK", SEK200.toString());
		assertEquals("-100.0 SEK", SEKn100.toString());
	}

	@Test
	public void testGlobalValue() {

		assertEquals(1500, EUR10.universalValue());
		assertEquals(3000, SEK200.universalValue());
		assertEquals(3000, EUR20.universalValue());
		assertEquals(0, SEK0.universalValue());
		assertEquals(1500, SEK100.universalValue());
		assertEquals(0, EUR0.universalValue());
		assertEquals(-1500, SEKn100.universalValue());
	}

	@Test
	public void testEqualsMoney() {

		String good = " equal";
		String notgood = "not equal";
		assertTrue(good, SEK100.equals(EUR10));
		assertFalse(notgood, SEK100.equals(EUR0));
		assertFalse(notgood, SEK100.equals(SEKn100));
	}

	@Test
	public void testAdd() {
		Money Sek200AddEur10 = new Money(30000, SEK);
		Money Eur10AddSek100 = new Money(2000, EUR);
		Money Sek100AddEur10 = new Money(20000, SEK);

		assertEquals(Sek200AddEur10.toString(), SEK200.add(EUR10).toString());
		assertEquals(Eur10AddSek100.toString(), EUR10.add(SEK100).toString());
		assertEquals(Sek100AddEur10.toString(), SEK100.add(EUR10).toString());


	}

	@Test
	public void testSub() {
		Money Sek200SubEur10 = new Money(10000, SEK);
		Money Eur10SubSek100 = new Money(0, EUR);
		Money Sek100SubEur10 = new Money(0, SEK);


		assertEquals(Sek200SubEur10.toString(), SEK200.sub(EUR10).toString());
		assertEquals(Eur10SubSek100.toString(), EUR10.sub(SEK100).toString());
		assertEquals(Sek100SubEur10.toString(), SEK100.sub(EUR10).toString());


	}

	@Test
	public void testIsZero() {
		String good = " zero";
		String notgood = "not zero";

		assertTrue(good, SEK0.isZero());
		assertTrue(good, EUR0.isZero());

		assertFalse(notgood, SEK100.isZero());
		assertFalse(notgood, EUR10.isZero());
		assertFalse(notgood, SEK200.isZero());
		assertFalse(notgood, EUR20.isZero());
		assertFalse(notgood, SEKn100.isZero());
	}

	@Test
	public void testNegate() {
		Money Eur10reverse = new Money(-1000, EUR);
		Money Sek100reverse = new Money(-10000, SEK);
		Money Sek200reverse = new Money(-20000, SEK);



		assertEquals(Eur10reverse.toString(), EUR10.negate().toString());
		assertEquals(Sek100reverse.toString(), SEK100.negate().toString());
		assertEquals(Sek200reverse.toString(), SEK200.negate().toString());


	}

	@Test
	public void testCompareTo() {
		assertEquals(0, SEK100.compareTo(EUR10));
		assertEquals(0, SEK200.compareTo(EUR20));
		assertEquals(0, SEK0.compareTo(EUR0));

		assertEquals(-1, SEK100.compareTo(EUR20));
		assertEquals(-1, EUR0.compareTo(SEK200));

		assertEquals(1, SEK200.compareTo(EUR10));
		assertEquals(1, EUR10.compareTo(SEK0));
	}
}
