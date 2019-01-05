package frc.team537.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.vision.VisionThread;
import frc.team537.robot.Robot;
import frc.team537.robot.subsystems.SwerveModule;
import frc.team537.robot.vision.GripBoiler;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

public class CommandFindBoiler extends Command {
	private final Object imgLock = new Object();
	private VisionThread visionThread;
	private double rate;

	public CommandFindBoiler() {
		requires(Robot.subsystemDrive);

		visionThread = new VisionThread(Robot.subsystemCamera.getUsbCamera(), new GripBoiler(), pipeline -> {
			if (!pipeline.filterContoursOutput().isEmpty()) {
				Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));

				synchronized (imgLock) {
					double halfWidth = (double) r.width / 2.0;
					rate = ((double) r.x - halfWidth) / halfWidth;
				}
			} else {
				rate = 1.0;
			}
		});
	}

	@Override
	protected void initialize() {
		Robot.subsystemDrive.reset();
		Robot.subsystemDrive.setMode(SwerveModule.SwerveMode.ModeSpeed);
		visionThread.start();
	}

	@Override
	protected void execute() {
		Robot.subsystemDrive.setTarget(0.0f, 0.5 * Math.pow(rate, 2.5), 0.0f, 0.0f);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.subsystemDrive.stop();
		visionThread.stop();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
