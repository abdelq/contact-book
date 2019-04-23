package ca.umontreal.iro.dift2905.contacts;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import ca.umontreal.iro.dift2905.contacts.databinding.ActivityEditContactBinding;

import static ca.umontreal.iro.dift2905.contacts.databinding.ActivityEditContactBinding.inflate;

public class EditContactActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        ActivityEditContactBinding binding = inflate(getLayoutInflater());
        //binding.setContact(); // TODO
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_contact_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save)
            return super.onOptionsItemSelected(item); // TODO
        return super.onOptionsItemSelected(item);
    }
}
