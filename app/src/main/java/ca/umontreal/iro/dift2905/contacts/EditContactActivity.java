package ca.umontreal.iro.dift2905.contacts;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import ca.umontreal.iro.dift2905.contacts.databinding.ActivityEditContactBinding;

/**
 * La classe EditContactActivity fournit des méthodes pour l'activité
 * qui permet de modifier ou d'ajouter un contact à la liste de contacts.
 */
public class EditContactActivity extends AppCompatActivity {
    private ActivityEditContactBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_contact);

        binding.setContact(getIntent().getParcelableExtra("contact"));
    }

    /**
     * Méthode qui crée le menu d'option dans le coin supérieur droit
     *
     * @param menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_contact_menu, menu);
        return true;
    }

    /**
     * Méthode qui sauvegarde la modification d'un contact ou qui en crée un
     * nouveau si le contact n'existe pas déjà lors d'un clic sur le bouton "SAVE".
     *
     * @param item bouton cliqué dans le menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            Contact contact = binding.getContact();

            if (!contact.isNameNull()) {
                DBHelper dbHelper = new DBHelper(getBaseContext());

                if (contact.getId() > 0)
                    dbHelper.updateContact(contact);
                else
                    dbHelper.addContact(contact);

                finish();
                return true;

            }
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
