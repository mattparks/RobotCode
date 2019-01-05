package frc.team537.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team537.robot.subsystems.*;

/**
 * The main entry point for the robot, holds OI, auto, and subsystems.
 */
public class Robot extends IterativeRobot {
	public static SubsystemCamera subsystemCamera;
	public static SubsystemGyro subsystemGyro;
	public static SubsystemLeds subsystemLeds;
	public static SubsystemShooter subsystemShooter;
	public static SubsystemFeeder subsystemFeeder;
	public static SubsystemDrive subsystemDrive;

	public static OI oi;

	@Override
	public void robotInit() {
		// Subsystems.
		if (RobotMap.Subsystems.CAMERA) {
			subsystemCamera = new SubsystemCamera();
		}

		subsystemGyro = new SubsystemGyro();

		if (RobotMap.Subsystems.LEDS) {
			subsystemLeds = new SubsystemLeds();
		}

		if (RobotMap.Subsystems.SHOOTER) {
			subsystemShooter = new SubsystemShooter();
		}

		if (RobotMap.Subsystems.FEEDER) {
			subsystemFeeder = new SubsystemFeeder();
		}

		if (RobotMap.Subsystems.DRIVE) {
			subsystemDrive = new SubsystemDrive();
		}

		// OI.
		oi = new OI();
	}

	@Override
	public void disabledInit() {
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void robotPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() {
	}
}
