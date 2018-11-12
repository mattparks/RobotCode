package frc.team537.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.team537.robot.Robot;
import frc.team537.robot.subsystems.SwerveModule;

public class CommandDriveRotate extends Command {
	private double angle;
	private Timer timer;
	
	public CommandDriveRotate(double angle) {
		requires(Robot.subsystemDrive);
		this.angle = angle;
		this.timer = new Timer();
	}

	@Override
	protected void initialize() {
		Robot.subsystemDrive.reset();
		Robot.subsystemDrive.setMode(SwerveModule.SwerveMode.ModeSpeed);
		Robot.subsystemDrive.setControllerRotate(angle);
		timer.reset();
		timer.start();
	}

	@Override
	protected void execute() {
		Robot.subsystemDrive.setTarget(0.0, Robot.subsystemDrive.getControllerRotate().get(), 0.0, 0.0);
	}

	@Override
	protected boolean isFinished() {
		return Robot.subsystemDrive.getControllerRotate().onTarget() || timer.get() > 2.0;
	}

	@Override
	protected void end() {
		timer.stop();
		Robot.subsystemDrive.stop();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
