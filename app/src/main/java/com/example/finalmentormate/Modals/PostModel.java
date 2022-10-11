package com.example.finalmentormate.Modals;

import android.os.Parcel;
import android.os.Parcelable;

public class PostModel implements Parcelable {

    String id;
    String username;
    String image;

    public PostModel(String id, String username, String image) {
        this.id = id;
        this.username = username;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PostModel(){

    }

    protected PostModel(Parcel in) {
        id = in.readString();
        username = in.readString();
        image = in.readString();

    }

    public static final Creator<PostModel> CREATOR = new Creator<PostModel>() {
        @Override
        public PostModel createFromParcel(Parcel in) {
            return new PostModel(in);
        }

        @Override
        public PostModel[] newArray(int size) {
            return new PostModel[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(username);
        parcel.writeString(image);

    }
}
