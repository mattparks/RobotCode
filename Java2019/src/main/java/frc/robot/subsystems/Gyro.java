package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Gyro extends Subsystem implements PIDSource {
	private AHRS ahrs;
	
	public Gyro() {
		setName("Gyro");
		
		try {
			ahrs = new AHRS(SPI.Port.kMXP);
		} catch (RuntimeException e) {
			DriverStation.reportError("Error instantiating navX MXP: " + e.getMessage(), true);
		}
	}

	@Override
	protected void initDefaultCommand() {
	//	SmartDashboard.putData("Gyro Reset", new CommandGyroReset(0.0));
	}

	public double getAngle() {
		return ahrs.getAngle();
	}
	
	public void setAngle(double angle) {
		ahrs.setAngleAdjustment(angle);
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return ahrs.getPIDSourceType();
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		ahrs.setPIDSourceType(pidSource);
	}

	@Override
	public double pidGet() {
		return getAngle();
	}
	
	public void reset() {
		ahrs.reset();
	}
}
