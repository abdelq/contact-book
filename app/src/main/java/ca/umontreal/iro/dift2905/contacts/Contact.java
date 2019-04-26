package ca.umontreal.iro.dift2905.contacts;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class Contact extends BaseObservable implements Parcelable {
    private int id;
    private String firstName, lastName;
    private String phone, email;
    private boolean isFavorite;

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    Contact() {
    }

    Contact(int id, String firstName, String lastName,
            String phone, String email, int isFavorite) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.isFavorite = isFavorite == 1;
    }

    private Contact(Parcel in) {
        id = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
        phone = in.readString();
        email = in.readString();
        isFavorite = in.readInt() == 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Bindable
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (this.firstName == null || !this.firstName.equals(firstName)) {
            this.firstName = firstName;
            notifyPropertyChanged(BR.contact);
        }
    }

    @Bindable
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (this.lastName == null || !this.lastName.equals(lastName)) {
            this.lastName = lastName;
            notifyPropertyChanged(BR.contact);
        }
    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (this.phone == null || !this.phone.equals(phone)) {
            this.phone = phone;
            notifyPropertyChanged(BR.contact);
        }
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (this.email == null || !this.email.equals(email)) {
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

    public String getInitials() {
        String initials = "";
        if (firstName != null)
            initials += firstName.charAt(0);
        if (lastName != null)
            initials += lastName.charAt(0);
        return initials;
    }

    public String getFullName() {
        String name = "";
        if (firstName != null)
            name += firstName + " "; // XXX
        if (lastName != null)
            name += lastName;
        return name;
    }

    @Override
    public int describeContents() {
        return 0; // XXX
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeInt(isFavorite ? 1 : 0);
    }
}
