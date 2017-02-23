package org.usfirst.frc.team6135.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team6135.robot.Robot;
import org.usfirst.frc.team6135.robot.subsystems.AutoDrive;
/**
 *
 */
public class RotateAngle extends Command {
	private double angle;
	private AutoDrive auto;
    public RotateAngle(AutoDrive a, double theta) {
    	requires(a);
    	angle = theta;
    	auto = a;
    	// Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	auto.drive.balance.enable();
    	auto.drive.setRotate();
    	auto.drive.balance.setSetpoint(angle);
    	auto.turn();
    	/*if(angle > 0) {
    		auto.turnRight();
    	}
    	else {
    		auto.turnLeft();
    	}*/
    	auto.enable();
    	auto.setRotate();
    	auto.setSetpoint(1);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	/*if(angle > 0) {
    		auto.turnRight();
    	}
    	else {
    		auto.turnLeft();
    	}*/
    	auto.turn();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return auto.drive.balance.onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	auto.disable();
    	auto.drive.balance.disable();
    	auto.drive.setMotors(0, 0);
    	auto.drive.setStraight();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
