/**
 * 
 */
package openLab.message.packet;

/**
 * @author Markus Lechner
 *
 */
public enum Measurement {
	Vpp("Vpp: "),
	Vmin("Vmin: "),
	Vmax("Vmax: "),
	Freq("f: "),
	Period("T: ");
	
	private final String measurement;
	
	Measurement(String measurement) {
		this.measurement = measurement;		
	}
	
	
	public String getValue() {
		return this.measurement;
	}
}

/* EOF */