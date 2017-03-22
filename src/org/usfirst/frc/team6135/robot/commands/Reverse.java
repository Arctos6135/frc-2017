package org.usfirst.frc.team6135.robot.commands;

import org.usfirst.frc.team6135.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *This command will reverse the control of the robot
 *Creator: Adrian Carpenter
 */
public class Reverse extends Command {

    public Reverse() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.auto);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.auto.reverse();
    	Robot.drive.reverse();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
