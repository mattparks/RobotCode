package frc.robot.subsystems;

import com.ctre.phoenix.CANifier;
import com.ctre.phoenix.CANifier.LEDChannel;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.LightRainbow;

public class Lights extends Subsystem {
	private CANifier m_canifier = new CANifier(RobotMap.CAN.CANIFIER);
	
	public Lights() {
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new LightRainbow());
	}

	public void setColour(double r, double g, double b) {
		m_canifier.setLEDOutput(RobotMap.Robot.LED_BRIGHTNESS * r, LEDChannel.LEDChannelA);
		m_canifier.setLEDOutput(RobotMap.Robot.LED_BRIGHTNESS * g, LEDChannel.LEDChannelB);
		m_canifier.setLEDOutput(RobotMap.Robot.LED_BRIGHTNESS * b, LEDChannel.LEDChannelC);
	}
}
