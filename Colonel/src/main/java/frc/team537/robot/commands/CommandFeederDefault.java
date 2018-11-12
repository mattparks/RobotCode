package frc.team537.robot.commands;

import frc.team537.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class CommandFeederDefault extends Command {
	public CommandFeederDefault() {
		requires(Robot.subsystemFeeder);
		setInterruptible(true);
	}

	@Override
	protected void initialize() {
		Robot.subsystemFeeder.stop();
	}

	@Override
	protected void execute() {
		Robot.subsystemFeeder.setSpeed(0.0);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.subsystemFeeder.stop();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
