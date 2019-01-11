#pragma once

#include <memory>
#include <AHRS.h>
#include <frc/commands/Subsystem.h>

class Gyro :
	public frc::Subsystem,
	public frc::PIDSource
{
private:
	std::unique_ptr<AHRS> m_ahrs;
public:
	Gyro();
	
	void InitDefaultCommand() override;
	
	PIDSourceType GetPIDSourceType() const override;

	void SetPIDSourceType(PIDSourceType pidSource) override;

	double PIDGet() override;

	double GetAngle() const;

	void SetAngle(const double &angle);

	void Reset();
};
