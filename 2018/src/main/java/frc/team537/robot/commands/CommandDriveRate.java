package frc.team537.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.team537.robot.Robot;
import frc.team537.robot.RobotMap;
import frc.team537.robot.subsystems.SwerveModule;

public class CommandDriveRate extends Command {
	private double angle;
	private double rate;
	private double timeout;
	private Timer timer;
	private boolean moving;
	
	public CommandDriveRate(double angle, double rate, double timeout) {
		requires(Robot.subsystemDrive);
		this.angle = angle;
		this.rate = rate * RobotMap.Robot.DRIVE_M_TO_ENCODER;
		this.timeout = timeout;
		this.timer = new Timer();
		this.moving = false;
	}

	@Override
	protected void initialize() {
		Robot.subsystemDrive.reset();
		Robot.subsystemDrive.setMode(SwerveModule.SwerveMode.ModeRate);
		timer.reset();
		moving = false;
	}

	@Override
	protected void execute() {
		double gyro = Robot.subsystemGyro.getAngle();
		Robot.subsystemDrive.setTarget(gyro, angle, moving ? rate : 0.0);
		
		if (!moving && Robot.subsystemDrive.isAtAngle(8.0)) {
			timer.start();
			moving = true;
		}
	}

	@Override
	protected boolean isFinished() {
		if (moving) {
			return timer.get() > timeout;
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
