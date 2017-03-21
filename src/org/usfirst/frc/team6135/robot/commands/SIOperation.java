package org.usfirst.frc.team6135.robot.commands;

import org.usfirst.frc.team6135.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
<<<<<<< HEAD
 *This command will run both shooter and indexer at the same time
 * Creater: Carl Yu
=======
 *
>>>>>>> parent of c6097e1... Added Comments Debug
 */
public class SIOperation extends CommandGroup {

    public SIOperation(double s) {
    		addParallel(new shooterOperation(s,Robot.oi.operatorJ));
    		addSequential(new indexOperation(s));
    }
}
