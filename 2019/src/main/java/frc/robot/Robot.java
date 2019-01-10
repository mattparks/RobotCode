package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.DrivePath;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Gyro;
import frc.robot.subsystems.Lights;

public class Robot extends TimedRobot {
	public static Gyro m_gyro = new Gyro();
	public static Lights m_lights = null; // new Lights();
	public static Drivetrain m_drivetrain = new Drivetrain();
	public static OI m_oi;

	private SendableChooser<String> m_chooserPath = new SendableChooser<>();
	private Command m_autonomousCommand;

	@Override
	public void robotInit() {
		m_oi = new OI();
		m_chooserPath.setDefaultOption("None", null);
		m_chooserPath.addOption("Left Hatch 1", "LeftHatch1/LeftHatch1.csv");
		m_chooserPath.addOption("Left Hatch 2", "LeftHatch1/LeftHatch2.csv");
		m_chooserPath.addOption("Left Hatch 3", "LeftHatch1/LeftHatch3.csv");
		SmartDashboard.putData("Auto Path", m_chooserPath);
	}

	@Override
	public void robotPeriodic() {
	}

	@Override
	public void disabledInit() {
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		String filename = m_chooserPath.getSelected();

		if (filename != null) {
			m_autonomousCommand = new DrivePath(filename);
			m_autonomousCommand.start();
		}
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() {
	}
}
