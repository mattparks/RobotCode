package frc.team537.robot;

import frc.team537.robot.commands.*;
import frc.team537.robot.joysticks.IJoystick;
import frc.team537.robot.joysticks.JoystickF310;
import frc.team537.robot.joysticks.JoystickExtreme;

/**
 * Sets up the joystick input scheme.
 */
public class OI {
	public IJoystick joystickPrimary;

	/**
	 * Assigns commands to joystick actions.
	 */
	public OI() {
		// Joystick Primary
		this.joystickPrimary = new JoystickF310(RobotMap.Driver.PRIMARY_PORT);

		this.joystickPrimary.getJoystickButton("Feed").whileHeld(new CommandFeederFeed());
		this.joystickPrimary.getJoystickButton("Shoot").whileHeld(new CommandShooterShoot());
	}
}
