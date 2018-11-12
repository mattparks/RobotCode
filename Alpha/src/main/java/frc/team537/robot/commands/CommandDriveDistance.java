package frc.team537.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.team537.robot.Robot;
import frc.team537.robot.RobotMap;
import frc.team537.robot.subsystems.SwerveModule;

public class CommandDriveDistance extends Command {
	private double angle;
	private double distance;
	private Timer timer;
	private boolean moving;
	
	public CommandDriveDistance(double angle, double distance) {
		requires(Robot.subsystemDrive);
		this.angle = angle;
		this.distance = distance * RobotMap.Robot.DRIVE_M_TO_ENCODER;
		this.timer = new Timer();
		this.moving = false;
	}

	@Override
	protected void initialize() {
		Robot.subsystemDrive.reset();
		Robot.subsystemDrive.setMode(SwerveModule.SwerveMode.ModeDistance);
		timer.reset();
		moving = false;
	}

	@Override
	protected void execute() {
		double gyro = Robot.subsystemGyro.getAngle();
		Robot.subsystemDrive.setTarget(gyro, angle, moving ? distance : 0.0);
		
		if (!moving && Robot.subsystemDrive.isAtAngle(8.0)) {
			timer.start();
			moving = true;
		}
	}

	@Override
	protected boolean isFinished() {
		if (moving) {
			return Robot.subsystemDrive.isAtTarget(); // timer.get() > (distance / RobotMap.Digital.DRIVE_M_TO_ENCODER) * 2.0;
		}
		
		return false;
	}

	@Override
	protected void end() {
		Robot.subsystemDrive.stop();
		timer.stop();
		moving = false;
	}

	@Override
	protected void interrupted() {
		end();
	}
}
