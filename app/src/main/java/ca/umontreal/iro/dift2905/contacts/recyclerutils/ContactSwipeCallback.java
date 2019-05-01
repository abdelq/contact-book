package ca.umontreal.iro.dift2905.contacts.recyclerutils;

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

import ca.umontreal.iro.dift2905.contacts.DBHelper;
import ca.umontreal.iro.dift2905.contacts.R;

/**
 * ContactSwipeCallback gère les actions supportées par le carnet d'addresse.
 */
public class ContactSwipeCallback extends SimpleCallback {
    private ContactAdapter adapter;
    private Context context;
    private Drawable icon;

    public ContactSwipeCallback(Context context, ContactAdapter adapter) {
        super(0, ItemTouchHelper.LEFT);

        /* XXX */
        this.adapter = adapter;
        this.context = context;

        icon = context.getDrawable(R.drawable.ic_delete_primary_24dp);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull ViewHolder viewHolder, @NonNull ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull ViewHolder viewHolder, int direction) {
        if (direction == ItemTouchHelper.LEFT)
            new DBHelper(context).deleteContact(adapter.deleteItem(viewHolder.getAdapterPosition())); // XXX
    }

    @Override
    public void onChildDraw(@NonNull Canvas canvas,
                            @NonNull RecyclerView recyclerView, @NonNull ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View itemView = viewHolder.itemView;

        ColorDrawable background = new ColorDrawable(Color.LTGRAY);
        background.setBounds(
                itemView.getRight() + (int) dX,
                itemView.getTop(),
                itemView.getRight(),
                itemView.getBottom()
        );
        background.draw(canvas);

        if (isCurrentlyActive) {
            int itemHeight = itemView.getBottom() - itemView.getTop();

            int iconWidth = icon.getIntrinsicWidth();
            int iconHeight = icon.getIntrinsicHeight();
            int iconMargin = (itemHeight - iconHeight) / 2;

            /* Position de l'icône de suppression */
            int iconTop = itemView.getTop() + iconMargin;
            int iconBottom = iconTop + iconHeight;
            int iconRight = itemView.getRight() - iconMargin;
            int iconLeft = Math.max(itemView.getRight() + (int) dX, iconRight - iconWidth);

            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
            icon.draw(canvas);
        }

        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
