package org.usfirst.frc.team6135.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class FarPositionDepositGear extends CommandGroup {

    public FarPositionDepositGear() {
        addSequential(new driveDistance(80,0.6));
        addSequential(new RotateAngle(-60));
        addSequential(new driveDistance(39.25,0.6));
    }
}
