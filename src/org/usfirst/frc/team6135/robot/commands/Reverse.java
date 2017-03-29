package org.usfirst.frc.team6135.robot.commands;

import org.usfirst.frc.team6135.robot.Robot;
import org.usfirst.frc.team6135.robot.subsystems.EncSensor;

import edu.wpi.first.wpilibj.command.Command;

public class reverse extends Command {
    public reverse() {
		requires(Robot.lEncSubsystem);
		requires(Robot.rEncSubsystem);
		requires(Robot.drive);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }
    // Called just before this Command runs the first time
    protected void initialize() {
		Robot.lEncSubsystem.reverse();
		Robot.rEncSubsystem.reverse();
		EncSensor temp1 = Robot.lEncSubsystem;
		EncSensor temp2 = Robot.rEncSubsystem;
		Robot.lEncSubsystem = temp2;
		Robot.rEncSubsystem = temp1;
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
