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
import frc.robot.commands.DriveArcade;
import frc.robot.commands.DriveReset;

public class Drivetrain extends Subsystem implements PIDOutput {
	public static enum SwerveMode {
		ModeSpeed(ControlMode.PercentOutput, RobotMap.PIDs.DRIVE_MODE_SPEED), 
		ModeRate(ControlMode.Velocity, RobotMap.PIDs.DRIVE_MODE_RATE), 
		ModeDistance(ControlMode.Position, RobotMap.PIDs.DRIVE_MODE_DISTANCE);
		
		private final ControlMode m_controlMode;
		private PID m_pidDrive;
		private boolean m_changed;
		
		private SwerveMode(ControlMode controlMode, PID pidDrive) {
			m_controlMode = controlMode;
			m_pidDrive = pidDrive;
			m_changed = true;
		//	SmartDashboard.putData(pidDrive);
		}
		
		public ControlMode getControlMode() {
			return m_controlMode;
		}

		public PID getPidDrive() {
			return m_pidDrive;
		}

		public void setPidDrive(PID pidDrive) {
			m_pidDrive = pidDrive;
			m_changed = true;
		}

		public boolean isChanged() {
			return m_changed;
		}

		public void setChanged(boolean changed) {
			m_changed = changed;
		}
	}

	public class SwerveModule {
		private String m_name;
		private boolean m_enabled;
		private WPI_TalonSRX m_talonAngle;
		private WPI_TalonSRX m_talonDrive;
		private double m_currentAngle;
		private double m_currentPosition;
		private double m_currentVelocity;
		private double m_setpointAngle;
		private double m_setpointDrive;
		private SwerveMode m_swerveMode;

		public SwerveModule(String name, boolean enabled, int portAngle, int portDrive, PID pidAngle) {
			m_name = name;
			m_enabled = enabled;
			m_talonAngle = new WPI_TalonSRX(portAngle);
			m_talonDrive = new WPI_TalonSRX(portDrive);
			m_currentAngle = 0.0;
			m_currentPosition = 0.0;
			m_currentVelocity = 0.0;
			m_setpointAngle = 0.0;
			m_setpointDrive = 0.0;
			m_swerveMode = SwerveMode.ModeSpeed;

			if (!enabled) {
				DriverStation.reportError("Module is set to be disabled: " + m_name, false);
			}
			
			m_talonAngle.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, RobotMap.kPIDLoopIdx, RobotMap.kTimeoutMs);
			m_talonAngle.config_kP(RobotMap.kPIDLoopIdx, pidAngle.getP(), RobotMap.kTimeoutMs);
			m_talonAngle.config_kI(RobotMap.kPIDLoopIdx, pidAngle.getI(), RobotMap.kTimeoutMs);
			m_talonAngle.config_kD(RobotMap.kPIDLoopIdx, pidAngle.getD(), RobotMap.kTimeoutMs);
			m_talonAngle.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 10, RobotMap.kTimeoutMs);
			m_talonAngle.enableCurrentLimit(false);
			m_talonAngle.configPeakCurrentDuration(0, RobotMap.kTimeoutMs); // 10
			m_talonAngle.configPeakCurrentLimit(0, RobotMap.kTimeoutMs); // 30
			
