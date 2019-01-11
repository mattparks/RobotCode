#pragma once

#include <memory>
#include <frc/commands/Subsystem.h>
#include <ctre/phoenix/CANifier.h>

class Lights :
	public frc::Subsystem
{
private:
	using CANifier = ctre::phoenix::CANifier;
	std::unique_ptr<CANifier> m_canifier;
public:
	Lights();
	
	void InitDefaultCommand() override;

	void SetColour(const double &r, const double &g, const double &b);
};
