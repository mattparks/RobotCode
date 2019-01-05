package frc.team537.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team537.robot.Robot;
import frc.team537.robot.helpers.Colour;
import frc.team537.robot.helpers.Maths;

public class CommandLedsDefault extends Command {
	private double lastTime;
	private double time;
	
	public CommandLedsDefault() {
		requires(Robot.subsystemLeds);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		double newTime = (double) System.currentTimeMillis() / 1000.0;
		double delta = newTime - lastTime;
		lastTime = newTime;
		time += delta * (1.0 + (5.0 * Maths.clamp(Math.abs(Robot.subsystemDrive.getAverageSpeed()), 0.0, 1.0)));
		
		double r = 2.0 * Math.sin(time);
		double b = 1.5 * Math.cos(time);
		double g = 1.0 - (2.0f * (r + b));
		Robot.subsystemLeds.setColour(new Colour(r, g, b));
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.subsystemLeds.reset();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
