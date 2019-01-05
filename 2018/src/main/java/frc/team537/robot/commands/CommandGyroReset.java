package frc.team537.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team537.robot.Robot;

public class CommandGyroReset extends Command {
	private double angle;
	
	public CommandGyroReset(double angle) {
		requires(Robot.subsystemGyro);
		this.angle = angle;
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		Robot.subsystemGyro.reset();
		Robot.subsystemGyro.setAngle(angle);
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
