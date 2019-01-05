package frc.team537.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team537.robot.subsystems.SubsystemRamp.RampSide;

public class CommandRampDeploy extends Command {
	private RampSide side;
	private boolean toggle;
	
	public CommandRampDeploy(RampSide side, boolean toggle) {
		requires(side.subsystem);
		this.side = side;
		this.toggle = toggle;
	}

	@Override
	protected void initialize() {
		side.subsystem.reset();
	}

	@Override
	protected void execute() {
		side.subsystem.setDeployed(toggle);
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
