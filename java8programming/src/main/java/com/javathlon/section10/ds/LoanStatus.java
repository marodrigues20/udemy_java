package com.javathlon.section10.ds;

public enum LoanStatus {

    NOT_STARTED("NS"),
    APPLIED("APP"),
    ASSESEMENT("ASSESS"),
    APPROVED("APPROVED"),
    REJECTED("R"),
    USED("U"),
    PAYING("P"),
    PAID("PAID"),
    DEBTED("DEBTED"),
    UNKNOWN("UNKNOWN");

    String dbKey;

    private LoanStatus(String dbKey) {
        this.dbKey = dbKey;
    }

    public static LoanStatus findByDbKey(String dbKey){
        LoanStatus[] allValues = LoanStatus.values();
        for (int i = 0; i < allValues.length; i++){
            if(allValues[i].dbKey.equals(dbKey))
                return allValues[i];
        }
        return UNKNOWN;
    }
}
