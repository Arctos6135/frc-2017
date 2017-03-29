package org.usfirst.frc.team6135.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DrivePastBaseLine extends CommandGroup {

    public DrivePastBaseLine(boolean n,boolean g,boolean e) {
        addSequential(new driveDistance(60,0.6,n,g,e));
    }
}
