package org.usfirst.frc.team6135.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class EncSensor extends PIDSubsystem {
	private double value;
	private boolean reversed = false;
	private Encoder enc = null;
	private static final double kP = 0.03;
	private static final double kI = 0.02;
	private static final double kD = 0.01;
	private static final double kToleranceDegrees = 2.0f;
	public EncSensor(Encoder e, boolean r) {
		super(kP, kI, kD);
		enc = e;
		reversed = r;
		enc.setReverseDirection(reversed);
    	super.setAbsoluteTolerance(kToleranceDegrees);
    	super.setOutputRange(-1, 1);
	}
	public double getOutput() {
		return value;
	}
	public void setSpeed(double speed) {
		getPIDController().setPID(speed * kP, speed * kI, speed * kD);
	}
	public void reset() {
		enc.reset();
	}
	public void reverse() {
		enc.setReverseDirection(!reversed);
		reversed = !reversed;
	}
	protected double returnPIDInput() {
		return enc.getDistance();
	}
	protected void usePIDOutput(double output) {
		value = output;
    }
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	public void outPutDis(String s)
	{
		System.out.println(s + " EncSensorDis "+enc.getDistance());
	}
	public void outPutVel(String s)
	{
		System.out.println(s + " EncSensorDis "+enc.getRate());
	}
}
