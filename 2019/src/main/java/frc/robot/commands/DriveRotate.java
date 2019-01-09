package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Drivetrain.SwerveMode;

public class DriveRotate extends Command {
	private double m_angle;

	public DriveRotate(double angle) {
		requires(Robot.m_drivetrain);
		m_angle = angle;
	}

	@Override
	protected void initialize() {
		Robot.m_drivetrain.reset();
		Robot.m_drivetrain.setMode(SwerveMode.ModeSpeed);
		Robot.m_drivetrain.setControllerRotate(m_angle);
	}

	@Override
	protected void execute() {
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
