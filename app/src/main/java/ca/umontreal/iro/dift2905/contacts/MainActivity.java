package ca.umontreal.iro.dift2905.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import ca.umontreal.iro.dift2905.contacts.utils.ContactAdapter;
import ca.umontreal.iro.dift2905.contacts.utils.ContactSwipeCallback;

public class MainActivity extends AppCompatActivity {
    ContactAdapter adapter;
    List<Contact> contacts;
    boolean favorites;
    String filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contacts = new DBHelper(getBaseContext()).getContacts();
        adapter = new ContactAdapter(contacts);
        favorites = false;
        filter = "";

        RecyclerView contactList = findViewById(R.id.contact_list);
        contactList.setHasFixedSize(true);
        contactList.setAdapter(adapter);

        ItemTouchHelper touchHelper = new ItemTouchHelper(new ContactSwipeCallback(getBaseContext()));
        touchHelper.attachToRecyclerView(contactList);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setSelectedItemId(favorites? R.id.navigation_fav : R.id.navigation_all);
        navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_fav:
                    favorites = true;
                    update(favorites);
                    return true;
                case R.id.navigation_all:
                    favorites = false;
                    update(favorites);
                    return true;
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        update(favorites);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem search = menu.findItem(R.id.search_contact);
        SearchView view = (SearchView) search.getActionView();

        view.setOnCloseListener(() -> {
            filter = "";
            return false;
        });

        view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter = newText;
                contacts.clear();
                System.out.println(favorites);
                contacts.addAll(new DBHelper(getBaseContext()).getContactsFiltered(favorites, filter));
                adapter.notifyDataSetChanged();
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.new_contact) {
            Intent intent =new Intent(this, EditContactActivity.class);
            intent.putExtra("contact", new Contact());
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void update(boolean favorites){
        contacts.clear();
        contacts.addAll(new DBHelper(getBaseContext()).getContactsFiltered(favorites, filter));
        adapter.notifyDataSetChanged(); // XXX
    }
}
