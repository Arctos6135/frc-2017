package org.usfirst.frc.team6135.robot.commands;

import org.usfirst.frc.team6135.robot.subsystems.AutoDrive;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveDistance extends Command {
	private AutoDrive auto;
	private double dist;
    public DriveDistance(AutoDrive a, double d) {
    	requires(a);
    	auto = a;
    	dist = d;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	auto.enable();
    	auto.setStraight();
    	auto.setSetpoint(dist);
    	auto.drive.balance.enable();
    	auto.drive.setStraight();
    	auto.drive.balance.setSetpoint(0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	auto.move();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return auto.onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	auto.disable();
    	auto.drive.balance.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
