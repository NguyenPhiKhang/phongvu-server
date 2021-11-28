package com.kltn.phongvuserver.models.dto;

import com.kltn.phongvuserver.models.District;
import com.kltn.phongvuserver.models.Province;
import com.kltn.phongvuserver.models.Ward;

public class AddressDTO {
    private int id;
    private String specificAddress;
    private String name;
    private String numberPhone;
    private String createdAt;
    private String updateAt;
    private boolean defaultIs;
    private Province province;
    private District district;
    private Ward ward;

    public AddressDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpecificAddress() {
        return specificAddress;
    }

    public void setSpecificAddress(String specificAddress) {
        this.specificAddress = specificAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public boolean isDefaultIs() {
        return defaultIs;
    }

    public void setDefaultIs(boolean defaultIs) {
        this.defaultIs = defaultIs;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Ward getWard() {
        return ward;
    }

    public void setWard(Ward ward) {
        this.ward = ward;
    }
}
