package frc.robot.commands;

import java.io.File;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.subsystems.Drivetrain.SwerveMode;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.SwerveModifier;

public class DrivePath extends Command {
	private class ModuleControl {
		private Trajectory m_trajectory;
		private EncoderFollower m_follower;
		public double m_speed, m_angle;

		public ModuleControl(Trajectory trajectory) {
			m_trajectory = trajectory;
			m_follower = new EncoderFollower(m_trajectory);
			m_follower.configureEncoder(0, 4098, RobotMap.Robot.WHEEL_DIAMETER);
			m_follower.configurePIDVA(1.0, 0.0, 0.0, 1.0 / RobotMap.Robot.MAX_VELOCITY, 0.0);
		}

		public void calculate(double encoderPosition) {
			m_speed = m_follower.calculate((int)encoderPosition);
			m_angle = Pathfinder.boundHalfDegrees(Pathfinder.r2d(m_follower.getHeading()));
		}

		public void reset() {
			m_follower.reset();
		}

		public boolean isFinished() {
			return m_follower.isFinished();
		}
	}

	private Trajectory m_trajectory;
	private SwerveModifier m_modifier;
	
	private ModuleControl m_frontLeft;
	private ModuleControl m_frontRight;
	private ModuleControl m_backLeft;
	private ModuleControl m_backRight;

	public DrivePath(Trajectory trajectory) {
		requires(Robot.m_drivetrain);
		m_trajectory = trajectory;

		m_modifier = new SwerveModifier(m_trajectory);
		m_modifier.modify(RobotMap.Robot.WIDTH, RobotMap.Robot.DEPTH, SwerveModifier.Mode.SWERVE_DEFAULT);

		m_frontLeft = new ModuleControl(m_modifier.getFrontLeftTrajectory());
		m_frontRight = new ModuleControl(m_modifier.getFrontRightTrajectory());
		m_backLeft = new ModuleControl(m_modifier.getBackLeftTrajectory());
		m_backRight = new ModuleControl(m_modifier.getBackRightTrajectory());
	}

	public DrivePath(String filepath) {
		this(Pathfinder.readFromCSV(new File("/home/lvuser/deploy/" + filepath))); // TODO: Use FileUtilities.getFilePath();
	}

	public DrivePath(Waypoint[] points) {
		this(Pathfinder.generate(points, RobotMap.Robot.TRAJECTORY_CONFIG));
	}

	@Override
	protected void initialize() {
		Robot.m_drivetrain.reset();
		Robot.m_drivetrain.setMode(SwerveMode.ModeSpeed);
		m_frontLeft.reset();
		m_frontRight.reset();
		m_backLeft.reset();
		m_backRight.reset();
	}

	@Override
	protected void execute() {
		m_frontLeft.calculate(Robot.m_drivetrain.m_frontLeft.getPosition());
		m_frontRight.calculate(Robot.m_drivetrain.m_frontRight.getPosition());
		m_backLeft.calculate(Robot.m_drivetrain.m_backLeft.getPosition());
		m_backRight.calculate(Robot.m_drivetrain.m_backRight.getPosition());

		Robot.m_drivetrain.m_frontLeft.setTarget(m_frontLeft.m_angle, m_frontLeft.m_speed, false);
		Robot.m_drivetrain.m_frontRight.setTarget(m_frontRight.m_angle, m_frontRight.m_speed, false);
		Robot.m_drivetrain.m_backLeft.setTarget(m_backLeft.m_angle, m_backLeft.m_speed, false);
		Robot.m_drivetrain.m_backRight.setTarget(m_backRight.m_angle, m_backRight.m_speed, false);
	}

	@Override
	protected boolean isFinished() {
		return m_frontLeft.isFinished() && m_frontRight.isFinished() && m_backLeft.isFinished() && m_backRight.isFinished();
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
