package ca.umontreal.iro.dift2905.contacts;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import java.io.Serializable;
import java.util.Comparator;

/**
 * La classe Contact contient les méthodes qui permettent de représenter
 * un contact dans la liste de contact du carnet d'adresse en stockant
 * ses informations.
 */
public class Contact extends BaseObservable implements Serializable {
    private int id;
    private String firstName, lastName;
    private String phone, email;
    private boolean isFavorite;

    Contact() {
    }

    /**
     * Création du contact
     *
     * @param id numéro d'identification
     * @param firstName prénom
     * @param lastName nom de famille
     * @param phone numéro de téléphone
     * @param email adresse email
     * @param isFavorite true s'il est un favori, false sinon
     */
    Contact(int id, String firstName, String lastName,
            String phone, String email, int isFavorite) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.isFavorite = isFavorite == 1;
    }

    /**
     * Méthode qui lit les informations d'un parcel et les associe au contact.
     *
     * @param in parcel qui contient les informations du contact
     */
    private Contact(Parcel in) {
        id = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
        phone = in.readString();
        email = in.readString();
        isFavorite = in.readInt() == 1;
    }

    /**
     * @return numéro d'identification du contact
     */
    public int getId() {
        return id;
    }

    /**
     * @param id numéro d'identification du contact
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return prénom du contact
     */
    @Bindable
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName prénom du contact
     */
    public void setFirstName(String firstName) {
        if (this.firstName == null || !this.firstName.equals(firstName)) {
            this.firstName = firstName.trim();
            notifyPropertyChanged(BR.contact);
        }
    }

    /**
     * @return nom de famille du contact
     */
    @Bindable
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName nom de famille du contact
     */
    public void setLastName(String lastName) {
        if (this.lastName == null || !this.lastName.equals(lastName)) {
            this.lastName = lastName.trim();
            notifyPropertyChanged(BR.contact);
        }
    }

    /**
     * @return numéro de téléphone du contact
     */
    @Bindable
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone numéro de téléphone du contact
     */
    public void setPhone(String phone) {
        if (this.phone == null || !this.phone.equals(phone)) {
            this.phone = phone.trim();
            notifyPropertyChanged(BR.contact);
        }
    }

    /**
     * @return adresse email du contact
     */
    @Bindable
    public String getEmail() {
        return email;
    }

    /**
     * @param email adresse email du contact
     */
    public void setEmail(String email) {
        if (this.email == null || !this.email.equals(email)) {
            this.email = email.trim();
            notifyPropertyChanged(BR.contact);
        }
    }

    /**
     * @return true si le contact est favori
     *         false sinon
     */
    @Bindable
    public boolean getIsFavorite() {
        return isFavorite;
    }

    /**
     * @param isFavorite == true s'il est un contact favori
     *                   == false sinon
     */
    public void setIsFavorite(boolean isFavorite) {
        if (this.isFavorite != isFavorite) {
            this.isFavorite = isFavorite;
            notifyPropertyChanged(BR.contact);
        }
    }

    /**
     * @return les initiales du contact
     */
    public String getInitials() {
        String initials = "";
        if (firstName != null && !firstName.isEmpty())
            initials += firstName.charAt(0);
        if (lastName != null && !lastName.isEmpty())
            initials += lastName.charAt(0);
        return initials;
    }

    /**
     * @return le nom complet du contact
     */
    public String getFullName() {
        String name = "";
        if (firstName != null && !firstName.isEmpty())
            name += firstName + " "; // XXX
        if (lastName != null && !lastName.isEmpty())
            name += lastName;
        return name;
    }


    public boolean isFirstNameNull(){
        return  firstName == null || firstName.replaceAll(" ", "").isEmpty();
    }

    public boolean isLastNameNull(){
        return lastName == null || lastName.replaceAll(" ", "").isEmpty();
    }

    /**
     * Méthode qui vérifie si les champs de prénom et nom de famille sont vides.
     *
     * @return true s'ils sont vides
     *         false sinon
     */
    public boolean isNameNull(){
        return isFirstNameNull()  && isLastNameNull();
    }

    public boolean hasName() {
        return (firstName != null && !firstName.trim().isEmpty()) ||
               (lastName != null && !lastName.trim().isEmpty());
    }
}

/**
 * La classe SortbyName contient une methode qui présente la comparaison
 * faite entre les contacts pour les classer en ordre alphabétque
 */
class SortbyName implements Comparator<Contact> {
    /**
     * Méthode qui compare lexicographiquement deux contacts
     *
     * @param a premier contact
     * @param b deuxième contact
     * @return valeur de la comparaison
     */
    public int compare(Contact a, Contact b) {
        if(!a.isFirstNameNull() && !b.isFirstNameNull()){
            int compare = a.getFirstName().toLowerCase().compareTo(b.getFirstName().toLowerCase());
            if(compare != 0)
                return compare;
        } else if (a.isFirstNameNull() ^ b.isFirstNameNull())
            return a.isFirstNameNull() ? 1 : -1;
        if(!a.isLastNameNull() && !b.isLastNameNull()){
            return a.getLastName().toLowerCase().compareTo(b.getLastName().toLowerCase());
        } else if (a.isLastNameNull() ^ b.isLastNameNull())
            return  a.getLastName() == null ? 1 : -1;

        return 0;
    }
}
