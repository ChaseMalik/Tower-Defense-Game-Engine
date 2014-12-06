/**
 * 
 */
/**
 * @author Greg Lyons
 * 
 * The listeners contained in this package serve to mask the powerful GUI Manager as a listener intended for more specific purposes.
 * Each GUI item does not need access to the behavior for the other items, so it views the Manager as its associated interface,
 * 		rather than as a complete GUI Manager.
 * The GUI Manager must implement all of these interfaces to handle the behavior of all of the GUI items.
 *
 */
package gamePlayer.Listeners;