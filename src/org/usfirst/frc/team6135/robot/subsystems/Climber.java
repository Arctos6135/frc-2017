package org.usfirst.frc.team6135.robot.subsystems;

import org.usfirst.frc.team6135.robot.Robot;
import org.usfirst.frc.team6135.robot.RobotMap;
import org.usfirst.frc.team6135.robot.commands.climberOff;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Climber extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private final CANTalon talon = RobotMap.climberTalon;
	private boolean ifOn=false;
	private boolean ifWarning;
	private double current=0;
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new climberOff());
    }
    public void set(double speed)
    {
    	talon.set(speed);
    }
    public void setOn(boolean val)
    {
    	ifOn=val;
    }
    public void printToSmartDashboard()
    {
    	current=Robot.pdp.getCurrent(12);
    	SmartDashboard.putNumber("Climber Current reading", current);
    	ifWarning=Robot.pdp.getCurrent(12)>30;
    	SmartDashboard.putBoolean("Climber Warning", ifWarning);
    	SmartDashboard.putBoolean("ClimberOn", ifOn);
    }
    public double getCurrent()
    {
    	return this.current;
    }
}

