package frc.team537.robot.subsystems;

import com.ctre.phoenix.CANifier;
import com.ctre.phoenix.CANifier.LEDChannel;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team537.robot.RobotMap;
import frc.team537.robot.commands.CommandLedsDefault;
import frc.team537.robot.helpers.Colour;

/**
 * A subsystem used for displaying a single colour to a canifier.
 */
public class SubsystemLeds extends Subsystem {
	private CANifier canifier = new CANifier(0);
	
	public SubsystemLeds() {
		setName("Leds");
		reset();
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new CommandLedsDefault());
	}

	/**
	 * Sets a new colour to be displayed immediately.
	 *
	 * @param colour The colour to display.
	 */
	public void setColour(Colour colour) {
		canifier.setLEDOutput(RobotMap.Robot.LED_BRIGHTNESS * colour.getR(), LEDChannel.LEDChannelA);
		canifier.setLEDOutput(RobotMap.Robot.LED_BRIGHTNESS * colour.getG(), LEDChannel.LEDChannelB);
		canifier.setLEDOutput(RobotMap.Robot.LED_BRIGHTNESS * colour.getB(), LEDChannel.LEDChannelC);
	}

	/**
	 * Resets the canifier to the default state.
	 */
	public void reset() {
		setColour(new Colour("#ff0000"));
	}
}
