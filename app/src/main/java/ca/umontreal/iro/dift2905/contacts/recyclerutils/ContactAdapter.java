package ca.umontreal.iro.dift2905.contacts.recyclerutils;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import java.util.List;

import ca.umontreal.iro.dift2905.contacts.Contact;
import ca.umontreal.iro.dift2905.contacts.R;

import static android.view.LayoutInflater.from;

/**
 * ContactAdapter permet d'associer le jeu de données de contacts à la vue
 * d'items affichée dans le {@link RecyclerView}.
 */
public class ContactAdapter extends Adapter<ContactViewHolder> {
    private List<Contact> mContacts;
    private OnItemClickListener mListener;
    private String filter;

    public ContactAdapter(List<Contact> contacts, OnItemClickListener listener) {
        mContacts = contacts;
        mListener = listener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = from(parent.getContext()).inflate(R.layout.contact_list_item, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = mContacts.get(position);

        /* XXX */
        int startIndex = -1, endIndex = -1;
        if (filter != null) {
            startIndex = contact.getFullName().toLowerCase().indexOf(filter.toLowerCase());
            endIndex = startIndex + filter.length();
        }

        holder.setInitials(contact.getInitials());
        holder.setName(contact.getFullName(), startIndex, endIndex);
        holder.setListener(mListener);
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    /**
     * Met à jour le filtre de recherche.
     *
     * @param filter filtre de recherche
     */
    public void setFilter(String filter) {
        this.filter = filter;
    }

    /**
     * Supprime un item de la liste et notifie du changement.
     *
     * @param position position de l'item
     * @return item supprimé
     */
    Contact deleteItem(int position) {
        Contact contact = mContacts.remove(position);
        notifyItemRemoved(position);
        return contact;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        boolean onItemLongClick(View view, int position);
    }
}
