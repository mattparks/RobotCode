package frc.team537.robot;

import frc.team537.robot.commands.*;
import frc.team537.robot.joysticks.IJoystick;
import frc.team537.robot.joysticks.JoystickBox;
import frc.team537.robot.joysticks.JoystickExtreme;
import frc.team537.robot.subsystems.SubsystemRamp.RampSide;

/**
 * Sets up the joystick input scheme.
 */
public class OI {
	public IJoystick joystickPrimary;
	public IJoystick joystickSecondary;

	/**
	 * Assigns commands to joystick actions.
	 */
	public OI() {
		// Joystick Primary
		this.joystickPrimary = new JoystickExtreme(RobotMap.Driver.PRIMARY_PORT);

		this.joystickPrimary.getJoystickButton("DriveLock").whileHeld(new CommandDriveLock());
		this.joystickPrimary.getJoystickButton("Pivot").whileHeld(new CommandDrivePivot());

		// Joystick Secondary
		this.joystickSecondary = new JoystickBox(RobotMap.Driver.SECONDARY_PORT);

		this.joystickSecondary.getJoystickButton("RampDeployLeft").whenPressed(new CommandRampDeploy(RampSide.SideLeft, true));
		this.joystickSecondary.getJoystickButton("RampDeployLeft").whenReleased(new CommandRampDeploy(RampSide.SideLeft, false));
		this.joystickSecondary.getJoystickButton("RampDownLeft").whileHeld(new CommandRampSpeed(RampSide.SideLeft, -0.7));
		this.joystickSecondary.getJoystickButton("RampUpLeft").whileHeld(new CommandRampSpeed(RampSide.SideLeft, 1.0));

		this.joystickSecondary.getJoystickButton("RampDeployRight").whenPressed(new CommandRampDeploy(RampSide.SideRight, true));
		this.joystickSecondary.getJoystickButton("RampDeployRight").whenReleased(new CommandRampDeploy(RampSide.SideRight, false));
		this.joystickSecondary.getJoystickButton("RampDownRight").whileHeld(new CommandRampSpeed(RampSide.SideRight, -0.7));
		this.joystickSecondary.getJoystickButton("RampUpRight").whileHeld(new CommandRampSpeed(RampSide.SideRight, 1.0));

		this.joystickSecondary.getJoystickButton("CubeUp").whileHeld(new CommandLiftSpeed(0.9)); // 0.85
		this.joystickSecondary.getJoystickButton("CubeDown").whileHeld(new CommandLiftSpeed(-0.8)); // -0.7

		this.joystickSecondary.getJoystickButton("CubeIn").whileHeld(new CommandCollectSpeed(-0.75));
		this.joystickSecondary.getJoystickButton("CubeOut").whileHeld(new CommandCollectSpeed(0.7));
	}
}
