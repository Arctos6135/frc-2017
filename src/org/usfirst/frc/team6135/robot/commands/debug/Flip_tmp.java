package org.usfirst.frc.team6135.robot.commands.debug;

import org.usfirst.frc.team6135.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *This command attempts to turn the robot 180 degree
 *Creator: Carl Yu
 */
public class Flip_tmp extends Command {

    public static final double ROTATE_DIS=(45.0/12)/2*3.14;
	public Flip_tmp() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.auto);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.auto.drive.setMotors(0.3, -0.3);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Robot.auto.getDisR()<=ROTATE_DIS;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
