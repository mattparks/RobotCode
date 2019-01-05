package frc.team537.robot.subsystems;

import frc.team537.robot.RobotMap;
import frc.team537.robot.commands.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.command.Subsystem;

import java.util.Timer;
import java.util.TimerTask;

public class SubsystemDrive extends Subsystem {
	private final TalonSRX driveLeft3 = new TalonSRX(RobotMap.CAN.DRIVE_LEFT_MINI);
	private final TalonSRX driveLeft2 = new TalonSRX(RobotMap.CAN.DRIVE_LEFT_NORMAL);
	private final TalonSRX driveLeft1 = new TalonSRX(RobotMap.CAN.DRIVE_LEFT_MASTER);

	private final VictorSPX driveRight3 = new VictorSPX(RobotMap.CAN.DRIVE_RIGHT_MINI);
	private final VictorSPX driveRight2 = new VictorSPX(RobotMap.CAN.DRIVE_RIGHT_NORMAL);
	private final TalonSRX driveRight1 = new TalonSRX(RobotMap.CAN.DRIVE_RIGHT_MASTER);

	private double averageSpeed = 0.0;

	public SubsystemDrive() {
		setName("Drive");

		driveLeft3.set(ControlMode.Follower, RobotMap.CAN.DRIVE_LEFT_MASTER);
		driveLeft2.set(ControlMode.Follower, RobotMap.CAN.DRIVE_LEFT_MASTER);

		driveRight3.set(ControlMode.Follower, RobotMap.CAN.DRIVE_RIGHT_MASTER);
		driveRight2.set(ControlMode.Follower, RobotMap.CAN.DRIVE_RIGHT_MASTER);
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new CommandDriveDefault());
	}

	public void setTarget(double left, double right) {
		driveLeft1.set(ControlMode.PercentOutput, RobotMap.Robot.DRIVE_SPEED * left);
		driveRight1.set(ControlMode.PercentOutput, RobotMap.Robot.DRIVE_SPEED * right);

		driveRight2.set(ControlMode.PercentOutput, RobotMap.Robot.DRIVE_SPEED * right);
		driveRight3.set(ControlMode.PercentOutput, RobotMap.Robot.DRIVE_SPEED * right);

		averageSpeed = (left + right) / 2.0f;
	}

	public double getAverageSpeed() {
		return averageSpeed;
	}

	public void stop() {
		driveLeft1.set(ControlMode.PercentOutput, 0.0);
		driveRight1.set(ControlMode.PercentOutput, 0.0);

		driveRight2.set(ControlMode.PercentOutput, 0.0);
		driveRight3.set(ControlMode.PercentOutput, 0.0);
	}
}
