package frc.robot.subsystems;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * A subsystem used for managing a Lidar.
 */
public class Lidar extends Subsystem implements PIDSource {
	private I2C m_lidar;
	private boolean m_connected;
	private double m_range;

	private Thread m_thread;

	public Lidar() {
		m_lidar = new I2C(I2C.Port.kOnboard, 0x30);
		m_connected = false;
		m_range = 0.0;

		m_thread = new Thread(this::threadRun);
		m_thread.start();
	}

	@Override
	protected void initDefaultCommand() {
	}

	private void threadRun() {
		byte[] buffer = new byte[3];
		System.out.println("RoboRio-537 lidar init");
		boolean found = false;

		while (!found) {
			m_lidar.write(0x60, 0x01);
			m_lidar.read(0x61, 3, buffer);
			found = ((byte) 0xa1 == buffer[0]);
			System.out.println(String.format("Rangefinder found %s val 0x%2x", (found ? "true" : "false"), buffer[0]));
			m_connected = true;

			try {
				Thread.sleep(50);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		while (found) {
			m_lidar.read(0x61, 3, buffer);

			int rmm = (buffer[0] << 8) + buffer[1];
			double metres = rmm * 1000.0;

			synchronized (this) {
				m_range = metres;
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
		return m_connected;
	}

	/**
	 * Gets the currently read range in metres.
	 *
	 * @return The current range in metres.
	 */
	public double getRange() {
		synchronized (this) {
			return m_range;
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
