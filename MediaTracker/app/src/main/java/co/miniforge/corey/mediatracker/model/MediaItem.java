package co.miniforge.corey.mediatracker.model;

import android.util.Log;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.Date;

import co.miniforge.corey.mediatracker.media_store.Md5IdHelper;

/**
 * Created by corey on 10/20/17.
 */

public class MediaItem {

    /*
    * Assignment 5: Media Tracker Part 2
    * Refactoring
    */

    //Remove the public static defaulted field
    //public static int defaultId = 0;

    public String id;
    public String title;
    public String description;
    public String url;
    public Date type;

    public MediaItem(JSONObject jsonObject){
        try{
            //Generate id based on the object instance (should work :D)
            this.id = jsonObject.getString("id");
            this.title = jsonObject.getString("title");
            this.description = jsonObject.getString("description");
            this.url = jsonObject.getString("url");
        } catch (Exception e){
            Log.e("toJSONError", String.format("There was an error: %s", e.getMessage()));
        }
    }

    /*
    * Assignment 5: Media Tracker Part 2
    * Refactoring
    */

    public MediaItem(){
        //update line from
        //this.id = Md5IdHelper.idForObject(defaultId++);
        //to this.id = Md5IdHelper.idForObject(this);
        this.id = Md5IdHelper.idForObject(this);
        this.title = "defaultTitle";
        this.description = "defaultDescription";
        this.url = "defaultUrl";
    }

    //you will need to have a java enum named MediaItemType with
    //the following values: Generic, TV, Movie

    //Will pull type value from the JSON object
    MediaItemType getTypeForString(String value){
        switch (value){
            case "TV":
                return  MediaItemType.TV;
            case "Movie":
                return  MediaItemType.Movie;
            default:
                return MediaItemType.Generic;
        }
    }

    //Will be used to save the type into a json object more efficiently
    String getStringForType (MediaItemType type){
        switch (type) {
            case Movie:
                return "Movie";
            case TV:
                return "TV";
            default:
                return "Generic";
        }
    }

    public JSONObject toJson(){
        JSONObject mediaItem = new JSONObject();

        try{
            mediaItem.put("id", this.id);
            mediaItem.put("title", this.title);
            mediaItem.put("description", this.description);
            mediaItem.put("url", this.url);
        } catch (Exception e){
            Log.e("toJSONError", String.format("There was an error: %s", e.getMessage()));
        }

        return mediaItem;
    }
}
