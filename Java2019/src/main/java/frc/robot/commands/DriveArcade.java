package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DriveArcade extends Command {
	public DriveArcade() {
		requires(Robot.m_drivetrain);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		double moveSpeed = Robot.m_oi.m_primary.getRawAxis("DriveForward");
		double rotateSpeed = Robot.m_oi.m_primary.getRawAxis("DriveRotate");

		Robot.m_drivetrain.arcadeDrive(moveSpeed, rotateSpeed);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.m_drivetrain.arcadeDrive(0.0, 0.0);
	}

	@Override
	protected void interrupted() {
		end();
	}
}
