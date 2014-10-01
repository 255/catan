package shared.model;

import shared.definitions.ResourceType;

/**
 * An immutable collection of resources used for prices, trades, etc.
 * @author Wyatt
 */
public interface IResourceBundle extends IResourceCollection {

    /**
     * Takes in a ResourceType and outputs a bundle of one of that resource
     * @param rType the type of resource in the bundle
     * @return the bundle object containing the one resource
     */
    public static IResourceBundle resourceToBundle(ResourceType rType) {
        return resourceToBundle(rType,1);
    }

    /**
     * Takes in a ResourceType and outputs a bundle of one of that resource
     * @param rType the type of resource in the bundle
     * @param count the number of the resource type to put in the bundle
     * @return the bundle object containing the one resource
     */
    public static IResourceBundle resourceToBundle(ResourceType rType, int count) {
        switch (rType) {
            case WOOD:
                return new ResourceBundle(count,0,0,0,0);
            case BRICK:
                return new ResourceBundle(0,count,0,0,0);
            case SHEEP:
                return new ResourceBundle(0,0,count,0,0);
            case WHEAT:
                return new ResourceBundle(0,0,0,count,0);
            case ORE:
                return new ResourceBundle(0,0,0,0,count);
            default:
                return new ResourceBundle(0,0,0,0,0);
        }
    }
}
