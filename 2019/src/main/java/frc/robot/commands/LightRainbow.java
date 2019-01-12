package frc.robot.commands;

import java.awt.Color;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class LightRainbow extends Command {
	private double m_r, m_g, m_b;

	public LightRainbow() {
		requires(Robot.m_lights);
	}
	
	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		float h = (1.0f + (float) Math.cos(0.6 * timeSinceInitialized())) / 2.0f;
		Color color = Color.getHSBColor(h, 1.0f, 1.0f);
		
		m_r = (double) color.getRed() / 255.0;
		m_b = (double) color.getGreen() / 255.0;
		m_g = (double) color.getBlue() / 255.0;
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
