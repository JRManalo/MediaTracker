package co.miniforge.corey.mediatracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import co.miniforge.corey.mediatracker.media_recycler.MediaRecyclerAdapter;
import co.miniforge.corey.mediatracker.media_sorter.MediaItemSortHelper;
import co.miniforge.corey.mediatracker.media_store.MediaStorageUtil;
import co.miniforge.corey.mediatracker.model.MediaItem;

public class MyListActivity extends AppCompatActivity {
    public static String mediaExtra = "mediaExtra";

    RecyclerView media_list_recycler;

    FloatingActionButton add_media_item_button;

    MediaStorageUtil storageUtil;

    List<MediaItem> mediaItems = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        storageUtil = new MediaStorageUtil(getApplicationContext());

        locateViews();

        bindData();

        handleIntent();
    }

    void handleIntent(){
        if(getIntent() != null){
            Intent intent = getIntent();

            if(intent.hasExtra(mediaExtra)){
                try {
                    JSONObject json = new JSONObject(intent.getStringExtra(mediaExtra));
                    MediaItem item = new MediaItem(json);

                    for(int i = 0; i < mediaItems.size(); i++){
                        if(mediaItems.get(i).id.equals(item.id)){
                            mediaItems.set(i, item);
                            break;
                        }
                    }

                    storageUtil.saveMediaData(mediaItems);
                } catch (Exception e){
                    Log.e("handleIntentErr", String.format("Could not update item from intent: %s", e.getMessage()));
                }
            }
        }
    }

    void locateViews(){
        media_list_recycler = (RecyclerView) findViewById(R.id.media_list_recycler);
        add_media_item_button = (FloatingActionButton) findViewById(R.id.add_media_item_button);
    }

    void bindData(){
        add_media_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create new empty media item
                MediaItem item = new MediaItem();
                mediaItems.add(item);
                storageUtil.saveMediaData(mediaItems);
                updateMediaItems(mediaItems);
            }
        });

        setUpRecyclerView();

        updateMediaItems(storageUtil.getMediaDataList());

        //Update list every 10 sec
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                updateMediaItems(storageUtil.getMediaDataList());

                //Update every 10 sec
                handler.postDelayed(this, 10000);
            }
        };
        handler.post(runnable);
    }

    public void updateMediaItems(List<MediaItem> mediaItems){
        this.mediaItems = mediaItems;
        ((MediaRecyclerAdapter)media_list_recycler.getAdapter()).updateList(this.mediaItems);
    }

    void setUpRecyclerView(){
        MediaRecyclerAdapter adapter = new MediaRecyclerAdapter();
        media_list_recycler.setAdapter(adapter);

        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        media_list_recycler.setLayoutManager(manager);
    }

    /*
    * Assignment 5: Media Tracker Part 2
    * Delete Functionality
    */

    public void deleteMediaItem(MediaItem item) {
        //This function will loop through the items in the media item list
        for (int i = 0; i < mediaItems.size(); i++) {
            if (mediaItems.get(i).id.equals(item.id)) {
                //remove the media item that has a matching id
                mediaItems.remove(i);
                break;
            }
            //reload the list using the storage utility
            storageUtil.saveMediaData(mediaItems);
            updateMediaItems(storageUtil.getMediaDataList());
        }
    }

    /*
    * Assignment 6: Media Tracker part 3
    * Sorting media items
    */

        public boolean createOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_my_list, menu);
        return true;
    }
        public boolean OptionsItemSelected(MenuItem item)
        {
            switch (item.getItemId())
            {
                case R.id.type:
                    updateMediaItems(MediaItemSortHelper.sortByType(mediaItems));
                case R.id.name:
                    updateMediaItems(MediaItemSortHelper.sortByName(mediaItems));
            }
            return true;
        }
}
