package ca.umontreal.iro.dift2905.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static ca.umontreal.iro.dift2905.contacts.DBHelper.ContactColumns.*;

/**
 * La classe DBHelper s'occupe des opérations qui gèrent la base
 * de données contenant les informations sur les contacts.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Contacts.db";

    private static SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        if (db == null)
            db = getWritableDatabase();
    }

    /**
     * @return une liste contenant l'ensemble des contacts.
     */
    List<Contact> getContacts() {
        Cursor cur = db.query(ContactColumns.TABLE_NAME, null,
                null, null, null, null,
                null
        );

        List<Contact> contacts = new ArrayList<>();
        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext())
            contacts.add(new Contact(
                    cur.getInt(cur.getColumnIndex(_ID)),
                    cur.getString(cur.getColumnIndex(COLUMN_NAME_FIRSTNAME)),
                    cur.getString(cur.getColumnIndex(COLUMN_NAME_LASTNAME)),
                    cur.getString(cur.getColumnIndex(COLUMN_NAME_PHONE)),
                    cur.getString(cur.getColumnIndex(COLUMN_NAME_EMAIL)),
                    cur.getInt(cur.getColumnIndex(COLUMN_NAME_FAVORITE))
            ));
        cur.close();

        Collections.sort(contacts, new SortbyName());
        return contacts;
    }


    /**
     * @param favorite
     * @param filter
     * @return une liste de contacts filtrée.
     */
    List<Contact> getContactsFiltered(boolean favorite, String filter) {
        String selection = COLUMN_NAME_FAVORITE + " = ?";
        String[] selectionArgs = new String[]{favorite ? "1" : "0"};
        Cursor cur;

        if(favorite)
            cur = db.query(ContactColumns.TABLE_NAME, null,
                    selection, selectionArgs,
                    null, null, null
            );
        else
            cur = db.query(ContactColumns.TABLE_NAME, null,
                    null, null,
                    null, null, null
            );
        List<Contact> contacts = new ArrayList<>();
        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
            String firstN = cur.getString(cur.getColumnIndex(COLUMN_NAME_FIRSTNAME));
            String lastN = cur.getString(cur.getColumnIndex(COLUMN_NAME_LASTNAME));

            if((firstN != null && firstN.toLowerCase().startsWith(filter.toLowerCase())) ||
                    (lastN != null && lastN.toLowerCase().startsWith(filter.toLowerCase())))
                contacts.add(new Contact(
                        cur.getInt(cur.getColumnIndex(_ID)),
                        cur.getString(cur.getColumnIndex(COLUMN_NAME_FIRSTNAME)),
                        cur.getString(cur.getColumnIndex(COLUMN_NAME_LASTNAME)),
                        cur.getString(cur.getColumnIndex(COLUMN_NAME_PHONE)),
                        cur.getString(cur.getColumnIndex(COLUMN_NAME_EMAIL)),
                        cur.getInt(cur.getColumnIndex(COLUMN_NAME_FAVORITE))
                ));
        }
        cur.close();

        Collections.sort(contacts, new SortbyName());
        return contacts;
    }

    /**
     * Méthode qui ajoute un contact à la base de données
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
     * Méthode qui met à jour un contact de la base de données
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

        String selection = _ID + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(contact.getId())};

        db.update(ContactColumns.TABLE_NAME, values, selection, selectionArgs);
    }

    /**
     * Méthode qui supprime un contact de la base de donnée
     *
     * @param contact contact que l'on veut supprimer
     */
    public void deleteContact(Contact contact) {
        String selection = _ID + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(contact.getId())};

        db.delete(ContactColumns.TABLE_NAME, selection, selectionArgs);
    }

    /**
     * Méthode qui supprime un contact de la base de donnée
     *
     * @param index contact que l'on veut supprimer
     */
    public void deleteContact(int index) {
        String selection = _ID + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(MainActivity.contacts.get(index).getId())};

        db.delete(ContactColumns.TABLE_NAME, selection, selectionArgs);
        MainActivity.contacts.remove(index);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format(
                "CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s INTEGER)",
                ContactColumns.TABLE_NAME, _ID, COLUMN_NAME_FIRSTNAME, COLUMN_NAME_LASTNAME,
                COLUMN_NAME_PHONE, COLUMN_NAME_EMAIL, COLUMN_NAME_FAVORITE
        ));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", ContactColumns.TABLE_NAME));
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
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
