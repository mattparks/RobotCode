package frc.team537.robot.commands;

import frc.team537.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class CommandShooterDefault extends Command {
	public CommandShooterDefault() {
		requires(Robot.subsystemShooter);
		setInterruptible(true);
	}

	@Override
	protected void initialize() {
		Robot.subsystemShooter.stop();
	}

	@Override
	protected void execute() {
		Robot.subsystemShooter.setSpeed(0.0);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.subsystemShooter.stop();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
