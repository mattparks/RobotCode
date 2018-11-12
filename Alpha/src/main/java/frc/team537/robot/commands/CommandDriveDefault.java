package frc.team537.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team537.robot.Robot;
import frc.team537.robot.helpers.Maths;
import frc.team537.robot.subsystems.SwerveModule;

public class CommandDriveDefault extends Command {
	public CommandDriveDefault() {
		requires(Robot.subsystemDrive);
	}

	@Override
	protected void initialize() {
		Robot.subsystemDrive.reset();
		Robot.subsystemDrive.setMode(SwerveModule.SwerveMode.ModeSpeed);
	}

	@Override
	protected void execute() {
		double gyro = Math.toRadians(Robot.subsystemGyro.getAngle());
		double rotation = Robot.oi.joystickPrimary.getRawAxis("DriveRotation");
		rotation = sensitivity(deadband(0.2, rotation), 0.2);
		double strafe = Robot.oi.joystickPrimary.getRawAxis("DriveStrafe");
		strafe = sensitivity(deadband(0.1, strafe), 0.1);
		double forward = Robot.oi.joystickPrimary.getRawAxis("DriveForward");
		forward = sensitivity(deadband(0.1, forward), 0.1);
		Robot.subsystemDrive.setTarget(gyro, rotation, strafe, forward);
	}
	
	private static double sensitivity(double value, double factor) {
		return ((1.0 - factor) * value) + (factor * Math.pow(value, 3.0));
	}

	public static double deadband(double min, double value) {
		double result = Maths.deadband(min, value);
		
		if (result < 0.0) {
			result += min;
		} else if (result > 0.0) {
			result -= min;
		}
		
		return Maths.clamp(result, -1.0, 1.0);
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
