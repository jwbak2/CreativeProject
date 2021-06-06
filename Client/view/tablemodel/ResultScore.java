package Client.view.tablemodel;

import javafx.beans.property.SimpleStringProperty;

public class ResultScore {
    private SimpleStringProperty univName;
    private SimpleStringProperty deptName;
    private SimpleStringProperty scoreOfIdct1;
    private SimpleStringProperty scoreOfIdct2;
    private SimpleStringProperty scoreOfIdct3;
    private SimpleStringProperty scoreOfTotal;

    public ResultScore(SimpleStringProperty univName, SimpleStringProperty deptName, SimpleStringProperty scoreOfIdct1, SimpleStringProperty scoreOfIdct2, SimpleStringProperty scoreOfIdct3, SimpleStringProperty scoreOfTotal) {
        this.univName = univName;
        this.deptName = deptName;
        this.scoreOfIdct1 = scoreOfIdct1;
        this.scoreOfIdct2 = scoreOfIdct2;
        this.scoreOfIdct3 = scoreOfIdct3;
        this.scoreOfTotal = scoreOfTotal;
    }

    public String getUnivName() {
        return univName.get();
    }

    public SimpleStringProperty univNameProperty() {
        return univName;
    }

    public String getDeptName() {
        return deptName.get();
    }

    public SimpleStringProperty deptNameProperty() {
        return deptName;
    }

    public String getScoreOfIdct1() {
        return scoreOfIdct1.get();
    }

    public SimpleStringProperty scoreOfIdct1Property() {
        return scoreOfIdct1;
    }

    public String getScoreOfIdct2() {
        return scoreOfIdct2.get();
    }

    public SimpleStringProperty scoreOfIdct2Property() {
        return scoreOfIdct2;
    }

    public String getScoreOfIdct3() {
        return scoreOfIdct3.get();
    }

    public SimpleStringProperty scoreOfIdct3Property() {
        return scoreOfIdct3;
    }

    public String getScoreOfTotal() {
        return scoreOfTotal.get();
    }

    public SimpleStringProperty scoreOfTotalProperty() {
        return scoreOfTotal;
    }
}
