package environment.interfaces;
/**
 * The Environment's graphics interface.
 * 
 * @author Vasco
 *
 */
public interface GraphicalDisplay {
	/**
	 * Displays the graphics for the currently rendered graphic.
	 */
	public void show();

	/**
	 * Hides the graphics for the currently rendered graphic.
	 */
	public void hide();

	/**
	 * Renders the given matrix into a graphical display.
	 */
	public void render(); // TODO: Investigation required on how to do this.
}
