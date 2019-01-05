package frc.team537.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * A command that just sits and until a timer is complete.
 */
public class CommandNothing extends Command {
	private double timeout;
	private Timer timer;

	/**
	 * Creates a command that does nothing.
	 *
	 * @param timeout The length this command will do nothing.
	 */
	public CommandNothing(double timeout) {
		this.timeout = timeout;
		this.timer = new Timer();
	}

	@Override
	protected void initialize() {
		timer.reset();
		timer.start();
	}

	@Override
	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return timer.get() > timeout;
	}

	@Override
	protected void end() {
		timer.stop();
	}

	@Override
	protected void interrupted() {
		end();
	}
}
