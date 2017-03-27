package controls;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;

/**
 * @author Markus Lechner
 * @version 0.1.0
 * 
 * This is a simple knob control for JavaFX
 * 
 * 0.0.1 -> 0.1
 * The control now animates correctly when the
 * user sets the value in code.
 * 
 * Removed a lot of bad code
 * Made some getter/setter non-final in order
 * to allow them to be overridden.
 */
public class BasicKnob extends Region
{
	private Region knob = new Region();
	private double minAngle = -20;
	private double maxAngle = 200;
	private double knobSize = 350;
	private boolean inverted = false;
	
	private Rotate rotate = new Rotate();

	private Line minLine = new Line();
	private Line maxLine = new Line();
	private Circle themeAccess = new Circle();

	private boolean initThemePos = true;
	private boolean withinAngle = false;
	
	// default values
	private Color visible = new Color(1.0f, 0.0f, 0.0f,0.3f);
	private Color invisible = new Color(0.5f,0.5f,0.5f,0f);

	private final DoubleProperty value = new SimpleDoubleProperty(this, "value", 50);
	private final DoubleProperty max = new SimpleDoubleProperty(this, "max", 100);
	private final DoubleProperty min = new SimpleDoubleProperty(this, "min", 0);

	// empty ctor so this control can be used with the scenebuilder
	public BasicKnob()
	{
		this(null,null, 60);
	}
	
	public BasicKnob(double knobSize)
	{
		this(null,null, knobSize);
	}

	public BasicKnob(double minAngle, double maxAngle, double knobSize)
	{
		this(null,null, knobSize);
		this.minAngle = minAngle;
		this.maxAngle = maxAngle;
	}
	
