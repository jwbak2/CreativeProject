package Client.view.tablemodel;

import javafx.beans.property.SimpleStringProperty;

public class RankInfo {
    private SimpleStringProperty rank;
    private SimpleStringProperty content;

    public RankInfo(SimpleStringProperty rank, SimpleStringProperty content) {
        this.rank = rank;
        this.content = content;
    }

    public String getRank() {
        return rank.get();
    }

    public SimpleStringProperty rankProperty() {
        return rank;
    }

    public String getContent() {
        return content.get();
    }

    public SimpleStringProperty contentProperty() {
        return content;
    }
}