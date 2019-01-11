#include "subsystems/Lights.h"

#include "RobotMap.h"
#include "commands/LightRainbow.h"

Lights::Lights() :
	frc::Subsystem("Lights"),
	m_canifier(std::make_unique<CANifier>(0))
{
}

void Lights::InitDefaultCommand()
{
	SetDefaultCommand(new LightRainbow());
}

void Lights::SetColour(const double &r, const double &g, const double &b)
{
	m_canifier->SetLEDOutput(kLedBrightness * r, CANifier::LEDChannel::LEDChannelA);
	m_canifier->SetLEDOutput(kLedBrightness * g, CANifier::LEDChannel::LEDChannelB);
	m_canifier->SetLEDOutput(kLedBrightness * b, CANifier::LEDChannel::LEDChannelC);
}
