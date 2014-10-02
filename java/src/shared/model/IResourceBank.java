package shared.model;

import shared.definitions.ResourceType;

/**
 * A bank of resources. Resources can be added and subtracted as ResourceBanks.
 * This can represent, e.g., the resources a player is holding or the game's resource bank.
 * @author Wyatt
 */
public interface IResourceBank {
    /**
     * Takes in a ResourceType and outputs a bundle of one of that resource
     * @param rType the type of resource in the bundle
     * @return the bundle object containing the one resource
     */
    public static IResourceBank resourceToBank(ResourceType rType) {
        return resourceToBank(rType,1);
    }

    /**
     * Takes in a ResourceType and outputs a bundle of one of that resource
     * @param rType the type of resource in the bundle
     * @param count the number of the resource type to put in the bundle
     * @return the bundle object containing the one resource
     */
    public static IResourceBank resourceToBank(ResourceType rType, int count) {
        switch (rType) {
            case WOOD:
                return new ResourceBank(count,0,0,0,0);
            case BRICK:
                return new ResourceBank(0,count,0,0,0);
            case SHEEP:
                return new ResourceBank(0,0,count,0,0);
            case WHEAT:
                return new ResourceBank(0,0,0,count,0);
            case ORE:
                return new ResourceBank(0,0,0,0,count);
            default:
                return new ResourceBank(0,0,0,0,0);
        }
    }

    /**
     * Add the specified resources to the resource bank.
     * @param resources the bundle of resources to add to the bank
     */
    public void add(IResourceBank resources);

    /**
     * Subtract the specified resources from the resource bank.
     * If the number of resources being subtracted exceeds the number of
     * resources in the resource bank, an exception is thrown.
     * @param resources the bundle of resources to remove from the bank
     */
    public IResourceBank subtract(IResourceBank resources);

    /**
     * Checks if the resource bank has enough resources for the purchase
     * @param purchase
     * @return whether the resource bank has enough resources for the purchase
     */
    public boolean canAfford(IResourceBank purchase);

    /**
     * Get the amount of wood in the resource collection.
     * @return the amount of wood in the resource collection
     */
    int getWood();

    /**
     * Set the amount of wood in the resource collection.
     * @param wood
     */
    void setWood(int wood);

    /**
     * Get the amount of brick in the resource collection.
     * @return the amount of brick in the resource collection
     */
    int getBrick();

    /**
     * Set the amount of brick in the resource collection.
     * @param brick
     */
    void setBrick(int brick);

    /**
     * Get the amount of sheep in the resource collection.
     * @return the amount of sheep in the resource collection
     */
    int getSheep();

    /**
     * Set the amount of sheep in the resource collection.
     * @param sheep
     */
    void setSheep(int sheep);

    /**
     * Get the amount of wheat in the resource collection.
     * @return the amount of wheat in the resource collection
     */
    int getWheat();

    /**
     * Set the amount of wheat in the resource collection.
     * @param wheat
     */
    void setWheat(int wheat);

    /**
     * Get the amount of ore in the resource collection.
     * @return the amount of ore in the resource collection
     */
    int getOre();

    /**
     * Set the amount of ore in the resource collection.
     * @param ore
     */
    void setOre(int ore);

    /**
     * Get the amount of the specified type of resource.
     * @param resourceType the type of resource for which to get the amount
     * @return the amount of the specified resource
     */
    int getCount(ResourceType resourceType);

    /**
     * Get the amount of resources a player has.
     * @return the amount of resources
     */
    int getCount();
}
