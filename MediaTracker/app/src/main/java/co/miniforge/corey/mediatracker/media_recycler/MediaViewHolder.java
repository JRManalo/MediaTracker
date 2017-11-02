package co.miniforge.corey.mediatracker.media_recycler;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import co.miniforge.corey.mediatracker.MediaItemDetailActivity;
import co.miniforge.corey.mediatracker.MyListActivity;
import co.miniforge.corey.mediatracker.R;
import co.miniforge.corey.mediatracker.model.MediaDetailActivity;
import co.miniforge.corey.mediatracker.model.MediaItem;

import static co.miniforge.corey.mediatracker.MyListActivity.mediaExtra;

/**
 * Created by corey on 10/15/17.
 */

public class MediaViewHolder extends RecyclerView.ViewHolder
{
    TextView mediaName;
    TextView mediaDescription;

    public static String jdata;

    View inflated;

    Context context;

    public MediaViewHolder(View itemView)
    {
        super(itemView);

        locateViews(itemView);
    }

    private void locateViews(View itemView)
    {
        inflated = itemView;
        context = itemView.getContext();

        mediaName = itemView.findViewById(R.id.mediaName);
        mediaDescription = itemView.findViewById(R.id.mediaDescription);
    }

    public void bindData(final MediaItem mediaItem)
    {
        this.mediaName.setText(mediaItem.title);
        this.mediaDescription.setText(mediaItem.description);

        // adds listener, once button is pressed it triggers the function
        inflated.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //TODO: Create a new activity with this object's data
                //Bind the click event of the view to start a MediaItemDetailActivity with the MediaItem at that position
                Intent intent = new Intent(context, MediaDetailActivity.class);
                //Hint: mediaItem.toJson().toString() && context.startActivity);
                //variable name, values
                jdata = mediaItem.toJson().toString();
                intent.putExtra(jdata, jdata);
                //start activity
                context.startActivity(intent);
            }
        });

    }

}
