#include "commands/LightColour.h"

#include "Robot.h"

LightColour::LightColour(const double &r, const double &g, const double &b) :
	m_r(0.0),
	m_g(0.0),
	m_b(0.0)
{
	Requires(&Robot::m_lights);
}

LightColour::LightColour(const std::string &hex) :
	m_r(0.0),
	m_g(0.0),
	m_b(0.0)
{
	Requires(&Robot::m_lights);

	uint32_t r, g, b;
	sscanf(hex.c_str() + (hex[0] == '#' ? 1 : 0), "%2x%2x%2x", &r, &g, &b);
	m_r = static_cast<double>(r) / 255.0;
	m_g = static_cast<double>(g) / 255.0;
	m_b = static_cast<double>(b) / 255.0;
}

void LightColour::Initialize()
{
}

void LightColour::Execute()
{
	Robot::m_lights.SetColour(m_r, m_g, m_b);
}

bool LightColour::IsFinished()
{ 
	return false; 
}

void LightColour::End()
{
	Robot::m_lights.SetColour(0.0, 0.0, 0.0);
}

void LightColour::Interrupted()
{
	End();
}
