package org.usfirst.frc.team6135.robot.commands;

import org.usfirst.frc.team6135.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class climberOperation extends Command {

    public final double STALLING_CURRENT=40;
	public climberOperation() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.climber);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.climber.setOn(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.climber.set(-1.0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.climber.getCurrent()>=STALLING_CURRENT;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.climber.setOn(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
