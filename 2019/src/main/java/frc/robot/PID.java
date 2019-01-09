package frc.robot;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SendableBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * Represents a sendable PID, this object can be added to a smartdashboard or a switchboard and have values changed live.
 * Useful for tuning PIDs used in motor control, values on the switchboard are erased on deploy/reboot.
 */
public class PID extends SendableBase implements Sendable {
	private static int INSTANCES;
	private double p, i, d, f;

	/**
	 * Creates a new PID.
	 *
	 * @param p The p, proportional value.
	 * @param i The i, integral value.
	 * @param d The d, derivative value.
	 * @param f The f, value.
	 * @param name The name.
	 */
	public PID(double p, double i, double d, double f, String name) {
		this.p = p;
		this.i = i;
		this.d = d;
		this.f = f;

		INSTANCES++;
		setName(name, INSTANCES);
	}

	/**
	 * Creates a new PID.
	 *
	 * @param p The p, proportional value.
	 * @param i The i, integral value.
	 * @param d The d, derivative value.
	 * @param f The f, value.
	 */
	public PID(double p, double i, double d, double f) {
		this(p, i, d, 0.0, "PID");
	}

	/**
	 * Creates a new PID.
	 *
	 * @param p The p, proportional value.
	 * @param i The i, integral value.
	 * @param d The d, derivative value.
	 * @param name The name.
	 */
	public PID(double p, double i, double d, String name) {
		this(p, i, d, 0.0, name);
	}

	/**
	 * Creates a new PID.
	 *
	 * @param p The p, proportional value.
	 * @param i The i, integral value.
	 * @param d The d, derivative value.
	 */
	public PID(double p, double i, double d) {
		this(p, i, d, 0.0);
	}

	public double getP() {
		return p;
	}

	public void setP(double p) {
		this.p = p;
	}

	public double getI() {
		return i;
	}

	public void setI(double i) {
		this.i = i;
	}

	public double getD() {
		return d;
	}

	public void setD(double d) {
		this.d = d;
	}

	public double getF() {
		return f;
	}

	public void setF(double f) {
		this.f = f;
	}

	@Override
	public void initSendable(SendableBuilder builder) {
		builder.setSmartDashboardType("PIDController");
		builder.addDoubleProperty("p", this::getP, this::setP);
		builder.addDoubleProperty("i", this::getI, this::setI);
		builder.addDoubleProperty("d", this::getD, this::setD);
		builder.addDoubleProperty("f", this::getF, this::setF);
	//	builder.addDoubleProperty("setpoint", this::getSetpoint, this::setSetpoint);
	//	builder.addBooleanProperty("enabled", this::isEnabled, this::setEnabled);
	}
}
