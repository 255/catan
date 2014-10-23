package client.base;

/**
 * Base interface for overlay views
 */
public interface IOverlayView extends IView
{
	
	/**
	 * Displays the modal overlay view.
	 */
	void showModal();
	
	/**
	 * Closes the TOP modal overlay view.
	 */
	void closeTopModal();

    /**
     * Closes THIS modal.
     */
    void closeThisModal();

    /**
	 * Indicates whether or not the overlay is currently showing.
	 * 
	 * @return True if the overlay is showing, false otherwise
	 */
	boolean isModalShowing();
}

