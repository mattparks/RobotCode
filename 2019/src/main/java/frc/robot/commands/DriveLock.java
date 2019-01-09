package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DriveLock extends Command {
	public DriveLock() {
		requires(Robot.m_drivetrain);
	}

	@Override
	protected void initialize() {
		double gyro = Robot.m_gyro.getAngle();
		Robot.m_drivetrain.setControllerRotate(gyro);
	}

	@Override
	protected void execute() {
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.m_drivetrain.getControllerRotate().disable();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
