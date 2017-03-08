package org.usfirst.frc.team6135.robot.commands;

import org.usfirst.frc.team6135.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SIOperation extends CommandGroup {

    public SIOperation(double s) {
    		addParallel(new shooterOperation(s,Robot.oi.operatorJ));
    		addSequential(new indexOperation(s));
    }
}
