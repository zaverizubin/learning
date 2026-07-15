package org.jobrunr.example.enums;

public enum Platform {

    JOBRUNR_FINANCE("jobrunr finance"),
    PAYPAL("paypal"),
    STRIPE("stripe");

    private String serverTag;

    Platform (final String serverTag){
        this.serverTag = serverTag;
    }

    public String getServerTag() {
        return this.serverTag;
    }

    public boolean isExternal(){
        return this == Platform.STRIPE || this == Platform.PAYPAL;
    }

}
