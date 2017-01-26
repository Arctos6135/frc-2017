
package org.usfirst.frc.team6135.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team6135.robot.commands.ExampleCommand;
import org.usfirst.frc.team6135.robot.subsystems.ExampleSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static OI oi;
	Encoder leftEnc=new Encoder(0,1,true,Encoder.EncodingType.k2X);
	Encoder rightEnc=new Encoder(2,3,false,Encoder.EncodingType.k2X);
	Joystick j=new Joystick(0);
	Drive drive = new Drive(j, RobotMap.lVicPort, RobotMap.rVicPort);
	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();
	Spark shooterMoter=new Spark(9);
	PowerDistributionPanel pdp = new PowerDistributionPanel();
	double sliderVal;
	int counter;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	
	@Override
	public void robotInit() {
		oi = new OI();
		chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", chooser);
		rightEnc.setDistancePerPulse(6*3.14/48);
		leftEnc.setDistancePerPulse(6*3.14/48);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		
		counter = 0; //will ++ per method loop iteration; per packet sent; per ~20ms
		if(counter < 100) { //~2s
			drive.setMotors(1.0,1.0); //both forward
		}
		else if (counter<200) { //~4s
			drive.setMotors(1.0, 0.0); //left fwd
		}
		else if(counter<300) { //~6s
			drive.setMotors(0.0, 1.0); //right fwd
		}
		else {
			drive.setMotors(0.0, 0.0); //stop
		}
		counter++;
		
		printValuesOnDashboard(); //updates values on SmartDashboard
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run(); //scheduler singleton instance currently empty; used for command-based
		drive.teleopDrive(); //refer to Drive.java teleopDrive method
		drive.setMotors(-0.5, 0.5); //placeholder, teleop instructions will be put here
		
		sliderVal = j.getRawAxis(3); //gets value from slider
		shooterMoter.set(Math.max(0.0,sliderVal)); //Math.max to prevent input of negative values
		
		System.out.println(sliderVal); //prints to console on driver station; debugging/testing purposes
		printValuesOnDashboard(); //updates values on SmartDashboard
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	
	public void printValuesOnDashboard() {
		//putNumber prints respective values to the SmartDashboard
		SmartDashboard.putNumber("shooterVal", sliderVal);
				
		SmartDashboard.putNumber("Left Encoder: ",leftEnc.getRate()/48);
		SmartDashboard.putNumber("Right Encoder: ", rightEnc.getRate()/48);
		//prints current drawn from all PDP ports 0-15
		for (int i = 0; i <= 15; i++) {
			SmartDashboard.putNumber("Port " + i + " Current", pdp.getCurrent(i));
		}
				
		SmartDashboard.putNumber("Current: ", pdp.getTotalCurrent());
		SmartDashboard.putNumber("Voltage: ", pdp.getVoltage());
		SmartDashboard.putNumber("Power: ", pdp.getTotalPower());
		SmartDashboard.putNumber("Energy: ", pdp.getTotalEnergy());
		SmartDashboard.putNumber("Temperature: ", pdp.getTemperature());		
	}
}
