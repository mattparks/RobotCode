package frc.team537.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team537.robot.Robot;

public class CommandCollectDefault extends Command {
	public CommandCollectDefault() {
		requires(Robot.subsystemCollect);
	}

	@Override
	protected void initialize() {
		Robot.subsystemCollect.reset();
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
		Robot.subsystemCollect.stop();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
