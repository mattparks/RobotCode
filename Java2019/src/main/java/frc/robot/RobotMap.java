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
	 * CAN ports that are set in the web panel
	 */
	public static class CAN {
		public static final int DRIVE_LEFT_MASTER = 2;
		public static final int DRIVE_LEFT_NORMAL = 1;
		public static final int DRIVE_LEFT_MINI = 3;
		public static final int DRIVE_RIGHT_MASTER = 9;
		public static final int DRIVE_RIGHT_NORMAL = 4;
		public static final int DRIVE_RIGHT_MINI = 6;
	}
}
