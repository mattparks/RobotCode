package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotMap;
import frc.robot.commands.DriveCurvature;

public class Drivetrain extends Subsystem {
	private Talon m_leftMasterTalon = null;
	private Talon m_leftNormalTalon = null;
	private Talon m_leftMiniTalon = null;
	private Talon m_rightMasterTalon = null;
	private Talon m_rightNormalTalon = null;
	private Talon m_rightMiniTalon = null;
  private DifferentialDrive m_differentialDrive = null;
  
	public Drivetrain() {
		m_leftMasterTalon = new Talon(RobotMap.CAN.DRIVE_LEFT_MASTER);
		m_leftNormalTalon = new Talon(RobotMap.CAN.DRIVE_LEFT_NORMAL);
		m_leftMiniTalon = new Talon(RobotMap.CAN.DRIVE_LEFT_MINI);
		m_rightMasterTalon = new Talon(RobotMap.CAN.DRIVE_RIGHT_MASTER);
		m_rightNormalTalon = new Talon(RobotMap.CAN.DRIVE_RIGHT_NORMAL);
		m_rightMiniTalon = new Talon(RobotMap.CAN.DRIVE_RIGHT_MINI);

		SpeedControllerGroup leftMotors = new SpeedControllerGroup(m_leftMasterTalon, m_leftNormalTalon, m_leftMiniTalon);
		SpeedControllerGroup rightMotors = new SpeedControllerGroup(m_rightMasterTalon, m_rightNormalTalon, m_rightMiniTalon);

		m_differentialDrive = new DifferentialDrive(leftMotors, rightMotors);
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
