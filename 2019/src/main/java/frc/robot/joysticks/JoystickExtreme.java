package frc.robot.joysticks;

/**
 * Represents a Logitech Extreme 3D Pro stick joystick.
 */
public class JoystickExtreme extends IJoystick {
	public class Keys {
		public static final int INDEX_TRIGGER = 1;
		public static final int THUMB_TRIGGER = 2;
		public static final int STICK_3 = 3;
		public static final int STICK_4 = 4;
		public static final int STICK_5 = 5;
		public static final int STICK_6 = 6;
		public static final int BASE_7 = 7;
		public static final int BASE_8 = 8;
		public static final int BASE_9 = 9;
		public static final int BASE_10 = 10;
		public static final int BASE_11 = 11;
		public static final int BASE_12 = 12;
	}

	public class Axis {
		public static final int STICK_X = 0;
		public static final int STICK_Y = 1;
		public static final int STICK_Z = 2;
		public static final int SLIDER = 3;
	}
	
	public JoystickExtreme(int port) {
		super(port);
		add("DriveRotation", new ValueUsage(Axis.STICK_Z));
		add("DriveStrafe", new ValueUsage(Axis.STICK_X));
		add("DriveForward", new ValueUsage(Axis.STICK_Y, true));
		add("DriveLock", new ValueUsage(Keys.INDEX_TRIGGER));
		add("Pivot", new ValueUsage(Keys.THUMB_TRIGGER));
	}
	
	@Override
	public String getJoystickType() {
		return "Extreme";
	}
}
