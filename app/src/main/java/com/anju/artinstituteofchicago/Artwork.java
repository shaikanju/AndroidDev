package com.anju.artinstituteofchicago;

public class Artwork {
    private String title;
    private String dateDisplay;
    private String artistDisplay;
    private String mediumDisplay;
    private String artworkTypeTitle;
    private String imageId;
    private String dimensions;
    private String departmentTitle;
    private String creditLine;
    private String placeOfOrigin;
    private String galleryTitle;
    private Integer galleryId;
    private Integer id;
    private String apiLink;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateDisplay() {
        return dateDisplay;
    }

    public void setDateDisplay(String dateDisplay) {
        this.dateDisplay = dateDisplay;
    }

    public String getArtistDisplay() {
        return artistDisplay;
    }

    public void setArtistDisplay(String artistDisplay) {
        this.artistDisplay = artistDisplay;
    }

    public String getMediumDisplay() {
        return mediumDisplay;
    }

    public void setMediumDisplay(String mediumDisplay) {
        this.mediumDisplay = mediumDisplay;
    }

    public String getArtworkTypeTitle() {
        return artworkTypeTitle;
    }

    public void setArtworkTypeTitle(String artworkTypeTitle) {
        this.artworkTypeTitle = artworkTypeTitle;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getDepartmentTitle() {
        return departmentTitle;
    }

    public void setDepartmentTitle(String departmentTitle) {
        this.departmentTitle = departmentTitle;
    }

    public String getCreditLine() {
        return creditLine;
    }

    public void setCreditLine(String creditLine) {
        this.creditLine = creditLine;
    }

    public String getPlaceOfOrigin() {
        return placeOfOrigin;
    }

    public void setPlaceOfOrigin(String placeOfOrigin) {
        this.placeOfOrigin = placeOfOrigin;
    }

    public String getGalleryTitle() {
        return galleryTitle;
    }

    public void setGalleryTitle(String galleryTitle) {
        this.galleryTitle = galleryTitle;
    }

    public Integer getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(Integer galleryId) {
        this.galleryId = galleryId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApiLink() {
        return apiLink;
    }

    public void setApiLink(String apiLink) {
        this.apiLink = apiLink;
    }
    public String getThumbnailImageUrl() {
        return "https://www.artic.edu/iiif/2/" + getImageId() + "/full/200,/0/default.jpg";
    }

}

