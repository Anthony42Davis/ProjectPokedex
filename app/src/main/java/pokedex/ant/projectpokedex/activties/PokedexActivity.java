package pokedex.ant.projectpokedex.activties;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import adapters.PokedexAdapter;
import http.RequestObject;
import models.PokedexDetail;
import pokedex.ant.projectpokedex.R;

/**
 * Created by Anthony on 2015/09/28.
 * Main page that fills the view and does searches
 */
public class PokedexActivity extends AppCompatActivity
{
    //Variable Dec
    private PokedexAdapter pdApdapter;
    private RecyclerView rvPokedex;
    private ArrayList<PokedexDetail>PokeItems;
    private ArrayList<PokedexDetail>PokeSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.green_glow);
        setContentView(R.layout.activity_pokedex);

            String pokeurl = "api/v1/pokedex/1/";
            rvPokedex = (RecyclerView) findViewById(R.id.rvPokedex);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            rvPokedex.setLayoutManager(layoutManager);

            pdApdapter = new PokedexAdapter();
            rvPokedex.setAdapter((pdApdapter));
            new PokedexFetch().execute(pokeurl);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        CreateMenu(menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.search)
                .getActionView();
        if (null != searchView) {
            searchView.setSearchableInfo(searchManager
                    .getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);
        }

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered
                return false;
            }

            public boolean onQueryTextSubmit(String query) {
                //Here u can get the value "query" which is entered in the search box.
                PokeSearch = new ArrayList<>();
                String firstL = query.substring(0, 1).toUpperCase();
                String newName = firstL + query.substring(1);

               for(PokedexDetail pokeDet : PokeItems)
                {
                    if(pokeDet.getPokeName().contains(newName))
                    {
                        PokeSearch.add(pokeDet);
                    }

                }

                if(!PokeSearch.isEmpty())
                {
                    pdApdapter.setPokedexItems(PokeSearch);
                    pdApdapter.notifyDataSetChanged();
                }
                return false;
            }
        };

        //Resets the view
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean queryTextFocused)
            {
                if(!queryTextFocused)
                {
                    rvPokedex.destroyDrawingCache();
                    rvPokedex.setVisibility(RecyclerView.INVISIBLE);
                    rvPokedex.setVisibility(RecyclerView.VISIBLE);
                    pdApdapter.setPokedexItems(PokeItems);
                    pdApdapter.notifyDataSetChanged();
                }
            }
        });
        //===
        searchView.setOnQueryTextListener(queryTextListener);

        return super.onCreateOptionsMenu(menu);
    }

    private  void closeapp()
    {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to close this application?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PokedexActivity.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }// alert dialog box for exit

    @Override
    public void onBackPressed()
    {
        closeapp();
    }

    private class PokedexFetch extends AsyncTask<String, Void, ArrayList<PokedexDetail>>
    {
        @Override
        protected void onPostExecute(ArrayList<PokedexDetail> pokedexDetails) {
            super.onPostExecute(pokedexDetails);
            pdApdapter.setPokedexItems(pokedexDetails);
            pdApdapter.notifyDataSetChanged();
        }

        @Override
        protected ArrayList<PokedexDetail> doInBackground(String... params)
        {
            RequestObject ReqObj = new RequestObject(params[0]);
            JSONObject JORekt = ReqObj.getInformation();
            PokeItems = new ArrayList<>();
            try
            {
                //casting the json object
                JSONArray JOPokemon = (JSONArray) JORekt.get("pokemon");

                for (int i =0; i < JOPokemon.length(); i++)//iterates through the JO
                {
                    JSONObject JOIterate = JOPokemon.getJSONObject(i);

                    //settting first letter to capital
                    String firstLetter = JOIterate.get("name").toString().substring(0,1).toUpperCase();
                    String name = firstLetter + JOIterate.get("name").toString().substring(1);

                    //sending to arraylist
                    PokeItems.add(new PokedexDetail(name,
                            JOIterate.get("resource_uri").toString()));
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            //sorts names into alphabetical order
            Collections.sort(PokeItems);
            return PokeItems;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return MenuChoice(item);
    }
    //****************************************************************
    private void CreateMenu(Menu menu)
    {
        menu.setQwertyMode(true);
        menu.add(0, 1, 1, "Help");
        menu.add(0, 2, 2, "Exit");

    }
    //****************************************************************
    private boolean MenuChoice(MenuItem item)
    {
        switch (item.getItemId())
        {
            case 1:
                Intent is = new Intent(this, PokedexHelp.class);
                startActivity(is);
                return true;

            case 2:
                closeapp();
                return true;

        }
        return false;
    }

}

