package frc.robot.subsystems;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cameraserver.CameraServerSharedStore;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Camera extends Subsystem {
	private UsbCamera m_camera;
	
	public Camera() {
		m_camera = new UsbCamera("USB Camera 0", 0);
	//	m_camera.setResolution(320, 240);
	//	m_camera.setFPS(30);
		CameraServer.getInstance().startAutomaticCapture(m_camera);
   		CameraServerSharedStore.getCameraServerShared().reportUsbCamera(m_camera.getHandle());
	}

	@Override
	protected void initDefaultCommand() {
	}
}
