package test.b_Money;

import b_Money.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK;
	Bank SweBank;
	Account testAccount;

	@Before
	public void setUp() throws Exception {

		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));
		SweBank.deposit("Alice", new Money(1000000, SEK));
	}

	@Test
	public void testAddRemoveTimedPayment() {

		String timedPaymentId = "account";

		testAccount.addTimedPayment(timedPaymentId, 0, 100, new Money(0, SEK), SweBank, "Hans");
		assertTrue("exists", testAccount.timedPaymentExists(timedPaymentId));

		testAccount.removeTimedPayment(timedPaymentId);
		assertFalse("not exists", testAccount.timedPaymentExists(timedPaymentId));
	}

	@Test
	public void testDeposit() {

		testAccount.deposit(new Money(100, SEK));
		assertEquals(100001.0, testAccount.getBalance().getAmount(), 0.02);
	}

	@Test
	public void testAddWithdraw() {

		testAccount.withdraw(new Money(10000000, SEK));
		assertEquals(0.0, testAccount.getBalance().getAmount(), 0.02);
	}

	@Test
	public void testGetBalance() {

		assertEquals(100000.0, testAccount.getBalance().getAmount(), 0.02);
	}
}
