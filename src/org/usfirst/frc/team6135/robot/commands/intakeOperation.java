package org.usfirst.frc.team6135.robot.commands;

import org.usfirst.frc.team6135.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command will start the intake
 * Activated by: Operator Joystick, toggled
 * Creater: Carl Yu
 */
public class intakeOperation extends Command {

    private double speed;
	public intakeOperation() {
        requires(Robot.intake);
    }
    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.intake.setOn(true);
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.intake.set(1.0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.intake.setOn(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
