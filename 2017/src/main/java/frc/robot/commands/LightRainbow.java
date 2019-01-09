package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class LightRainbow extends Command {
	private double m_lastTime;
	private double m_time;
	private double m_r, m_g, m_b;

	public LightRainbow() {
		requires(Robot.m_lights);
	}
	
	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		double newTime = (double) System.currentTimeMillis() / 1000.0;
		double delta = newTime - m_lastTime;
		m_lastTime = newTime;
		m_time += delta;
		
		m_r = 2.0 * Math.sin(m_time);
		m_b = 1.5 * Math.cos(m_time);
		m_g = 1.0 - (2.0 * (m_r + m_b));
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
