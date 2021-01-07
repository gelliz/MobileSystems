package gev.fit.bstu.by.lr_6;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Person implements Serializable {
    private String name;
    private String email;
    private String location;
    private String phone;
    private String socialNetwork;

    @NonNull
    @Override
    public String toString() {
        return name;
    }

    public Person() { }

    public Person(String name, String email, String location,
                  String phone, String socialNetwork) {

        this.name = name;
        this.email = email;
        this.location = location;
        this.phone = phone;
        this.socialNetwork = socialNetwork;
    }

    public String getEmail() {
        return email;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getSocialNetwork() {
        return socialNetwork;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSocialNetwork(String socialNetwork) {
        this.socialNetwork = socialNetwork;
    }
}
