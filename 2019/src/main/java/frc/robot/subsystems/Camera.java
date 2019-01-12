package frc.robot.subsystems;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cameraserver.CameraServerSharedStore;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class Camera extends Subsystem implements Sendable {
	private UsbCamera m_camera;
	private double m_brightness;
	private double m_whiteBalance;
	private double m_exposure;
	
	public Camera() {
		m_camera = new UsbCamera("USB Camera 0", 0);
	//	m_camera.setResolution(320, 240);
	//	m_camera.setFPS(30);
		CameraServer.getInstance().startAutomaticCapture(m_camera);
		CameraServerSharedStore.getCameraServerShared().reportUsbCamera(m_camera.getHandle());
		   
		setName("USB Camera 0", 0);
		m_brightness = 0.4;
		m_whiteBalance = 0.8;
		m_exposure = 0.5;
		setBrightness(m_brightness);
		setWhiteBalance(m_whiteBalance);
		setExposure(m_exposure);
	}

	@Override
	protected void initDefaultCommand() {
	}
	
	/**
	 * Get the brightness, as a value (0-1.0).
	 */
	public double getBrightness() {
		return m_brightness;
	}

	/**
	 * Set the brightness, as a value (0-1.0).
	 */
	public void setBrightness(double brightness) {
		m_brightness = brightness;
		m_camera.setBrightness((int)(brightness * 100.0));
	}

	/**
	 * Get the white balance, as a value (0-1.0).
	 */
	public double getWhiteBalance() {
		return m_whiteBalance;
	}

	/**
	 * Set the white balance, as a value (0-1.0).
	 */
	public void setWhiteBalance(double whiteBalance) {
		m_whiteBalance = whiteBalance;
		m_camera.setWhiteBalanceManual((int)(whiteBalance * 100.0));
	}

	/**
	 * Get the manual exposure, as a value (0-1.0).
	 */
	public double getExposure() {
		return m_exposure;
	}

	/**
	 * Set the manual exposure, as a value (0-1.0).
	 */
	public void setExposure(double exposure) {
		m_brightness = exposure;
		m_camera.setExposureManual((int)(exposure * 100.0));
	}

	@Override
	public void initSendable(SendableBuilder builder) {
		builder.setSmartDashboardType("Camera");
		builder.addDoubleProperty("Brightness", this::getBrightness, this::setBrightness);
		builder.addDoubleProperty("White Balance", this::getWhiteBalance, this::setWhiteBalance);
		builder.addDoubleProperty("Exposure", this::getExposure, this::setExposure);
	}
}
