package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class DriveCurvature extends Command {
	public DriveCurvature() {
		requires(Robot.m_drivetrain);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		double moveSpeed = RobotMap.Robot.DRIVE_SPEED * Robot.m_oi.m_primary.getRawAxis("DriveForward");
		double rotateSpeed = RobotMap.Robot.DRIVE_SPEED * Robot.m_oi.m_primary.getRawAxis("DriveRotate");
		boolean quickTurn = Robot.m_oi.m_primary.getRawButton("DriveQuickTurn");

		Robot.m_drivetrain.curvatureDrive(moveSpeed, rotateSpeed, quickTurn);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.m_drivetrain.curvatureDrive(0.0, 0.0, false);
	}

	@Override
	protected void interrupted() {
		end();
	}
}
