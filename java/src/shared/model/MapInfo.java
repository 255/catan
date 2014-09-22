package shared.model;

/**
 * A class used to pass information about the map into the MapController.
 * The place pieces on the map, the model controller needs to know
 * the location of the piece and type information about it (color, port type, number value, etc).
 *
 * @author Wyatt
 */
public class MapInfo<Location, Type> implements IMapInfo {
    public final Location location;
    public final Type type;

    public MapInfo(Location location, Type type) {
        this.location = location;
        this.type = type;
    }
}

/* These would be useful, but would each need to be in a separate file...
public class RoadInfo<EdgeLocation, CatanColor> {}
public class PortInfo<EdgeLocation, PortType> {}
public class TownInfo<VertexLocation, CatanColor> {}
public class NumberInfo<HexLocation, Integer> {}
*/
