package frc.robot;

import jaci.pathfinder.Trajectory;

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
		public static final double LED_BRIGHTNESS = 0.5;

		public static final double WHEEL_DIAMETER = 0.1016; // m
		public static final double WIDTH = 0.26; // m
		public static final double DEPTH = 0.207; // m
		public static final double RATIO = Math.sqrt((DEPTH * DEPTH) + (WIDTH * WIDTH));

		public static final double DRIVE_M_TO_ENCODER = 1984.4878; // ticks/m
		public static final double DRIVE_SPEED = 0.8; // % 0-1.0
		
		public static final double DRIVE_PATH_SCALE = 0.4; // % -1.0-1.0 speed scale for the pathfinder

		public static final double MAX_VELOCITY = 1.7; // m/s (maximum robot velocity the robot is capable of)
		public static final double MAX_ACCELERATION = 2.0; // m/s/s
		public static final double MAX_JERK = 60.0; // m/s/s/s
		public static final Trajectory.Config TRAJECTORY_CONFIG = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, 
			MAX_VELOCITY, MAX_ACCELERATION, MAX_JERK);
	}

	/**
	 * PIDs used in the actual robot, these values can be tuned from the switchboard.
	 */
	public static class PIDs {
		public static PID DRIVE_ANGLE_FRONT_LEFT = new PID(4.9, 0.0, 4.0, "PID Front Left");
		public static PID DRIVE_ANGLE_FRONT_RIGHT = new PID(3.8, 0.0, 4.0, "PID Front Right");
		public static PID DRIVE_ANGLE_BACK_LEFT = new PID(5.4, 0.0, 4.3, "PID Back Left");
		public static PID DRIVE_ANGLE_BACK_RIGHT = new PID(5.4, 0.0, 4.3, "PID Back Right");

		public static PID DRIVE_PATH = new PID(1.0, 0.0, 0.0, "PID Path");

		public static PID DRIVE_ROTATE = new PID(0.01, 0.0, 0.002, "PID Rotate");
		public static PID DRIVE_MODE_SPEED = new PID(0.0, 0.0, 0.0, "PID Mode Speed");
		public static PID DRIVE_MODE_RATE = new PID(0.05, 0.0, 0.025, 0.3, "PID Mode Rate");
		public static PID DRIVE_MODE_DISTANCE = new PID(0.22, 0.0, 0.0, "PID Front Distance");
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
	 * CAN ports that are set from the Phoenix control application.
	 */
	public static class CAN {
		public static final int CANIFIER = 0;

		public static final int DRIVE_FRONT_LEFT_DRIVE = 4;
		public static final int DRIVE_FRONT_LEFT_ANGLE = 3;
		public static final int DRIVE_FRONT_RIGHT_DRIVE = 1;
		public static final int DRIVE_FRONT_RIGHT_ANGLE = 2;
		public static final int DRIVE_BACK_LEFT_DRIVE = 5;
		public static final int DRIVE_BACK_LEFT_ANGLE = 6;
		public static final int DRIVE_BACK_RIGHT_DRIVE = 8;
		public static final int DRIVE_BACK_RIGHT_ANGLE = 7;
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
