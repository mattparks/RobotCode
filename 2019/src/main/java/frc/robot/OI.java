package frc.robot;

import frc.robot.commands.DriveLock;
import frc.robot.commands.DrivePivot;
import frc.robot.commands.DriveTune;
import frc.robot.commands.DriveTune.TuneCommand;
import frc.robot.joysticks.IJoystick;
import frc.robot.joysticks.JoystickExtreme;
import frc.robot.joysticks.JoystickF310;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public IJoystick m_primary = new JoystickF310(RobotMap.Driver.PRIMARY_PORT);
//	public IJoystick m_secondary = new JoystickExtreme(RobotMap.Driver.SECONDARY_PORT);
	
	/**
	 * Binds physical controls to commands and command groups.
	 */
	public OI() {
		m_primary.getJoystickButton("DriveLock").whileHeld(new DriveLock());
		m_primary.getJoystickButton("Pivot").whileHeld(new DrivePivot());

		m_primary.getJoystickButton("TuneModule").whenPressed(new DriveTune(TuneCommand.ChangeTuning, 0, 0.0));
		m_primary.getJoystickButton("TuneMode").whenPressed(new DriveTune(TuneCommand.ChangeModule, 0, 0.0));
		m_primary.getJoystickButton("TuneIncrement").whenPressed(new DriveTune(TuneCommand.ChangeIncrement, 0, 0.0));
		m_primary.getJoystickButton("TuneP").whenPressed(new DriveTune(TuneCommand.ChangeRun, 0, 0.05));
		m_primary.getJoystickButton("TuneI").whenPressed(new DriveTune(TuneCommand.ChangeRun, 1, 0.05));
		m_primary.getJoystickButton("TuneD").whenPressed(new DriveTune(TuneCommand.ChangeRun, 2, 0.05));
	}
}
