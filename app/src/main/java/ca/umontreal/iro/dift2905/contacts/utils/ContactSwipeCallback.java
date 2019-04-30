package ca.umontreal.iro.dift2905.contacts.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import ca.umontreal.iro.dift2905.contacts.Contact;
import ca.umontreal.iro.dift2905.contacts.R;

import static java.util.Objects.requireNonNull;

/**
 * La classe ContactSwipeCallback contient des méthodes qui traitent les
 * actions apportées aux contacts du carnet d'adresse.
 */
public class ContactSwipeCallback extends SimpleCallback {
    private final ContactAdapter adapter;
    private Context context;
    private Drawable icon;
    private int iconWidth, iconHeight;

    public ContactSwipeCallback(Context context, ContactAdapter adapter) {
        // TODO Vous avez le droit de changer la signature du constructeur
        super(ItemTouchHelper.UP, ItemTouchHelper.LEFT);
        this.adapter = adapter;
        this.context = context;
        icon = context.getDrawable(R.drawable.ic_delete_primary_24dp);
        iconWidth = requireNonNull(icon).getIntrinsicWidth();
        iconHeight = requireNonNull(icon).getIntrinsicHeight();
    }

    /**
     * Methode qui traite le mouvement de la liste
     *
     * @param recyclerView
     * @param viewHolder
     * @param target
     * @return
     */
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull ViewHolder viewHolder,
                          @NonNull ViewHolder target) {
        return false; // TODO
    }

    /**
     * Méthode qui traite le glissement sur un contact
     *
     * @param viewHolder
     * @param direction direction du glissement
     */
    @Override
    public void onSwiped(@NonNull ViewHolder viewHolder, int direction) {
        adapter.removeContact(context, viewHolder.getAdapterPosition());
        //TODO : Supprimer le contact et mettre à jour la mainActivityView

    }

    /**
     * Méthode qui gère l'affichage de l'icone "poubelle" lorsqu'on glisse
     * le contact à gauche
     *
     * @param canvas
     * @param recyclerView
     * @param viewHolder
     * @param dX
     * @param dY
     * @param actionState
     * @param isCurrentlyActive
     */
    @Override
    public void onChildDraw(@NonNull Canvas canvas,
                            @NonNull RecyclerView recyclerView, @NonNull ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {

        View itemView = viewHolder.itemView;
        int itemHeight = itemView.getBottom() - itemView.getTop();

        ColorDrawable background = new ColorDrawable(Color.LTGRAY);
        background.setBounds(
                itemView.getRight() + (int) dX,
                itemView.getTop(),
                itemView.getRight(),
                itemView.getBottom()
        );
        background.draw(canvas);

        if (isCurrentlyActive) {
            // Calculate position of delete icon
            int iconTop = itemView.getTop() + (itemHeight - iconHeight) / 2;
            int iconBottom = iconTop + iconHeight;
            int iconMargin = (itemHeight - iconHeight) / 2;

            int iconRight = itemView.getRight() - iconMargin;
            int iconLeft = Math.max(itemView.getRight()
                    + (int) dX, itemView.getRight() - iconMargin - iconWidth);
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
            icon.draw(canvas);
        }

        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
