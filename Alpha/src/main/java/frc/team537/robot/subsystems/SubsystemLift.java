package frc.team537.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team537.robot.RobotMap;
import frc.team537.robot.commands.CommandLiftDefault;
import frc.team537.robot.commands.CommandLiftDeploy;
import frc.team537.robot.helpers.Maths;

import java.util.Timer;
import java.util.TimerTask;

public class SubsystemLift extends Subsystem {
	private TalonSRX talonLift = new TalonSRX(RobotMap.CAN.LIFT);
	private DigitalInput switchTop = new DigitalInput(RobotMap.Digital.LIMIT_SWITCH_TOP);
	private DigitalInput switchBottom = new DigitalInput(RobotMap.Digital.LIMIT_SWITCH_BOTTOM);
	private double currentPosition;
	private boolean foundZero;

	public SubsystemLift() {
		setName("Lift");
		
		talonLift.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, RobotMap.kPIDLoopIdx, RobotMap.kTimeoutMs);
		talonLift.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 10, RobotMap.kTimeoutMs);
		talonLift.setSensorPhase(true); 
	//	talonLift.configForwardSoftLimitThreshold(RobotMap.Robot.LIFT_LIMIT_TOP, RobotMap.kTimeoutMs);
	//	talonLift.configForwardSoftLimitEnable(false, RobotMap.kTimeoutMs);
	//	talonLift.configReverseSoftLimitThreshold(RobotMap.Robot.LIFT_LIMIT_BOTTOM, RobotMap.kTimeoutMs);
	//	talonLift.configReverseSoftLimitEnable(false, RobotMap.kTimeoutMs);
	//	talonLift.configContinuousCurrentLimit(30, RobotMap.kTimeoutMs);
	//	talonLift.configPeakCurrentLimit(40, RobotMap.kTimeoutMs);
	//	talonLift.configPeakCurrentDuration(100, RobotMap.kTimeoutMs);
	//	talonLift.enableCurrentLimit(true);

		talonLift.setSelectedSensorPosition(0, RobotMap.kPIDLoopIdx, RobotMap.kTimeoutMs);
		
		currentPosition = 0.0;
		foundZero = false;

		Timer timerDashboard = new Timer();
		timerDashboard.schedule(new TimerTask() {
			@Override
			public void run() {
				dashboard();
			}
		}, 0, 100);
	}

	@Override
	protected void initDefaultCommand() {
		SmartDashboard.putData("Lift Deploy", new CommandLiftDeploy());
		
		setDefaultCommand(new CommandLiftDefault());
	}
	
	public void dashboard() {
		SmartDashboard.putNumber("Lift Height", currentPosition);
		SmartDashboard.putBoolean("Lift Bottom", !switchBottom.get());
		SmartDashboard.putBoolean("Lift Top", !switchTop.get());
		SmartDashboard.putBoolean("Lift Found Zero", foundZero);
	}

	public void setSpeed(double speed) {
		currentPosition = talonLift.getSelectedSensorPosition(RobotMap.kPIDLoopIdx);
		
		if (foundZero) {
			if (speed < 0.0 && RobotMap.Robot.LIFT_LIMIT_BOTTOM - currentPosition <= RobotMap.Robot.LIFT_DEADBAND) {
			//	SmartDashboard.putString("Lift State", "Bottom Slowdown");
				double progress = (RobotMap.Robot.LIFT_LIMIT_BOTTOM - currentPosition) / RobotMap.Robot.LIFT_DEADBAND;
				
				if (progress <= 0.0) {
					speed = 0.0;
				} else {
					speed *= Maths.clamp(progress, 0.3, 1.0);
				}
			} else if (speed > 0.0 && currentPosition - RobotMap.Robot.LIFT_LIMIT_TOP <= RobotMap.Robot.LIFT_DEADBAND) {
			//	SmartDashboard.putString("Lift State", "Top Slowdown");
				double progress = (currentPosition - RobotMap.Robot.LIFT_LIMIT_TOP) / RobotMap.Robot.LIFT_DEADBAND;

				if (progress <= 0.0) {
					speed = 0.0;
				} else {
					speed *= Maths.clamp(progress, 0.3, 1.0);
				}
			} else {
			//	SmartDashboard.putString("Lift State", "None");
			}
			
		} else {
			if (!switchTop.get()) {
				talonLift.setSelectedSensorPosition(0, RobotMap.kPIDLoopIdx, RobotMap.kTimeoutMs);
				foundZero = true;
			}
			
			if (speed < 0.0 && !switchBottom.get()) {
				speed = 0.0;
			} else if (speed > 0.0 && !switchTop.get()) {
				speed = 0.0;
			}
			
			speed = Maths.clamp(speed, -0.35, 0.35);
		}
		
		//if (Math.abs(talonLift.getOutputCurrent()) > 40.0) {
		//	speed = 0.0;
		//}

		speed = Maths.deadband(0.1, speed);
		talonLift.set(ControlMode.PercentOutput, speed);
	}

	public boolean hasFoundZero() {
		return foundZero;
	}

	public void resetFoundZero() {
		foundZero = false;
	}
	
	public void reset() {
		stop();
	}
	
	public void stop() {
		talonLift.set(ControlMode.PercentOutput, 0.0);
	}
}
