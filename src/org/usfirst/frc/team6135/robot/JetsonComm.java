package org.usfirst.frc.team6135.robot;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITableListener;
/**
This class receives data from network table provided by devices and softwares on the network
Creater: Carl Yu 
*/
public class JetsonComm {
			private NetworkTable nt;
			private ITableListener t;
			private double dis;
			private double straightDis;
			private double targetRPM;
			public JetsonComm()
			{
				nt=NetworkTable.getTable("SmartDashboard");
				nt.addTableListener(this.t);
			}
			public void update()
			{
				dis=nt.getNumber("dis",0.0);
				straightDis=nt.getNumber("straightDis",0.0);
			}
			public double getDis()
			{
				return dis;
			}
			public double getStraightDis()
			{
				return straightDis;
			}
			public double gettargetRPM()
			{
				return targetRPM;
			}
}
