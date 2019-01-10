package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class LightColour extends Command {
	private double m_r, m_g, m_b;

	public LightColour(double r, double g, double b) {
		requires(Robot.m_lights);
		m_r = r;
		m_g = g;
		m_b = b;
	}
	
	public void setColour(String hex) {
		requires(Robot.m_lights);
		m_r = (double) Integer.valueOf(hex.substring(1, 3), 16) / 255.0;
		m_b = (double) Integer.valueOf(hex.substring(3, 5), 16) / 255.0;
		m_g = (double) Integer.valueOf(hex.substring(5, 7), 16) / 255.0;
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.m_lights.setColour(m_r, m_g, m_b);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.m_lights.setColour(0.0, 0.0, 0.0);
	}

	@Override
	protected void interrupted() {
		end();
	}
}
