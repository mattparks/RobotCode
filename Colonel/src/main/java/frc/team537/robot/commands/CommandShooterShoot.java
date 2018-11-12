package frc.team537.robot.commands;

import frc.team537.robot.Robot;
import frc.team537.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Command;

public class CommandShooterShoot extends Command {
	public CommandShooterShoot() {
		requires(Robot.subsystemShooter);
		setInterruptible(true);
	}

	@Override
	protected void initialize() {
		Robot.subsystemShooter.stop();
	}

	@Override
	protected void execute() {
		Robot.subsystemShooter.setSpeed(RobotMap.Robot.SHOOT_SPEED);
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
