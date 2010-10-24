package net.sitina.android.expenses.tests;

import net.sitina.android.expenses.ExpensesDbAdapter;
import android.database.Cursor;
import android.test.AndroidTestCase;

public class ExpensesDbAdapterTest extends AndroidTestCase {

	@Override
	protected void setUp() throws Exception {
		ExpensesDbAdapter expensesAdapter = new ExpensesDbAdapter(getContext());
		expensesAdapter.open();
		expensesAdapter.deleteAll();
		expensesAdapter.close();
		
		// TODO Auto-generated method stub
		super.setUp();
	}

	public void testCreateExpense() {
		ExpensesDbAdapter expensesAdapter = new ExpensesDbAdapter(getContext());
		expensesAdapter.open();
		expensesAdapter.createExpense("title", "body", Double.valueOf(333.33), null);
		
		Cursor allExpenses = expensesAdapter.fetchAllExpenses();
		
		assertEquals(1, allExpenses.getCount());
		allExpenses.moveToFirst();
        assertEquals("title", allExpenses.getString(1));
        assertEquals("body", allExpenses.getString(2));
        assertEquals(333.33, allExpenses.getDouble(3));
        
        allExpenses.close();
		
		expensesAdapter.close();
	}
	
	@Override
	protected void tearDown() throws Exception {
		ExpensesDbAdapter expensesAdapter = new ExpensesDbAdapter(getContext());
		expensesAdapter.open();
		expensesAdapter.deleteAll();
		expensesAdapter.close();

		// TODO Auto-generated method stub
		super.tearDown();
	}

	
}
