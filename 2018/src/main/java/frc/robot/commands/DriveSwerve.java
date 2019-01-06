package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.subsystems.Drivetrain.SwerveMode;

public class DriveSwerve extends Command {
	public DriveSwerve() {
		requires(Robot.m_drivetrain);
	}

	@Override
	protected void initialize() {
		Robot.m_drivetrain.reset();
		Robot.m_drivetrain.setMode(SwerveMode.ModeSpeed);
	}

	@Override
	protected void execute() {
		double gyro = Math.toRadians(Robot.m_gyro.getAngle());
		double rotation = Robot.m_oi.m_primary.getRawAxis("DriveRotation");
		double strafe = Robot.m_oi.m_primary.getRawAxis("DriveStrafe");
		double forward = Robot.m_oi.m_primary.getRawAxis("DriveForward");
		Robot.m_drivetrain.setTarget(gyro, rotation, strafe, forward);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.m_drivetrain.stop();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
