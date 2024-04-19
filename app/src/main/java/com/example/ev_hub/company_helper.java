package com.example.ev_hub;

public class company_helper {
    String name, contact_no, password;

    public company_helper() {
    }

    public company_helper(String name, String contact_no, String password) {
        this.name = name;
        this.contact_no = contact_no;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
