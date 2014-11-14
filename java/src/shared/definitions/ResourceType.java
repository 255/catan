package shared.definitions;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum ResourceType
{
	WOOD, BRICK, SHEEP, WHEAT, ORE;

    private static final List<ResourceType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static ResourceType randomResourceType()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

}

