package shared.model;

/**
 * The superclass of the MapInfo classes. The place pieces on the map, the model controller needs to know
 * the location of the piece and a piece of information about it (color, type, value, etc).
 *
 * This is basically std::pair.
 *
 * Created by Wyatt on 9/21/2014.
 */
public class MapInfo<Location, Type> implements IMapInfo {
    public final Location location;
    public final Type type;

    public MapInfo(Location location, Type type) {
        this.location = location;
        this.type = type;
    }
}
/*  Should I make specializations? Not really necessary, and too much work since Java is stupid
    and forces you to put these in separate files.
public class RoadInfo<EdgeLocation, CatanColor> {}
public class PortInfo<EdgeLocation, PortType> {}
public class TownInfo<VertexLocation, CatanColor> {}
public class NumberInfo<HexLocation, Integer> {}
*/