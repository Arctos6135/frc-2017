package org.usfirst.frc.team6135.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class stopAllSubsystem extends CommandGroup {

    public stopAllSubsystem() {
        addParallel(new stopClimber());
        //addParallel(new stopDriveTrain());
        addParallel(new stopIndexer());
        addParallel(new stopIntake());
        addParallel(new stopShooter());
        
    }
}
