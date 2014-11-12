package shared.definitions;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum DevCardType
{
	SOLDIER, YEAR_OF_PLENTY, MONOPOLY, ROAD_BUILD, MONUMENT;


    private static final List<DevCardType> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static DevCardType randomDevCardType()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

}

