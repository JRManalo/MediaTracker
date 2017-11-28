package co.miniforge.corey.mediatracker.model;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.audiofx.AudioEffect;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import co.miniforge.corey.mediatracker.MyListActivity;
import co.miniforge.corey.mediatracker.R;
import co.miniforge.corey.mediatracker.media_recycler.MediaViewHolder;
import co.miniforge.corey.mediatracker.model.MediaItem;

import static co.miniforge.corey.mediatracker.MyListActivity.mediaExtra;

public class MediaDetailActivity extends AppCompatActivity {

    //variables
    EditText Title;
    EditText Desciption;
    EditText Url;
    Button saveButton;
    JSONObject jData;
    MediaItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_detail);
        //calling bindViews method
        bindViews();
        //calling checkExtra method
        checkExtra();
        //enable saver
        clickSave();
    }

    public void bindViews() {
        // attaching variables to activities button and fields
        // pull and change data
        Title = (EditText) findViewById(R.id.Title);
        Desciption = (EditText) findViewById(R.id.Description);
        Url = (EditText) findViewById(R.id.Url);
        saveButton = (Button) findViewById(R.id.save);
    }

    // Check if the intent has an extra with the tag from MyListActivity
    // If it does, then create a new JSONObject from the string extra
    // Create a new MediaItem from the JSONObject
    // Save it as a class-scope variable

    public void checkExtra() {
        if (getIntent().hasExtra(MediaViewHolder.jdata)) {
            try {
                jData = new JSONObject(MediaViewHolder.jdata);

                item = new MediaItem(jData);

                Title.setText(item.title);
                Desciption.setText(item.description);
                Url.setText(item.url);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void clickSave() {
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //assigning the title, description, and url in media
                item.title = Title.getText().toString();
                item.description = Desciption.getText().toString();
                item.url = Url.getText().toString();

                // Open new activities when buttons are pressed
                // Goes back to main page
                //Intent intent = new Intent(getApplicationContext(), MyListActivity.class);

                //intent.putExtra(mediaExtra, item.toJson().toString());

                //start activity with the intent
                //startActivity(intent);

                promptConfirmation();
            }
        });
    }

    /*
   * Assignment 6: Media Tracker Part 3
   * Save changes confirmation dialog
   */
    public void promptConfirmation()
    {
        //https://developer.android.com/guide/topics/ui/dialogs.html#AlertDialog
        //Make sure to put the code in the activity, the builder requires an activity to be passed in
        //import android.support.v7.app for the alert dialog

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Save Changes").setMessage("Are you sure you want to save these changes?");

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                // Put the start activity with intent code here
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                // Do nothing, unless you want this button to go back to
                // ListActivity without putting an intent extra
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
