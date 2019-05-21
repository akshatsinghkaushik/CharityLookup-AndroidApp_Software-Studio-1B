package com.dude.funky.charitylookup;

public class CharityInformation {

    private String charityName;
    private String otherName;
    private String password;
    private String ABN;
    private String email;
    private String phoneNo;
    private String charityWebsite;
    private String dateEst;
    private String charityPurpose;

    public CharityInformation(String charityName, String otherName, String password, String ABN, String email, String phoneNo,
                              String charityWebsite, String dateEst, String charityPurpose) {

        this.charityName = charityName;
        this.otherName = otherName;
        this.password = password;
        this.ABN = ABN;
        this.email = email;
        this.phoneNo = phoneNo;
        this.charityWebsite = charityWebsite;
        this.dateEst = dateEst;
        this.charityPurpose = charityPurpose;
    }

    public String getCharityName() {
        return charityName;
    }

    public void setCharityName(String charityName) {
        this.charityName = charityName;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getABN() {
        return ABN;
    }

    public void setABN(String ABN) {
        this.ABN = ABN;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getCharityWebsite() {
        return charityWebsite;
    }

    public void setCharityWebsite(String charityWebsite) {
        this.charityWebsite = charityWebsite;
    }

    public String getDateEst() {
        return dateEst;
    }

    public void setDateEst(String dateEst) {
        this.dateEst = dateEst;
    }

    public String getCharityPurpose() {
        return charityPurpose;
    }

    public void setCharityPurpose(String charityPurpose) {
        this.charityPurpose = charityPurpose;
    }
}
