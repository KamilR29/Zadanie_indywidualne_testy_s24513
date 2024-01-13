package test.b_Money;

import static org.junit.Assert.*;

import b_Money.*;
import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;

	@Before
	public void setUp() throws Exception {

		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
		SweBank.deposit("Bob", new Money(10000000, SEK));
	}

	@Test
	public void testGetName() {

		assertEquals("Nordea", Nordea.getName());
		assertEquals("SweBank", SweBank.getName());
		assertEquals("DanskeBank", DanskeBank.getName());
	}

	@Test
	public void testGetCurrency() {

		assertEquals(SEK, Nordea.getCurrency());
		assertEquals(SEK, SweBank.getCurrency());
		assertEquals(DKK, DanskeBank.getCurrency());
	}

	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {

		String accountId = "account";
		SweBank.openAccount(accountId);
		assertEquals(0, SweBank.getBalance(accountId), 0.02);
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {

		SweBank.deposit("Bob", new Money(100, SEK));
		assertEquals(100001.0, SweBank.getBalance("Bob"), 0.02);
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {

		SweBank.withdraw("Bob", new Money(10000000, SEK));
		assertEquals(0.0, SweBank.getBalance("Bob"), 0.02);

	}

	@Test
	public void testGetBalance() throws AccountDoesNotExistException {

		assertEquals(0, SweBank.getBalance("Ulrika"), 0.02);

	}

	@Test
	public void testTransfer() throws AccountDoesNotExistException {

		Money money = new Money(6000000, SEK);

		SweBank.transfer("Bob", Nordea, "Bob", money);
		assertEquals(40000.0, SweBank.getBalance("Bob"), 0.02);
		assertEquals(60000.0, Nordea.getBalance("Bob"), 0.02);

		Money money2 = new Money(3000000, SEK);

		SweBank.transfer("Bob", "Ulrika", money2);
		assertEquals(10000.0, SweBank.getBalance("Bob"), 0.02);
		assertEquals(30000.0, SweBank.getBalance("Ulrika"), 0.02);

	}

	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {

		String timedPaymentId = "payment";
		String fromAccountId = "Ulrika";
		String toAccountId = "Bob";
		Account fromAccount = SweBank.getAccount(fromAccountId);

		SweBank.addTimedPayment(fromAccountId, timedPaymentId, 0, 100, new Money(0, SEK), SweBank, toAccountId);
		assertTrue("exists", fromAccount.timedPaymentExists(timedPaymentId));

		SweBank.removeTimedPayment(fromAccountId, timedPaymentId);
		assertFalse("not exists", fromAccount.timedPaymentExists(timedPaymentId));
	}
}
