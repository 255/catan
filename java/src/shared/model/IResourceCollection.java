package shared.model;

import shared.definitions.ResourceType;

/**
 * An abstract collection of resources.
 * @author Wyatt
 */
public interface IResourceCollection {
    /**
     * Get the amount of wood in the resource collection.
     * @return the amount of wood in the resource collection
     */
    public int getWood();

    /**
     * Set the amount of wood in the resource collection.
     * @param wood
     */
    public void setWood(int wood);

    /**
     * Get the amount of brick in the resource collection.
     * @return the amount of brick in the resource collection
     */
    public int getBrick();

    /**
     * Set the amount of brick in the resource collection.
     * @param brick
     */
    public void setBrick(int brick);

    /**
     * Get the amount of sheep in the resource collection.
     * @return the amount of sheep in the resource collection
     */
    public int getSheep();

    /**
     * Set the amount of sheep in the resource collection.
     * @param sheep
     */
    public void setSheep(int sheep);

    /**
     * Get the amount of wheat in the resource collection.
     * @return the amount of wheat in the resource collection
     */
    public int getWheat();

    /**
     * Set the amount of wheat in the resource collection.
     * @param wheat
     */
    public void setWheat(int wheat);

    /**
     * Get the amount of ore in the resource collection.
     * @return the amount of ore in the resource collection
     */
    public int getOre();

    /**
     * Set the amount of ore in the resource collection.
     * @param ore
     */
    public void setOre(int ore);

    /**
     * Get the amount of the specified type of resource.
     * @param resourceType the type of resource for which to get the amount
     * @return the amount of the specified resource
     */
    public int getCount(ResourceType resourceType);
}
