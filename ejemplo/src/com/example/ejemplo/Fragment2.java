package com.example.ejemplo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

public class Fragment2 extends Fragment{

	Calendar myCalendar = Calendar.getInstance();
	EditText birthdate;
	DatePickerDialog.OnDateSetListener date;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_2, container, false);
	}
	
	  public void onActivityCreated(Bundle savedInstanceState)
	    {
	        super.onActivityCreated(savedInstanceState);
	     // Create the spinner
			Spinner spinner = (Spinner) getView().findViewById(R.id.gender_spinner);
			// Create an ArrayAdapter using the string array and a default spinner layout
			ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
			        R.array.gender_array, android.R.layout.simple_spinner_item);
			// Specify the layout to use when the list of choices appears
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			// Apply the adapter to the spinner
			spinner.setAdapter(adapter);
			
			birthdate = (EditText) getView().findViewById(R.id.textedit_birthdate);

			date = new DatePickerDialog.OnDateSetListener() {

			    @Override
			    public void onDateSet(DatePicker view, int year, int monthOfYear,
			            int dayOfMonth) {
			        // TODO Auto-generated method stub
			        myCalendar.set(Calendar.YEAR, year);
			        myCalendar.set(Calendar.MONTH, monthOfYear);
			        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			        updateLabel();
			    }

			};
			
			birthdate.setOnClickListener(
					new View.OnClickListener(){
						@Override
				        public void onClick(View v) {
				            // TODO Auto-generated method stub
							new DatePickerDialog(getActivity(), date, 
									myCalendar.get(Calendar.YEAR), 
									myCalendar.get(Calendar.MONTH), 
									myCalendar.get(Calendar.DAY_OF_MONTH)).show();
				        }
					}
			);
	    }
	  
	
	private void updateLabel() {
	    String myFormat = "dd/MM/yyyy"; //In which you need put here
	    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMANY);

	    birthdate.setText(sdf.format(myCalendar.getTime()));
    }

	/*@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}*/
	
	public void onCheckboxClicked(View view) {
	    // Is the view now checked?
	    boolean checked = ((CheckBox) view).isChecked();
	    
	    AlertDialog.Builder alert_add = new AlertDialog.Builder(getActivity());
	    alert_add.setTitle("Interests");
	    alert_add.setMessage("Are you sure you want to add "+((CheckBox) view).getText()+"?");
	    alert_add.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) { 
            	// do nothing
            }
         });
	    alert_add.setIcon(android.R.drawable.ic_dialog_alert);
	    
	    AlertDialog.Builder alert_delete = new AlertDialog.Builder(getActivity());
	    alert_delete.setTitle("Interests");
	    alert_delete.setMessage("Are you sure you want to delete "+((CheckBox) view).getText()+"?");
	    alert_delete.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) { 
            	// do nothing
            }
         });
	    alert_delete.setIcon(android.R.drawable.ic_dialog_alert);
	    
	    // Check which checkbox was clicked
	    switch(view.getId()) {
	        case R.id.checkbox_sports:
	            if (checked)
	            	alert_add.show();
	            else
	            	alert_delete.show();
	            break;
	        case R.id.checkbox_party:
	            if (checked)
	            	alert_add.show();
	            else
	            	alert_delete.show();
	            break;
	        case R.id.checkbox_music:
	            if (checked)
	            	alert_add.show();
	            else
	            	alert_delete.show();
	            break;
	    }
	}
}
