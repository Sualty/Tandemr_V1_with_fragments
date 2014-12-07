package com.example.ejemplo;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;

//the profile of another user 's profile
public class Fragment3 extends Fragment{
	//sharedpreferences allow us to store some variables (like int, string , etc) which 
	//can be read and wrote . We need it for example for the social points of a user : it's
	//not static, it will have to increase . We can't use XML for that, because with XML
	//we just can read the variables and to modify them .

	SharedPreferences sharedpreferences;
	public static final String MyPREFERENCES = "MyPrefs" ;
	public static final String foreign_social_points ="foreign social points";

	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_3, container, false);
	}

    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
      //Displaying of the round profile image

      		Bitmap foreign_image = BitmapFactory.decodeResource(getResources(),R.drawable.pinguin);
      		setBitmapClippedCircle(foreign_image);

      		//Personnal message
      		setMessage();

      		//intializing sharedpreferences
      		//creating the entry of the social points of another user . THIS IS TEMPORARY.
      		//because when we will use bluetooth, we'll get this info directly from the other user
      		sharedpreferences = super.getActivity().getSharedPreferences(MyPREFERENCES, 0);
      		SharedPreferences.Editor editor = sharedpreferences.edit();

      		if(!sharedpreferences.contains(foreign_social_points)){
      			editor.putInt(foreign_social_points, 0);
      			editor.commit();
      		}

      		//Getting the social points via sharedpreference and displaying
      		int points = sharedpreferences.getInt(foreign_social_points, -1);
      		setPoints(points);



      		//init the button which permit to add a social point to the foreign user
      		Button add_1_point = (Button) getView().findViewById(R.id.add_1_social_point_button);
      		add_1_point.setOnClickListener(new View.OnClickListener() {
      			public void onClick(View v) {
      				//Intent intent =  new Intent(MainActivity.this, ProfileActivity.class);
      				//startActivity(intent);
      				SharedPreferences.Editor editor = sharedpreferences.edit();
      				int points = sharedpreferences.getInt(foreign_social_points, -1);
      				editor.putInt(foreign_social_points, points+1);
      				editor.commit();
      				TextView points_view = (TextView) getView().findViewById(R.id.foreign_points);
      				setPoints(points);

      			}
      		});

      		//init the button which is used to send a sms
      		Button send_sms = (Button) getView().findViewById(R.id.button_send_sms);
      		send_sms.setOnClickListener(new View.OnClickListener() {
      			@Override
      			public void onClick(View v) {
      				sendSMS();
      			}
      		});

      		if(getString(R.string.foreign_phone).length()>=6)
      			send_sms.setVisibility(View.VISIBLE);
       
    }


	/**
	 * Display an image  as the profile photo 
	 * @param bitmap
	 * @return
	 */
	public void setBitmapClippedCircle(Bitmap bitmap) {
		ImageView imageview = (ImageView) getView().findViewById(R.id.foreign_image);//nullpointerexception here

		bitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, false);

		final int width = bitmap.getWidth();
		final int height = bitmap.getHeight();
		final Bitmap outputBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);

		final Path path = new Path();
		
		path.addCircle(
				(float)(width / 2)
				, (float)(height / 2)
				, (float) Math.min(width, (height / 2))
				, Path.Direction.CCW);
		
		final Canvas canvas = new Canvas(outputBitmap);
		canvas.clipPath(path);//nullpointereception here
		canvas.drawBitmap(bitmap, 0, 0, null);
		imageview.setImageBitmap(outputBitmap);//nullpointerexception here
	}

	/**
	 * Displaying the personnal message
	 * @return
	 */
	public void setMessage() {
		TextView message_view = (TextView) getView().findViewById(R.id.foreign_message);
		String message =  getString(R.string.foreign_message);
		String tmp ="";

		for(int i=0;i<message.length();i++){
			if((i%25 == 0) && (i!=0))
				tmp=tmp+"\n";

			String c = message.substring(i,i+1);
			tmp=tmp+c;

		}
		message_view.setText(tmp);//nullpoiterexception here
	}

	/**Displaying the social points
	 * 
	 */
	public void setPoints(int points) {		
		TextView points_view = (TextView) getView().findViewById(R.id.foreign_points);

		String tmp= points + " "+getString(R.string.points_text);
		points_view.setText(tmp);
	}

	/**
	 * Sending a SMS to the foreign user
	 */

	public void sendSMS() {
		String phone_number = getString(R.string.foreign_phone);
		Intent smsIntent = new Intent(Intent.ACTION_VIEW);
		smsIntent.setData(Uri.parse("smsto:"));
		smsIntent.setType("vnd.android-dir/mms-sms");
		smsIntent.putExtra("address",new String(phone_number));
		smsIntent.putExtra("sms_body","Sms sent by a Tandemr user .\n");
		try {
			startActivity(smsIntent);
			super.getActivity().finish();
			Log.i("Finished sending SMS...", "");
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(Fragment3.super.getActivity(), 
					"SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		/*if (id == R.id.action_settings) {
			return true;
		}*/
		return super.onOptionsItemSelected(item);
	}
}
