package com.example.finalmentormate.Modals;

public class ImageViewModal {

    String image;

    public ImageViewModal() {
    }

    public ImageViewModal(String imageUrl) {
        this.image = imageUrl;
    }

    public String getImageUrl() {
        return image;
    }

    public void setImageUrl(String imageUrl) {
        this.image = imageUrl;
    }
}
