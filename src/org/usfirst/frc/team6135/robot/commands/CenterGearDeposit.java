package org.usfirst.frc.team6135.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CenterGearDeposit extends CommandGroup {

    public CenterGearDeposit() {
    	addSequential(new driveDistance(55,0.6));
    	addSequential(new driveDistance(14.5,0.3));
    }
}
