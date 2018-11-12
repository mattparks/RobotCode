package frc.team537.robot.autos;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team537.robot.commands.CommandDriveRate;
import frc.team537.robot.commands.CommandGyroReset;
import frc.team537.robot.commands.CommandLiftDeploy;
import frc.team537.robot.commands.CommandNothing;

public class AutoSwitch extends CommandGroup {
	public AutoSwitch(int location, boolean isSwitchLeft, double delay, boolean reversed) {
		addSequential(new CommandGyroReset(reversed ? 180.0 : 0.0));
		addSequential(new CommandNothing(delay));
		
		switch(location) {
			case 1: // Left.
				if (!isSwitchLeft) {
					addSequential(new CommandDriveRate(0.0, 0.4, 2.0));
					addSequential(new CommandDriveRate(90.0, 0.6, 2.7));
				} else {
					addSequential(new CommandDriveRate(90.0, 0.6, 2.5));
				}

			//	addSequential(new CommandDriveRotate(180.0));
				addSequential(new CommandDriveRate(0.0, 0.75, 2.3));
				break;
			case 2: // Centre.
				addSequential(new CommandDriveRate(0.0, 0.3, 0.9));
				addSequential(new CommandDriveRate(isSwitchLeft ? 270.0 : 90.0, 0.4, 2.25));
				
			//	addSequential(new CommandDriveRotate(180.0));
				addSequential(new CommandDriveRate(0.0, 0.75, 2.0));
				break;
			case 3: // Right.
				if (isSwitchLeft) {
					addSequential(new CommandDriveRate(0.0, 0.4, 2.0));
					addSequential(new CommandDriveRate(270.0, 0.6, 2.7));
				} else {
					addSequential(new CommandDriveRate(270.0, 0.6, 2.5));
				}

			//	addSequential(new CommandDriveRotate(180.0));
				addSequential(new CommandDriveRate(0.0, 0.75, 2.3));
				break;
			default:
				DriverStation.reportError("Invalid Cross Auto location: " + location, false);
				break;
		}

		addParallel(new CommandLiftDeploy());
	//	addSequential(new CommandLedsColour(new Colour("#0000ff"), 10.0));
	}
}
