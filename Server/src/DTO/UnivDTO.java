package DTO;

public class UnivDTO {
    private String univId;
    private String univName;
    private String univType;
    private String univEstablishmentCls;
    private String univArea;
    private String univAddress;
    private String univRepresentativeNumber;
    private String univHomepageUrl;
    private Byte[] univLogoImageFile;
    private String univIntroduction;
    private Long view;

    public String getUnivId() {
        return this.univId;
    }

    public void setUnivId(String univId) {
        this.univId = univId;
    }

    public String getUnivName() {
        return this.univName;
    }

    public void setUnivName(String univName) {
        this.univName = univName;
    }

    public String getUnivType() {
        return this.univType;
    }

    public void setUnivType(String univType) {
        this.univType = univType;
    }

    public String getUnivEstablishmentCls() {
        return this.univEstablishmentCls;
    }

    public void setUnivEstablishmentCls(String univEstablishmentCls) {
        this.univEstablishmentCls = univEstablishmentCls;
    }

    public String getUnivArea() {
        return this.univArea;
    }

    public void setUnivArea(String univArea) {
        this.univArea = univArea;
    }

    public String getUnivAddress() {
        return this.univAddress;
    }

    public void setUnivAddress(String univAddress) {
        this.univAddress = univAddress;
    }

    public String getUnivRepresentativeNumber() {
        return this.univRepresentativeNumber;
    }

    public void setUnivRepresentativeNumber(String univRepresentativeNumber) {
        this.univRepresentativeNumber = univRepresentativeNumber;
    }

    public String getUnivHomepageUrl() {
        return this.univHomepageUrl;
    }

    public void setUnivHomepageUrl(String univHomepageUrl) {
        this.univHomepageUrl = univHomepageUrl;
    }

    public Byte[] getUnivLogoImageFile() {
        return this.univLogoImageFile;
    }

    public void setUnivLogoImageFile(Byte[] univLogoImageFile) {
        this.univLogoImageFile = univLogoImageFile;
    }

    public String getUnivIntroduction() {
        return this.univIntroduction;
    }

    public void setUnivIntroduction(String univIntroduction) {
        this.univIntroduction = univIntroduction;
    }

    public Long getView() {
        return this.view;
    }

    public void setView(Long view) {
        this.view = view;
    }
}