			m_talonDrive.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, RobotMap.kPIDLoopIdx, RobotMap.kTimeoutMs);
			m_talonDrive.setSensorPhase(false); // true
			m_talonDrive.configClosedloopRamp(0.0, RobotMap.kTimeoutMs); // 0.08
			m_talonDrive.configOpenloopRamp(0.0, RobotMap.kTimeoutMs); // 0.08
			m_talonDrive.enableCurrentLimit(false);
			m_talonDrive.configPeakCurrentDuration(0, RobotMap.kTimeoutMs); // 10
			m_talonDrive.configPeakCurrentLimit(0, RobotMap.kTimeoutMs); // 40
			
		//	int absolutePosition = m_talonAngle.getSensorCollection().getPulseWidthPosition() & 0xFFF; 
		//	m_talonAngle.getSensorCollection().setPulseWidthPosition(absolutePosition, RobotMap.kTimeoutMs); // talonAngle.setEncPosition(absolutePosition);
		}

		public void setTarget(double angle, double drive, boolean driverControl) {
			if (!m_enabled) {
				return;
			}

			if (m_swerveMode.isChanged()) {
				setDrivePID(m_swerveMode.getPidDrive());
				m_swerveMode.setChanged(false);
			}
			
			// Gets the sensor values.
			m_currentAngle = m_talonAngle.getSelectedSensorPosition(RobotMap.kPIDLoopIdx);
			m_currentPosition = m_talonDrive.getSelectedSensorPosition(RobotMap.kPIDLoopIdx);
			m_currentVelocity = m_talonDrive.getSelectedSensorVelocity(RobotMap.kPIDLoopIdx);
	
			// Sets the setpoint, on 537 swerve angles are negated.
			m_setpointAngle = -angle;
			m_setpointDrive = drive;
			
			// Calculates the setpoint in encoder ticks.
			m_setpointAngle = 4096.0 * (m_setpointAngle / 360.0);
			double angleError = m_currentAngle - m_setpointAngle;
			
			// If the setpoint error is half a rotation ahead or behind modify range for closer setpoint.
			if (angleError < -2048.0) {
				m_setpointAngle -= 4096.0;
			} else if (angleError > 2048.0) {
				m_setpointAngle += 4096.0;
			}
			
			// If the driver lets go of the control don't set angle, 0.0 will be imposible to reach on a controller.
			if (!driverControl || angle != 0.0) {
				m_talonAngle.set(ControlMode.Position, m_setpointAngle);
			}
			
			m_talonDrive.set(m_swerveMode.getControlMode(), m_setpointDrive);
		}

		public String getName() {
			return m_name;
		}
		
		public double getAngle() {
			return 360.0 * (m_currentAngle / 4096.0); // degrees
		}

		public double getPosition() {
			return m_currentPosition; // ticks
		}

		public double getVelocity() {
			return m_currentVelocity; // ticks/s
		}

		public SwerveMode getMode() {
			return m_swerveMode;
		}

		public void setAnglePID(PID pid) {
			m_talonAngle.config_kP(RobotMap.kPIDLoopIdx, pid.getP(), RobotMap.kTimeoutMs);
			m_talonAngle.config_kI(RobotMap.kPIDLoopIdx, pid.getI(), RobotMap.kTimeoutMs);
			m_talonAngle.config_kD(RobotMap.kPIDLoopIdx, pid.getD(), RobotMap.kTimeoutMs);
			m_talonAngle.config_kF(RobotMap.kPIDLoopIdx, pid.getF(), RobotMap.kTimeoutMs);
		}
		
		public void setDrivePID(PID pid) {
			m_talonDrive.config_kP(RobotMap.kPIDLoopIdx, pid.getP(), RobotMap.kTimeoutMs);
			m_talonDrive.config_kI(RobotMap.kPIDLoopIdx, pid.getI(), RobotMap.kTimeoutMs);
			m_talonDrive.config_kD(RobotMap.kPIDLoopIdx, pid.getD(), RobotMap.kTimeoutMs);
			m_talonDrive.config_kF(RobotMap.kPIDLoopIdx, pid.getF(), RobotMap.kTimeoutMs);
		}
		
		public void setMode(SwerveMode swerveMode) {
			m_swerveMode = swerveMode;
			setDrivePID(m_swerveMode.getPidDrive());
		}
		
		public void resetAngleReading() {
			m_talonAngle.setSelectedSensorPosition(0, RobotMap.kPIDLoopIdx, RobotMap.kTimeoutMs);
		}
		
		public double getSetpointDrive() {
			return m_setpointDrive;
		}
		
		public boolean isAtTarget() {
			if (!m_enabled) {
				return true;
			}
			
			switch (m_swerveMode) {
				case ModeSpeed:
					return true; // Speed is a setpoint value and cannot be measured from the module.
				case ModeRate:
					return Maths.nearTarget((double) m_talonDrive.getSelectedSensorVelocity(RobotMap.kPIDLoopIdx), m_setpointDrive, 0.06 * RobotMap.Robot.DRIVE_M_TO_ENCODER);
				case ModeDistance:
					return Maths.nearTarget((double) m_talonDrive.getSelectedSensorPosition(RobotMap.kPIDLoopIdx), m_setpointDrive, 0.08 * RobotMap.Robot.DRIVE_M_TO_ENCODER);
				default:
					return true;
			}
		}
		
		public boolean isAtAngle(double error) {
			if (!m_enabled) {
				return true;
			}
			
			// Gets if the error is within the bounds provided.
			return Maths.nearTarget((double) m_talonAngle.getSelectedSensorPosition(RobotMap.kPIDLoopIdx), m_setpointAngle, 4096.0 * (error / 360.0));
		}

		public void reset() {
			m_talonDrive.setSelectedSensorPosition(0, RobotMap.kPIDLoopIdx, RobotMap.kTimeoutMs);
			stop();
		}
	
		public void stop() {
			m_talonDrive.set(ControlMode.PercentOutput, 0.0);
			m_swerveMode = SwerveMode.ModeSpeed;
		}
	}
	
	public SwerveModule m_frontLeft = new SwerveModule(
		"Front Left", true, 
		RobotMap.CAN.DRIVE_FRONT_LEFT_ANGLE, RobotMap.CAN.DRIVE_FRONT_LEFT_DRIVE,
		RobotMap.PIDs.DRIVE_ANGLE_FRONT_LEFT
	);
	public SwerveModule m_frontRight = new SwerveModule(
		"Front Right", true, 
		RobotMap.CAN.DRIVE_FRONT_RIGHT_ANGLE, RobotMap.CAN.DRIVE_FRONT_RIGHT_DRIVE,
		RobotMap.PIDs.DRIVE_ANGLE_FRONT_RIGHT
	);
	public SwerveModule m_backLeft = new SwerveModule(
		"Back Left", true, 
		RobotMap.CAN.DRIVE_BACK_LEFT_ANGLE, RobotMap.CAN.DRIVE_BACK_LEFT_DRIVE,
		RobotMap.PIDs.DRIVE_ANGLE_BACK_LEFT
	);
	public SwerveModule m_backRight = new SwerveModule(
		"Back Right", true, 
		RobotMap.CAN.DRIVE_BACK_RIGHT_ANGLE, RobotMap.CAN.DRIVE_BACK_RIGHT_DRIVE,
		RobotMap.PIDs.DRIVE_ANGLE_BACK_RIGHT
	);
	private SwerveMode m_swerveMode;
	private PIDController m_controllerRotate;

	public Drivetrain() {
		m_swerveMode = SwerveMode.ModeSpeed;
		m_controllerRotate = new PIDController(RobotMap.PIDs.DRIVE_ROTATE.getP(), RobotMap.PIDs.DRIVE_ROTATE.getI(), RobotMap.PIDs.DRIVE_ROTATE.getD(),
			Robot.m_gyro, this);
		m_controllerRotate.setInputRange(0.0, 360.0);
		m_controllerRotate.setOutputRange(-0.5, 0.5);
		m_controllerRotate.setPercentTolerance(0.07);
		m_controllerRotate.setContinuous();
		m_controllerRotate.disable();
		
		DriverStation.reportError("Is FMS Attached: " + DriverStation.getInstance().isFMSAttached(), false);

		if (DriverStation.getInstance().isFMSAttached()) {
			recalibrate();
		}
	}

	@Override
	public void initDefaultCommand() {
		SmartDashboard.putData("Drive Reset", new DriveReset());

		setDefaultCommand(new DriveArcade());
	}
	
	public void setTarget(double gyro, double rotation, double strafe, double forward) {
		if (m_controllerRotate.isEnabled()) {
			rotation = m_controllerRotate.get();
		}

		boolean driverControl = isDriverControl();
		double fwd2 = (forward * Math.cos(gyro)) + strafe * Math.sin(gyro);
		double str2 = (-forward * Math.sin(gyro)) + strafe * Math.cos(gyro);

		double r = RobotMap.Robot.RATIO / 2.0;
		double a = str2 - rotation * ((RobotMap.Robot.DEPTH / r) * 0.5);
		double b = str2 + rotation * ((RobotMap.Robot.DEPTH / r) * 0.5);
		double c = fwd2 - rotation * ((RobotMap.Robot.WIDTH / r) * 0.5);
		double d = fwd2 + rotation * ((RobotMap.Robot.WIDTH / r) * 0.5);

		double fls = Math.sqrt((a * a) + (c * c));
		double frs = Math.sqrt((b * b) + (c * c));
		double bls = Math.sqrt((a * a) + (d * d));
		double brs = Math.sqrt((b * b) + (d * d));
		
		double fla = Math.atan2(b, d) * (180.0 / Math.PI);
		double fra = Math.atan2(b, c) * (180.0 / Math.PI);
		double bla = Math.atan2(a, d) * (180.0 / Math.PI);
		double bra = Math.atan2(a, c) * (180.0 / Math.PI);

		double maxSpeed = Maths.maxValue(fls, frs, bls, brs);

		if (maxSpeed > 1.0) {
			fls /= maxSpeed;
			frs /= maxSpeed;
			bls /= maxSpeed;
			brs /= maxSpeed;
		}
		
		if ((driverControl && !isAtAngle(60.0)) || (!driverControl && !isAtAngle(8.0))) {
			fls = 0.0;
			frs = 0.0;
			bls = 0.0;
			brs = 0.0;
		}

		m_frontLeft.setTarget(fla, fls * RobotMap.Robot.DRIVE_SPEED, driverControl);
		m_frontRight.setTarget(fra, frs * RobotMap.Robot.DRIVE_SPEED, driverControl);
		m_backLeft.setTarget(bla, bls * RobotMap.Robot.DRIVE_SPEED, driverControl);
		m_backRight.setTarget(bra, brs * RobotMap.Robot.DRIVE_SPEED, driverControl);
	}

	public void setTarget(double gyro, double angle, double forward) {
		double f = Maths.wrapDegrees(angle - gyro);
		
		//if (!isAtAngle(8.0)) {
		//	forward = 0.0;
		//}
		
		m_frontRight.setTarget(f, forward, false);
		m_frontLeft.setTarget(f, forward, false);
		m_backLeft.setTarget(f, forward, false);
		m_backRight.setTarget(f, forward, false);
	}

	@Override
	public void pidWrite(double output) {
		// Ignored, commands will set the drivetrain targets.
	}

	public SwerveModule getModule(int index) {
		switch (index) {
			case 0:
				return m_frontLeft;
			case 1:
				return m_frontRight;
			case 2:
				return m_backLeft;
			case 3:
				return m_backRight;
			default:
				System.out.println("Index " + index + " is out of drive modules range");
				return null;
		}
	}

	public PID getModulePID(int index) {
		switch (index) {
			case 0:
				return RobotMap.PIDs.DRIVE_ANGLE_FRONT_LEFT;
			case 1:
				return RobotMap.PIDs.DRIVE_ANGLE_FRONT_RIGHT;
			case 2:
				return RobotMap.PIDs.DRIVE_ANGLE_BACK_LEFT;
			case 3:
				return RobotMap.PIDs.DRIVE_ANGLE_BACK_RIGHT;
			default:
				System.out.println("Index " + index + " is out of drive modules range");
				return null;
		}
	}

	public SwerveMode getMode() {
		return m_swerveMode;
	}
	
	public void setMode(SwerveMode swerveMode) {
		m_frontRight.setMode(swerveMode);
		m_frontLeft.setMode(swerveMode);
		m_backLeft.setMode(swerveMode);
		m_backRight.setMode(swerveMode);
		m_swerveMode = swerveMode;
	}

	public PIDController getControllerRotate() {
		return m_controllerRotate;
	}
	
	public void setControllerRotate(double setpoint) {
		if (!m_controllerRotate.isEnabled()) {
			m_controllerRotate.reset();
			m_controllerRotate.enable();
		}
		
		m_controllerRotate.setPID(RobotMap.PIDs.DRIVE_ROTATE.getP(), RobotMap.PIDs.DRIVE_ROTATE.getI(), RobotMap.PIDs.DRIVE_ROTATE.getD());
		m_controllerRotate.setSetpoint(setpoint);
	}
	
	public void recalibrate() {
		m_backLeft.resetAngleReading();
		m_backRight.resetAngleReading();
		m_frontLeft.resetAngleReading();
		m_frontRight.resetAngleReading();
	}

	public boolean isAtTarget() {
		return m_frontRight.isAtTarget() && m_frontLeft.isAtTarget() && m_backLeft.isAtTarget() && m_backRight.isAtTarget();
	}
	
	public boolean isAtAngle(double error) {
		return m_frontRight.isAtAngle(error) && m_frontLeft.isAtAngle(error) && m_backLeft.isAtAngle(error) && m_backRight.isAtAngle(error);
	}
	
	public boolean isDriverControl() {
		return m_frontRight.getMode() == SwerveMode.ModeSpeed;
	}
	
	public double getAverageSpeed() {
		return (m_frontRight.getSetpointDrive() + m_frontLeft.getSetpointDrive() + m_backLeft.getSetpointDrive() + m_backRight.getSetpointDrive()) / 4.0;
	}
	
	public void reset() {
		m_backLeft.reset();
		m_backRight.reset();
		m_frontLeft.reset();
		m_frontRight.reset();
	}
	
	public void stop() {
		m_controllerRotate.disable();
		m_backLeft.stop();
		m_backRight.stop();
		m_frontLeft.stop();
		m_frontRight.stop();
	}
}
