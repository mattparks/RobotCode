package frc.team537.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team537.robot.Robot;

public class CommandLiftSpeed extends Command {
	private double speed;
	
	public CommandLiftSpeed(double speed) {
		requires(Robot.subsystemLift);
		this.speed = speed;
	}

	@Override
	protected void initialize() {
		Robot.subsystemLift.reset();
	}

	@Override
	protected void execute() {
		Robot.subsystemLift.setSpeed(speed);
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
