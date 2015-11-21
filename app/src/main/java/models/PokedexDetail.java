package models;

/**
 * Created by Anthony on 2015/09/28.
 * get/set for the pokemon name an uri used to assist in get information from api
 */
public class PokedexDetail implements  Comparable<PokedexDetail>
{
    private String pokeName;
    private String pokeUri;


    public PokedexDetail(String pokeName, String pokeUri) {
        this.pokeName = pokeName;
        this.pokeUri = pokeUri;
    }

    public String getPokeName()
    {
        return pokeName;
    }


    public String getPokeUri()
    {
        return pokeUri;
    }



    @Override
    public int compareTo(PokedexDetail another)
    {
        //https://stackoverflow.com/questions/1814095/sorting-an-arraylist-of-contacts-based-on-name
        return pokeName.compareTo(another.getPokeName());
    }
}
