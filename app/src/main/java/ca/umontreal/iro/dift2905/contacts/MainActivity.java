package ca.umontreal.iro.dift2905.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import ca.umontreal.iro.dift2905.contacts.utils.ContactAdapter;
import ca.umontreal.iro.dift2905.contacts.utils.ContactSwipeCallback;

/**
 * La classe MainActivity fournit les méthodes pour l'activité principale
 * qui affiche la liste de tous les contacts ou des contacts favoris, et qui
 * offre l'option d'ajouter un nouveau contact.
 */
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
        adapter = new ContactAdapter(contacts, this);
        favorites = false;
        filter = "";
        this.setTitle(getResources().getString(R.string.all));

        RecyclerView contactList = findViewById(R.id.contact_list);
        contactList.setHasFixedSize(false);
        contactList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        contactList.setAdapter(adapter);

        ItemTouchHelper touchHelper = new ItemTouchHelper((new ContactSwipeCallback(getBaseContext(), adapter)));
        touchHelper.attachToRecyclerView(contactList);


        /* Barre de navigation inférieure qui permet de passer de l'affichage de tous les
           contacts à l'affichage des favoris seulement */
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
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        update(favorites);
    }

    @Override
    protected void onResume() {
        super.onResume();
        update(favorites);
    }

    /**
     * Méthode qui crée le menu d'options dans le coin supérieur droit
     *
     * @param menu
     */
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
                adapter.setFilter(filter);
                adapter.notifyDataSetChanged();
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Méthode qui ouvre l'activité d'édition de contact lors d'un clic
     * sur le bouton "nouveau contact"
     *
     * @param item bouton cliqué dans le menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.new_contact) {
            Intent intent =new Intent(this, EditContactActivity.class);
            Bundle extras = new Bundle();
            extras.putParcelable("contact", new Contact());
            extras.putString("title", "New contact");
            intent.putExtras(extras);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Méthode qui met à jour l'affichage de la liste de contacts
     *
     * @param favorites == true si on veut visualiser les favoris
     *                  == false si on veut visualiser tous les contacts
     */
    public void update(boolean favorites){
        if(favorites)
            this.setTitle(getResources().getString(R.string.favorites));
        else
            this.setTitle(getResources().getString(R.string.all));
        contacts.clear();
        contacts.addAll(new DBHelper(getBaseContext()).getContactsFiltered(favorites, filter));
        adapter.notifyDataSetChanged(); // XXX
    }
}
