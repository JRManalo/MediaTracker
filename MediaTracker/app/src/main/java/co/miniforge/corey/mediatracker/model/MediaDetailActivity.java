package co.miniforge.corey.mediatracker.model;

import android.content.Intent;
import android.media.audiofx.AudioEffect;
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

    public void clickSave(){
        saveButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //assigning the title, description, and url in media
                item.title = Title.getText().toString();
                item.description = Desciption.getText().toString();
                item.url = Url.getText().toString();

                // Open new activities when buttons are pressed
                // Goes back to main page
                Intent intent = new Intent(getApplicationContext(), MyListActivity.class);

                intent.putExtra(mediaExtra, item.toJson().toString());

                //start activity with the intent
                startActivity(intent);
            }
        });

    }
}
