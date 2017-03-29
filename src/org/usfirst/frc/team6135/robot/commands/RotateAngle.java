package org.usfirst.frc.team6135.robot.commands;
import org.usfirst.frc.team6135.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class RotateAngle extends Command {
	private static final double diameter = 9;
	private double distance = 0.0;
	private double angle = 0.0;
	private boolean useNAVX = false;
    public RotateAngle(double angle) { //range from -180 to 180
		distance = Math.PI * diameter * angle / 360.0;
		requires(Robot.drive);
		requires(Robot.lEncSubsystem);
		requires(Robot.rEncSubsystem);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }
	public RotateAngle(double angle, boolean n) {
		useNAVX = n;
		requires(Robot.drive);
	}
    // Called just before this Command runs the first time
    protected void initialize() {
		Robot.lEncSubsystem.reset();
		Robot.rEncSubsystem.reset();
		Robot.lEncSubsystem.setSetpoint(distance);
		Robot.rEncSubsystem.setSetpoint(-distance);
		Robot.lEncSubsystem.enable();
		Robot.rEncSubsystem.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		Robot.drive.setMotors(Robot.lEncSubsystem.getOutput(), Robot.rEncSubsystem.getOutput());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.lEncSubsystem.onTarget() && Robot.rEncSubsystem.onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
		Robot.lEncSubsystem.disable();
		Robot.rEncSubsystem.disable();
		Robot.drive.setMotors(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
