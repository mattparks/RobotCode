package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.PID;
import frc.robot.subsystems.Drivetrain.SwerveMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTune extends Command {
	public enum TuneCommand {
		ChangeTuning, ChangeModule, ChangeIncrement, ChangeRun
	}

	public static boolean TUNING_ANGLE = false; 
	public static int TUNING_MODULE_INDEX = 0; 
	public static boolean INCREMENTING = true; 

	public TuneCommand m_tuneCommand;
	private int m_indexPID;
	private double m_delta;

	public DriveTune(TuneCommand tuneCommand, int indexPID, double delta) {
	//	requires(Robot.m_drivetrain);
		m_tuneCommand = tuneCommand;
		m_indexPID = indexPID;
		m_delta = delta;
	}

	@Override
	protected void initialize() {
	}

	@Override
	protected void execute() {
		switch (m_tuneCommand) {
			case ChangeTuning:
				TUNING_ANGLE = !TUNING_ANGLE;
				SmartDashboard.putString("Tuning Mode", TUNING_ANGLE ? "Angle" : "Drive");
				break;
			case ChangeModule:
				TUNING_MODULE_INDEX = (TUNING_MODULE_INDEX + 1) % 4;
				SmartDashboard.putString("Tuning Module", Robot.m_drivetrain.getModule(TUNING_MODULE_INDEX).getName());
				break;
			case ChangeIncrement:
				INCREMENTING = !INCREMENTING;
				SmartDashboard.putBoolean("Tuning Incrementing", INCREMENTING);
				break;
			case ChangeRun:
				if (TUNING_ANGLE) {
					PID pid = Robot.m_drivetrain.getModulePID(TUNING_MODULE_INDEX);
					pid.setIndex(m_indexPID, pid.getIndex(m_indexPID) + (m_delta * (INCREMENTING ? 1.0 : -1.0)));
					Robot.m_drivetrain.getModule(TUNING_MODULE_INDEX).setAnglePID(pid);
				} else {
					Robot.m_drivetrain.getMode().getPidDrive().setIndex(m_indexPID, Robot.m_drivetrain.getMode().getPidDrive().getIndex(m_indexPID) + 
						(m_delta * (INCREMENTING ? 1.0 : 0.0)));
					Robot.m_drivetrain.getMode().setChanged(true);
				}
				break;
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
	}

	@Override
	protected void interrupted() {
		end();
	}
}
