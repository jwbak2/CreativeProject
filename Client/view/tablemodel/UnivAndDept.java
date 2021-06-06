package Client.view.tablemodel;

import javafx.beans.property.SimpleStringProperty;

public class UnivAndDept {
    private final SimpleStringProperty univ;;
    private final SimpleStringProperty dept;

    public UnivAndDept(SimpleStringProperty univ, SimpleStringProperty dept) {
        this.univ = univ;
        this.dept = dept;
    }

    public String getUniv() {
        return univ.get();
    }

    public SimpleStringProperty univProperty() {
        return univ;
    }

    public String getDept() {
        return dept.get();
    }

    public SimpleStringProperty deptProperty() {
        return dept;
    }

}
