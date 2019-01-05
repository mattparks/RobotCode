package frc.team537.robot.subsystems;

import frc.team537.robot.RobotMap;
import frc.team537.robot.commands.CommandFeederDefault;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

public class SubsystemFeeder extends Subsystem {
	private TalonSRX talonFeed = new TalonSRX(RobotMap.CAN.FEEDER);

	public SubsystemFeeder() {
		setName("Feeder");
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new CommandFeederDefault());
	}

	public void setSpeed(double speed) {
		talonFeed.set(ControlMode.PercentOutput, speed);
	}

	public void stop() {
		talonFeed.set(ControlMode.PercentOutput, 0.0);
	}

}
