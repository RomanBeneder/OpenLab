package controls;


// HOW THIS CONTROL WORKS
// The user supplies a static list of possible values (eg. 0,5,100,200)
// The minValue of the underlying knob is set to 0
// The maxValue of he underlying knob is set to list.size()-1
// The user can turn the knob, it will internally always be between 0 and list.size()
// If the user wants to know the value, NOT the internal value is returned, instead we
// use the internal value as an index to the list of values and return that..
// Finally some function of the baseclass have to be overridden and disabled...

/**
 * This knob only allows user defined values
 * The animation is stepless though
 * @author Jakob
 */
public class SteppedKnob extends BasicKnob {

	private double[] steps;
	
	public SteppedKnob(double knobSize, double[] values) {
		this(null, null, knobSize, values);
	}
	
	public SteppedKnob(String styleClass, String knobTheme, double knobSize, double[] values){
		super(styleClass, knobTheme, knobSize);
		steps = values;
		super.setMinAngle(-70);
		super.setMaxAngle(250);
		super.setMin(0);
		super.setMax(steps.length-1);
		super.setValue(0);
	}

	/**
	 * Returns the current step (value)
	 */
	public double getValue() { 
		int index = (int)super.getValue();
		
		if(steps != null && steps.length > index) {
			return steps[index];
		}
		throw new RuntimeException("Invalid steps or index");
	}
	
	/**
	 * Sets the new value if it was found in the initially supplied list
	 * If not found, it throws a RuntimeException...
	 */
	public void setValue(double v){
		
		int newIndexValue = findValueInList(v);
		if(newIndexValue < 0) {
			throw new RuntimeException("This value cannot be set because it is not in the initial list of allowed values!");
		}		
		super.setValue(newIndexValue);
	}
	
	/**
	 * Tries to find the index of the value in the list
	 * @return the index in the list, if not found < 0
	 */
	private final int findValueInList(double stepValue) {
		
		for(int i = 0; i < steps.length; ++i){
			if(Math.abs(steps[i] - stepValue) < 1.0e-9)
				return i;
		}
		
		return -1;
	}
	
	/**
	 * NOT SUPPORTED
	 */
	public void setMin(double v){
		throw new RuntimeException("setMin() not supported");
	}
	
	/**
	 * NOT SUPPORTED
	 */
	public void setMax(double v){
		throw new RuntimeException("setMin() not supported");
	}
}
