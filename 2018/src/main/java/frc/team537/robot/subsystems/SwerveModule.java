package frc.team537.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DriverStation;
import frc.team537.robot.RobotMap;
import frc.team537.robot.helpers.Maths;
import frc.team537.robot.helpers.PID;

public class SwerveModule {
	public static enum SwerveMode {
		ModeSpeed(ControlMode.PercentOutput, RobotMap.PIDs.DRIVE_MODE_SPEED), 
		ModeRate(ControlMode.Velocity, RobotMap.PIDs.DRIVE_MODE_RATE), 
		ModeDistance(ControlMode.Position, RobotMap.PIDs.DRIVE_MODE_DISTANCE);
		
		private final ControlMode controlMode;
		private final PID pidDrive;
		
		private SwerveMode(ControlMode controlMode, PID pidDrive) {
			this.controlMode = controlMode;
			this.pidDrive = pidDrive;
		//	SmartDashboard.putData(pidDrive);
		}
		
		public ControlMode getControlMode() {
			return controlMode;
		}

		public PID getPidDrive() {
			return pidDrive;
		}
	}
	
	private String moduleName;
	private boolean enabled;
	private TalonSRX talonAngle;
	private TalonSRX talonDrive;
	private double currentAngle;
	private double currentPosition;
	private double currentVelocity;
	private double setpointAngle;
	private double setpointDrive;
	private SwerveMode swerveMode;

