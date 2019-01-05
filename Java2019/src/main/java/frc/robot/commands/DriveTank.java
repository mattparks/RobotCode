package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class DriveTank extends Command {
	public DriveTank() {
		requires(Robot.m_drivetrain);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		double tankLeft = RobotMap.Robot.DRIVE_SPEED * Robot.m_oi.m_primary.getRawAxis("DriveTankLeft");
		double rankRight = RobotMap.Robot.DRIVE_SPEED * Robot.m_oi.m_primary.getRawAxis("DriveTankRight");

		Robot.m_drivetrain.tankDrive(tankLeft, rankRight);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.m_drivetrain.tankDrive(0.0, 0.0);
	}

	@Override
	protected void interrupted() {
		end();
	}
}
