#pragma once

#include <frc/commands/Command.h>

class LightRainbow :
	public frc::Command
{
private:
	double m_r, m_g, m_b;
public:
	LightRainbow();

	void Initialize() override;

	void Execute() override;

	bool IsFinished() override;

	void End() override;

	void Interrupted() override;
};
