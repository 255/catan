package shared.model;

/**
 * The information that the GUI will need to draw the pieces on the map.
 * The model facade gives MapInfo objects to the map controller so it knows where to place pieces.
 *
 * @param <Location> the type of location object to use (edge, vertex, or hex)
 * @param <Type> type/appearance of the object (color, number, etc.)
 *
 * @author Wyatt
 */
public interface IMapInfo<Location, Type> {
}
