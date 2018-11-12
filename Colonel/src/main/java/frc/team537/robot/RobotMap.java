package frc.team537.robot;

/**
 * Holds a large amount of values that define how the robot will work.
 */
public class RobotMap {
	/**
	 * Values specific to driver control.
	 */
	public static class Driver {
		public static final int PRIMARY_PORT = 0;
	}

	/**
	 * Boolean states that define if a subsystem will be active.
	 */
	public static class Subsystems {
		public static final boolean DRIVE = true;
		public static final boolean CAMERA = false;
		public static final boolean FEEDER = true;
		public static final boolean SHOOTER = true;
		public static final boolean LEDS = false;
	}


	/**
	 * Values that represent robot dimensions and limits.
	 */
	public static class Robot {
		public static final double DRIVE_SPEED_MIN = 0.03;
		public static final double DRIVE_SPEED = 0.8;

		public static final double SHOOT_SPEED = 0.6;

		public static final double FEED_SPEED = 0.9;

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

	public static final int kSlotIdx = 0;
	public static final int kPIDLoopIdx = 0;
	public static final int kTimeoutMs = 10;

	/**
	 * CAN ports that are set in the web panel
	 */
	public static class CAN {
		public static final int DRIVE_LEFT_MASTER = 2;
		public static final int DRIVE_LEFT_NORMAL = 1;
		public static final int DRIVE_LEFT_MINI = 3;
		public static final int DRIVE_RIGHT_MASTER = 9;
		public static final int DRIVE_RIGHT_NORMAL = 4;
		public static final int DRIVE_RIGHT_MINI = 6;

		public static final int SHOOTER_MASTER = 7;
		public static final int SHOOTER_NORMAL = 8;

		public static final int FEEDER = 10;
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
