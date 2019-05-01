package ca.umontreal.iro.dift2905.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import static ca.umontreal.iro.dift2905.contacts.DBHelper.ContactColumns.*;
import static java.lang.String.format;
import static java.lang.String.valueOf;

/**
 * DBHelper est un utilitaire pour effectuer des opérations sur la base de
 * données SQLite locale contenant les informations des contacts.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "Contacts.db";
    private static int DATABASE_VERSION = 1;

    private static SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        if (db == null)
            db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(format(
                "CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s INTEGER)",
                ContactColumns.TABLE_NAME, _ID, COLUMN_NAME_FIRSTNAME, COLUMN_NAME_LASTNAME,
                COLUMN_NAME_PHONE, COLUMN_NAME_EMAIL, COLUMN_NAME_FAVORITE));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(format("DROP TABLE IF EXISTS %s", ContactColumns.TABLE_NAME));
        onCreate(db);
    }

    /**
     * Génère une liste de l'ensemble les contacts.
     *
     * @return liste de contacts
     */
    List<Contact> getContacts() {
        Cursor cur = db.query(ContactColumns.TABLE_NAME, null,
                null, null, null, null,
                format("%s, %s", COLUMN_NAME_FIRSTNAME, COLUMN_NAME_LASTNAME));

        List<Contact> contacts = new ArrayList<>();
        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext())
            contacts.add(new Contact(cur.getInt(cur.getColumnIndex(_ID)),
                    cur.getString(cur.getColumnIndex(COLUMN_NAME_FIRSTNAME)),
                    cur.getString(cur.getColumnIndex(COLUMN_NAME_LASTNAME)),
                    cur.getString(cur.getColumnIndex(COLUMN_NAME_PHONE)),
                    cur.getString(cur.getColumnIndex(COLUMN_NAME_EMAIL)),
                    cur.getInt(cur.getColumnIndex(COLUMN_NAME_FAVORITE))));

        cur.close();
        return contacts;
    }


    /**
     * Génère une liste de contacts selon le statut de favori et un filtre.
     *
     * @param favorite statut de favori
     * @param filter   filtre de nom
     * @return liste de contacts filtrée
     */
    List<Contact> getContacts(boolean favorite, String filter) {
        String selection = format("%s = ?", COLUMN_NAME_FAVORITE);
        String[] selectionArgs = new String[]{favorite ? "1" : "0"};
        String orderBy = format("%s, %s", COLUMN_NAME_FIRSTNAME, COLUMN_NAME_LASTNAME);

        Cursor cur = db.query(ContactColumns.TABLE_NAME, null,
                favorite ? selection : null, favorite ? selectionArgs : null,
                null, null, orderBy);

        List<Contact> contacts = new ArrayList<>();
        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
            String firstName = cur.getString(cur.getColumnIndex(COLUMN_NAME_FIRSTNAME));
            String lastName = cur.getString(cur.getColumnIndex(COLUMN_NAME_LASTNAME));

            if ((firstName != null && firstName.toLowerCase().startsWith(filter.toLowerCase())) ||
                    (lastName != null && lastName.toLowerCase().startsWith(filter.toLowerCase()))) // XXX
                contacts.add(new Contact(cur.getInt(cur.getColumnIndex(_ID)),
                        cur.getString(cur.getColumnIndex(COLUMN_NAME_FIRSTNAME)),
                        cur.getString(cur.getColumnIndex(COLUMN_NAME_LASTNAME)),
                        cur.getString(cur.getColumnIndex(COLUMN_NAME_PHONE)),
                        cur.getString(cur.getColumnIndex(COLUMN_NAME_EMAIL)),
                        cur.getInt(cur.getColumnIndex(COLUMN_NAME_FAVORITE))));
        }

        cur.close();
        return contacts;
    }

    /**
     * Ajoute un contact à la base de données.
     *
     * @param contact contact à ajouter
     */
    void addContact(Contact contact) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_FIRSTNAME, contact.getFirstName());
        values.put(COLUMN_NAME_LASTNAME, contact.getLastName());
        values.put(COLUMN_NAME_PHONE, contact.getPhone());
        values.put(COLUMN_NAME_EMAIL, contact.getEmail());
        values.put(COLUMN_NAME_FAVORITE, contact.getIsFavorite());

        db.insert(ContactColumns.TABLE_NAME, null, values);
    }

    /**
     * Met à jour un contact de la base de données.
     *
     * @param contact contact à modifier
     */
    void updateContact(Contact contact) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_FIRSTNAME, contact.getFirstName());
        values.put(COLUMN_NAME_LASTNAME, contact.getLastName());
        values.put(COLUMN_NAME_PHONE, contact.getPhone());
        values.put(COLUMN_NAME_EMAIL, contact.getEmail());
        values.put(COLUMN_NAME_FAVORITE, contact.getIsFavorite());

        db.update(ContactColumns.TABLE_NAME, values,
                String.format("%s = ?", _ID), new String[]{valueOf(contact.getId())});
    }

    /**
     * Supprime un contact de la base de données.
     *
     * @param contact contact à supprimer
     */
    public void deleteContact(Contact contact) {
        db.delete(ContactColumns.TABLE_NAME,
                format("%s = ?", _ID), new String[]{valueOf(contact.getId())});
    }

    static class ContactColumns implements BaseColumns {
        static final String TABLE_NAME = "contact";

        static final String COLUMN_NAME_FIRSTNAME = "firstname";
        static final String COLUMN_NAME_LASTNAME = "lastname";
        static final String COLUMN_NAME_PHONE = "phone";
        static final String COLUMN_NAME_EMAIL = "email";
        static final String COLUMN_NAME_FAVORITE = "favorite";
    }
}
