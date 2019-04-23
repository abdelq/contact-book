package ca.umontreal.iro.dift2905.contacts.utils;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import java.util.List;

import ca.umontreal.iro.dift2905.contacts.Contact;
import ca.umontreal.iro.dift2905.contacts.R;

import static android.view.LayoutInflater.from;

public class ContactAdapter extends Adapter<ContactViewHolder> {
    private List<Contact> mContacts;

    public ContactAdapter(List<Contact> contacts) {
        mContacts = contacts;
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

        holder.initialsTextView.setText(contact.getInitials());
        holder.nameTextView.setText(contact.getFullName());
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }
}
