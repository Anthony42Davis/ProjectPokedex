package pokedex.ant.projectpokedex.activties;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import http.RequestObject;
import models.PokemonDetails;
import models.PokemonEvo;
import pokedex.ant.projectpokedex.R;

/**
 * Created by Anthony on 2015/09/30.
 * http://square.github.io/picasso/
 */
public class PokedexDetails extends AppCompatActivity
{

    TextView txtName;
    TextView txtSpecies;
    TextView txtType;
    TextView txtEvolution;
    ImageView imgVPokemon;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.green_glow);
        setContentView(R.layout.activity_pokedex_item);

        Intent intentD = getIntent();

        if(intentD.getStringExtra("items") != null)
        {
            url = intentD.getStringExtra("items");
        }
        //instantiate the views
        txtName = (TextView) findViewById(R.id.txtPokeName);
        txtSpecies = (TextView) findViewById(R.id.txtSpecies);
        txtType = (TextView) findViewById(R.id.txtType);
        txtEvolution = (TextView) findViewById(R.id.txtEvolution);
        imgVPokemon = (ImageView)findViewById(R.id.imageView);

        //sets the font
        Typeface font = Typeface.createFromAsset(getAssets(), "Pokemon GB.ttf");
        txtName.setTypeface(font);
        txtSpecies.setTypeface(font);
        txtType.setTypeface(font);
        txtEvolution.setTypeface(font);

        new PokedexFetch(this).execute(url);


    }

    //==============================================================================

    public String formatText(String text)
    {
        //getting name and setting first letter capital
        if(!text.equals(null) && !text.isEmpty())
        {
            String firstLetter = text.substring(0, 1).toUpperCase();
            String newName = firstLetter + text.substring(1);
            return newName;
        }
        else
        {
            //Returns unknown species if no species
            return "Unknown Species";
        }
    }


    private class PokedexFetch extends AsyncTask<String, Void, PokemonDetails>
    {
        //context of the async
        Context context;
        String mType ="";
        public PokedexFetch(Context context)
        {
            this.context = context;
        }


        @Override
        protected void onPostExecute(final PokemonDetails objPokeDetails)
        {
            super.onPostExecute(objPokeDetails);
            LinearLayout layout =(LinearLayout)findViewById(R.id.pokeLinear);

            //sets the card background
            switch (mType) {
                case "grass":
                case "bug":
                    layout.setBackgroundResource(R.drawable.grass);
                    break;
                case "electric":
                    layout.setBackgroundResource(R.drawable.lightning);
                    break;
                case "fire":
                    layout.setBackgroundResource(R.drawable.fire_modern);
                    break;
                case "lava":
                    layout.setBackgroundResource(R.drawable.fire_classic);
                    break;
                case "fighting":
                    layout.setBackgroundResource(R.drawable.fighting);
                    break;
                case "water":
                case "ice":
                    layout.setBackgroundResource(R.drawable.water);
                    break;
                case "psychic":
                case "poison":
                    layout.setBackgroundResource(R.drawable.psychic);
                    break;
                case "rock":
                    layout.setBackgroundResource(R.drawable.earth);
                    break;
                case "steel":
                    layout.setBackgroundResource(R.drawable.metal_modern);
                    break;
                case "dark":
                case "ghost":
                    layout.setBackgroundResource(R.drawable.metal_classic);
                    break;
            }
            //set the ojbects obtained to the views
            txtName.setText(objPokeDetails.getPokeName());
            txtSpecies.setText("Species" + "\n\n\t" + objPokeDetails.getSpecies());
            txtType.setText("Type" + "\n\n\t" + objPokeDetails.getTypes());
            //picasso library setting the image
            Picasso.with(context).load(objPokeDetails.getImage()).into(imgVPokemon);
            txtEvolution.setText("Evolution" + "\n\n" + "\n\tLevel: " + objPokeDetails
                    .getEvolution().getLevel() + "\n\tInto : " +
                    objPokeDetails.getEvolution().getEvoTo());
            //moves to the evolved state
            if(objPokeDetails.getEvolution().getEvoToResource() != null )
            {
                txtEvolution.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                            Intent iv = new Intent(v.getContext(), PokedexDetails.class);
                            iv.putExtra("items", objPokeDetails
                                    .getEvolution().getEvoToResource());
                            v.getContext().startActivity(iv);

                    }
                });

            }

        }

        @Override
        protected PokemonDetails doInBackground(String... params)
        {
            //first param being sent in
            RequestObject ReqObj = new RequestObject(params[0]);
            JSONObject JORekt = ReqObj.getInformation();
            PokemonDetails objPokeDetails = new PokemonDetails();

            try
            {
                //obtaining the objects
                PokemonEvo PokeEvo = new PokemonEvo();
                String imguri = "http://pokeapi.co/";
                String oldName = JORekt.get("name").toString();
                String oldSpecies = JORekt.get("species").toString();
                JSONArray types = (JSONArray) JORekt.get("types");
                JSONArray imgArray = (JSONArray) JORekt.get("sprites");
                JSONArray JOAevo = (JSONArray) JORekt.get("evolutions");
                //check if the arraylist is empty
                if(JOAevo.length() > 0)
                {
                    JSONObject evo = (JSONObject) JOAevo.get(0);
                    String evolveTo = "";
                    String evoLvl = "";

                    if (evo.has("level")) {
                        evoLvl = evo.get("level").toString();
                    } else {
                        evoLvl = "Unknown";
                    }
                    if (evo.has("to")) {
                        evolveTo = evo.get("to").toString();
                    } else {
                        evolveTo = "Unknown";
                    }
                    PokeEvo.setEvoToResource(evo.get("resource_uri").toString());
                    PokeEvo.setEvoTo(formatText(evolveTo));
                    PokeEvo.setLevel(formatText(evoLvl));
                }
                else
                {
                    PokeEvo.setEvoToResource("Unknown");
                    PokeEvo.setEvoTo(formatText("Unknown"));
                    PokeEvo.setLevel(formatText("Unknown"));
                }
                objPokeDetails.setEvolution(PokeEvo);
                objPokeDetails.setSpecies(formatText(oldSpecies));
                objPokeDetails.setPokeName(formatText(oldName));

                JSONObject JOIterate = imgArray.getJSONObject(0);
                RequestObject ReqImg = new RequestObject(JOIterate.get("resource_uri").toString());
                JSONObject JOImg = ReqImg.getInformation();
                objPokeDetails.setImage(imguri + JOImg.get("image").toString());
                String temp="";
                String passTemp ="";

                for (int i =0; i < types.length(); i++)//iterates through the JO
                {

                    JSONObject JOType = types.getJSONObject(i);
                    temp = JOType.get("name").toString();
                    mType = temp;
                    passTemp += formatText(temp);
                    //adds  comma delimeter when more than 1 type
                    if(i != types.length() -1)
                    {
                        passTemp += ", ";
                    }
                }

                objPokeDetails.setTypes(passTemp);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            return objPokeDetails;
        }

    }
}
