#include "subsystems/Gyro.h"

#include "RobotMap.h"

Gyro::Gyro() :
	frc::Subsystem("Gyro"),
	m_ahrs(std::make_unique<AHRS>(SPI::Port::kMXP))
{
	m_ahrs->Reset();
}

void Gyro::InitDefaultCommand()
{
}
	
PIDSourceType Gyro::GetPIDSourceType() const
{
	return m_ahrs->GetPIDSourceType();
}

void SetPIDSourceType(PIDSourceType pidSource)
{
	m_ahrs->SetPIDSourceType(pidSource);
}

double Gyro::PIDGet()
{
	return GetAngle();
}

double GetAngle() const
{
	return m_ahrs->GetAngle();
}

void SetAngle(const double &angle)
{
	m_ahrs->SetAngleAdjustment(angle);
}

void Reset()
{
	m_ahrs->Reset();
}
