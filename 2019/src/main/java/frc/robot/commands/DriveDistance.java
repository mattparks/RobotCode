package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.subsystems.Drivetrain.SwerveMode;

public class DriveDistance extends Command {
	private double m_angle;
	private double m_distance;
	private boolean m_moving;

	public DriveDistance(double angle, double distance, double timeout) {
		super(timeout);
		requires(Robot.m_drivetrain);
		m_angle = angle;
		m_distance = distance * RobotMap.Robot.DRIVE_M_TO_ENCODER;
		m_moving = false;
	}

	@Override
	protected void initialize() {
		Robot.m_drivetrain.reset();
		Robot.m_drivetrain.setMode(SwerveMode.ModeDistance);
		m_moving = false;
	}

	@Override
	protected void execute() {
		double gyro = Robot.m_gyro.getAngle();
		Robot.m_drivetrain.setTarget(gyro, m_angle, m_moving ? m_distance : 0.0);
		
		if (!m_moving && Robot.m_drivetrain.isAtAngle(8.0)) {
			m_moving = true;
		}
	}

	@Override
	protected boolean isFinished() {
		if (m_moving) {
			return Robot.m_drivetrain.isAtTarget(); //  distance > (distance / RobotMap.Digital.DRIVE_M_TO_ENCODER) * 2.0;
		}
		
		return false;
	}

	@Override
	protected void end() {
		Robot.m_drivetrain.stop();
		m_moving = false;
	}

	@Override
	protected void interrupted() {
		end();
	}
}
