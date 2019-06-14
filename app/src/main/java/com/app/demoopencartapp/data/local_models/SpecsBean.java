package com.app.demoopencartapp.data.local_models;

public class SpecsBean {

    private String spec_key;
    private String spec_value;

    public SpecsBean(String spec_key, String spec_value) {
        this.spec_key = spec_key;
        this.spec_value = spec_value;
    }

    public String getSpec_key() {
        return spec_key;
    }

    public void setSpec_key(String spec_key) {
        this.spec_key = spec_key;
    }

    public String getSpec_value() {
        return spec_value;
    }

    public void setSpec_value(String spec_value) {
        this.spec_value = spec_value;
    }
}
