package com.isadev.instagramclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import org.w3c.dom.Text;
import java.util.List;

/**
 * Created by Isaac on 7/11/2015.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
//What Data do we need from the activity
//context, Data Source
	public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
		super(context, android.R.layout.simple_list_item_1, objects);
	}
	//What our item looks like
	//Use the template to display each photo
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //Get the data item for this position
        InstagramPhoto photo = getItem(position);
        // Check if we are using a recycled view, if not we need to inflate
        if (convertView == null) {
        //Create a new view from template
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }
        // Look up the views for populating the data (image, caption)
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        // insert the model data
        tvCaption.setText(photo.caption);
        // Clear out the ImageView if it was recycled (right away)
        ivPhoto.setImageResource(0);
        // Insert the Image using Picasso (send out async)
		//----------------------------------------------------------- Dont forget try this Isaac
        // Picasso.with(getContext()).load(photo.imageUrl).placeholder(R.drawable.ic_launcher).into(ivPhoto);
		Picasso.with(getContext()).load(photo.imageUrl).into(ivPhoto);
		//return the created item as a view
        return convertView;
    }
}
