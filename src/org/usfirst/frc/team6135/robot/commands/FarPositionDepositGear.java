package org.usfirst.frc.team6135.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class FarPositionDepositGear extends CommandGroup {

    public FarPositionDepositGear(boolean n,boolean g,boolean e) {
        addSequential(new driveDistance(80,0.6,n,g,e));
        addSequential(new RotateAngle(-60));
        addSequential(new driveDistance(39.25,0.6,n,g,e));
    }
}
