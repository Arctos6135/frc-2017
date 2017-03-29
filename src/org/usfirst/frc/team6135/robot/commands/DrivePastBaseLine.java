package org.usfirst.frc.team6135.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DrivePastBaseLine extends CommandGroup {

    public DrivePastBaseLine() {
        addSequential(new driveDistance(60,0.6));
    }
}
