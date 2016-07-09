package com.fpadilha.snowmen.views;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fpadilha.snowmen.R;
import com.fpadilha.snowmen.adapters.ListAdapterControl;
import com.fpadilha.snowmen.helpers.SnowmanHelper;
import com.fpadilha.snowmen.models.Snowman;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by felipe on 08/07/2016.
 */
@EViewGroup(R.layout.snowman_view)
public class SnowmanView extends RelativeLayout {

    private final Context context;
    @ViewById
    CircleImageView photo;
    @ViewById
    TextView name;
    @ViewById
    MaterialFavoriteButton favorite;

    public SnowmanView(Context context) {
        super(context);
        this.context = context;
    }

    public void bind(final Snowman snowman, final ListAdapterControl adapterControl) {

        name.setText(snowman.getName());
        favorite.setOnFavoriteChangeListener(null);
        favorite.setFavorite(snowman.isFavorite(), false);
        Picasso.with(context).load(snowman.getPhoto())
                .resize(150, 150)
                .centerCrop().into(photo);

        favorite.setOnFavoriteChangeListener(
                new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                        snowman.setFavorite(favorite);
                        SnowmanHelper.setFavoriteSnowman(context, snowman);

                        adapterControl.refreshList();
                    }
                });

    }
}
