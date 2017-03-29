package org.usfirst.frc.team6135.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Flip extends CommandGroup {
	public Flip() {
		addSequential(new reverse());
	}
}
