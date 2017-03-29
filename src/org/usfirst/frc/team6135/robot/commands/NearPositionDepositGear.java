package org.usfirst.frc.team6135.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class NearPositionDepositGear extends CommandGroup {

    public NearPositionDepositGear(boolean n,boolean g,boolean e) {
        addSequential(new driveDistance(78.75,0.6,n,g,e));
        addSequential(new RotateAngle(60));
        addSequential(new driveDistance(42,0.6,n,g,e));
    }
}
