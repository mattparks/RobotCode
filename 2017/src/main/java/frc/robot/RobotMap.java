package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	/**
	 * Values specific to driver control.
	 */
	public static class Driver {
		public static final int PRIMARY_PORT = 0;
		public static final int SECONDARY_PORT = 1;
	}
	
	/**
	 * Values that represent robot dimensions and limits.
	 */
	public static class Robot {
		public static final double DRIVE_SPEED = 0.8;
		public static final double LED_BRIGHTNESS = 0.4;
	}

	/**
	 * PIDs used in the actual robot.
	 */
	public static class PIDs {
	}

	/**
	 * Ports used for PWMs, like a rotary knob, connected to the the RoboRIO or PDP.
	 */
	public static class PWM {
	}

	/**
	 * CAN ports that are set in the web panel
	 */
	public static class CAN {
		public static final int CANIFIER = 0;
		public static final int DRIVE_LEFT_MASTER = 2;
		public static final int DRIVE_LEFT_NORMAL = 1;
		public static final int DRIVE_LEFT_MINI = 3;
		public static final int DRIVE_RIGHT_MASTER = 9;
		public static final int DRIVE_RIGHT_NORMAL = 4;
		public static final int DRIVE_RIGHT_MINI = 6;
	}

	/**
	 * Ports used for digital inputs, like switches, connected to the RoboRIO.
	 */
	public static class Digital {
	}

	/**
	 * Ports used for analog inputs.
	 */
	public static class Analog {
	}

	/**
	 * Ports used for solenoids.
	 */
	public static class Solenoid {
	}
}
