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
	 * Closes the modal overlay view.
	 */
	void closeTopModal();

    void closeThisModal();

    /**
	 * Indicates whether or not the overlay is currently showing.
	 * 
	 * @return True if the overlay is showing, false otherwise
	 */
	boolean isModalShowing();
}

