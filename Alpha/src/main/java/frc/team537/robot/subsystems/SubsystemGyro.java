package frc.team537.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team537.robot.commands.CommandGyroReset;
import frc.team537.robot.helpers.Maths;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A subsystem used for managing a AHRS gyroscope and accelerometer.
 * This subsystem can be used as a {@link PIDSource} where {@link SubsystemGyro#pidGet} will get the angle in degrees.
 */
public class SubsystemGyro extends Subsystem implements PIDSource {
	private AHRS ahrs;
	
	public SubsystemGyro() {
		setName("Gyro");
		
		try {
			ahrs = new AHRS(SPI.Port.kMXP);
		} catch (RuntimeException e) {
			DriverStation.reportError("Error instantiating navX MXP: " + e.getMessage(), true);
		}
		
		Timer timerDashboard = new Timer();
		timerDashboard.schedule(new TimerTask() {
			@Override
			public void run() {
				SmartDashboard.putNumber("NavX Angle", Maths.roundToPlace(getAngle(), 3));
			}
		}, 0, 100);
		
		reset();
	}

	@Override
	protected void initDefaultCommand() {
		SmartDashboard.putData("Gyro Reset", new CommandGyroReset(0.0));
	}

	/**
	 * Gets the current yaw angle in degrees. This angle is fixed between 0-360.
	 *
	 * @return The angle in degrees.
	 */
	public double getAngle() {
		return Maths.normalizeAngle(ahrs.getAngle());
	}

	/**
	 * Sets the angle currently read, this can be used to 'recalibrate' the device.
	 *
	 * @param angle The angle in degrees.
	 */
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
