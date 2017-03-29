package org.usfirst.frc.team6135.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class operateSI extends CommandGroup {

    public operateSI() {
        addParallel(new operateShooter());
        addParallel(new operateIndexer());
    }
}
