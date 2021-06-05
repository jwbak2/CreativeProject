package Client.vo;

import java.io.Serializable;

public class RankVO implements Serializable {

    private String name;
    private int view;


    public RankVO(String name, int view) {
        this.name = name;
        this.view = view;
    }

    public String getName() {
        return name;
    }

    public int getView() {
        return view;
    }

}
