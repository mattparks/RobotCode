package frc.team537.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team537.robot.Robot;

public class CommandLiftDefault extends Command {
	public CommandLiftDefault() {
		requires(Robot.subsystemLift);
	}

	@Override
	protected void initialize() {
		Robot.subsystemLift.reset();
	}

	@Override
	protected void execute() {
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.subsystemLift.stop();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
