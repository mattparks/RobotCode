package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DriveReset extends Command {
	public DriveReset() {
		requires(Robot.m_drivetrain);
		requires(Robot.m_gyro);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.m_drivetrain.reset();
		Robot.m_gyro.reset();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
		end();
	}
}
