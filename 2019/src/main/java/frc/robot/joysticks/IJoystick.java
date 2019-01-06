package frc.robot.joysticks;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

import java.util.HashMap;
import java.util.Map;

/**
 * A abstract class that is implemented to represent a driver input device.
 * This object allows buttons and axes to be mapped to a string name.
 * When set up properly two Joysticks can represent the same control schemes, Ex: XBox and PS4.
 */
public abstract class IJoystick extends Joystick {
	/**
	 * A joystick button with {@link IJoystickButton#whenChanged} implemented.
	 * This is a simple extension of {@link JoystickButton}.
	 */
	public static class IJoystickButton extends JoystickButton {
		/**
		 * Create a joystick button for triggering commands.
		 *
		 * @param joystick The {@link Joystick} object this is going to be used with.
		 * @param buttonNumber The button ID that this will follow the change of.
		 */
		public IJoystickButton(GenericHID joystick, int buttonNumber) {
			super(joystick, buttonNumber);
		}

		/**
		 * Starts the given command with both {@link JoystickButton#whenPressed} and {@link JoystickButton#whenReleased}.
		 *
		 * @param command The command to start.
		 */
		public void whenChanged(final Command command) {
			super.whenPressed(command);
			super.whenReleased(command);
		}
	}

	/**
	 * A value that might represent a axis for a button.
	 */
	public static class ValueUsage {
		public final int m_value;
		public boolean m_inverted;
		private IJoystickButton m_joystickButton;

		/**
		 * Creates a representation of a axis or a button. If used as a button {@link ValueUsage#joystickButton} will be created from {@link IJoystick#getJoystickButton}.
		 *
		 * @param value The value that will be used as the axis number or button number.
		 * @param inverted If the state will be inverted, axis negated or button toggle being flipped.
		 */
		public ValueUsage(int value, boolean inverted) {
			m_value = value;
			m_inverted = inverted;
			m_joystickButton = null;
		}
		
		/**
		 * Creates a representation of a axis or a button. If used as a button {@link ValueUsage#joystickButton} will be created from {@link IJoystick#getJoystickButton}.
		 *
		 * @param value The value that will be used as the axis number or button number.
		 */
		public ValueUsage(int value) {
			m_value = value;
			m_inverted = false;
			m_joystickButton = null;
		}	
	}
	
	private Map<String, ValueUsage> m_mappedUsages;

	/**
	 * Creates a instances of the joystick on a driver station port.
	 *
	 * @param port The driver station port this is plugged into, starts at zero.
	 */
	public IJoystick(int port) {
		super(port);
		m_mappedUsages = new HashMap<>();
	}

	/**
	 * Gets the model number of the implemented joystick.
	 *
	 * @return The model number.
	 */
	public abstract String getJoystickType();

	/**
	 * Adds a mapping between a short name and a value, this might be a axis or a button.
	 *
	 * @param shortName The short name to use as the key.
	 * @param valueUsage The value given to the key.
	 */
	protected void add(String shortName, ValueUsage valueUsage) {
		m_mappedUsages.remove(shortName); // Key should not be mutated while used in the map.
		m_mappedUsages.put(shortName, valueUsage);
	}

	/**
	 * Gets the value usage mapped to the short name from {@link IJoystick#add}.
	 *
	 * @param shortName The short name to use as the key.
	 * @return The value found at the key, if not previously present a new object with {@link ValueUsage#value} of -1 is returned.
	 */
	public ValueUsage get(String shortName) {
		if (!m_mappedUsages.containsKey(shortName)) {
			ValueUsage valueUsage = new ValueUsage(-1, false);
			DriverStation.reportError("IJoystick implementation missing usage for: " + m_mappedUsages, false);
			add(shortName, valueUsage);
			return valueUsage;
		}
		
		return m_mappedUsages.get(shortName);
	}

	/**
	 * A overload of {@link GenericHID#getRawAxis} that will get my short name instead of axis number.
	 *
	 * @param shortName The short name that is used to represent the axis.
	 * @return The value of the axis.
	 */
	public double getRawAxis(String shortName) {
		ValueUsage valueUsage = get(shortName);
		return (valueUsage.m_inverted ? -1.0 : 1.0) * getRawAxis(valueUsage.m_value);
	}

	/**
	 * A overload of {@link GenericHID#getRawButton} that will get my short name instead of button number.
	 *
	 * @param shortName The short name that is used to represent the button.
	 * @return The state of the button.
	 */
	public boolean getRawButton(String shortName) {
		ValueUsage valueUsage = get(shortName);
		return valueUsage.m_inverted != getRawButton(valueUsage.m_value);
	}

	/**
	 * Gets the joystick button stored in a value usage.
	 *
	 * @param shortName The short name to get the value usage from.
	 * @return The joystick button, if not previously created a new object will be created and returned.
	 */
	public IJoystickButton getJoystickButton(String shortName) {
		ValueUsage valueUsage = get(shortName);
		
		if (valueUsage.m_joystickButton == null) {
			valueUsage.m_joystickButton = new IJoystickButton(this, valueUsage.m_value);
		}
		
		return valueUsage.m_joystickButton;
	}
}
