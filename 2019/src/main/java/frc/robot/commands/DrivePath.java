package frc.robot.commands;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.PID;
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
			PID pid = RobotMap.PIDs.DRIVE_PATH;
			m_follower.configurePIDVA(pid.getP(), pid.getI(), pid.getD(), RobotMap.Robot.DRIVE_PATH_SCALE * (1.0 / RobotMap.Robot.MAX_VELOCITY), 0.0);
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
		this(readFromXML(new File("/home/lvuser/deploy/" + filepath)));
	}

	public DrivePath(Waypoint[] points) {
		this(Pathfinder.generate(points, RobotMap.Robot.TRAJECTORY_CONFIG));
	}

	private static Waypoint[] readFromXML(File file) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(file);
			document.getDocumentElement().normalize();

			// Path data defined in the XML is ignored, the path is the only data we look for.
			System.out.println("Root element: " + document.getDocumentElement().getNodeName());

			NodeList nodeList = document.getElementsByTagName("Waypoint");
			Waypoint[] waypoints = new Waypoint[nodeList.getLength()];
			
			for (int temp = 0; temp < nodeList.getLength(); temp++) {
				Node node = nodeList.item(temp);
				
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					double x = Double.parseDouble(element.getElementsByTagName("X").item(0).getTextContent());
					double y = Double.parseDouble(element.getElementsByTagName("Y").item(0).getTextContent());
					double angle = Double.parseDouble(element.getElementsByTagName("Angle").item(0).getTextContent());
					waypoints[temp] = new Waypoint(x, y, Pathfinder.d2r(angle));
				}
			}

			return waypoints;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
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
