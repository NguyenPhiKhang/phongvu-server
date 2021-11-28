package com.kltn.phongvuserver.models.dto;

public class UserDTO {
    private int id;

    private String name;
    private String email;
    private String birthday;
    private String phoneNumber;
    private String sex;
    private boolean active;
    private String timeCreated;
    private String timeUpdated;

    private String image_url;

    private AddressDTO address;

    public UserDTO() {
    }

    public UserDTO(int id, String name, String email, String birthday, String phoneNumber, String sex, boolean active, String timeCreated, String timeUpdated, String image_url) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
        this.active = active;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
        this.image_url = image_url;
    }

    public UserDTO(int id, String name, String email, String birthday, String phoneNumber, String sex, boolean active, String timeCreated, String timeUpdated, String image_url, AddressDTO address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
        this.active = active;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
        this.image_url = image_url;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getTimeUpdated() {
        return timeUpdated;
    }

    public void setTimeUpdated(String timeUpdated) {
        this.timeUpdated = timeUpdated;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }
}
