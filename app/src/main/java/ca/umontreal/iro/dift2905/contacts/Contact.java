package ca.umontreal.iro.dift2905.contacts;


import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import java.io.Serializable;

import static java.lang.Character.toUpperCase;

/**
 * Contact est un modèle relié à l'édition de contacts par liaison de données
 * bidirectionnelle.
 */
public class Contact extends BaseObservable implements Serializable {
    private int id;
    private String firstName, lastName;
    private String phone, email;
    private boolean isFavorite;

    Contact() {
    }

    /**
     * Crée un contact.
     *
     * @param id         numéro d'identification
     * @param firstName  prénom
     * @param lastName   nom de famille
     * @param phone      numéro de téléphone
     * @param email      courriel
     * @param isFavorite statut de favori
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
     * @return numéro d'identification du contact
     */
    int getId() {
        return id;
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
            notifyPropertyChanged(BR.firstName);
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
            notifyPropertyChanged(BR.lastName);
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
            notifyPropertyChanged(BR.phone);
        }
    }

    /**
     * @return courriel du contact
     */
    @Bindable
    public String getEmail() {
        return email;
    }

    /**
     * @param email courriel du contact
     */
    public void setEmail(String email) {
        if (this.email == null || !this.email.equals(email)) {
            this.email = email.trim();
            notifyPropertyChanged(BR.email);
        }
    }

    /**
     * @return statut de favori
     */
    @Bindable
    public boolean getIsFavorite() {
        return isFavorite;
    }

    /**
     * @param isFavorite statut de favori
     */
    public void setIsFavorite(boolean isFavorite) {
        if (this.isFavorite != isFavorite) {
            this.isFavorite = isFavorite;
            notifyPropertyChanged(BR.isFavorite);
        }
    }

    /**
     * @return si le contact a un prénom
     */
    private boolean hasFirstName() {
        return firstName != null && !firstName.isEmpty();
    }

    /**
     * @return si le contact a un nom de famille
     */
    private boolean hasLastName() {
        return lastName != null && !lastName.isEmpty();
    }

    /**
     * @return si le contact a un nom
     */
    boolean hasName() {
        return hasFirstName() || hasLastName();
    }

    /**
     * @return initiales du contact
     */
    public String getInitials() {
        StringBuilder initials = new StringBuilder();

        if (hasFirstName())
            initials.append(toUpperCase(firstName.charAt(0)));
        if (hasLastName())
            initials.append(toUpperCase(lastName.charAt(0)));

        return initials.toString();
    }

    /**
     * @return nom complet du contact
     */
    public String getFullName() {
        boolean hasFirst = hasFirstName(),
                hasLast = hasLastName();

        StringBuilder name = new StringBuilder();

        if (hasFirst) {
            name.append(firstName);
            if (hasLast)
                name.append(' ');
        }
        if (hasLast)
            name.append(lastName);

        return name.toString();
    }
}