	public BasicKnob(String styleClass, String knobTheme, double knobSize)
	{
		super();
		
		// default style for the scenebuilder
		if(styleClass == null || knobTheme == null){
			String url = this.getClass().getResource("knob.png").toString();
			String style = "-fx-background-image: url("+url+"); -fx-background-size: stretch;";
			knob.setStyle(style);
		// custom style
		}else{
			getStyleClass().add(styleClass);
			knob.getStyleClass().add(knobTheme);
		}

		this.knobSize = knobSize;
		knob.setPrefSize(this.knobSize,this.knobSize);
		knob.getTransforms().add(rotate);

		themeAccess.setFill(invisible);

		minLine.setStroke(Color.BLACK);
		minLine.setStrokeWidth(4);
		maxLine.setStroke(Color.BLACK);
		maxLine.setStrokeWidth(4);
		minLine.setStroke(invisible);
		maxLine.setStroke(invisible);

		getChildren().addAll(minLine, maxLine, knob, themeAccess);
		setPrefSize(this.knobSize,this.knobSize);

		themeAccess.setOnMouseDragged(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event) {
				double x = event.getX();
				double y = event.getY();
				double centerX = getWidth() / 2.0;
				double centerY = getHeight() / 2.0;

				double theta = Math.atan2((y - centerY), (x - centerX));
				double angle = Math.toDegrees(theta);

				double xcp = knob.getHeight() / 2 * Math.cos(theta);
				double ycp = knob.getHeight() / 2 * Math.sin(theta);

				if (angle > 0.0)
					angle = 180 + (180 - angle);

				else
					angle = 180 - (180 - Math.abs(angle));

				if (angle >= 270)
					angle =  angle - 360;

				if(angle >= getMinAngle() && angle <= getMaxAngle())
				{
					themeAccess.setCenterX(xcp + knob.getWidth());
					themeAccess.setCenterY(ycp + knob.getWidth());
					withinAngle = true;
				}

				else
					withinAngle = false;

				double _value = angleToValue(angle);
				value.set(_value);
			}
		});


	}

	/* (non-Javadoc)
	 * @see javafx.scene.Parent#layoutChildren()
	 */
	@Override
	protected void layoutChildren() {
		super.layoutChildren();

		double centerX = getWidth() / 2.0;
		double centerY = getHeight() / 2.0;

		minLine.setStartX(centerX);
		minLine.setStartY(centerY);
		minLine.setEndX(centerX + knob.getWidth() * Math.cos(Math.toRadians(-minAngle)));
		minLine.setEndY(centerY + knob.getWidth() * Math.sin(Math.toRadians(-minAngle)));

		maxLine.setStartX(centerX);
		maxLine.setStartY(centerY);
		maxLine.setEndX(centerX + knob.getWidth() * Math.cos(Math.toRadians(-maxAngle)));
		maxLine.setEndY(centerY + knob.getWidth() * Math.sin(Math.toRadians(-maxAngle)));

		double knobX = (getWidth() - knob.getPrefWidth()) / 2.0;
		double knobY = (getHeight() - knob.getPrefHeight()) / 2.0;

		knob.setLayoutX(knobX);
		knob.setLayoutY(knobY);

		themeAccess.setCenterX(centerX);
		themeAccess.setCenterY(centerY);
		themeAccess.setRadius(knob.getWidth() / 2);

		double angle = valueToAngle(value.get());

		if (minAngle <= angle && angle <= maxAngle && withinAngle) {
			rotate.setPivotX(knob.getWidth() / 2.0);
			rotate.setPivotY(knob.getHeight() / 2.0);
			
			if(initThemePos) {
				rotate.setAngle(0);
				initThemePos = false;
			} else {
				rotate.setAngle(-angle + 90);
			}
		}
	}

	/**
	 * @param A value between min & max
	 * @return the angle the control should be drawn
	 * if this value is set
	 */
	protected double valueToAngle(double value) {
		double maxValue;
		double minValue;
		
		if(inverted){
			maxValue = getMin();
			minValue = getMax();
		}
		else{
			maxValue = getMax();
			minValue = getMin();
		}
		
		double angle = minAngle + ((maxAngle - minAngle) * (value - maxValue) / (minValue - maxValue));
		return angle;
	}

	/**
	 * @param An angle between minAngle & maxAngle
	 * @return the value this angle points to
	 */
	protected double angleToValue(double angle) {
		double maxValue;
		double minValue;
		
		if(inverted){
			maxValue = getMin();
			minValue = getMax();
		}
		else{
			maxValue = getMax();
			minValue = getMin();
		}
		
		double value = minValue + ((maxValue - minValue) * (angle - maxAngle) / (minAngle - maxAngle));
		
		if(inverted){
			value = Math.min(minValue, value);
			value = Math.max(maxValue, value);
		}
		else{
			value = Math.max(minValue, value);
			value = Math.min(maxValue, value);
		}
		return value;
	}

	/**
	 * Sets the value of the control and
	 * animates it accordingly
	 */
	public void setValue(double v) {
		value.set(v);
		withinAngle = true;	// allow rotation
		layoutChildren();	// redraw
	}

	
	/**
	 * @return the selected value
	 */
	public double getValue() {
		return (Math.round(Math.round(value.get()*10.0))/10.0);
	}


	public final DoubleProperty valueProperty() {
		return value;
	}


	/**
	 * Sets the smallest allowed value
	 */
	public void setMin(double v) {
		min.set(v);
	}

	/**
	 * @return the smallest allowed value
	 */
	public double getMin() {
		return min.get();
	}


	public final DoubleProperty minProperty() {
		return min;
	}


	/**
	 * Sets the highest allowed value
	 */
	public void setMax(double v) {
		max.set(v);
	}

	/**
	 * @return the highest allowed value
	 */
	public double getMax() {
		return max.get();
	}

	public final DoubleProperty maxProperty() {
		return max;
	}

	/**
	 * @return left stop of the knob
	 */
	public double getMinAngle() {
		return minAngle;
	}

	/**
	 * Sets the left stop of the knob
	 */
	public void setMinAngle(double minAngle) {
		this.minAngle = minAngle;
	}

	/**
	 * @return the right stop of the knob
	 */
	public double getMaxAngle() {
		return maxAngle;
	}

	/**
	 * Sets the right stop of the knob
	 */
	public void setMaxAngle(double maxAngle) {
		this.maxAngle = maxAngle;
	}


	public void showMaxMinLines() {
//		minLine.setStroke(Color.GREENYELLOW);
//		maxLine.setStroke(Color.BLACK);
		
		
		minLine.setDisable(true);
		maxLine.setDisable(true);
	}


	public void hideMaxMinLines() {
		minLine.setStroke(invisible);
		maxLine.setStroke(invisible);
		

	}

	public void showThemeAccess() {
		themeAccess.setFill(visible);
	}


	public void hideThemeAccess() {
		themeAccess.setFill(invisible);
	}
	
	/**
	 * Set the dial direction
	 * false: default, lowest value is left
	 * true: lowest value is right
	 */
	public void setInverted(boolean v) {
		inverted = v;
	}
	
	/**
	 * @return true if the dial direction is inverted
	 */
	public boolean getInverted(){
		return inverted;
	}
}