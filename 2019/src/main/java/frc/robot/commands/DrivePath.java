package frc.robot.commands;

import java.io.File;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.subsystems.Drivetrain.SwerveMode;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.SwerveModifier;

public class DrivePath extends Command {
	private Trajectory m_trajectory;
	private SwerveModifier m_modifier;

	public DrivePath(Trajectory trajectory) {
		requires(Robot.m_drivetrain);
		m_trajectory = trajectory;
		m_modifier = new SwerveModifier(m_trajectory).modify(RobotMap.Robot.WIDTH, RobotMap.Robot.DEPTH, SwerveModifier.Mode.SWERVE_DEFAULT);
	}

	public DrivePath(String filepath) {
		requires(Robot.m_drivetrain);
		m_trajectory = Pathfinder.readFromFile(new File("/home/lvuser/deploy/" + filepath)); //	FileUtilities.getFilePath();
		m_modifier = new SwerveModifier(m_trajectory).modify(RobotMap.Robot.WIDTH, RobotMap.Robot.DEPTH, SwerveModifier.Mode.SWERVE_DEFAULT);
	}

	public DrivePath(Waypoint[] points) {
		requires(Robot.m_drivetrain);
		m_trajectory = Pathfinder.generate(points, RobotMap.Robot.TRAJECTORY_CONFIG);
		m_modifier = new SwerveModifier(m_trajectory).modify(RobotMap.Robot.WIDTH, RobotMap.Robot.DEPTH, SwerveModifier.Mode.SWERVE_DEFAULT);
	}

	@Override
	protected void initialize() {
		Robot.m_drivetrain.reset();
		Robot.m_drivetrain.setMode(SwerveMode.ModeDistance);
	}

	@Override
	protected void execute() {
        Trajectory fl = m_modifier.getFrontLeftTrajectory();
        Trajectory fr = m_modifier.getFrontRightTrajectory();
        Trajectory bl = m_modifier.getBackLeftTrajectory();
        Trajectory br = m_modifier.getBackRightTrajectory();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.m_drivetrain.stop();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
