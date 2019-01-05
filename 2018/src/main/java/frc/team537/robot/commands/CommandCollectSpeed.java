package frc.team537.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team537.robot.Robot;

public class CommandCollectSpeed extends Command {
	private double speed;
	
	public CommandCollectSpeed(double speed) {
		requires(Robot.subsystemCollect);
		this.speed = speed;
	}

	@Override
	protected void initialize() {
		Robot.subsystemCollect.reset();
	}

	@Override
	protected void execute() {
		if (speed < 0.0 && Robot.oi.joystickSecondary.getRawButton("CubeIntakeSlow"))
		{
			Robot.subsystemCollect.setSpeed(speed * 0.15f);
			return;
		}
		
		Robot.subsystemCollect.setSpeed(speed);
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
