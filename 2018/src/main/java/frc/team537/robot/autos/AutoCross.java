package frc.team537.robot.autos;

import frc.team537.robot.commands.*;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoCross extends CommandGroup {
	public AutoCross(int location, boolean isSwitchLeft, double delay, boolean reversed) {
		addSequential(new CommandGyroReset(reversed ? 180.0 : 0.0));
		addSequential(new CommandNothing(delay));
		
		switch(location) {
			case 1: // Left.
				addSequential(new CommandDriveRate(0.0, 0.35, 6.0));
				break;
			case 2: // Centre.
				addSequential(new CommandDriveRate(0.0, 0.8, 1.5));
				addSequential(new CommandDriveRate(isSwitchLeft ? 90.0 : 270.0, 0.7, 2.3));
				addSequential(new CommandDriveRate(0.0, 0.8, 2.0));
				break;
			case 3: // Right.
				addSequential(new CommandDriveRate(0.0, 0.35, 6.0));
				break;
			default:
				DriverStation.reportError("Invalid Cross Auto location: " + location, false);
				break;
		}
		
	//	addSequential(new CommandDriveRotate(0.0));
		addParallel(new CommandLiftDeploy());		
	//	addSequential(new CommandLedsColour(new Colour("#0000ff"), 10.0));
	}
}
