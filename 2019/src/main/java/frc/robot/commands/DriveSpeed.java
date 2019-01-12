package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.subsystems.Drivetrain.SwerveMode;

public class DriveSpeed extends Command {
	private double m_angle;
	private double m_speed;
	private boolean m_moving;

	public DriveSpeed(double angle, double speed, double timeout) {
		super(timeout);
		requires(Robot.m_drivetrain);
		m_angle = angle;
		m_speed = speed * RobotMap.Robot.DRIVE_M_TO_ENCODER;
		m_moving = false;
	}

	@Override
	protected void initialize() {
		Robot.m_drivetrain.reset();
		Robot.m_drivetrain.setMode(SwerveMode.ModeSpeed);
		m_moving = false;
	}

	@Override
	protected void execute() {
		double gyro = Robot.m_gyro.getAngle();
		Robot.m_drivetrain.setTarget(gyro, m_angle, m_moving ? m_speed : 0.0);
		
		if (!m_moving && Robot.m_drivetrain.isAtAngle(8.0)) {
			m_moving = true;
		}
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
