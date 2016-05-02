package io.iqube.kctgrad.model;

import io.realm.RealmObject;

/**
 * Created by raja sudhan on 5/2/2016.
 */
public class Notification extends RealmObject {
    private  String message;



    public String getMessage(){return this.message;}

    public void setMessage(String message)
    {
        this.message=message;
    }

}
