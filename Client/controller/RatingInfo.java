package Client.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.HBox;
import org.controlsfx.control.Rating;

public class RatingInfo {
    private SimpleStringProperty creationDate;;
    private SimpleStringProperty ratingContent;
    private Rating score;
    private HBox test;

    public RatingInfo(SimpleStringProperty creationDate, SimpleStringProperty ratingContent, Rating score) {
        this.creationDate = creationDate;
        this.ratingContent = ratingContent;
        this.score = score;
    }
    public RatingInfo(SimpleStringProperty creationDate, SimpleStringProperty ratingContent, HBox test) {
        this.creationDate = creationDate;
        this.ratingContent = ratingContent;
        this.test = test;
    }

    public HBox getTest() {
        return test;
    }

    public String getCreationDate() {
        return creationDate.get();
    }

    public SimpleStringProperty creationDateProperty() {
        return creationDate;
    }

    public String getRatingContent() {
        return ratingContent.get();
    }

    public SimpleStringProperty ratingContentProperty() {
        return ratingContent;
    }

    public Rating getScore() {
        return score;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate.set(creationDate);
    }

    public void setRatingContent(String ratingContent) {
        this.ratingContent.set(ratingContent);
    }

    public void setScore(Rating score) {
        this.score = score;
    }
}
