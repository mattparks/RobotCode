package frc.team537.robot.subsystems;

import frc.team537.robot.RobotMap;
import frc.team537.robot.commands.CommandShooterDefault;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

public class SubsystemShooter extends Subsystem {
	private TalonSRX shooterMaster = new TalonSRX(RobotMap.CAN.SHOOTER_MASTER);
	private TalonSRX shooterNormal = new TalonSRX(RobotMap.CAN.SHOOTER_NORMAL);

	public SubsystemShooter() {
		setName("Shooter");

		shooterNormal.set(ControlMode.Follower, RobotMap.CAN.SHOOTER_MASTER);
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new CommandShooterDefault());
	}

	public void setSpeed(double speed) {
		shooterMaster.set(ControlMode.PercentOutput, speed);
	}

	public void stop() {
		shooterMaster.set(ControlMode.PercentOutput, 0.0);
	}
}
