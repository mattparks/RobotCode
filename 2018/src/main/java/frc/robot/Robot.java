package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Gyro;

public class Robot extends TimedRobot {
	public static Gyro m_gyro = new Gyro();
	public static Drivetrain m_drivetrain = new Drivetrain();
	public static OI m_oi;

	private SendableChooser<Command> m_chooser = new SendableChooser<>();
	private Command m_autonomousCommand;

	@Override
	public void robotInit() {
		m_oi = new OI();
		m_chooser.setDefaultOption("None", null);
		// chooser.addOption("Straight", new MyAutoCommand());
		SmartDashboard.putData("Auto Mode", m_chooser);
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
		m_autonomousCommand = m_chooser.getSelected();

		if (m_autonomousCommand != null) {
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
