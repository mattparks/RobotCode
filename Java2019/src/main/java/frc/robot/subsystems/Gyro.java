package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Gyro extends Subsystem implements PIDSource {
	private AHRS m_ahrs;
	
	public Gyro() {
		try {
			m_ahrs = new AHRS(SPI.Port.kMXP);
			reset();
		} catch (RuntimeException e) {
			DriverStation.reportError("Error instantiating navX MXP: " + e.getMessage(), true);
		}
	}

	@Override
	protected void initDefaultCommand() {
	//	SmartDashboard.putData("Gyro Reset", new CommandGyroReset(0.0));
	}

	public double getAngle() {
		return m_ahrs.getAngle();
	}
	
	public void setAngle(double angle) {
		m_ahrs.setAngleAdjustment(angle);
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return m_ahrs.getPIDSourceType();
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		m_ahrs.setPIDSourceType(pidSource);
	}

	@Override
	public double pidGet() {
		return getAngle();
	}
	
	public void reset() {
		m_ahrs.reset();
	}
}
