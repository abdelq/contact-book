package ca.umontreal.iro.dift2905.contacts;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import ca.umontreal.iro.dift2905.contacts.databinding.ActivityEditContactBinding;

public class EditContactActivity extends AppCompatActivity {
    private ActivityEditContactBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_contact);

        binding.setContact(getIntent().getParcelableExtra("contact"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_contact_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            Contact contact = binding.getContact();
            DBHelper dbHelper = new DBHelper(getBaseContext());

            if (contact.getId() > 0)
                dbHelper.updateContact(contact);
            else
                dbHelper.addContact(contact);

            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
