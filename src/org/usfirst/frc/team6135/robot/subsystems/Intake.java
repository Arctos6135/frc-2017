// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc.team6135.robot.subsystems;

import org.usfirst.frc.team6135.robot.Robot;
import org.usfirst.frc.team6135.robot.RobotMap;
import org.usfirst.frc.team6135.robot.commands.intakeOff;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 */
public class Intake extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final CANTalon talon;
    private boolean ifOn=false;
    private boolean ifWarning;
    public Intake()
    {
    	talon=RobotMap.intakeTalon;
    	talon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
    }
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS


    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        setDefaultCommand(new intakeOff());

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
    public void set(double speed)
    {
    	talon.set(speed);
    }
    public double getVel()
    {
    	return talon.getEncVelocity();
    }
    public void setOn(boolean val)
    {
    	ifOn=val;
    }
    public void printToSmartDashboard()
    {
    	SmartDashboard.putNumber("Intake Current reading", Robot.pdp.getCurrent(14));
    	ifWarning=Robot.pdp.getCurrent(14) > 20 && (getVel()-0)<=10e2;
    	SmartDashboard.putBoolean("Intake Warning", ifWarning);
    	SmartDashboard.putBoolean("IntakeOn", ifOn);
    	SmartDashboard.putNumber("Intake RPM", getVel());
    }
}

