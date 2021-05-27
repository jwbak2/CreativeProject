package Server.model.dto;

public class IndicatorSelectionStatisticsDTO {
    private String indicatorName;
    private Long selectionNumber;

    public IndicatorSelectionStatisticsDTO(String indicatorName, Long selectionNumber) {
        this.indicatorName = indicatorName;
        this.selectionNumber = selectionNumber;
    }

    public String getIndicatorName() {
        return this.indicatorName;
    }

    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }

    public Long getSelectionNumber() {
        return this.selectionNumber;
    }

    public void setSelectionNumber(Long selectionNumber) {
        this.selectionNumber = selectionNumber;
    }
}
