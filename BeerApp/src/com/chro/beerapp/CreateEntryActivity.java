package com.chro.beerapp;

import java.util.Calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.woodchro.bemystore.R;

public class CreateEntryActivity extends ActionBarActivity {

	Button btn_CreateEntry;
	Toolbar toolbar;
	SeekBar sbarPrice;
	SeekBar sbarAmount;
	EditText etextPrice;
	EditText etextAmount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_entry);
		initTime();
		addListenerOnButton();

		toolbar = (Toolbar) findViewById(R.id.toolbar);

		if (toolbar != null) {
			setSupportActionBar(toolbar);
		}
		ActionBar supportActionBar = getSupportActionBar();
		supportActionBar.setTitle(getString(R.string.Btn_CreateEntry));
		supportActionBar.setDisplayHomeAsUpEnabled(true);

		sbarPrice = (SeekBar) findViewById(R.id.sb_price);
		etextPrice = (EditText) findViewById(R.id.edit_price);

		sbarAmount = (SeekBar) findViewById(R.id.sb_amount);
		etextAmount = (EditText) findViewById(R.id.edit_amount);

		// connect seekbar with edittext for price
		sbarPrice.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// ---change the font size of the EditText---
				float value = (float) progress / 2;
				etextPrice.setText(String.valueOf(value) + "0");
				etextPrice.setSelection(etextPrice.getText().length());
			}
		});

		etextPrice.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				try {
					// Update Seekbar value after entering a number
					// sbarPrice.setProgress(Integer.parseInt(s.toString()));
				} catch (Exception ex) {
				}
			}

		});

		sbarAmount.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// ---change the font size of the EditText---

				etextAmount.setText(String.valueOf(progress));
				etextAmount.setSelection(etextAmount.getText().length());
			}
		});

		etextAmount.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				try {
					// Update Seekbar value after entering a number
					// sbarPrice.setProgress(Integer.parseInt(s.toString()));
				} catch (Exception ex) {
				}
			}

		});

	}

	DatabaseHandler db = new DatabaseHandler(this);

	public void showTimePickerDialog(View v) {
		DialogFragment newFragment = new TimePickerFragment();
		newFragment.show(getSupportFragmentManager(), "timePicker");
	}

	public void addListenerOnButton() {
		btn_CreateEntry = (Button) findViewById(R.id.btn_Save_New_Entry);
		btn_CreateEntry.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int i = 1;
				ConnectionAdd Request = new ConnectionAdd(getApplicationContext());
				TextView timeBegin 	= (TextView) findViewById(R.id.timeBegin);
				TextView endBegin 	= (TextView) findViewById(R.id.timeEnd);
				CheckBox retry   = (CheckBox) findViewById(R.id.checkBox1);
				Spinner Kategorie 	= (Spinner) findViewById(R.id.spinner1);
				TextView productName= (TextView) findViewById(R.id.editText3);
				TextView price		= (TextView) findViewById(R.id.edit_price);
				TextView amount		= (TextView) findViewById(R.id.edit_amount);
				TextView contact	= (TextView) findViewById(R.id.txt_Handy_Number);
				String s = String.valueOf(retry.isChecked());
				Request.execute("0", timeBegin.getText().toString(), endBegin
						.getText().toString(), Kategorie.getSelectedItem()
						.toString(), price.getText().toString(), amount
						.getText().toString(), contact.getText().toString(), s,
						productName.getText().toString());
				// Entry entry1 = new
				// Entry(i,"entry"+i,i*0,67,"details",234,456,"2014-04-23 16:29","2014-04-23 18:29");
				// db.addEntry(entry1);
				// Intent intent = new
				// Intent(getApplicationContext(),EntryActivity.class);
				// startActivity(intent);
			}
		});
	}

	public void initTime() {

		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		Log.i("ausgabe minute", "minute=" + minute);

		TextView timeBegin = (TextView) findViewById(R.id.timeBegin);
		if (minute < 10)
			timeBegin.setText(hour + ":" + "0" + minute);
		else
			timeBegin.setText(hour + ":" + minute);

		hour = hour + 2;
		TextView timeEnd = (TextView) findViewById(R.id.timeEnd);
		if (minute < 10)
			timeEnd.setText(hour + ":" + "0" + minute);
		else
			timeEnd.setText(hour + ":" + minute);

	}

}
