package shared.locations;

import com.sun.javafx.geom.Edge;

/**
 * Represents the location of an edge on a hex map
 */
public class EdgeLocation
{
	
	private HexLocation hexLoc;
	private EdgeDirection dir;

	public EdgeLocation(int x, int y, EdgeDirection dir)
	{
		this.hexLoc = new HexLocation(x, y);
		this.dir = dir;
	}

	public EdgeLocation(HexLocation hexLoc, EdgeDirection dir)
	{
		setHexLoc(hexLoc);
		setDir(dir);
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
	
	public EdgeDirection getDir()
	{
		return dir;
	}
	
	private void setDir(EdgeDirection dir)
	{
		this.dir = dir;
	}
	
	@Override
	public String toString()
	{
		return "EdgeLocation [hexLoc=" + hexLoc + ", dir=" + dir + "]";
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
		EdgeLocation other = (EdgeLocation)obj;
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
	 * Returns a canonical (i.e., unique) value for this edge location. Since
	 * each edge has two different locations on a map, this method converts a
	 * hex location to a single canonical form. This is useful for using hex
	 * locations as map keys.
	 * 
	 * @return Normalized hex location
	 */
	public EdgeLocation getNormalizedLocation()
	{
		
		// Return an EdgeLocation that has direction NW, N, or NE
		
		switch (dir)
		{
			case NorthWest:
			case North:
			case NorthEast:
				return this;
			case SouthWest:
			case South:
			case SouthEast:
				return new EdgeLocation(hexLoc.getNeighborLoc(dir),
										dir.getOppositeDirection());
			default:
				assert false;
				return null;
		}
	}

    /**
     * Get the "other" equivalent edge that is on the same hex.
     * @return the equivalent edge
     */
    public EdgeLocation getEquivalentEdge() {
        return new EdgeLocation(hexLoc.getNeighborLoc(dir), dir.getOppositeDirection());
    }

    /**
     * Get the normalized edge that is clockwise around the hex from this edge.
     * @return the normalized clockwise edge
     */
    public EdgeLocation getNormalizedClockwise() {
        return new EdgeLocation(hexLoc, dir.getClockwise()).getNormalizedLocation();
    }

    /**
     * Get the normalized edge that is counterclockwise around the hex from this edge.
     * @return the normalized counterclockwise edge
     */
    public EdgeLocation getNormalizedCounterClockwise() {
        return new EdgeLocation(hexLoc, dir.getCounterclockwise()).getNormalizedLocation();
    }

    /**
     * Get the edges adjacent or connecting to the specified edge.
     * For boundary edges, some of the edges returned may be off the map.
     * @return the four adjacent edges as an array
     */
     public EdgeLocation[] getAdjacentEdges() {
        EdgeLocation other = getEquivalentEdge();

        return new EdgeLocation[] {
                getNormalizedClockwise(),
                getNormalizedCounterClockwise(),
                other.getNormalizedClockwise(),
                other.getNormalizedCounterClockwise(),
        };
    }

    /**
     * Get the vertices connected to the specified edge.
     * @return the two adjacent vertices as an array
     */
    public VertexLocation[] getAdjacentVertices() {
        VertexDirection[] vertexDirs = getDir().getNeighboringVertexDirections();
        return new VertexLocation[] {
                new VertexLocation(getHexLoc(), vertexDirs[0]).getNormalizedLocation(),
                new VertexLocation(getHexLoc(), vertexDirs[1]).getNormalizedLocation(),
        };
    }

    /**
     * Return the vertex between the two specified edges, or null if there is none
     * @param edge1 one edge
     * @param edge2 the other edge
     * @return the vertex between the edges or null if there is none
     */
    public static VertexLocation getVertexBetweenEdges(EdgeLocation edge1, EdgeLocation edge2) {
        // check if the two edges share a vertex
        for (VertexLocation vertex1 : edge1.getAdjacentVertices()) {
            for (VertexLocation vertex2 : edge2.getAdjacentVertices()) {
                if (vertex1.equals(vertex2)) {
                    return vertex1;
                }
            }
        }

        return null;
    }
}

