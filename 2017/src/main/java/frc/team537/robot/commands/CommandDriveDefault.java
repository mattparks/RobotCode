package frc.team537.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team537.robot.Robot;
import frc.team537.robot.RobotMap;
import frc.team537.robot.helpers.Maths;

public class CommandDriveDefault extends Command {
	public CommandDriveDefault() {
		requires(Robot.subsystemDrive);
	}

	@Override
	protected void initialize() {
		Robot.subsystemDrive.stop();
	}

	@Override
	protected void execute() {

		if (Robot.oi.joystickPrimary.getName() == "Extreme") {
			double axisX = Robot.oi.joystickPrimary.getRawAxis("DriveX");
			double axisY = Robot.oi.joystickPrimary.getRawAxis("DriveY");
			double left = axisY + axisX;
			double right = axisY - axisX;
			left = Maths.deadband(RobotMap.Robot.DRIVE_SPEED_MIN, left);
			right = Maths.deadband(RobotMap.Robot.DRIVE_SPEED_MIN, right);
		} else {
			double left = Robot.oi.joystickPrimary.getRawAxis("DriveLeft");
			double right = Robot.oi.joystickPrimary.getRawAxis("DriveRight");
			left = Maths.deadband(RobotMap.Robot.DRIVE_SPEED_MIN, left);
			right = Maths.deadband(RobotMap.Robot.DRIVE_SPEED_MIN, right);
			Robot.subsystemDrive.setTarget(left, right);
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.subsystemDrive.stop();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
