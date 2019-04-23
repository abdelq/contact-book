package ca.umontreal.iro.dift2905.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import ca.umontreal.iro.dift2905.contacts.utils.ContactAdapter;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Contact> contacts = new DBHelper(getBaseContext()).getContacts();
        ContactAdapter adapter = new ContactAdapter(contacts);

        RecyclerView contactList = findViewById(R.id.contact_list);
        contactList.setHasFixedSize(true);
        contactList.setAdapter(adapter);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_fav:
                    contacts.removeIf(x -> !x.getIsFavorite());
                    adapter.notifyDataSetChanged(); // XXX
                    return true;
                case R.id.navigation_all:
                    List<Contact> notFavorite = new DBHelper(getBaseContext()).getContacts(false);
                    contacts.addAll(notFavorite);
                    adapter.notifyItemRangeInserted(contacts.size() - notFavorite.size(), notFavorite.size());
                    return true;
            }
            return false;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.new_contact)
            startActivity(new Intent(this, EditContactActivity.class));
        return super.onOptionsItemSelected(item);
    }
}
