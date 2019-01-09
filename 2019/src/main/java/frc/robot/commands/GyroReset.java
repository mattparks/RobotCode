package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class GyroReset extends Command {
	private double m_angle;

	public GyroReset(double angle) {
		requires(Robot.m_gyro);
		m_angle = angle;
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.m_gyro.reset();
		Robot.m_gyro.setAngle(m_angle);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
		end();
	}
}
