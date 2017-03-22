package org.usfirst.frc.team6135.robot.commands;

import org.usfirst.frc.team6135.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AUTOSecondGear extends CommandGroup {

    public AUTOSecondGear() {
        addSequential(new DriveDistance(Robot.auto,55,1));
        addSequential(new DriveDistance(Robot.auto,14.5,0.5));
    }
}
