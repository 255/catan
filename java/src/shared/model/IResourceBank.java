package shared.model;

/**
 * A bank of resources. Resources can be added and subtracted as ResourceBundles.
 * This can represent, e.g., the resources a player is holding or the game's resource bank.
 * @author Wyatt
 */
public interface IResourceBank extends IResourceCollection {
    /**
     * Add the specified resources to the resource bank.
     * @param resources the bundle of resources to add to the bank
     */
    public void add(IResourceBundle resources);

    /**
     * Subtract the specified resources from the resource bank.
     * If the number of resources being subtracted exceeds the number of
     * resources in the resource bank, an exception is thrown.
     * @param resources the bundle of resources to remove from the bank
     */
    public IResourceBundle subtract(IResourceBundle resources);

    /**
     * Gets all the resources in the resource bank
     * @return the resources in the resource bank
     */
    public IResourceBundle getResources();

    /**
     * Checks if the resource bank has enough resources for the purchase
     * @param purchase
     * @return whether the resource bank has enough resources for the purchase
     */
    public boolean canAfford(IResourceBundle purchase);
}
