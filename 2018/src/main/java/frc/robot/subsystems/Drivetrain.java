package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Maths;
import frc.robot.PID;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.DriveSwerve;
import frc.robot.commands.DriveReset;

public class Drivetrain extends Subsystem implements PIDOutput {
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

	public class SwerveModule {
		private String moduleName;
		private boolean enabled;
		private WPI_TalonSRX talonAngle;
		private WPI_TalonSRX talonDrive;
		private double currentAngle;
		private double currentPosition;
		private double currentVelocity;
		private double setpointAngle;
		private double setpointDrive;
		private SwerveMode swerveMode;

		public SwerveModule(String name, boolean enabled, int portAngle, int portDrive, PID pidAngle) {
			this.moduleName = name;
			this.enabled = enabled;
			this.talonAngle = new WPI_TalonSRX(portAngle);
			this.talonDrive = new WPI_TalonSRX(portDrive);
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
					return nearTarget((double) talonDrive.getSelectedSensorVelocity(RobotMap.kPIDLoopIdx), setpointDrive, 0.06 * RobotMap.Robot.DRIVE_M_TO_ENCODER);
				case ModeDistance:
					return nearTarget((double) talonDrive.getSelectedSensorPosition(RobotMap.kPIDLoopIdx), setpointDrive, 0.08 * RobotMap.Robot.DRIVE_M_TO_ENCODER);
				default:
					return true;
			}
		}
		
		public boolean isAtAngle(double error) {
			if (!enabled) {
				return true;
			}
			
			return nearTarget((double) talonAngle.getSelectedSensorPosition(RobotMap.kPIDLoopIdx), setpointAngle, 4096.0 * (error / 360.0));
		}
	
