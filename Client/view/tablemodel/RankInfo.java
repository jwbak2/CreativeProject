package Client.view.tablemodel;

import javafx.beans.property.SimpleStringProperty;

public class RankInfo {
    private SimpleStringProperty rank;
    private SimpleStringProperty content;
    private SimpleStringProperty view;

    public RankInfo(SimpleStringProperty rank, SimpleStringProperty content, SimpleStringProperty view) {
        this.rank = rank;
        this.content = content;
        this.view = view;
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

    public String getView() {
        return view.get();
    }

    public SimpleStringProperty viewProperty() {
        return view;
    }
}