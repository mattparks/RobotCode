package frc.robot.joysticks;

/**
 * Represents a Logitech F310 hand-held controller.
 */
public class JoystickF310 extends IJoystick {
	public class Keys {
		public static final int A = 1;
		public static final int B = 2;
		public static final int X = 3;
		public static final int Y = 4;
		public static final int BUMPER_LEFT = 5;
		public static final int BUMPER_RIGHT = 6;
		public static final int BACK = 7;
		public static final int START = 8;
		public static final int STICK_LEFT = 9;
		public static final int STICK_RIGHT = 10;
	}

	public class Axis {
		public static final int LEFT_X = 0;
		public static final int LEFT_Y = 1;
		public static final int LEFT_TRIGGER = 2;
		public static final int RIGHT_TRIGGER = 3;
		public static final int RIGHT_X = 4;
		public static final int RIGHT_Y = 5;
	}
	
	public JoystickF310(int port) {
		super(port);
		add("DriveRotation", new ValueUsage(Axis.RIGHT_X, false));
		add("DriveStrafe", new ValueUsage(Axis.LEFT_X, false));
		add("DriveForward", new ValueUsage(Axis.LEFT_Y, true));
		add("DriveLock", new ValueUsage(Keys.BUMPER_LEFT));
		add("Pivot", new ValueUsage(Keys.BUMPER_RIGHT, false));
	}

	@Override
	public String getJoystickType() {
		return "F310";
	}
}
