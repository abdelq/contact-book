package ca.umontreal.iro.dift2905.contacts.recyclerutils;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import ca.umontreal.iro.dift2905.contacts.R;
import ca.umontreal.iro.dift2905.contacts.recyclerutils.ContactAdapter.OnItemClickListener;

import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;

/**
 * ContactViewHolder contient les éléments qui sont affichés dans un item de la
 * liste de contacts.
 */
class ContactViewHolder extends ViewHolder {
    private TextView initialsTextView;
    private TextView nameTextView;

    ContactViewHolder(@NonNull View itemView) {
        super(itemView);

        initialsTextView = itemView.findViewById(R.id.initials);
        nameTextView = itemView.findViewById(R.id.name);
    }

    /**
     * @param initials initiales à afficher
     */
    void setInitials(String initials) {
        initialsTextView.setText(initials);
    }

    /**
     * @param name nom à afficher
     * @param startIndex index de début du soulignement
     * @param endIndex index de fin du soulignement
     */
    void setName(String name, int startIndex, int endIndex) {
        SpannableString text = new SpannableString(name);
        if (startIndex != -1)
            text.setSpan(new UnderlineSpan(), startIndex, endIndex, SPAN_EXCLUSIVE_EXCLUSIVE); // XXX Flag
        nameTextView.setText(text);
    }

    /**
     * @param listener événement de clic d'un item
     */
    void setListener(OnItemClickListener listener) {
        itemView.setOnClickListener(v -> listener.onItemClick(v, getAdapterPosition()));
        itemView.setOnLongClickListener(v -> listener.onItemLongClick(v, getAdapterPosition()));
    }
}
