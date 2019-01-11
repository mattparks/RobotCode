/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.												*/
/* Open Source Software - may be modified and shared by FRC teams. The code	 */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.																															 */
/*----------------------------------------------------------------------------*/

#include "Robot.h"

#include <frc/commands/Scheduler.h>
#include <frc/smartdashboard/SmartDashboard.h>

Gyro Robot::m_gyro = Gyro();
Lights Robot::m_lights = Lights();
OI Robot::m_oi = OI();

void Robot::RobotInit()
{
	m_chooser.SetDefaultOption("None", "");
	m_chooser.AddOption("Left Hatch 1", "LeftHatch1.xml");
	m_chooser.AddOption("Left Hatch 2", "LeftHatch2.xml");
	m_chooser.AddOption("Left Hatch 3", "LeftHatch3.xml");
	frc::SmartDashboard::PutData("Auto Path", &m_chooser);
}

void Robot::RobotPeriodic()
{
}

void Robot::DisabledInit()
{
}

void Robot::DisabledPeriodic()
{ 
	frc::Scheduler::GetInstance()->Run(); 
}

void Robot::AutonomousInit()
{
	std::string filename = m_chooser.GetSelected();

	if (!filename.empty())
	{
	//	m_command = std::make_unique<DrivePath>(filename);
		m_command->Start();
	}
}

void Robot::AutonomousPeriodic()
{ 
	frc::Scheduler::GetInstance()->Run(); 
}

void Robot::TeleopInit()
{
	// This makes sure that the autonomous stops running when teleop starts running. 
	if (m_command != nullptr)
	{
		m_command->Cancel();
		m_command = nullptr;
	}
}

void Robot::TeleopPeriodic()
{ 
	frc::Scheduler::GetInstance()->Run(); 
}

void Robot::TestPeriodic()
{
}

#ifndef RUNNING_FRC_TESTS
int main() { return frc::StartRobot<Robot>(); }
#endif
