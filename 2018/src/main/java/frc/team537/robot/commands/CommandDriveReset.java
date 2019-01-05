package frc.team537.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team537.robot.Robot;

public class CommandDriveReset extends Command {
	public CommandDriveReset() {
		requires(Robot.subsystemDrive);
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.subsystemDrive.recalibrate();
		Robot.subsystemGyro.reset();
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
