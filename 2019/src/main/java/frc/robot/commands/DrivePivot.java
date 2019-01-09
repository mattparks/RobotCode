package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Drivetrain.SwerveMode;

public class DrivePivot extends Command {
	private double m_angle;

	public DrivePivot() {
		requires(Robot.m_drivetrain);
		m_angle = 0.0;
	}

	@Override
	protected void initialize() {
		m_angle = getAngle();
		Robot.m_drivetrain.reset();
		Robot.m_drivetrain.setMode(SwerveMode.ModeSpeed);
		Robot.m_drivetrain.setControllerRotate(m_angle);
	}

	@Override
	protected void execute() {
		double newAngle = getAngle();
		
		if (newAngle != m_angle) {
			m_angle = newAngle;
			Robot.m_drivetrain.setControllerRotate(m_angle);
		}
	}
	
	private double getAngle() {
		return (double) Robot.m_oi.m_primary.getPOV() / (double) Robot.m_oi.m_primary.getPOVCount();
	}

	@Override
	protected boolean isFinished() {
		return Robot.m_drivetrain.getControllerRotate().onTarget() || timeSinceInitialized() > 2.0;
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
