package moose.tandemr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class AroundYou extends ListFragment {
	private int[] images = new int[] {
			R.drawable.pinguin,
			R.drawable.pinguin,
			R.drawable.pinguin,
			R.drawable.pinguin,
			R.drawable.pinguin,
			R.drawable.pinguin,
			R.drawable.pinguin,
			R.drawable.pinguin,
			R.drawable.pinguin,
			R.drawable.pinguin
	};

	private String[] names = new String[] {
			"Pingu",
			"Pingu",
			"Pingu",
			"Pingu",
			"Pingu",
			"Pingu",
			"Pingu",
			"Pingu",
			"Pingu",
			"Pingu"			
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		MyAdapter adapter = new MyAdapter(images, names);
		setListAdapter(adapter);

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Fragment fr=new ForeignProfileActivity();
		FragmentManager fm = getActivity().getSupportFragmentManager();

		FragmentTransaction fragmentTransaction = fm.beginTransaction();
		fragmentTransaction.replace(R.id.fragment_place, fr);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();

	}

	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		((MyAdapter) getListAdapter()).setImages();

	}

	private class MyAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		private int[] images;
		private String[] names;
		MyAdapter(int[] img,String[] txt) {
			mInflater = (LayoutInflater)AroundYou.this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			images = img;
			names = txt;
		}
		public void setImages() {
			for(int i = 0;i < 10;i++){
				View view = getView(i, null, getListView());
				ImageView imageview = (ImageView) view.findViewById(R.id.image);

				Bitmap foreign_image = BitmapFactory.decodeResource(getResources(), images[i]);
				Canvas canvas = null;
				Bitmap bitmap = getBitmapClippedCircle(foreign_image,200,200,canvas);
				//TODO :store the new image (bitmap) in a tmp rep . And when we are closing the app, the tmp rep is deleted
			}
			notifyDataSetChanged();
		}
		@Override
		public int getCount() {
			return names.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.around_you_line_of_list, null);
				holder = new ViewHolder();
				holder.textView = (TextView)convertView.findViewById(R.id.name);
				holder.imageView = (ImageView)convertView.findViewById(R.id.image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder)convertView.getTag();
			}
			holder.textView.setText(names[position]);
			holder.imageView.setImageResource(images[position]);
			return convertView;
		}
	}
	
	
	private static class ViewHolder {
		public ImageView imageView;
		public TextView textView;
	}
	
	public Bitmap getBitmapClippedCircle(Bitmap bitmap,int width,int height,Canvas canvas) {

		bitmap = Bitmap.createScaledBitmap(bitmap, width,height, false);

		final Bitmap outputBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);

		final Path path = new Path();

		path.addCircle(
				(float)(width / 2)
				, (float)(height / 2)
				, (float) Math.min(width, (height / 2))
				, Path.Direction.CCW);

		canvas = new Canvas(outputBitmap);
		canvas.clipPath(path);
		canvas.drawBitmap(bitmap, 0, 0, null);
		return outputBitmap;
	}
}




