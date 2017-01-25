
package org.usfirst.frc.team6135.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
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
	int counter=0;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	PowerDistributionPanel pdp = new PowerDistributionPanel();//STEPHEN WROTE THIS------------------------
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
		if(counter<134)
		{
			drive.setMotors(1.0,1.0);
		}
		else
		{
			if(counter<268)
			{
				drive.setLeft(1.0);
				drive.setRight(0);
			}
			else
			{
				if(counter<402)
				{
					drive.setRight(1.0);
					drive.setLeft(0);
				}
			}
		}
		counter++;
		SmartDashboard.putNumber("Left Encoder: ",(leftEnc.getRate()/2));
		SmartDashboard.putNumber("Right Encoder: ", -(rightEnc.getRate()/96));
		
		SmartDashboard.putNumber("Current: ", pdp.getTotalCurrent());
		SmartDashboard.putNumber("Voltage: ", pdp.getVoltage());
		SmartDashboard.putNumber("Power: ", pdp.getTotalPower());
		SmartDashboard.putNumber("Energy: ", pdp.getTotalEnergy());
		SmartDashboard.putNumber("Temperature: ", pdp.getTemperature());
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
		Scheduler.getInstance().run();
		//drive.teleopDrive();
		//drive.setMotors(1.0,-1.0);
		drive.setRight(1.0);
		SmartDashboard.putNumber("Left Encoder: ",leftEnc.getRate()/48);
		SmartDashboard.putNumber("Right Encoder: ", rightEnc.getRate()/48);
		
		System.out.println(leftEnc.getRate()/48); //48 pulses
		System.out.println(rightEnc.getRate()/48); //48 pulses
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
