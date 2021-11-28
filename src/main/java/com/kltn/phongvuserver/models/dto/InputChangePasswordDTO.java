package com.kltn.phongvuserver.models.dto;

public class InputChangePasswordDTO {
    private String newPassword;
    private String oldPassword;

    public InputChangePasswordDTO() {
    }

    public InputChangePasswordDTO(String newPassword, String oldPassword) {
        this.newPassword = newPassword;
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
