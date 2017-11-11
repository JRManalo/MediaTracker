package co.miniforge.corey.mediatracker.model;

import org.json.JSONObject;

/**
 * Created by corey on 10/20/17.
 * Assignment 5: Media Tracker Part 2
 * Model Objects
 */

public class MovieModel extends MediaItem
{
    //Added fields to MovieModel class
    public int myRating;
    public String genre;

    public MovieModel(JSONObject jsonObject)
    {
        super(jsonObject);
    }
}

