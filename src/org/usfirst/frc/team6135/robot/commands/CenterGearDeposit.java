package org.usfirst.frc.team6135.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CenterGearDeposit extends CommandGroup {

    public CenterGearDeposit(boolean n,boolean g, boolean e) {
    	addSequential(new driveDistance(55,0.6,n,g,e));
    	addSequential(new driveDistance(14.5,0.3,n,g,e));
    }
}
