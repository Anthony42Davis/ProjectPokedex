package models;

/**
 * Created by Anthony on 2015/10/06.
 * specific gets/set related to pokemon evolved state
 */
public class PokemonEvo
{

    private String  level;
    private String evoTo;
    private String  evoToResource;

    //class also needs to be accessed without the passed param, hence reason for empty constructor
    public PokemonEvo()
    {

    }
    public PokemonEvo(String level, String evoTo, String evoToResource)
    {
        this.level = level;
        this.evoTo = evoTo;
        this.evoToResource = evoToResource;
    }

    public String getLevel()
    {
        return level;
    }

    public void setLevel(String level)
    {
        this.level = level;
    }

    public String getEvoTo()
    {
        return evoTo;
    }

    public void setEvoTo(String evoTo)
    {
        this.evoTo = evoTo;
    }

    public String getEvoToResource()
    {
        return evoToResource;
    }

    public void setEvoToResource(String evoToResource)
    {
        this.evoToResource = evoToResource;
    }



}
