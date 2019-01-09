package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.subsystems.Drivetrain.SwerveMode;

public class DriveRate extends Command {
	private double m_angle;
	private double m_rate;
	private double m_timeout;
	private boolean m_moving;

	public DriveRate(double angle, double rate, double timeout) {
		requires(Robot.m_drivetrain);
		m_angle = angle;
		m_rate = rate * RobotMap.Robot.DRIVE_M_TO_ENCODER;
		m_timeout = timeout;
		m_moving = false;
	}

	@Override
	protected void initialize() {
		Robot.m_drivetrain.reset();
		Robot.m_drivetrain.setMode(SwerveMode.ModeRate);
		m_moving = false;
	}

	@Override
	protected void execute() {
		double gyro = Robot.m_gyro.getAngle();
		Robot.m_drivetrain.setTarget(gyro, m_angle, m_moving ? m_rate : 0.0);
		
		if (!m_moving && Robot.m_drivetrain.isAtAngle(8.0)) {
			m_moving = true;
		}
	}

	@Override
	protected boolean isFinished() {
		if (m_moving) {
			return timeSinceInitialized() > m_timeout;
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
