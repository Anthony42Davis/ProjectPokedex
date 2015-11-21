package adapters;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import models.PokedexDetail;
import pokedex.ant.projectpokedex.R;
import pokedex.ant.projectpokedex.activties.PokedexDetails;

/**
 * Created by Anthony on 2015/09/28.
 * Customer Adapter
 * http://code.tutsplus.com/tutorials/customize-android-fonts--mobile-1601
 * http://www.fontspace.com/jackster-productions/pokemon-gb
 * Custom recycler view adapter
 */
public class PokedexAdapter extends RecyclerView.Adapter<PokedexAdapter.ViewHolder>
{

    //globals
    ArrayList<PokedexDetail> items;


    public PokedexAdapter()
    {

    }

    public void setPokedexItems(ArrayList<PokedexDetail>items)
    {
        this.items = items;

    }
    @Override
    public PokedexAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                .view_pokedex_item,viewGroup,false);
        ViewHolder vHolder = new ViewHolder(view);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i)
    {

        Typeface font = Typeface.createFromAsset(viewHolder.mView.getContext().getAssets(), "Pokemon GB.ttf");
        viewHolder.mTextView.setTypeface(font);
        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent iv = new Intent(v.getContext(),PokedexDetails.class);
                iv.putExtra("items", items.get(i).getPokeUri());
                v.getContext().startActivity(iv);

            }
        });
        viewHolder.mTextView.setText(items.get(i).getPokeName());
    }

    @Override
    public int getItemCount()
    {
        //checks for item count if empty
        if(items == null)
        {
            return 0;
        }
        else
        {
            return items.size();
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        // each data item is just a string in this case
        public View mView;
        public TextView mTextView;
        public ViewHolder(View v)
        {
            super(v);
            mView = v;
            mTextView = (TextView) v.findViewById(R.id.txtName);

        }
    }
}
