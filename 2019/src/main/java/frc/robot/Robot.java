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

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
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
		m_chooserPath.addOption("Left Hatch 1", "LeftHatch1.xml");
		m_chooserPath.addOption("Left Hatch 2", "LeftHatch2.xml");
		m_chooserPath.addOption("Left Hatch 3", "LeftHatch3.xml");
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
		// This makes sure that the autonomous stops running when teleop starts running. 
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
