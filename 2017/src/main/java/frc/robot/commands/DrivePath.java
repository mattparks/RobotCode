package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class DrivePath extends Command {
	public DrivePath(String pathGroup) {
		requires(Robot.m_drivetrain);
	//	FileUtilities.getFilePath();
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {

		Robot.m_drivetrain.tankDrive(left, right);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.m_drivetrain.tankDrive(0.0, 0.0);
	}

	@Override
	protected void interrupted() {
		end();
	}
}
