package client.base;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Base class for overlay views
 */
@SuppressWarnings("serial")
public class OverlayView extends PanelView implements IOverlayView {
    /**
     * The frame window for the overlays
     */
    private static JFrame window;

    /**
     * Default glass pane component (displayed when no overlays are visible)
     */
    private static Component defaultGlassPane;

    /**
     * Stack of overlays that are currently displayed
     */
    private static Deque<OverlayInfo> overlayStack;

    public static void setWindow(JFrame window) {
        OverlayView.window = window;
        defaultGlassPane = window.getGlassPane();
        overlayStack = new ArrayDeque<OverlayInfo>();
    }

    public OverlayView() {
        super();
    }

    /**
     * Displays the overlay. The overlay is displayed on top of any other
     * overlays that are already visible.
     */
    public void showModal() {
        // Open the new overlay
        JPanel overlayPanel = new JPanel();
        overlayPanel.setLayout(new BorderLayout());
        overlayPanel.setOpaque(false);

        // Discard all mouse and keyboard events
        MouseAdapter mouseAdapter = new MouseAdapter() {
        };
        overlayPanel.addMouseListener(mouseAdapter);
        overlayPanel.addMouseMotionListener(mouseAdapter);
        overlayPanel.addMouseWheelListener(mouseAdapter);

        KeyAdapter keyAdapter = new KeyAdapter() {
        };
        overlayPanel.addKeyListener(keyAdapter);

        Dimension winSize = window.getContentPane().getSize();
        Dimension prefSize = this.getPreferredSize();

        int widthDiff = (int) (winSize.getWidth() - prefSize.getWidth());
        int heightDiff = (int) (winSize.getHeight() - prefSize.getHeight());

        overlayPanel.add(this, BorderLayout.CENTER);
        if (widthDiff / 2 > 0) {
            overlayPanel.add(Box.createRigidArea(new Dimension(widthDiff / 2, 0)),
                    BorderLayout.WEST);
            overlayPanel.add(Box.createRigidArea(new Dimension(widthDiff / 2, 0)),
                    BorderLayout.EAST);
        }
        if (heightDiff / 2 > 0) {
            overlayPanel.add(Box.createRigidArea(new Dimension(0,
                            heightDiff / 2)),
                    BorderLayout.NORTH);
            overlayPanel.add(Box.createRigidArea(new Dimension(0,
                            heightDiff / 2)),
                    BorderLayout.SOUTH);
        }

        if (overlayStack.size() > 0) {

            // Hide the currently-visible overlay
            overlayStack.peek().getOverlayPanel().setVisible(false);
        }

        window.setGlassPane(overlayPanel);
        overlayPanel.setVisible(true);
        overlayStack.push(new OverlayInfo(this, overlayPanel));
    }

    /**
     * Hides the top-most overlay
     */
    public void closeTopModal() {
        //assert overlayStack.size() > 0;
        if (overlayStack.size() == 0) return;
        assert window.getGlassPane() == overlayStack.peek().getOverlayPanel();

        if (overlayStack.size() > 0) {

            overlayStack.pop().getOverlayPanel().setVisible(false);

            if (overlayStack.size() > 0) {

                window.setGlassPane(overlayStack.peek().getOverlayPanel());
                overlayStack.peek().getOverlayPanel().setVisible(true);
            } else {
                window.setGlassPane(defaultGlassPane);
                window.getGlassPane().setVisible(false);
            }
        }
    }

    public static void closeAllModals() {
        for (OverlayInfo info : overlayStack) {
            info.getOverlayPanel().setVisible(false);
        }

        window.setGlassPane(defaultGlassPane);
        window.getGlassPane().setVisible(false);

        overlayStack = new ArrayDeque<>();
    }

	/**
	 * Removes all of this modal's instances from the overlay stack.
	 */
    @Override
	public void closeThisModal() {
		if (overlayStack.size() == 0) return;
		//assert window.getGlassPane() == overlayStack.peek().getOverlayPanel();

		if (this == overlayStack.peek().getOverlayView()) {
			overlayStack.pop().getOverlayPanel().setVisible(false);

			if (overlayStack.size() > 0) {
				window.setGlassPane(overlayStack.peek().getOverlayPanel());
				overlayStack.peek().getOverlayPanel().setVisible(true);
			}
			else {
				window.setGlassPane(defaultGlassPane);
				window.getGlassPane().setVisible(false);
			}
		}

        // If there are any other pointers to this dialog in the stack, remove them
        Iterator<OverlayInfo> infoIterator = overlayStack.iterator();
        if (infoIterator.hasNext()) {
            infoIterator.next(); // skipped the first -- already checked it

            while (infoIterator.hasNext()) {
                OverlayInfo info = infoIterator.next();
                if (this == info.getOverlayView()) {
                    infoIterator.remove();
                    info.getOverlayPanel().setVisible(false);
                    break;
                    // not affecting what's on top of the stack -- don't shouldn't change anything else's visibility
                }
            }
        }
    }

    /**
	 * Is the overlay currently showing?
	 * 
	 * @return True if overlay is showing, false otherwise
	 */
	@Override
	public boolean isModalShowing()
	{
		
		for (OverlayInfo info : overlayStack)
		{
			
			if(info.getOverlayView() == this)
				return true;
		}
		
		return false;
	}
	
	private static class OverlayInfo
	{
		private OverlayView overlayView;
		private JPanel overlayPanel;
		
		public OverlayInfo(OverlayView overlayView, JPanel overlayPanel)
		{
			
			setOverlayView(overlayView);
			setOverlayPanel(overlayPanel);
		}
		
		public OverlayView getOverlayView()
		{
			
			return overlayView;
		}
		
		public void setOverlayView(OverlayView overlayView)
		{
			
			this.overlayView = overlayView;
		}
		
		public JPanel getOverlayPanel()
		{
			
			return overlayPanel;
		}
		
		public void setOverlayPanel(JPanel overlayPanel)
		{
			
			this.overlayPanel = overlayPanel;
		}
	}
	
}

