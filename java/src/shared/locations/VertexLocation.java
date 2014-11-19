package shared.locations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents the location of a vertex on a hex map
 */
public class VertexLocation implements Serializable
{
	
	private HexLocation hexLoc;
	private VertexDirection dir;
	
	public VertexLocation(HexLocation hexLoc, VertexDirection dir)
	{
		setHexLoc(hexLoc);
		setDir(dir);
	}

	public VertexLocation(int x, int y, VertexDirection dir)
	{
		this.hexLoc = new HexLocation(x, y);
		this.dir = dir;
	}
	public HexLocation getHexLoc()
	{
		return hexLoc;
	}
	
	private void setHexLoc(HexLocation hexLoc)
	{
		if(hexLoc == null)
		{
			throw new IllegalArgumentException("hexLoc cannot be null");
		}
		this.hexLoc = hexLoc;
	}
	
	public VertexDirection getDir()
	{
		return dir;
	}
	
	private void setDir(VertexDirection direction)
	{
		this.dir = direction;
	}
	
	@Override
	public String toString()
	{
		return "VertexLocation [hexLoc=" + hexLoc + ", dir=" + dir + "]";
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dir == null) ? 0 : dir.hashCode());
		result = prime * result + ((hexLoc == null) ? 0 : hexLoc.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		VertexLocation other = (VertexLocation)obj;
		if(dir != other.dir)
			return false;
		if(hexLoc == null)
		{
			if(other.hexLoc != null)
				return false;
		}
		else if(!hexLoc.equals(other.hexLoc))
			return false;
		return true;
	}
	
	/**
	 * Returns a canonical (i.e., unique) value for this vertex location. Since
	 * each vertex has three different locations on a map, this method converts
	 * a vertex location to a single canonical form. This is useful for using
	 * vertex locations as map keys.
	 * 
	 * @return Normalized vertex location
	 */
	public VertexLocation getNormalizedLocation() {
		
		// Return location that has direction NW or NE
		
		switch (dir) {
			case NorthWest:
			case NorthEast:
				return this;
			case West:
				return new VertexLocation(
										  hexLoc.getNeighborLoc(EdgeDirection.SouthWest),
										  VertexDirection.NorthEast);
			case SouthWest:
				return new VertexLocation(
										  hexLoc.getNeighborLoc(EdgeDirection.South),
										  VertexDirection.NorthWest);
			case SouthEast:
				return new VertexLocation(
										  hexLoc.getNeighborLoc(EdgeDirection.South),
										  VertexDirection.NorthEast);
			case East:
				return new VertexLocation(
										  hexLoc.getNeighborLoc(EdgeDirection.SouthEast),
										  VertexDirection.NorthWest);
			default:
				assert false;
				return null;
		}
	}

    /**
     * Get the three normalized vertices adjacent to this vertex.
     * The vertices may not be on the map for boundary vertices.
     * @return an array containing the three vertices adjacent to this vertex
     */
    public VertexLocation[] getAdjacentVertices() {
        VertexLocation normalized = getNormalizedLocation();
        Collection<VertexLocation> vertices = new ArrayList<>();

        // to make life easier, look at the vertices that are adjacent to
        // this vertex and add them (excluding this vertex)
        for (EdgeLocation edge : getAdjacentEdges()) {
            for (VertexLocation vertex : edge.getAdjacentVertices()) {
                if (!vertex.equals(normalized)) {
                    vertices.add(vertex);
                }
            }
        }

        assert vertices.size() == 3 : "Failed to find 3 adjacent vertices!";

        return vertices.toArray(new VertexLocation[3]);
    }

    /**
     * Get the three edges that connect to this vertex.
     * @return the edges that connect to this vertex as an array
     */
    public EdgeLocation[] getAdjacentEdges() {
        switch (getDir()) {
            case NorthWest:
                return createEdgeLocations(hexLoc, EdgeDirection.NorthWest, EdgeDirection.North,
                        hexLoc.getNeighborLoc(EdgeDirection.NorthWest), EdgeDirection.NorthEast);
            case NorthEast:
                return createEdgeLocations(hexLoc, EdgeDirection.North, EdgeDirection.NorthEast,
                        hexLoc.getNeighborLoc(EdgeDirection.North), EdgeDirection.SouthEast);
            case East:
                return createEdgeLocations(hexLoc, EdgeDirection.NorthEast, EdgeDirection.SouthEast,
                        hexLoc.getNeighborLoc(EdgeDirection.NorthEast), EdgeDirection.South);
            case SouthEast:
                return createEdgeLocations(hexLoc, EdgeDirection.SouthEast, EdgeDirection.South,
                        hexLoc.getNeighborLoc(EdgeDirection.SouthEast), EdgeDirection.SouthWest);
            case SouthWest:
                return createEdgeLocations(hexLoc, EdgeDirection.South, EdgeDirection.SouthWest,
                        hexLoc.getNeighborLoc(EdgeDirection.South), EdgeDirection.NorthWest);
            case West:
                return createEdgeLocations(hexLoc, EdgeDirection.SouthWest, EdgeDirection.NorthWest,
                        hexLoc.getNeighborLoc(EdgeDirection.SouthWest), EdgeDirection.North);
            default:
                assert false;
                return null;
        }
    }

    /** A helper function for creating an array of two edges */
    private static EdgeLocation[] createEdgeLocations(HexLocation hexLoc, EdgeDirection dir1, EdgeDirection dir2,
                                                      HexLocation otherLoc, EdgeDirection otherDir) {
        return new EdgeLocation[]{
                   new EdgeLocation(hexLoc, dir1).getNormalizedLocation(),
                   new EdgeLocation(hexLoc, dir2).getNormalizedLocation(),
                   new EdgeLocation(otherLoc, otherDir).getNormalizedLocation(),
        };
    }
}

