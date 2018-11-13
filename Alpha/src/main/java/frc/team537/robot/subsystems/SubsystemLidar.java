package frc.team537.robot.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team537.robot.commands.CommandGyroReset;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A subsystem used for managing a LIDAR.
 */
public class SubsystemLidar extends Subsystem implements PIDSource {
	private I2C lidar;
	private boolean connected;
	private double range;

	private Thread thread;

	public SubsystemLidar() {
		setName("Lidar");


		this.lidar = new I2C(I2C.Port.kOnboard, 0x30);
		this.connected = false;
		this.range = 0.0;

		this.thread = new Thread(this::threadRun);
		this.thread.start();

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
		SmartDashboard.putData("Gyro Reset", new CommandGyroReset(0.0));
	}

	private void dashboard() {
		SmartDashboard.putBoolean("Lidar Connected", connected);
		SmartDashboard.putNumber("Lidar Range (M)", range);
	}

	private void threadRun() {
		byte[] buffer = new byte[3];
		System.out.println("RoboRio-537 lidar init");
		boolean found = false;

		while (!found) {
			lidar.write(0x60, 0x01);
			lidar.read(0x61, 3, buffer);
			found = ((byte) 0xa1 == buffer[0]);
			System.out.println(String.format("Rangefinder found %s val 0x%2x", (found ? "true" : "false"), buffer[0]));
			connected = true;

			try {
				Thread.sleep(50);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		while (found) {
			lidar.read(0x61, 3, buffer);

			int rmm = (buffer[0] << 8) + buffer[1];
			double metres = rmm * 1000.0;

			synchronized (this) {
				range = metres;
			}

			try {
				Thread.sleep(10);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Gets if the lidar is connected.
	 *
	 * @return If the lidar is connected.
	 */
	public boolean isConnected() {
		return connected;
	}

	/**
	 * Gets the currently read range in metres.
	 *
	 * @return The current range in metres.
	 */
	public double getRange() {
		synchronized (this) {
			return range;
		}
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kDisplacement;
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
	}

	@Override
	public double pidGet() {
		return getRange();
	}
}
