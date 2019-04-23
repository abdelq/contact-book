package ca.umontreal.iro.dift2905.contacts;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class Contact extends BaseObservable {
    /* TODO */
    private int id;
    private String firstName, lastName;
    private String phone, email;
    private boolean isFavorite;

    public Contact(int id, String firstName, String lastName,
                   String phone, String email, boolean isFavorite) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.isFavorite = isFavorite;
    }

    @Bindable
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (!this.firstName.equals(firstName)) {
            this.firstName = firstName;
            notifyPropertyChanged(BR.contact);
        }
    }

    @Bindable
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (!this.lastName.equals(lastName)) {
            this.lastName = lastName;
            notifyPropertyChanged(BR.contact);
        }
    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (!this.phone.equals(phone)) {
            this.phone = phone;
            notifyPropertyChanged(BR.contact);
        }
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (!this.email.equals(email)) {
            this.email = email;
            notifyPropertyChanged(BR.contact);
        }
    }

    @Bindable
    public boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        if (this.isFavorite != isFavorite) {
            this.isFavorite = isFavorite;
            notifyPropertyChanged(BR.contact);
        }
    }
}
