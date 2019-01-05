package frc.team537.robot.commands;

import frc.team537.robot.Robot;
import frc.team537.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Command;

public class CommandFeederFeed extends Command {
	public CommandFeederFeed() {
		requires(Robot.subsystemFeeder);
		setInterruptible(true);
	}

	@Override
	protected void initialize() {
		Robot.subsystemFeeder.stop();
	}

	@Override
	protected void execute() {
		Robot.subsystemFeeder.setSpeed(RobotMap.Robot.FEED_SPEED);
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