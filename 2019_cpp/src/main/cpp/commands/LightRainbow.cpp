#include "commands/LightRainbow.h"

#include <cmath>
#include "Robot.h"

LightRainbow::LightRainbow() :
	m_r(0.0),
	m_g(0.0),
	m_b(0.0)
{
	Requires(&Robot::m_lights);
}

void LightRainbow::Initialize()
{
}

void LightRainbow::Execute()
{
	double h = (1.0 + std::cos(0.6 * TimeSinceInitialized())) / 2.0;
//	Color color = Color.getHSBColor(360.0f * h, 1.0f, 1.0f);
		
//	m_r = color.getRed() / 255.0;
//	m_b = color.getGreen() / 255.0;
//	m_g = color.getBlue() / 255.0;
	Robot::m_lights.SetColour(m_r, m_g, m_b);
}

bool LightRainbow::IsFinished()
{ 
	return false; 
}

void LightRainbow::End()
{
	Robot::m_lights.SetColour(0.0, 0.0, 0.0);
}

void LightRainbow::Interrupted()
{
	End();
}
