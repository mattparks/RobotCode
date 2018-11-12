package frc.team537.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.team537.robot.Robot;
import frc.team537.robot.helpers.Colour;

public class CommandLedsColour extends Command {
	private Colour colour;
	private double timeout;
	private Timer timer;
	
	public CommandLedsColour(Colour colour, double timeout) {
		requires(Robot.subsystemLeds);
		this.colour = colour;
		this.timeout = timeout;
		this.timer = new Timer();
	}

	@Override
	protected void initialize() {
		timer.reset();
		timer.start();
	}

	@Override
	protected void execute() {
		Robot.subsystemLeds.setColour(colour);
	}

	@Override
	protected boolean isFinished() {
		return timer.get() > timeout;
	}

	@Override
	protected void end() {
		Robot.subsystemLeds.reset();
		timer.stop();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
