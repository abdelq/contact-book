package ca.umontreal.iro.dift2905.contacts.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import ca.umontreal.iro.dift2905.contacts.R;

import static java.util.Objects.requireNonNull;

public class ContactSwipeCallback extends SimpleCallback {
    private Drawable icon;
    private int iconWidth, iconHeight;

    public ContactSwipeCallback(Context context) {
        // TODO Vous avez le droit de changer la signature du constructeur
        super(0, 0);

        icon = context.getDrawable(R.drawable.ic_delete_primary_24dp);
        iconWidth = requireNonNull(icon).getIntrinsicWidth();
        iconHeight = requireNonNull(icon).getIntrinsicHeight();
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull ViewHolder viewHolder,
                          @NonNull ViewHolder target) {
        return false; // TODO
    }

    @Override
    public void onSwiped(@NonNull ViewHolder viewHolder, int direction) {
        // TODO
    }

    @Override
    public void onChildDraw(@NonNull Canvas canvas,
                            @NonNull RecyclerView recyclerView, @NonNull ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        // Cette méthode gère l'affichace de l'icône "poubelle" lorsqu'on glisse le contach à
        // gauche. TODO IL N'EST PAS NÉCESSAIRE DE LA MODIFIER.

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