	public SwerveModule(String name, boolean enabled, int portAngle, int portDrive, PID pidAngle) {
		this.moduleName = name;
		this.enabled = enabled;
		this.talonAngle = new TalonSRX(portAngle);
		this.talonDrive = new TalonSRX(portDrive);
		this.currentAngle = 0.0;
		this.currentPosition = 0.0;
		this.currentVelocity = 0.0;
		this.setpointAngle = 0.0;
		this.setpointDrive = 0.0;
		this.swerveMode = SwerveMode.ModeSpeed;
		
		if (!enabled) {
			DriverStation.reportError("Module is set to be disabled: " + name, false);
		}
		
		talonAngle.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, RobotMap.kPIDLoopIdx, RobotMap.kTimeoutMs);
		talonAngle.config_kP(RobotMap.kPIDLoopIdx, pidAngle.getP(), RobotMap.kTimeoutMs);
		talonAngle.config_kI(RobotMap.kPIDLoopIdx, pidAngle.getI(), RobotMap.kTimeoutMs);
		talonAngle.config_kD(RobotMap.kPIDLoopIdx, pidAngle.getD(), RobotMap.kTimeoutMs);
		talonAngle.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 10, RobotMap.kTimeoutMs);
		talonAngle.enableCurrentLimit(false);
		talonAngle.configPeakCurrentDuration(0, RobotMap.kTimeoutMs); // 10
		talonAngle.configPeakCurrentLimit(0, RobotMap.kTimeoutMs); // 30
		
		talonDrive.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, RobotMap.kPIDLoopIdx, RobotMap.kTimeoutMs);
		talonDrive.setSensorPhase(false); // true
		talonDrive.configClosedloopRamp(0.0, RobotMap.kTimeoutMs); // 0.08
		talonDrive.configOpenloopRamp(0.0, RobotMap.kTimeoutMs); // 0.08
		talonDrive.enableCurrentLimit(false);
		talonDrive.configPeakCurrentDuration(0, RobotMap.kTimeoutMs); // 10
		talonDrive.configPeakCurrentLimit(0, RobotMap.kTimeoutMs); // 40
		
	//	int absolutePosition = talonAngle.getSensorCollection().getPulseWidthPosition() & 0xFFF; 
	//	talonAngle.getSensorCollection().setPulseWidthPosition(absolutePosition, RobotMap.kTimeoutMs); // talonAngle.setEncPosition(absolutePosition);
	}

	public void dashboard() {
	//	SmartDashboard.putNumber(moduleName + " Angle Setpoint", setpointAngle);
	//	SmartDashboard.putNumber(moduleName + " Angle (deg)", currentAngle);

	//	SmartDashboard.putNumber(moduleName + " Drive Setpoint", setpointDrive);
	//	SmartDashboard.putNumber(moduleName + " Drive (m)", currentPosition / RobotMap.Digital.DRIVE_M_TO_ENCODER);
	//	SmartDashboard.putNumber(moduleName + " Rate (m/s)", currentVelocity / RobotMap.Digital.DRIVE_M_TO_ENCODER);
	}

	public void setTarget(double angle, double drive, boolean driverControl) {
		if (!enabled) {
			return;
		}
		
		currentAngle = talonAngle.getSelectedSensorPosition(RobotMap.kPIDLoopIdx);
		currentPosition = talonDrive.getSelectedSensorPosition(RobotMap.kPIDLoopIdx);
		currentVelocity = talonDrive.getSelectedSensorVelocity(RobotMap.kPIDLoopIdx);

		setpointAngle = -angle;
		setpointDrive = drive;
		
		setpointAngle = 4096.0 * (setpointAngle / 360.0);
		double angleError = currentAngle - setpointAngle;
		
		if (angleError < -2048.0) {
			setpointAngle -= 4096.0;
		} else if (angleError > 2048.0) {
			setpointAngle += 4096.0;
		}
		
		if (!driverControl || angle != 0.0) {
			talonAngle.set(ControlMode.Position, setpointAngle);
		}
		
		talonDrive.set(swerveMode.getControlMode(), setpointDrive);
	}
	
	public SwerveMode getMode() {
		return this.swerveMode;
	}
	
	public void setMode(SwerveMode swerveMode) {
		talonDrive.config_kP(RobotMap.kPIDLoopIdx, swerveMode.getPidDrive().getP(), RobotMap.kTimeoutMs);
		talonDrive.config_kI(RobotMap.kPIDLoopIdx, swerveMode.getPidDrive().getI(), RobotMap.kTimeoutMs);
		talonDrive.config_kD(RobotMap.kPIDLoopIdx, swerveMode.getPidDrive().getD(), RobotMap.kTimeoutMs);
		talonDrive.config_kF(RobotMap.kPIDLoopIdx, swerveMode.getPidDrive().getF(), RobotMap.kTimeoutMs);

		this.swerveMode = swerveMode;
	}
	
	public void resetAngleReading() {
		talonAngle.setSelectedSensorPosition(0, RobotMap.kPIDLoopIdx, RobotMap.kTimeoutMs);
	}
	
	public double getSetpointDrive() {
		return setpointDrive;
	}
	
	public boolean isAtTarget() {
		if (!enabled) {
			return true;
		}
		
		switch (swerveMode) {
			case ModeSpeed:
				return true;
			case ModeRate:
				return Maths.nearTarget((double) talonDrive.getSelectedSensorVelocity(RobotMap.kPIDLoopIdx), setpointDrive, 0.06 * RobotMap.Robot.DRIVE_M_TO_ENCODER);
			case ModeDistance:
				return Maths.nearTarget((double) talonDrive.getSelectedSensorPosition(RobotMap.kPIDLoopIdx), setpointDrive, 0.08 * RobotMap.Robot.DRIVE_M_TO_ENCODER);
			default:
				return true;
		}
	}
	
	public boolean isAtAngle(double error) {
		if (!enabled) {
			return true;
		}
		
		return Maths.nearTarget((double) talonAngle.getSelectedSensorPosition(RobotMap.kPIDLoopIdx), setpointAngle, 4096.0 * (error / 360.0));
	}

	public void reset() {
		talonDrive.setSelectedSensorPosition(0, RobotMap.kPIDLoopIdx, RobotMap.kTimeoutMs);
		stop();
	}

	public void stop() {
		talonDrive.set(ControlMode.PercentOutput, 0.0);
		swerveMode = SwerveMode.ModeSpeed;
	}
}
