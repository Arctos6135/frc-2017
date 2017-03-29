package org.usfirst.frc.team6135.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class NearPositionDepositGear extends CommandGroup {

    public NearPositionDepositGear() {
        addSequential(new driveDistance(78.75,0.6));
        addSequential(new RotateAngle(60));
        addSequential(new driveDistance(42,0.6));
    }
}
