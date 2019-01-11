#pragma once

#include <memory>
#include <string>

#include <frc/TimedRobot.h>
#include <frc/commands/Command.h>
#include <frc/smartdashboard/SendableChooser.h>

#include "OI.h"
#include "subsystems/Gyro.h"
#include "subsystems/Lights.h"

class Robot :
	public frc::TimedRobot
{
private:
	frc::SendableChooser<std::string> m_chooser;
	std::unique_ptr<Command> m_command;
public:
	static Lights m_lights;
	static Gyro m_gyro;
	static OI m_oi;

	void RobotInit() override;

	void RobotPeriodic() override;
	
	void DisabledInit() override;

	void DisabledPeriodic() override;

	void AutonomousInit() override;

	void AutonomousPeriodic() override;

	void TeleopInit() override;

	void TeleopPeriodic() override;

	void TestPeriodic() override;
};
