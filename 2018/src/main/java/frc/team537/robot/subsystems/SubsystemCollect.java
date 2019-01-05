package frc.team537.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team537.robot.RobotMap;
import frc.team537.robot.commands.CommandCollectDefault;

public class SubsystemCollect extends Subsystem {
	private VictorSPX talonCollectLeft = new VictorSPX(RobotMap.CAN.COLLECT_LEFT);
	private VictorSPX talonCollectRight = new VictorSPX(RobotMap.CAN.COLLECT_RIGHT);
	
	public SubsystemCollect() {
		setName("Collect");
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new CommandCollectDefault());
	}

	public void setSpeed(double speed) {
		talonCollectLeft.set(ControlMode.PercentOutput, speed);
		talonCollectRight.set(ControlMode.PercentOutput, -speed);
	}
	
	public void reset() {
		stop();
	}
	
	public void stop() {
		talonCollectLeft.set(ControlMode.PercentOutput, 0.0);
		talonCollectRight.set(ControlMode.PercentOutput, 0.0);
	}
}
