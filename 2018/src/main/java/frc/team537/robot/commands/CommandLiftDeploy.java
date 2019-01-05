package frc.team537.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.team537.robot.Robot;

public class CommandLiftDeploy extends Command {
	private Timer timer;
	private int state;
	
	public CommandLiftDeploy() {
		requires(Robot.subsystemLift);
		this.timer = new Timer();
		this.state = 0;
	}

	@Override
	protected void initialize() {
		Robot.subsystemLift.reset();
		timer.reset();
		timer.start();
		state = 0;
	}

	@Override
	protected void execute() {
		switch (state) {
		case 0:
			if (timer.get() > 0.3) { // 1.3
				timer.reset();
				timer.start();
				state = 1;
			}
			
			Robot.subsystemLift.setSpeed(-0.4);
			break;
		case 1:
			if (timer.get() > 0.2) {
				state = 2;
			}
			
			Robot.subsystemLift.setSpeed(0.4);
			break;
		case 2:
			//if (!Robot.subsystemLift.hasFoundZero()) {
			//	Robot.subsystemLift.setSpeed(0.3);
			//} else {
				state = 3;
			//}
			break;
		case 3:
			Robot.subsystemLift.setSpeed(0.0);
			break;
		}
	}
	
	@Override
	protected boolean isFinished() {
		return state == 3;
	}

	@Override
	protected void end() {
		Robot.subsystemLift.stop();
		timer.stop();
		state = 0;
	}

	@Override
	protected void interrupted() {
		end();
	}
}
