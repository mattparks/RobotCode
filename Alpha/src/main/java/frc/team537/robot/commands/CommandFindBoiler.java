package frc.team537.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.vision.VisionThread;
import frc.team537.robot.Robot;
import frc.team537.robot.vision.GripBoiler;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

public class CommandFindBoiler extends Command {
	private final Object imgLock = new Object();
	private VisionThread visionThread;
	private int centreX;

	public CommandFindBoiler(double angle) {
		requires(Robot.subsystemDrive);

		visionThread = new VisionThread(Robot.subsystemCamera.getUsbCamera(), new GripBoiler(), pipeline -> {
			if (!pipeline.filterContoursOutput().isEmpty()) {
				Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));

				synchronized (imgLock) {
					centreX = r.x + (r.width / 2);
				}
			}
		});
	}

	@Override
	protected void initialize() {
		visionThread.start();
	}

	@Override
	protected void execute() {
		// centreX is the centre, do something with it.
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		visionThread.stop();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
