package shared.communication;

/**
 * The parameter passed into a get game model request.
 * The parameter is the version of the model.
 */
public class GameModelParam extends AbstractGameParams {
    public final Integer version;

    public GameModelParam(Integer version) {
        this.version = version;
    }
}
