package net.sitina.android.expenses;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ExpensesEdit extends Activity {

	private EditText mTitleText;
    private EditText mBodyText;
    private EditText mAmount;
    private Long mRowId;
    private ExpensesDbAdapter mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbHelper = new ExpensesDbAdapter(this);
        mDbHelper.open();
        setContentView(R.layout.expenses_edit);
        
       
        mTitleText = (EditText) findViewById(R.id.title);
        mBodyText = (EditText) findViewById(R.id.body);
        mAmount = (EditText) findViewById(R.id.amount);
      
        Button confirmButton = (Button) findViewById(R.id.confirm);
       
        mRowId = savedInstanceState != null ? savedInstanceState.getLong(ExpensesDbAdapter.KEY_ROWID) 
                							: null;
		if (mRowId == null) {
			Bundle extras = getIntent().getExtras();            
			mRowId = extras != null ? extras.getLong(ExpensesDbAdapter.KEY_ROWID) 
									: null;
		}

		populateFields();
		
        confirmButton.setOnClickListener(new View.OnClickListener() {

        	public void onClick(View view) {
        	    setResult(RESULT_OK);
        	    finish();
        	}
          
        });
    }
    
    private void populateFields() {
        if (mRowId != null) {
            Cursor note = mDbHelper.fetchExpense(mRowId);
            startManagingCursor(note);
            
            String title = note.getString(note.getColumnIndexOrThrow(ExpensesDbAdapter.KEY_TITLE));
            String body = note.getString(note.getColumnIndexOrThrow(ExpensesDbAdapter.KEY_BODY));            
            Double amount = note.getDouble(note.getColumnIndexOrThrow(ExpensesDbAdapter.KEY_AMOUNT));
            
            mTitleText.setText(title != null ? title : "");
            mBodyText.setText(body != null ? body : "");
            mAmount.setText(amount != null ? amount.toString() : "0.00");
        }
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(ExpensesDbAdapter.KEY_ROWID, mRowId);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        saveState();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        populateFields();
    }
    
    private void saveState() {
        String title = mTitleText.getText().toString();
        String body = mBodyText.getText().toString();
        Double amount = Double.valueOf(mAmount.getText().toString());

        if (mRowId == null) {
            long id = mDbHelper.createExpense(title, body, amount, null);
            if (id > 0) {
                mRowId = id;
            }
        } else {        	
            mDbHelper.updateExpense(mRowId, title, body, amount);
        }
    }
    
}