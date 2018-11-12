package frc.team537.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team537.robot.Robot;

public class CommandDriveLock extends Command {
	public CommandDriveLock() {
	//	requires(Robot.subsystemDrive);
	}

	@Override
	protected void initialize() {
		double gyro = Robot.subsystemGyro.getAngle();
		Robot.subsystemDrive.setControllerRotate(gyro);
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.subsystemDrive.getControllerRotate().disable();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
