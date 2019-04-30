package ca.umontreal.iro.dift2905.contacts.utils;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import ca.umontreal.iro.dift2905.contacts.R;

/**
 * La classe ContactViewHolder contient les éléments qui sont
 * affichés dans la liste de contacts pour les représenter.
 */
class ContactViewHolder extends ViewHolder {
    TextView initialsTextView;
    TextView nameTextView;

    ContactViewHolder(@NonNull View itemView) {
        super(itemView);

        initialsTextView = itemView.findViewById(R.id.initials);
        nameTextView = itemView.findViewById(R.id.name);
    }
}
