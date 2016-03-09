package br.com.rf.purpledeckschallenge.model;

import java.util.List;

/**
 * Created by rodrigoferreira on 3/7/16.
 */
public class FlickrPhoto {

    public String stat;
    public Photos photos;

    public class Photos {
        public List<Photo> photo;
    }

    public class Photo {
        public String id;
        public String farm;
        public String secret;
        public String server;
    }

}
