package pokedex.ant.projectpokedex.activties;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import pokedex.ant.projectpokedex.R;

/**
 * Created by Anthony on 2015/10/07.
 * Help informaton
 */
public class PokedexHelp extends AppCompatActivity
{

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        TextView txtHelp = (TextView) findViewById(R.id.txtHelp);

        //sets the font
        Typeface font = Typeface.createFromAsset(getAssets(), "Pokemon GB.ttf");
        txtHelp.setTypeface(font);
    }

}
