#pragma once

#include <string>
#include <frc/commands/Command.h>

class LightColour :
	public frc::Command
{
private:
	double m_r, m_g, m_b;
public:
	LightColour(const double &r = 1.0f, const double &g = 1.0f, const double &b = 1.0f);

	LightColour(const std::string &hex = "#FFFFFF");

	void Initialize() override;

	void Execute() override;

	bool IsFinished() override;

	void End() override;

	void Interrupted() override;
};
