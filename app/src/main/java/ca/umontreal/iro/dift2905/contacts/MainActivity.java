package ca.umontreal.iro.dift2905.contacts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import ca.umontreal.iro.dift2905.contacts.recyclerutils.ContactAdapter;
import ca.umontreal.iro.dift2905.contacts.recyclerutils.ContactAdapter.OnItemClickListener;
import ca.umontreal.iro.dift2905.contacts.recyclerutils.ContactSwipeCallback;

import static androidx.recyclerview.widget.DividerItemDecoration.VERTICAL;

/**
 * MainActivity affiche la liste de tous les contacts ou seulement les favoris.
 * Il offre aussi l'option d'ajouter un nouveau contact et d'en filtrer selon
 * le nom.
 */
public class MainActivity extends AppCompatActivity {
    private List<Contact> contacts;
    private ContactAdapter adapter;
    private boolean favorites;
    private String filter = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.all_contacts));

        contacts = new DBHelper(this).getContacts();
        adapter = new ContactAdapter(contacts, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent dial = new Intent(Intent.ACTION_DIAL,
                        Uri.parse("tel:" + contacts.get(position).getPhone()));
                startActivity(dial); // Appel du contact
            }

            @Override
            public boolean onItemLongClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, EditContactActivity.class);
                intent.putExtra("contact", contacts.get(position));
                startActivity(intent); // Édition du contact
                return true;
            }
        });

        /* Liste de contacts */
        RecyclerView contactList = findViewById(R.id.contact_list);
        contactList.setAdapter(adapter);
        contactList.setHasFixedSize(true);
        contactList.addItemDecoration(new DividerItemDecoration(this, VERTICAL));

        ItemTouchHelper helper = new ItemTouchHelper(new ContactSwipeCallback(this, adapter));
        helper.attachToRecyclerView(contactList);

        /* Barre de navigation du bas */
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_all);
        navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_fav:
                    favorites = true;
                    update();
                    return true;
                case R.id.navigation_all:
                    favorites = false;
                    update();
                    return true;
            }
            return false;
        });
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        update(); // XXX
    }

    @Override
    protected void onResume() {
        super.onResume();
        update(); // XXX
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        SearchView view = (SearchView) menu.findItem(R.id.search_contact).getActionView();

        view.setOnCloseListener(() -> {
            filter = "";
            return false;
        });

        view.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                /* XXX */
                filter = newText;
                adapter.setFilter(filter);
                update();
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != R.id.new_contact)
            return super.onOptionsItemSelected(item);

        Intent intent = new Intent(this, EditContactActivity.class);
        intent.putExtra("contact", new Contact());
        startActivity(intent);
        return true;
    }

    /**
     * Met à jour l'affichage de la liste de contacts.
     */
    public void update() {
        setTitle(getString(favorites ? R.string.favorite_contacts : R.string.all_contacts));

        /* XXX */
        contacts.clear();
        contacts.addAll(new DBHelper(this).getContacts(favorites, filter));
        adapter.notifyDataSetChanged();
    }
}
