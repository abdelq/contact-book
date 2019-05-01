package ca.umontreal.iro.dift2905.contacts;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import ca.umontreal.iro.dift2905.contacts.databinding.ActivityEditContactBinding;

import static android.widget.Toast.makeText;

/**
 * EditContactActivity est l'activité qui contient un formulaire permettant
 * d'ajouter ou de modifier un contact.
 */
public class EditContactActivity extends AppCompatActivity {
    private Contact contact;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityEditContactBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_contact);

        contact = (Contact) getIntent().getSerializableExtra("contact");
        binding.setContact(contact);
        if (contact.getId() > 0)
            setTitle(getString(R.string.edit_contact));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_contact_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != R.id.save)
            return super.onOptionsItemSelected(item);

        if (contact.hasName()) {
            DBHelper dbHelper = new DBHelper(getBaseContext());
            if (contact.getId() > 0)
                dbHelper.updateContact(contact);
            else
                dbHelper.addContact(contact);

            finish();
        } else {
            makeText(getBaseContext(), getString(R.string.name_error), Toast.LENGTH_LONG).show();
        }

        return true;
    }
}
