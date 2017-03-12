package org.usfirst.frc.team6135.robot.commands.debug;

import org.usfirst.frc.team6135.robot.Robot;
import org.usfirst.frc.team6135.robot.commands.Reverse;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *This command will reverse the control of the robot and then rotate 180 degree
 *Creater: Adrian Carpenter, Carl Yu
 */
public class Flip extends CommandGroup {

    public Flip() {
    	addSequential(new Reverse());
    	//addSequential(new RotateAngle(Robot.auto, 179.9));
    	addSequential(new Flip_tmp());
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}