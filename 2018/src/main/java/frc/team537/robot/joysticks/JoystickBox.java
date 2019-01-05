package frc.team537.robot.joysticks;

/**
 * Represents a custom box controller used in 2018.
 */
public class JoystickBox extends IJoystick {
	public class Keys {
		public static final int RAMP_DOWN_RIGHT = 1;
		public static final int RAMP_DOWN_LEFT = 2;
		public static final int RAMP_UP_LEFT = 3;
		public static final int RAMP_UP_RIGHT = 4;
		public static final int CUBE_UP = 5;
		public static final int CUBE_DOWN = 6;
		public static final int CUBE_IN = 8;
		public static final int CUBE_OUT = 7;
		public static final int DEPLOY_LEFT_RAMP = 9;
		public static final int DEPLOY_RIGHT_RAMP = 10;
		public static final int CUBE_INTAKE_SLOW = 11;
	}

	public JoystickBox(int port) {
		super(port);
		add("RampDeployRight", new ValueUsage(Keys.DEPLOY_RIGHT_RAMP, false));
		add("RampDownRight", new ValueUsage(Keys.RAMP_DOWN_RIGHT, false));
		add("RampUpRight", new ValueUsage(Keys.RAMP_UP_RIGHT, false));
		
		add("RampDeployLeft", new ValueUsage(Keys.DEPLOY_LEFT_RAMP, false));
		add("RampDownLeft", new ValueUsage(Keys.RAMP_DOWN_LEFT, false));
		add("RampUpLeft", new ValueUsage(Keys.RAMP_UP_LEFT, false));

		add("CubeUp", new ValueUsage(Keys.CUBE_UP, false));
		add("CubeDown", new ValueUsage(Keys.CUBE_DOWN, false));
		
		add("CubeIn", new ValueUsage(Keys.CUBE_IN, false));
		add("CubeOut", new ValueUsage(Keys.CUBE_OUT, false));
		add("CubeIntakeSlow", new ValueUsage(Keys.CUBE_INTAKE_SLOW, false));
	}

	@Override
	public String getJoystickType() {
		return "F310";
	}
}
