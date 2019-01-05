package frc.robot;

import frc.robot.joysticks.IJoystick;
import frc.robot.joysticks.JoystickExtreme;
import frc.robot.joysticks.JoystickF310;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public IJoystick m_primary = new JoystickF310(RobotMap.Driver.PRIMARY_PORT);
//  public IJoystick m_secondary = new JoystickExtreme(RobotMap.Driver.SECONDARY_PORT);
  
	/**
	 * Binds physical controls to commands and command groups.
	 */
	public OI() {
  }
}
