package ca.umontreal.iro.dift2905.contacts.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import java.util.List;

import ca.umontreal.iro.dift2905.contacts.Contact;
import ca.umontreal.iro.dift2905.contacts.DBHelper;
import ca.umontreal.iro.dift2905.contacts.EditContactActivity;
import ca.umontreal.iro.dift2905.contacts.R;

import static android.view.LayoutInflater.from;

/**
 * La classe ContactAdapter contient des méthodes qui permettent
 * l'association des initiales et des noms aux poisitions des contacts dans
 * la liste.
 */
public class ContactAdapter extends Adapter<ContactViewHolder> {
    private List<Contact> mContacts;
    private Context mContext;

    public ContactAdapter(List<Contact> contacts, Context context) {
        mContacts = contacts;
        mContext = context;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = from(parent.getContext()).inflate(R.layout.contact_list_item, parent, false);
        return new ContactViewHolder(view);
    }

    /**
     * Méthode qui met à jour le contenu du holder
     * et traite les actions sur un contact à une position donnée
     *
     * @param holder
     * @param position du contact
     */
    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = mContacts.get(position);

        holder.initialsTextView.setText(contact.getInitials());
        holder.nameTextView.setText(contact.getFullName());
        
        /* Démarre l'activité d'édition de contact lors d'un long clic sur un contact */
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent =new Intent(mContext, EditContactActivity.class);
                Bundle extras = new Bundle();
                extras.putParcelable("contact", contact);
                extras.putString("title", "Edit contact");
                intent.putExtras(extras);
                mContext.startActivity(intent);
                return false;
            }
        });
        
        /* Permet un appel lors d'un clic sur un contact */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent call = new Intent(Intent.ACTION_DIAL);
                call.setData(Uri.parse("tel:"+contact.getPhone()));
                mContext.startActivity(call);
            }
        });
    }

    /**
     * @return le nombre de contacts
     */
    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    /**
     * Méthode qui retire un contact de la liste
     *
     * @param context
     * @param position position du contact à retirer
     */
    public void removeContact(Context context, int position){
        Contact contact = mContacts.get(position);
        new DBHelper(context).deleteContact(contact);
        mContacts.remove(position);
        notifyItemRemoved(position);
    }
}
