package models;



/**
 * Created by Anthony on 2015/10/01.
 * gets/sets for the individual pokemon information
 */
public class PokemonDetails
{
    private String pokeName;
    private String Species;
    private String Types;
    private PokemonEvo Evolution;
    private String Image;

    //class also needs to be accessed without the passed param, hence reason for empty constructor
    public PokemonDetails()
    {

    }

    public PokemonDetails(String pokeName, String species, String types,
                          PokemonEvo evolution, String image) {
        this.pokeName = pokeName;
        this.Species = species;
        this.Types = types;
        this.Evolution = evolution;
        this.Image = image;
    }

    public String getPokeName() {
        return pokeName;
    }

    public void setPokeName(String pokeName) {
        this.pokeName = pokeName;
    }

    public String getSpecies() {
        return Species;
    }

    public void setSpecies(String species) {
        Species = species;
    }

    public String getTypes() {
        return Types;
    }

    public void setTypes(String types) {
        Types = types;
    }

    public PokemonEvo getEvolution() {
        return Evolution;
    }

    public void setEvolution(PokemonEvo evolution) {
        Evolution = evolution;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String  images) {
        Image = images;
    }

}
