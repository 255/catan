package shared.model;

import java.util.Map;

/**
 * Created by sdaltonb on 9/20/14.
 *
 * Represents a log
 */
public interface ILog
{
    /**
     * Returns the log
     *
     * @return a map from messages to player
     */
    public Map<String, IPlayer> getLog();
}
