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
     * Get the amount of brick in the resource collection.
     * @return the amount of brick in the resource collection
     */
    public int getBrick();

    /**
     * Get the amount of sheep in the resource collection.
     * @return the amount of sheep in the resource collection
     */
    public int getSheep();

    /**
     * Get the amount of wheat in the resource collection.
     * @return the amount of wheat in the resource collection
     */
    public int getWheat();

    /**
     * Get the amount of the specified type of resource.
     * @param resourceType the type of resource for which to get the amount
     * @return the amount of the specified resource
     */
    public int getCount(ResourceType resourceType);
}
