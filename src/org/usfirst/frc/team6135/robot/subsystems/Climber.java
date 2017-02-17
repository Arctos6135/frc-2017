package org.usfirst.frc.team6135.robot.subsystems;

import org.usfirst.frc.team6135.robot.RobotMap;
import org.usfirst.frc.team6135.robot.commands.climberOff;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climber extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private final CANTalon Talon = RobotMap.climberTalon;
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new climberOff());
    }
    public void setTalon(double speed)
    {
    	Talon.set(speed);
    }
}