		private boolean nearTarget(double value, double target, double tolerance) {
			return Math.abs(value - target) < tolerance;
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
	
	private SwerveModule frontLeft = new SwerveModule(
		"Front Left", RobotMap.Robot.DRIVE_ENABLED_FRONT_LEFT, 
		RobotMap.CAN.DRIVE_FRONT_LEFT_ANGLE, RobotMap.CAN.DRIVE_FRONT_LEFT_DRIVE,
		RobotMap.PIDs.DRIVE_ANGLE_FRONT_LEFT
	);
	private SwerveModule frontRight = new SwerveModule(
		"Front Right", RobotMap.Robot.DRIVE_ENABLED_FRONT_RIGHT, 
		RobotMap.CAN.DRIVE_FRONT_RIGHT_ANGLE, RobotMap.CAN.DRIVE_FRONT_RIGHT_DRIVE,
		RobotMap.PIDs.DRIVE_ANGLE_FRONT_RIGHT
	);
	private SwerveModule backLeft = new SwerveModule(
		"Back Left", RobotMap.Robot.DRIVE_ENABLED_BACK_LEFT, 
		RobotMap.CAN.DRIVE_BACK_LEFT_ANGLE, RobotMap.CAN.DRIVE_BACK_LEFT_DRIVE,
		RobotMap.PIDs.DRIVE_ANGLE_BACK_LEFT
	);
	private SwerveModule backRight = new SwerveModule(
		"Back Right", RobotMap.Robot.DRIVE_ENABLED_BACK_RIGHT, 
		RobotMap.CAN.DRIVE_BACK_RIGHT_ANGLE, RobotMap.CAN.DRIVE_BACK_RIGHT_DRIVE,
		RobotMap.PIDs.DRIVE_ANGLE_BACK_RIGHT
	);
	private PIDController controllerRotate;

	public Drivetrain() {
		this.controllerRotate = new PIDController(RobotMap.PIDs.DRIVE_ROTATE.getP(), RobotMap.PIDs.DRIVE_ROTATE.getI(), RobotMap.PIDs.DRIVE_ROTATE.getD(),
				Robot.m_gyro, this);
		this.controllerRotate.setInputRange(0.0, 360.0);
		this.controllerRotate.setOutputRange(-0.5, 0.5);
		this.controllerRotate.setPercentTolerance(0.07);
		this.controllerRotate.setContinuous();
		this.controllerRotate.disable();
		
		DriverStation.reportError("Is FMS Attached: " + DriverStation.getInstance().isFMSAttached(), false);

		if (!(RobotMap.Robot.TESTING_MODE && !DriverStation.getInstance().isFMSAttached())) {
			recalibrate();
		}
	}

	@Override
	public void initDefaultCommand() {
		SmartDashboard.putData("Drive Reset", new DriveReset());

		setDefaultCommand(new DriveSwerve());
	}
	
	public void setTarget(double gyro, double rotation, double strafe, double forward) {
		if (controllerRotate.isEnabled()) {
			rotation = controllerRotate.get();
		}

		boolean driverControl = isDriverControl();
		double fwd2 = (forward * Math.cos(gyro)) + strafe * Math.sin(gyro);
		double str2 = (-forward * Math.sin(gyro)) + strafe * Math.cos(gyro);

		double r = RobotMap.Robot.RATIO / 2.0;
		double a = str2 - rotation * ((RobotMap.Robot.LENGTH / r) * 0.5);
		double b = str2 + rotation * ((RobotMap.Robot.LENGTH / r) * 0.5);
		double c = fwd2 - rotation * ((RobotMap.Robot.WIDTH / r) * 0.5);
		double d = fwd2 + rotation * ((RobotMap.Robot.WIDTH / r) * 0.5);

		double frs = Math.sqrt((b * b) + (c * c));
		double fls = Math.sqrt((a * a) + (c * c));
		double bls = Math.sqrt((a * a) + (d * d));
		double brs = Math.sqrt((b * b) + (d * d));
		
		double fra = Math.atan2(b, c) * (180.0 / Math.PI);
		double fla = Math.atan2(b, d) * (180.0 / Math.PI);
		double bla = Math.atan2(a, d) * (180.0 / Math.PI);
		double bra = Math.atan2(a, c) * (180.0 / Math.PI);

		double maxSpeed = Maths.maxValue(frs, fls, bls, brs);

		if (maxSpeed > 1.0) {
			frs /= maxSpeed;
			fls /= maxSpeed;
			bls /= maxSpeed;
			brs /= maxSpeed;
		}
		
		if ((driverControl && !isAtAngle(60.0)) || (!driverControl && !isAtAngle(8.0))) {
			frs = 0.0;
			fls = 0.0;
			bls = 0.0;
			brs = 0.0;
		}

		frontRight.setTarget(fra, frs * RobotMap.Robot.DRIVE_SPEED, driverControl);
		frontLeft.setTarget(fla, fls * RobotMap.Robot.DRIVE_SPEED, driverControl);
		backLeft.setTarget(bla, bls * RobotMap.Robot.DRIVE_SPEED, driverControl);
		backRight.setTarget(bra, brs * RobotMap.Robot.DRIVE_SPEED, driverControl);
	}

	public void setTarget(double gyro, double angle, double forward) {
		double f = Maths.wrapDegrees(angle - gyro);
		
		//if (!isAtAngle(8.0)) {
		//	forward = 0.0;
		//}
		
		frontRight.setTarget(f, forward, false);
		frontLeft.setTarget(f, forward, false);
		backLeft.setTarget(f, forward, false);
		backRight.setTarget(f, forward, false);
	}

	@Override
	public void pidWrite(double output) {
	}

	public void setMode(SwerveMode swerveMode) {
		frontRight.setMode(swerveMode);
		frontLeft.setMode(swerveMode);
		backLeft.setMode(swerveMode);
		backRight.setMode(swerveMode);
	}

	public PIDController getControllerRotate() {
		return controllerRotate;
	}
	
	public void setControllerRotate(double setpoint) {
		controllerRotate.reset();
		controllerRotate.setSetpoint(setpoint);
		controllerRotate.enable();
	}
	
	public void recalibrate() {
		backLeft.resetAngleReading();
		backRight.resetAngleReading();
		frontLeft.resetAngleReading();
		frontRight.resetAngleReading();
	}

	public boolean isAtTarget() {
		return frontRight.isAtTarget() && frontLeft.isAtTarget() && backLeft.isAtTarget() && backRight.isAtTarget();
	}
	
	public boolean isAtAngle(double error) {
		return frontRight.isAtAngle(error) && frontLeft.isAtAngle(error) && backLeft.isAtAngle(error) && backRight.isAtAngle(error);
	}
	
	public boolean isDriverControl() {
		return frontRight.getMode() == SwerveMode.ModeSpeed;
	}
	
	public double getAverageSpeed() {
		return (frontRight.getSetpointDrive() + frontLeft.getSetpointDrive() + backLeft.getSetpointDrive() + backRight.getSetpointDrive()) / 4.0;
	}
	
	public void reset() {
		backLeft.reset();
		backRight.reset();
		frontLeft.reset();
		frontRight.reset();
	}
	
	public void stop() {
		controllerRotate.disable();
		backLeft.stop();
		backRight.stop();
		frontLeft.stop();
		frontRight.stop();
	}
}
