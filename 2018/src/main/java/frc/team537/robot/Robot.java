package frc.team537.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team537.robot.choosers.ChooserAuto;
import frc.team537.robot.subsystems.*;
import frc.team537.robot.subsystems.SubsystemRamp.RampSide;

/**
 * The main entry point for the robot, holds OI, auto, and subsystems.
 */
public class Robot extends IterativeRobot {
	public static SubsystemCamera subsystemCamera;
	public static SubsystemGyro subsystemGyro;
	public static SubsystemLidar subsystemLidar;
	public static SubsystemLeds subsystemLeds;
	public static SubsystemCollect subsystemCollect;
	public static SubsystemDrive subsystemDrive;
	public static SubsystemRamp subsystemRampLeft;
	public static SubsystemRamp subsystemRampRight;
	public static SubsystemLift subsystemLift;

	public static OI oi;

	private ChooserAuto autoChooser;
	private Command autoCommand;

	@Override
	public void robotInit() {
		// Subsystems.
		if (RobotMap.Subsystems.CAMERA) {
			subsystemCamera = new SubsystemCamera();
		}

		subsystemGyro = new SubsystemGyro();

		if (RobotMap.Subsystems.LIDAR) {
			subsystemLidar = new SubsystemLidar();
		}

		if (RobotMap.Subsystems.LEDS) {
			subsystemLeds = new SubsystemLeds();
		}

		if (RobotMap.Subsystems.COLLECT) {
			subsystemCollect = new SubsystemCollect();
		}

		if (RobotMap.Subsystems.RAMPS_LEFT) {
			subsystemRampLeft = new SubsystemRamp(RampSide.SideLeft);
		}

		if (RobotMap.Subsystems.RAMPS_RIGHT) {
			subsystemRampRight = new SubsystemRamp(RampSide.SideRight);
		}

		if (RobotMap.Subsystems.LIFT) {
			subsystemLift = new SubsystemLift();
		}

		if (RobotMap.Subsystems.DRIVE) {
			subsystemDrive = new SubsystemDrive();
		}

		// OI.
		oi = new OI();

		// Autonomous chooser to display on the dashboard.
		autoChooser = new ChooserAuto();
	}

	@Override
	public void disabledInit() {
		if (autoCommand != null) {
			autoCommand.cancel();
		}
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
		autoCommand = autoChooser.getSelected();

		if (autoCommand != null) {
			autoCommand.start();
		}
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		if (autoCommand != null) {
			autoCommand.cancel();
			autoCommand = null;
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
