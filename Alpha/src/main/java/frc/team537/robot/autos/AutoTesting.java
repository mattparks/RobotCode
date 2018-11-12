package frc.team537.robot.autos;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team537.robot.commands.CommandDriveDistance;
import frc.team537.robot.commands.CommandDriveRotate;

public class AutoTesting extends CommandGroup {
	public AutoTesting() {
		addSequential(new CommandDriveRotate(180.0f));
		addSequential(new CommandDriveDistance(90.0f, 0.0f));
	}
}
