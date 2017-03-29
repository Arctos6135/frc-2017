package org.usfirst.frc.team6135.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class NearCollectBall extends CommandGroup {

    public NearCollectBall() {
    	addSequential(new driveDistance(222.25,0.6));
    	addSequential(new RotateAngle(-150));
    	addParallel(new operateIntake());
    	addSequential(new driveDistance(127.5,0.6));
    	addSequential(new driveDistance(31.25,0.6));
    }
}
