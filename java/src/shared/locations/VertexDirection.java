package shared.locations;

public enum VertexDirection
{
	West, NorthWest, NorthEast, East, SouthEast, SouthWest;
	
	private VertexDirection opposite;
	
	static
	{
		West.opposite = East;
		NorthWest.opposite = SouthEast;
		NorthEast.opposite = SouthWest;
		East.opposite = West;
		SouthEast.opposite = NorthWest;
		SouthWest.opposite = NorthEast;
	}
	
	public VertexDirection getOppositeDirection()
	{
		return opposite;
	}

    /**
     * Convert a string to an EdgeDirection.
     * @param abbr the abbreviation of the direction
     * @return the correct enum
     */
    public static VertexDirection fromAbbreviation(String abbr) {
        switch (abbr.toUpperCase()) {
            case "NE": return NorthEast;
            case "E" : return East;
            case "SE": return SouthEast;
            case "SW": return SouthWest;
            case "W" : return West;
            case "NW": return NorthWest;
            default:
                throw new IllegalArgumentException("Illegal VertexDirection abbreviation \"" + abbr + "\"");
        }
    }

    public EdgeDirection[] getNeighboringEdgeDirections() {
        switch (this) {
            case NorthEast:
                return new EdgeDirection[]{EdgeDirection.North, EdgeDirection.NorthEast};
            case East:
                return new EdgeDirection[]{EdgeDirection.NorthEast, EdgeDirection.SouthEast};
            case SouthEast:
                return new EdgeDirection[]{EdgeDirection.SouthEast, EdgeDirection.South};
            case SouthWest:
                return new EdgeDirection[]{EdgeDirection.South, EdgeDirection.SouthWest};
            case West:
                return new EdgeDirection[]{EdgeDirection.SouthWest, EdgeDirection.NorthWest};
            case NorthWest:
                return new EdgeDirection[]{EdgeDirection.NorthWest, EdgeDirection.North};
            default:
                assert false;
                return null;
        }
    }
}

