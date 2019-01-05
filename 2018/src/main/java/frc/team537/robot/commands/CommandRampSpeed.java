package frc.team537.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team537.robot.subsystems.SubsystemRamp.RampSide;

public class CommandRampSpeed extends Command {
	private RampSide side;
	private double speed;
	
	public CommandRampSpeed(RampSide side, double speed) {
		requires(side.subsystem);
		this.side = side;
		this.speed = speed;
	}

	@Override
	protected void initialize() {
	//	side.subsystem.reset();
	}

	@Override
	protected void execute() {
		side.subsystem.setSpeed(speed);
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		side.subsystem.stop();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
