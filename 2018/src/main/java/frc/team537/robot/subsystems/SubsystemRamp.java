package frc.team537.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team537.robot.RobotMap;
import frc.team537.robot.commands.CommandRampDefault;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A subsystem for a ramp, defined per side.
 */
public class SubsystemRamp extends Subsystem {
	/**
	 * A enum used to represent each ramp subsystem.
	 */
	public static enum RampSide {
		SideLeft("Left", RobotMap.Solenoid.RAMP_DEPLOY_LEFT, RobotMap.CAN.RAMP_LIFT_LEFT), 
		SideRight("Right", RobotMap.Solenoid.RAMP_DEPLOY_RIGHT, RobotMap.CAN.RAMP_LIFT_RIGHT);

		public final String name;
		public final int relay;
		public final int talon;
		public SubsystemRamp subsystem;

		/**
		 * Enum type that represents a ramp subsystem.
		 *
		 * @param name The sides name.
		 * @param relay The relay used on the side.
		 * @param talon The talon used on the side.
		 */
		RampSide(String name, int relay, int talon) {
			this.name = name;
			this.relay = relay;
			this.talon = talon;
			this.subsystem = null;
		}
	}

	private final RampSide side;
	private Relay deploy;
	private TalonSRX rampLift;
	private boolean deployed;
	
	public SubsystemRamp(RampSide side) {
		setName("Ramp" + side.name);
		side.subsystem = this;
		this.side = side;
		this.deploy = new Relay(side.relay);
		this.rampLift = new TalonSRX(side.talon);
		this.deployed = false;
		
		this.deploy.set(Value.kOff);

		Timer timerDashboard = new Timer();
		timerDashboard.schedule(new TimerTask() {
			@Override
			public void run() {
				dashboard();
			}
		}, 0, 100);
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new CommandRampDefault(side));
	}

	public void dashboard() {
	}
	
	public boolean isDeployed() {
		return deployed;
	}
	
	public void setDeployed(boolean toggle) {
		deployed = toggle;
		deploy.set(deployed ? Value.kForward : Value.kOff);
	}
	
	public void toggleDeployed() {
		setDeployed(!isDeployed());
	}

	public void setSpeed(double speed) {
		if (deploy.get() != Value.kForward) {
			rampLift.set(ControlMode.PercentOutput, 0.0);
			return;
		}
		
		rampLift.set(ControlMode.PercentOutput, speed);
	}

	public void reset() {
		deploy.set(Value.kOff);
		stop();
	}
	
	public void stop() {
		rampLift.set(ControlMode.PercentOutput, 0.0);
	}
}
