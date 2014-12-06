package moose.tandemr;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
	}

	public void selectFrag(View view) {
		Fragment fr=null;

		if(view == findViewById(R.id.tmp_button_foreign_view)) {
			fr = new ForeignProfileActivity();
		}
		else if(view == findViewById(R.id.welcome_button))
			fr = new ProfileActivity();

		FragmentManager fm = getSupportFragmentManager();

		FragmentTransaction fragmentTransaction = fm.beginTransaction();
		fragmentTransaction.replace(R.id.fragment_place, fr);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();

	}
}
