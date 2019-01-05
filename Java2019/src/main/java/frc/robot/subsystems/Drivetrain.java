package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotMap;
import frc.robot.commands.DriveCurvature;

public class Drivetrain extends Subsystem {
	private WPI_TalonSRX m_left0Talon = new WPI_TalonSRX(RobotMap.CAN.DRIVE_LEFT_MASTER);
	private WPI_TalonSRX m_left1Talon = new WPI_TalonSRX(RobotMap.CAN.DRIVE_LEFT_NORMAL);
	private WPI_TalonSRX m_left2Talon = new WPI_TalonSRX(RobotMap.CAN.DRIVE_LEFT_MINI);
	private WPI_TalonSRX m_right0Talon = new WPI_TalonSRX(RobotMap.CAN.DRIVE_RIGHT_MASTER);
	private WPI_TalonSRX m_right1Talon = new WPI_TalonSRX(RobotMap.CAN.DRIVE_RIGHT_NORMAL);
	private WPI_TalonSRX m_right2Talon = new WPI_TalonSRX(RobotMap.CAN.DRIVE_RIGHT_MINI);
	
  private DifferentialDrive m_differentialDrive = null;
  
	public Drivetrain() {
		m_left1Talon.set(ControlMode.Follower, RobotMap.CAN.DRIVE_LEFT_MASTER);
		m_left2Talon.set(ControlMode.Follower, RobotMap.CAN.DRIVE_LEFT_MASTER);
		m_right1Talon.set(ControlMode.Follower, RobotMap.CAN.DRIVE_RIGHT_MASTER);
		m_right2Talon.set(ControlMode.Follower, RobotMap.CAN.DRIVE_RIGHT_MASTER);

		m_differentialDrive = new DifferentialDrive(m_left0Talon, m_right0Talon);
  }

  @Override
  public void initDefaultCommand() {
		setDefaultCommand(new DriveCurvature());
  }

	public void arcadeDrive(double moveSpeed, double rotateSpeed) {
		m_differentialDrive.arcadeDrive(moveSpeed, rotateSpeed);
  }
  
  public void curvatureDrive(double xSpeed, double zRotation, boolean isQuickTurn) {
    m_differentialDrive.curvatureDrive(xSpeed, zRotation, isQuickTurn);
  }

  public void tankDrive(double leftSpeed, double rightSpeed) {
    m_differentialDrive.tankDrive(leftSpeed, rightSpeed);
  }
}
