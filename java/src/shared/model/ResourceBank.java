package shared.model;

import shared.definitions.ResourceType;

/**
 * Created by link8_000 on 9/27/2014.
 */
public class ResourceBank implements IResourceBank {
    /**
     * Add the specified resources to the resource bank.
     *
     * @param resources the bundle of resources to add to the bank
     */
    @Override
    public void add(IResourceBundle resources) {

    }

    /**
     * Subtract the specified resources from the resource bank.
     * If the number of resources being subtracted exceeds the number of
     * resources in the resource bank, an exception is thrown.
     *
     * @param resources the bundle of resources to remove from the bank
     */
    @Override
    public void subtract(IResourceBundle resources) {

    }

    /**
     * Get the amount of wood in the resource collection.
     *
     * @return the amount of wood in the resource collection
     */
    @Override
    public int getWood() {
        return 0;
    }

    /**
     * Get the amount of brick in the resource collection.
     *
     * @return the amount of brick in the resource collection
     */
    @Override
    public int getBrick() {
        return 0;
    }

    /**
     * Get the amount of sheep in the resource collection.
     *
     * @return the amount of sheep in the resource collection
     */
    @Override
    public int getSheep() {
        return 0;
    }

    /**
     * Get the amount of wheat in the resource collection.
     *
     * @return the amount of wheat in the resource collection
     */
    @Override
    public int getWheat() {
        return 0;
    }

    /**
     * Get the amount of the specified type of resource.
     *
     * @param resourceType the type of resource for which to get the amount
     * @return the amount of the specified resource
     */
    @Override
    public int getCount(ResourceType resourceType) {
        return 0;
    }
}
